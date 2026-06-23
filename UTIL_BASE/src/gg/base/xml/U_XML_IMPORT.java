package gg.base.xml;

import static gg.base.util.L_Base.*;
import static gg.base.xml.L_XML.*;

import static gg.base.util.U_Print.*;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Classes.*;
import static gg.base.util.U_Constructors.newInstance;
import static gg.base.util.U_Fields.*;

import static gg.base.xml.U_XML_IO.xmlPrint;

import java.util.*;
import java.lang.reflect.*;

import org.jdom2.Element;

public interface U_XML_IMPORT {

	static Object xmlImportArray(Element e, Class<?> componentType, int dim) {
//prn_("\n--------------------------- " + e.getAttributeValue(ID) + ", dim = " + dim + ", componentType = " + componentType);			
//xmlPrint("Parent", e);
		
//		String name = componentType.getName();
		List<Element> cc = e.getChildren();
//prn_("cc.size = " + cc.size() );//+ ", name " + name			
		
		int length = 0; // cc.size();
		for (Element c : cc) {
			int index = Integer.parseInt(c.getAttributeValue(ITEM));
			if (length < index) length = index;
		}
		length++;
//prn_("length = " + length + "\n");			
		
		Class<?> subType = componentType;
		while (dim > 1) { subType = Array.newInstance(subType, 0).getClass(); dim--; }
		Object array = Array.newInstance(subType, length);
		for (Element c : cc) {
			int index = Integer.parseInt(c.getAttributeValue(ITEM));
//xmlPrint("Child", c);
			Object value = xmlImport(c); 
//prn_("value = " + value + "\n");			
			Array.set(array, index, value);
		}			
//prn_("\n---------------------------\n");			
		return array;
	}

	static Object xmlClone(Object object, int level) {
		Element e = U_XML_EXPORT.xmlExport(object, level);//xmlPrint(e);
		return xmlImport(e);
	}

	static Object xmlImport(Element e) {
//xmlPrint("XML_IMPORT" , e);
		String eName = e.getName();
		
		String typeName = JAVA_PRIMITIVE_NAME_MAP.get(eName);// prn_("kind " + kind + " -> " + JAVA_PRIMITIVE_NAME_MAP.get(kind));
		if (typeName == null) typeName = XML_PRIMITIVE_NAME_MAP.get(eName); 
		if (typeName == null) typeName = eName; 

		Class<?> type = getClassByName(typeName); 
		if (type == null) assertion(new Exception(), "FAILURE cannot find Class " + typeName);
		String arr = e.getAttributeValue(ARRAY);
		int dim = (arr == null) ? 0 : Integer.parseInt(arr);
		
		Object object;
		
		if (dim > 0) {
			object = xmlImportArray(e, type, dim);  			
			if (object == null) assertion(new Exception(), "FAILURE new instance of Array " + type);
//			return object;
		} else if (isJavaPrimitive(type)) {
			String arg = e.getAttributeValue(VALUE);
			object = getJavaPrimitiveValue(type, arg);  
			if (object == null) assertion(new Exception(), "FAILURE new instance of Java Primitive " + type);
		} else {
			object = newInstance(type);
			if (object == null) assertion(new Exception(), "FAILURE new instance of " + type);
			if (object instanceof Collection) {
				Collection collection = (Collection<?>) object;
				for (Element c : e.getChildren()) {
					Object o = xmlImport(c);
					collection.add(o);
				}
			} else if (object instanceof Map) {
				Map map = (Map<?, ?>) object; 
				for (Element c : e.getChildren()) {
					if (!c.getName().equals(ENTRY)) continue;
//xmlPrint(c);
					List<Element> cc = c.getChildren();
					Object key = c.getAttributeValue(KEY);
					Object val = c.getAttributeValue(VALUE);
					if (key == null) {
						Element eKey = cc.remove(0); //xmlPrint(eKey);
						key = xmlImport(eKey); //prn_("  key " + key);
					} 
					if (val == null)  {
						Element eVal = cc.remove(0); //xmlPrint(eVal);
						val = xmlImport(eVal); //prn_("  val " + key);
					}
					map.put(key, val);
				}
			} else {
				for (Element eChild : e.getChildren()) xmlImportFields(object, eChild);// maps and collections has no public fields to assign				
			}
		}
		return object;
	}
	
