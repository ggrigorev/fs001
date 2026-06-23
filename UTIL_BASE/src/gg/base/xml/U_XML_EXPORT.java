package gg.base.xml;

import static gg.base.util.L_Base.*;

import static gg.base.util.U_Print.*;

import static gg.base.util.U_Classes.isJavaPrimitive;
import static gg.base.util.U_Fields.getFieldMap;
import static gg.base.util.U_Fields.getFieldValueMap;
import static gg.base.util.U_Properties.*;

import java.lang.reflect.*;

import java.util.*;

import org.jdom2.*;

import gg.base.text.*;
import gg.base.text.Text;

import static gg.base.xml.U_XML_IO.*;

public interface U_XML_EXPORT {

	static boolean IGNORE_NULL_VALUES = true;
	static ArrayList<String> IGNORE_EMPTY_VALUES = new ArrayList<>(Arrays.asList(new String[] { HEAP, CHILDREN }));

//	static SSet shortcats = new SSet( // shortcut list
//			SMap.class.getSimpleName(),
//			SSet.class.getSimpleName(),
//			SList.class.getSimpleName(),
//			Text.class.getSimpleName()
//	); // shortcut
//	
	static Element xmlExport_SList(Collection<?> oo) { // shortcut
//		Collection<String> cc = null;
//		
//			try { cc = (Collection<String>) oo; } catch (Exception ex) {
//				ex.printStackTrace(System.out); brk(); 
//							 return null; 
//						}
		
		SList cc = new SList();
		cc.appendAll(oo);
		
		String s = (String) getProperty(oo, KIND);
		if (s == null) s =  cc.getClass().getName();
		String[] ss = new String[] { s.replaceAll("\\$", "-")};//s.split("\\$");
		Element e = new Element(ss[0]);
		e.setAttribute(SIZE, "" + cc.size());
		if (ss.length == 2) e.setAttribute(KIND, ss[1]);
		int i = 0;
		for (String value : cc) {
			Element c = new Element(ENTRY);
			c.setAttribute(INDEX, "" + (i++));
			c.setAttribute(VALUE, value);
			e.addContent(c);
		}
		return e;
	}

//	static Element xmlExport_SList(Object o) { // shortcut
////		static Element xmlExport(SList cc) { // shortcut
////		static Element xmlExport_SList(SList cc) { // shortcut
//			Collection<String> cc = null;
//			
//				try { cc = (Collection<String>) o; } catch (Exception ex) {
//					ex.printStackTrace(System.out); brk(); 
//								 return null; 
//							}
//			
//
//			String s = (String) getProperty(cc, KIND);
//			if (s == null) s =  cc.getClass().getName();
//			String[] ss = new String[] { s.replaceAll("\\$", "-")};//s.split("\\$");
//			Element e = new Element(ss[0]);
//			e.setAttribute(SIZE, "" + cc.size());
//			if (ss.length == 2) e.setAttribute(KIND, ss[1]);
//			int i = 0;
//			for (String value : cc) {
//				Element c = new Element(ENTRY);
//				c.setAttribute(INDEX, "" + (i++));
//				c.setAttribute(VALUE, value);
//				e.addContent(c);
//			}
//			return e;
//		}

	static Element xmlExport_SMap(SMap map) { // shortcut
		Element e = new Element(map.kind);
		e.setAttribute(SIZE, "" + map.size());
		int i = 0;
		for (String key : map.keySet()) {
			String value = map.get(key);
			Element c = new Element(ENTRY);
			c.setAttribute(INDEX, "" + (i++));
			c.setAttribute(KEY, key);
			c.setAttribute(VALUE, value);
			e.addContent(c);
		}
		return e;
	}

	static LinkedHashSet<Object> exported = new LinkedHashSet<>();
	
	boolean DEBUG_EXPORT = false;
	
