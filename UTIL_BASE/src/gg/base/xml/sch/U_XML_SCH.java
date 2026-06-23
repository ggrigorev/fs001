package gg.base.xml.sch;

import static gg.base.java.U_Java.*;
import static gg.base.util.L_Base.INSTANCE_PREFIX;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Classes.*;
import static gg.base.util.U_Constructors.*;
import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Files.*;
import static gg.base.util.U_Print.*;

//import static gg.base.util.U_Properties.getProperty_name;

//import static gg.base.xml.U_XML_ELEMENTS.*;
//import static gg.base.xml.U_XML_ATTRIBUTES.*;
import static gg.base.xml.L_XML.*;
import static gg.base.xml.U_XML.*;
import static gg.base.xml.U_XML_IO.*;
import static gg.base.xml.sch.L_XML_DTD.*;
import static gg.vivado.util.L_Vivado.*;

import java.io.File;
import java.lang.reflect.*;

import java.util.*;

import org.jdom2.Attribute;
import org.jdom2.Element;

import gg.base.text.*;

public interface U_XML_SCH {
	
	boolean DEBUG_XML_SCH = false;
	
	public static <T> List<T> newList(Class<T> cls) {
	    return new ArrayList<>();
	}
	
	String[] packageName = new String[1]; 

	static String getTypeName(String kind) { return packageName[0] + "."+ kind; }
	
	static Class<?> getType(String kind) { 
		String typeName = getTypeName(kind);
		Class<?> type = getClassByName(typeName);
		assertion(type != null, "Cannnot find class <" + typeName + ">");
		return type; 
	}

	Stack<Object> stack = new Stack<>();
	static int level() { return stack.size(); }
	static String tap() { return getTap(stack.size()); }

//	/*
//	 * multi-file parser wrapper
//	 */
//	static TMap<Schema> xml2sch(File schemaDir, String packgePath, Collection<String> names) {
//		TMap<Schema> schemaObjects = new TMap<>();
//		xml2sch(schemaDir, packgePath, schemaObjects, names);
//		return schemaObjects;
//	}

	/*
	 * multi-file parser
	 * each file has only one Element - schema object
	 *  File		   Element		reference 
	 * 	board		 - board
	 * 	part0_pins	 - part_info	board.components.component.pin_map_file
	 * 	preset		 - ip_presets	board.preset_file
	 */
	static void xml2sch(File schemaDir, String packgePath, TMap<Element> schemaElements, TMap<Schema> schemaObjects, Collection<String> names) {		
		for (String schemaName : names) {
			File schemaFile = checkFile(new File(schemaDir, schemaName + XML_EXT));
			Element eSchema = xmlFromFile(schemaFile);
			//xmlPrint(e);
			schemaElements.put(schemaName, eSchema);
			Schema schema = xml2sch(eSchema, packgePath, schemaName);
			schemaObjects.put(schemaName, schema);
		}			
	}

	/*
	 * Element - Schema hierarchical object
	 */
	static Schema xml2sch(Element eSchema, String... packgePath) {
		Text p = new Text(packgePath);
		packageName[0] = p.join(".", false);
	prn_("packageName[0] " + packageName[0]);
		Object topObject = createObject(eSchema);
		assertion(topObject != null, "Unexpected null");
		assertion(topObject instanceof Schema, "Unexpected object of " + topObject.getClass());
		return (Schema) topObject;
	}
	
	static Object addArrayItem(Element eItem, Field itemField, String arrayName, String arrayElementKind, Collection array) {
		Type gt = (ParameterizedType) itemField.getGenericType();
		ParameterizedType pt = (ParameterizedType) gt;
		Class<?> itemType = (Class<?>) pt.getActualTypeArguments()[0];
		String itemKind = itemType.getSimpleName();
//	prn_(tap() + "child " + childTitle + " -> field of " + fieldType.getSimpleName() + "<" + itemType.getSimpleName() + ">, or " + childKind +  "<" + itemKind + ">");
		assertion(arrayElementKind.equals(itemKind), "Invald  collection ITEM TYPE ");
//prn_(); xmlPrint(eItem);
		Object item = createObject(eItem);
		array.add(item);
		if (DEBUG_XML_SCH) prn_(tap() + TAP + "item[" + array.size() + "]");
		return item;
	}
	