	/*
	 * import public fields
	 */
	static Object xmlImportFields(Object oParent, Element eChild) {

		Object fieldValue = xmlImport(eChild); // not NULL
		
		if (oParent != null) {
			String id = eChild.getAttributeValue(ID);
			if (id == null) assertion(new Exception(), "FAILURE instance of " + fieldValue.getClass() + " in container " + oParent + " has NO ID attribute");
			boolean OK = setFieldValue(id, oParent, fieldValue);
			if (!OK) {
				if (fieldValue.getClass().isArray()) {
					Field field = getField(oParent, id);
					if (field == null) assertion(new Exception(), "FAILURE cannot find field " + id + " in container " + oParent);
//prn_(fieldValue.getClass() + "\n\t" + Arrays.deepToString((Object[]) fieldValue));
					fieldValue = translateJavaPrimitiveArray(fieldValue);
//prn_(fieldValue.getClass() + "\n\t" + Arrays.deepToString((Object[]) fieldValue));
					OK = setFieldValue(id, oParent, fieldValue);
				}
			}
			if (!OK) assertion(new Exception(), "FAILURE assign field " + id + " in container " + oParent);
		}
		
		return fieldValue;
	}
	
}

//String kind = e.getName();
//Class<?> type = null;
//try { type = findClass(kind); } catch (ClassNotFoundException ex) {
//	assertion(ex, "FAILURE cannot find Class " + type);
//}
//Object object = null;
//String arr = e.getAttributeValue(ARRAY);
//int dim = (arr == null) ? 0 : Integer.parseInt(arr);
//if (dim > 0) {
//	xmlImportArray(e, Collection collection);
//}
//if (isJavaPrimitive(type)) {
//	String arg = e.getAttributeValue(VALUE);
//	if (dim == 0) {
//		object = getJavaPrimitiveValue(type, arg);  
//	}
//} else object = newInstance(type);
//
//if (object == null) assertion(new Exception(), "FAILURE instance of " + type + " in container " + container);
//prn_("-------------------------------------- set value of field " + id);

//Object value = (dim > 1) ? xmlImportArray(c, subType, dim - 1) : xmlImportObject(c); 

//prn_("[" + n + "]-------------------------------------- collection size " + collection.size() + ", index " + Integer.parseInt(c.getAttributeValue(INDEX)));

//	static int xmlImportCollection(Element e, Collection collection) {
//		int n = 0;
//		for (Element c : e.getChildren()) {
//			Object o = xmlImport(null, c);
//			collection.add(o);
//			n++;
////			prn_("[" + n + "]-------------------------------------- collection size " + collection.size() + ", index " + Integer.parseInt(c.getAttributeValue(INDEX)));
//		}
//		return n;
//	}

//	static void xmlImportMap(Element e, Map map) {
//		int n = 0;
//		for (Element c : e.getChildren()) {
//			if (!c.getName().equals(ENTRY)) continue;
//			List<Element> cc = c.getChildren();
////			prn_("entry size " + cc.size());
//			Element eKey = cc.remove(0); //xmlPrint(eKey);
//			Element eVal = cc.remove(0); //xmlPrint(eVal);
//			Object key = xmlImport(null, eKey); //prn_("  key " + key);
//			Object val = xmlImport(null, eVal); //prn_("  val " + key);
//			map.put(key, val);
//			n++;
////			prn_("[" + n + "]-------------------------------------- map size " + map.size() + ", index " + Integer.parseInt(c.getAttributeValue(INDEX)));
//		}
//	}

	//prn_("-----------------------");
	//xmlPrint(e);		

	