	static Element xmlExport(Object object, int level) {
		if (!isJavaPrimitive(object.getClass())) {
			if (isExported(object)) { 
				prn_(exported); 
				prn_("OBJECT IS ALREADY EXPORTED " + getObjectId(object)); 
			} else {
				dbg(DEBUG_EXPORT, "OBJECT EXPORT " + getObjectId(object) + " = " + object); 
				exported.add(object);
			}
		}
		if (object instanceof SMap) {
//brk("Collection for xmlExport_SMap");
			return xmlExport_SMap((SMap) object); // NOT ALWAYS automatically detected by Java, only if called outside this package
		}
		if(object instanceof Collection) {
//brk("Collection for xmlExport_SList");
			Element e = xmlExport_SList((Collection<?>) object);
			if (e != null) return e;
		}
//		String kind = (String) getProperty(object, KIND);
//		
//		if (kind == null) {
//			if (object instanceof Collection) try {
//				return xmlExport_SList((Collection<String>) object);
//			} catch (Exception ex) {}
//		}
//
//		if (object instanceof SList) return xmlExport_SList((SList) object);
//		if (object instanceof SSet) return xmlExport_SList((SList) object);
//		if (object instanceof SList) return xmlExport_SList((SList) object);

		Class<?> type = object.getClass();

//		String kind = object.getClass();
		int dim = 0;
		while (type.isArray()) {
			dim++;
			type = type.componentType();
		}

		Element element = new Element(type.getName());//"gg_" + 
//prn_("Type " + type.getName());

		if (dim > 0) {
			element.setAttribute(ARRAY, "" + dim);
			int length = Array.getLength(object);
			for (int i = 0; i < length; i++) {
				Object sub = Array.get(object, i);
				Element component;
				if (sub == null) {
					component = new Element(NULL);
				} else {
					component = xmlExport(sub, level + 1); // value is not field
					component.setAttribute(ITEM, "" + i);
				}
				element.addContent(component);
			}
		} else if (isJavaPrimitive(type)) { // here value equals to the object
//prn_("Primitive");
			element.setAttribute(VALUE, "" + object);
		} else if (object instanceof Collection) { 
//prn_("Collection");
			Collection<?> collection = (Collection<?>) object;
			element.setAttribute(SIZE, "" + collection.size());
			int i = 0;
			for (Object value : collection) {
				Element component = xmlExport(value, level + 1); // value is not field
				component.setAttribute(ITEM, "" + (i++));
				element.addContent(component);
			}
		} else if (object instanceof Map) {
//prn_("Map");
			Map<?, ?> map = (Map<?, ?>) object;
			element.setAttribute(SIZE, "" + map.size());
			int i = 0;
			for (Object key : map.keySet()) {
				Object val = map.get(key);
				Element eKey = xmlExport(key, level + 1); // key is not field
				Element eVal = xmlExport(val, level + 1); // val is not field
				Element entry = new Element(ENTRY);
				entry.setAttribute(ITEM, "" + (i++));
				entry.addContent(eKey);
				entry.addContent(eVal);
				element.addContent(entry);
			}
		} else {
			xmlExportFields(object, element, level + 1);
		}
		return element;
	}

	static boolean isExported(Object o) {
		for (Object io : exported) if (io == o) return true;
		return false;
	}
	/*
	 * export public fields
	 */
	static void xmlExportFields(Object oParent, Element eParent, int level) {
		LinkedHashMap<String, Field> fieldMap = getFieldMap(oParent);
		LinkedHashMap<String, Object> valueMap = getFieldValueMap(oParent);
		for (String fieldName : fieldMap.keySet()) {
			Field field = fieldMap.get(fieldName);
			Object fieldValue = valueMap.get(fieldName);
//if (isExported(fieldValue)) {
//	brk("FIELD '" + fieldName + "' VALUE '" + fieldValue + "' IS ALREADY EXPORTED\n PARENT: " + getObjectId(oParent)+ "\n --------------------- \n"+xmlToString(eParent));
//}

			Element eChild;
//brk(fieldName + " =================" + IGNORE_NULL_VALUES + "======================== " + fieldValue);
			if (fieldValue != null) {
				if (fieldValue instanceof Map) {
					if (((Map<?, ?>) fieldValue).isEmpty() && IGNORE_EMPTY_VALUES.contains(fieldName))
						continue;
				}
				if (fieldValue instanceof Collection) {
					if (((Collection<?>) fieldValue).isEmpty() && IGNORE_EMPTY_VALUES.contains(fieldName))
						continue;
				}
				eChild = xmlExport(fieldValue, level + 1);
				eChild.setAttribute(ID, fieldName);
				eParent.addContent(eChild);

//prn_(fieldName + " add " + fieldValue);
			} else if (!IGNORE_NULL_VALUES) {
				Class<?> fieldType = field.getType();
				int dim = 0;
				while (fieldType.isArray()) {
					dim++;
					fieldType = fieldType.componentType();
				}
				eChild = new Element(fieldType.getName());
				if (dim > 0)
					eChild.setAttribute(ARRAY, "" + dim);
				eChild.setAttribute(ID, fieldName);
				eParent.addContent(eChild);
//prn_(fieldName + " add " + fieldValue);
			}
		}
	}

}
//int dim = 0;
//while (fieldType.isArray()) {
//	dim++;
//	fieldType = fieldType.componentType();
//}
//prn("key " + key + ", name " + fieldTypeName + ", type " + fieldType.getSimpleName() + ", dim " + dim);
