package gg.base.xml.sch;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import gg.base.text.*;

import static gg.base.util.U_Text.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Files.*;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Classes.*;
import static gg.base.util.U_Constructors.*;
import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Properties.*;

import static gg.base.java.U_Java.*;

import static gg.base.xml.L_XML.*;
import static gg.base.xml.U_XML_IO.*;

import static gg.base.xml.sch.U_SCH_XML.*;

import org.jdom2.Element;
import org.jdom2.Attribute;

import static gg.base.xml.sch.L_XML_DTD.*;

//import static gg.base.xml.sch.U_XML_Parser.*;
import static gg.base.xml.sch.U_XML_SCH.*;
import static gg.vivado.util.L_Vivado.*;
import static gg.vivado.util.U_Vivado.*;

//import static gg.base.xml.sch.U_XML_DTD.*;

public interface U_SCH_XML {

	boolean DEBUG_SCH_XML = false;	

	/*
	 * hierarchical top output
	 */
	static Element sch2xml(Schema schemaObject) { return sch2xml(null, schemaObject); }

	/*
	 * hierarchical output
	 */
	static Element sch2xml(Element eParent, Schema schemaObject) {
		Element eShema = new Element(schemaObject.title);
		if (DEBUG_SCH_XML) prn_("eSchema '" + eShema.getName() + "'");
		if (eParent != null) eParent.addContent(eShema);
		TMap<Object> fieldMap = getFieldValueMap(schemaObject);
		for (String fieldName : schemaObject.fields.keySet()) {
			if (SCHEMA_XML_IGNORE_FIELDS.contains(fieldName)) {
				if (DEBUG_SCH_XML) prn_("----Ignore XML field '" + fieldName + "'");
				continue;
			}
//prn_("sch2xml.Field '" + fieldName + "'");
			Object fieldValue = fieldMap.get(fieldName);
			if (fieldValue == null) {
//prn_("Ignore NULL field '" + fieldName + "'");
				if (DEBUG_SCH_XML) prn_("----Primitive '" + fieldName + "' = NULL");
				continue;
			}
			Field field = schemaObject.fields.get(fieldName);
			Class<?> fieldType = field.getType();

			if (Text.class.isAssignableFrom(fieldType)) {
				assertion(PCDATA.equals("#" + fieldName), "Unexpected field name /" + fieldName + "'");
				Text text = (Text) fieldValue;
				if (DEBUG_SCH_XML) {
					xmlPrint(eParent);
					prn_("----Text '" + fieldName + "', size = " + text.size());
				}
				text.addAll(tokenizeString(eParent.getTextTrim()));
			} else if (Collection.class.isAssignableFrom(fieldType)) {
				Type gt = (ParameterizedType) field.getGenericType();
				ParameterizedType pt = (ParameterizedType) gt;
				Class<?> itemType = (Class<?>) pt.getActualTypeArguments()[0];
				assertion(Schema.class.isAssignableFrom(itemType), "UNEXPECTED non Schema ARRAY '" + fieldName + "' item of <" + itemType.getSimpleName() + ">");
				Collection<?> array = (Collection<?>) fieldValue;
				if (DEBUG_SCH_XML) prn_("----Array<" + itemType.getSimpleName() + "> '" + fieldName + "', size = " + array.size());

				for (Object item : array) {
					assertion(item != null, "UNEXPECTED non Schema NULL ITEM array '" + fieldName + "'");
					//if (item instanceof Schema) {
						Schema schemaItem = (Schema) item;
						Element eItem = sch2xml(eShema, schemaItem);
					//} else 
				}
				
			} else if (Schema.class.isAssignableFrom(fieldType)) {
				Schema schemaChild = (Schema) fieldValue;
				Element eChild = sch2xml(eShema, schemaChild);
				if (DEBUG_SCH_XML) prn_("----Child '" + fieldName + "' -> '" + eChild.getName() + "'");
			} else {
				eShema.setAttribute(fieldName, "" + fieldValue);
				if (DEBUG_SCH_XML) prn_("----Primitive '" + fieldName + "' = '" + fieldValue + "'");
			}
		}
		return eShema;
	}
	
}

//Element eArray = new Element(fieldName);
//eShema.addContent(eArray);
//eArray.setAttribute(TYPE, itemType.getSimpleName());
//eArray.setAttribute(DTD_ARRAY, "" + array.size());

//schemaAddToXML(eArray, array);

//static void schemaAddToXML(Element eArray, Collection<?> array) {
//prn_("eArray '" + eArray.getName() + ", size " + array.size());
////brk();
//	for (Object item : array) {
//		if (item instanceof Schema) {
//			Schema schemaItem = (Schema) item;
//			Element eShemaItem = sch2xml(eArray, schemaItem);
//		} else {
//			Element eItem = new Element("" + item); // must be NAME
//			eArray.addContent(eItem);
//		}
//	}
//}