//	static void xmlExportValue(Object object, Element e) {
//		LinkedHashMap<String, Field> fieldMap = getFieldMap(object);
//		LinkedHashMap<String, Object> valueMap = getFieldValueMap(object);
//		for (String key : fieldMap.keySet()) {
//			Field field = fieldMap.get(key);
//			Class<?> fieldType = field.getType();
////			String fieldTypeName = fieldType.getName();
//			Object value = valueMap.get(key);
//
//			int dim = 0;
//			Class<?> componentType = fieldType;
//			while (componentType.isArray()) {
//				dim++;
//				componentType = componentType.componentType();
//			}
//
//			Element c = null;
//			if (value == null) {
////				int dim = 0;
////				while (fieldType.isArray()) {
////					dim++;
////					fieldType = fieldType.componentType();
////				}
////prn("key " + key + ", name " + fieldTypeName + ", type " + fieldType.getSimpleName() + ", dim " + dim);
//				c = new Element(componentType.getName());
//				c.setAttribute(ID, key);
//				if (dim > 0) c.setAttribute(ARRAY, "" + dim);
//// do not include 				
//				e.addContent(c);
//			} else {
//				if (dim > 0) {
//					//Object[] oo = (Object[]) value;
//					int length = Array.getLength(value);
//					ArrayList<Object> L = new ArrayList<>();
//					for (int i = 0; i < length; i++) L.add(Array.get(value, i));
////prn_("----------- length " + length);
//					value = L;
//				}
//				c = xmlExport(value, key, e);
//				if(value instanceof Collection) {
//					Collection<?> collection = (Collection<?>) value;
//					xmlExportCollection(c, collection);
//				}
//				if(value instanceof Map) {
//					Map<?, ?> map = (Map<?, ?>) value;
//					xmlExportMap(c, map);
//				}
//			}
////			n++;
//		}		
////		return n;
//	}

//	if (c == null) { keys.add(key); continue; } // keep if failure

//	static Element xmlExport(Object object) { return xmlExport(object, null, null); }
//
//	static Element xmlExport(Object value, String name, Element eParent) {
//		Element e = null;
//		if (value == null) {
//			e = new Element(NULL);
//		} else {
//			Class<?> type = value.getClass();
//			if (isJavaPrimitive(type)) {
//				e = new Element(type.getName());
//				e.setAttribute(VALUE, "" + value);
//			} else {
//				int dim = 0;
//				while (type.isArray()) {
//					dim++;
//					type = type.getComponentType();
//				}
//				e = new Element(type.getName());
//				if (dim > 0) e.setAttribute(ARRAY, "" + dim);
//				xmlExportValue(value, e);
//			}
//		}
//		if (eParent != null) eParent.addContent(e);
//		if (name != null) e.setAttribute(ID, name);
//		return e;
//	}

//	static void xmlExportArray(Element e, Object value, int dim) {
//		
//	}
//
//	static void xmlExportCollection(Element e, Collection<?> collection) {
//		int i = 0;
////		Class<?> itemType = null;
////		String itemTypeName = null;
////		e.setAttribute(TYPE, itemTypeName);
//		e.setAttribute(SIZE, "" + collection.size());
//		for (Object value : collection) {
//			Element c = xmlExport(value, null, e);
//			c.setAttribute(INDEX, "" + (i++));
//		}
//	}
//	
//	//prn_("SMap " + map);
//
//	static void xmlExportMap(Element e, Map<?, ?> map) {
//		int i = 0;
////		Class<?> keyType = null;
////		Class<?> valType = null;
////		String keyTypeName = null;
////		String valTypeName = null;
////		e.setAttribute(TYPE, keyTypeName + ":" + valTypeName);
//		e.setAttribute(SIZE, "" + map.size());
//		for (Object key : map.keySet()) {
//			Object val = map.get(key);
//			Element m = new Element(ENTRY); e.addContent(m);
//			xmlExport(key, null, m);
//			xmlExport(val, null, m);			
//			m.setAttribute(INDEX, "" + (i++));
//		}
//	}