	static Object createObject(Element eObject) {

		String objectTitle = eObject.getName();
		
		if (SCHEMA_DTD_SPECIAL_FIELDS.contains(objectTitle)) objectTitle = INSTANCE_PREFIX + objectTitle;

		String objectKind = getJavaClassName(objectTitle); // default separator

		SMap primitiveMap = getAttributeMap(eObject);
		ArrayList<Element> eChildren = new ArrayList<>(eObject.getChildren());
		if (DEBUG_XML_SCH)  {		
//if (objectTitle.indexOf("interface") >= 0)  {			
			prn_("\n" + tap() + " Create new instance '" + objectTitle + "', type of <" + getTypeName(objectKind) + ">");
			prn_("   primitives", primitiveMap);
			prn_("   children", getElementNames(eChildren));
			brk();
		}
		Class<?> objectType = getType(objectKind);
		Object object = newInstance(objectType);
		if (object instanceof Schema) {
			Schema schema = (Schema) object;
			schema.title = eObject.getName(); // TITLE is ignored field name
		}
		setPrimitives(object, primitiveMap);
		stack.push(object);

		for (Element eChild : eChildren) { 
			String childTitle = eChild.getName();
			if (SCHEMA_DTD_SPECIAL_FIELDS.contains(childTitle)) childTitle = INSTANCE_PREFIX + childTitle;

			String childKind = getJavaClassName(childTitle);
			Field childField = getField(objectType, childTitle);// parent.fields.get(child.title);
			assertion((childField != null), "Unexpected NULL field " + childTitle + " in parent <" + objectKind + ">");
			Class<?> fieldType = childField.getType();
			Object fieldValue = getFieldValue(childField, object);
			if (DEBUG_XML_SCH) prn_("\n" + tap() + "child " + childTitle + " -> field of <" + childKind + ">");
			if (Collection.class.isAssignableFrom(fieldType)) {
				assertion(fieldValue != null, "Invald NULL collection field");
				Object item = addArrayItem(eChild, childField, childTitle, childKind, (Collection) fieldValue);
			} else {
				assertion(fieldValue == null, "Invald NOT NULL object filed");
//xmlPrint(eChild);
				Object child = createObject(eChild);
				assertion(child != null, "Invald NULL child object new instance");
				assertion(child instanceof Schema, "Invald NOT SCHEMA child object new instance");
				setFieldValue(object, child, childField);
				if (DEBUG_XML_SCH) prn_(tap() + "SCHEMA child " + childTitle + " -> field of " + childKind +  " ASSIGNED '" + child + "'");
				//brk();
			}
		}

//brk();
//		setObjectProperties(packageName, object, level, eObject);
		return stack.pop();
	}

	static void setPrimitives(Object object, SMap primitiveMap) { //Element eObject) {
		// simplex
		for (String primitiveName : primitiveMap.keySet()) { //) {
			Field primitiveField = getField(object, primitiveName);
			assertion(primitiveField != null, "Cannnot find field '" + primitiveField + "' in class '" + object.getClass().getSimpleName() + "'");
			String primitiveArgument = primitiveMap.get(primitiveName);
			if (DEBUG_XML_SCH) prn_(tap() +"U_Schema.parseSimplexFields: primitive attribute '" + primitiveName + "' = '" + primitiveArgument + "'");
			Class<?> primitiveClass = primitiveField.getType();
			Object primitiveValue = newInstance(primitiveClass, new Object[]{primitiveArgument});
			if (DEBUG_XML_SCH) prn_(tap() +"U_Schema.parseSimplexFields: primitive oblect of <" + primitiveClass.getSimpleName() + "> = '" + primitiveValue + "'");
			setFieldValue(object, primitiveValue, primitiveField);
		}
	}	

}

//ArrayList<Element> eItems = new ArrayList<>(eChild.getChildren());
//prn_(tap() + TAP + "items", getElementNames(eItems));
//int i = 0;
//for (Element eItem : eItems) {
//	Object item = createObject(eItem);
//	array.add(item);
//	prn_(tap() + TAP + "item[" + (i++) + "]"); //brk();
//}
//prn_(tap() + TAP + "array " + array.size()); 
//brk();

//prn_(getTap(level) +"U_Schema.parseObject: objectTitle '" + objectTitle + "'");