//static int xmlExportPrimitives(Element e, Map<String, Field> fieldMap, Map<String, Object> valueMap, List<String> keys) {
//	int n = 0;
//	int size = keys.size();
//	for (int i = 0; i < size; i++) {
//		String key = keys.remove(0);
//		Field field = fieldMap.get(key);
//		Object value = valueMap.get(key);
//		Class<?> type = (value == null) ? field.getType() : value.getClass();
//		if (isJavaPrimitive(type)) {
//			Element c = new Element(type.getSimpleName());
//			e.addContent(c);
//			c.setAttribute(NAME, key);
//			c.setAttribute(VALUE, "" + value);
//			n++; continue;
//		}
//		keys.add(key); // keep not primitive
//	}		
//	return n;
//}
//prn_("U_XML_IMPEX.xmlExport: fieldMap " + fieldMap);
//prn_("U_XML_IMPEX.xmlExport: valueMap " + valueMap);
//int n = 0;


//static boolean isNode(Object object) {
//	Method method = getMethod(object, PARENT);
//	return (method != null);
//}

//static boolean isHierarchy(Object object) {
//	Method method = getMethod(object, CHILDREN);
//	return (method != null);
//}


//Field field = getField(container, id);
//setFieldValue(container, object, field);

//ArrayList<Element> eWait = new ArrayList<>(e.getChildren());
//ArrayList<Element> eDone = new ArrayList<>();
//prn_("I_Item.readXML(" + e + ")> INFO: BEFORE wait " + eWait.size() + ", done " + eDone.size());
//xmlImportAdd(object, eWait, eDone);
//prn_("I_Item.readXML(" + e + ")> INFO: AFTER  wait " + eWait.size() + ", done " + eDone.size());

//static void xmlImportAdd(Object object, List<Element> eWait, List<Element> eDone) {
//	ArrayList<Element> ePassed = new ArrayList<Element>();
//	Class<?> type = object.getClass();
//	String kind = type.getSimpleName();
//	for (Element eChild : eWait) {
//		String fieldKind = eChild.getName();
//		String fieldName = eChild.getAttributeValue(NAME);
//		Field field = null;
//		Object fieldValue = null;
//		if (fieldName == null) {
//			prn_("Item.readXML> WARNING: Child element has no attribute " + NAME + "");
//			continue;
//		}
//
//		try { field = object.getClass().getField(fieldName); } catch (NoSuchFieldException | SecurityException e1) {}
//		if (field == null) {
//			prn_("Item.readXML> WARNING: Object of <" + kind + "> type has no field [" + fieldName + "]");
//			continue;
//		}
//		Class<?> primitiveType = JAVA_PRIMITIVES.get(fieldKind); 
//		if (primitiveType != null) {
//			String attributeValue = eChild.getAttributeValue(VALUE);
//			if (attributeValue == null) {
//				prn_("Item.readXML> WARNING: Object of <" + kind + "> type has field [" + fieldName + "] without value argument");
//				continue;
//			}
//			fieldValue = getJavaPrimitiveValue(primitiveType, attributeValue);
//			if (fieldValue == null) {
//				prn_("Item.readXML> ERROR: Unxpected NULL valueOf(" + attributeValue + ") for Java primitive of type '" + primitiveType.getSimpleName() + "'");
//				continue;
////				assertion(new Exception(), "Unxpected NULL valueOf(" + attributeValue + ") for Java primitive of type '" + primitiveType + "'");
//			}
//		} else {
//			fieldValue = xmlImport(eChild);
//			if (fieldValue == null) {
//				prn_("Item.readXML> ERROR: Unxpected NULL static field value (" + eChild + ")");
//				continue;
////				assertion(new Exception(), "Unxpected NULL static field value (" + eChild + ")");
//			}
//		}
//		if (!setFieldValue(object, fieldValue, field)) {
//			prn_("Item.readXML> ERROR: Cannot set value to field(" + fieldName + ")");
//			continue;
////			assertion(new Exception(), "Cannot set value to field(" + fieldName + ")");
//		}			
//		ePassed.add(eChild);
//	}
//	eWait.removeAll(ePassed);
//	eDone.addAll(ePassed);
//	
//}

//c = new Element("mmm");
//try {
//} catch (Exception ex) {
//	c = new Element(XML_VIEW);					
//	c.setAttribute(NAME, fieldTypeName);
//} 

