package gg.base.util;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.ENDS_WIDTH;
import static gg.base.util.U_Text.STARTS_WIDTH;
import static gg.pcb.bga.U_BGA.getBgaPinIndexPair;
import static gg.pcb.bga.U_BGA.getBgaRowName;
import static gg.base.util.U_Classes.getClassByName;
import static gg.base.util.U_Classes.getSupers;
import static gg.base.util.U_Constructors.newInstance;
import static gg.base.util.U_Fields.getFieldValue;

import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gg.base.IPair;
import gg.base.text.*;

public interface U_Fields {

	static boolean matchField(Object object, SMap map) {
		boolean match, NOT, START, END; 
		for (String key : map.keySet()) {
			String regex = "" + map.get(key);
			NOT = regex.substring(0, 1).equals("!");
			if (NOT) regex = regex.substring(1);
			START = regex.startsWith(STARTS_WIDTH);
			if (START) regex = regex.substring(STARTS_WIDTH.length());
			END = regex.startsWith(ENDS_WIDTH);
			if (END) regex = regex.substring(ENDS_WIDTH.length());
			String value = "" + getFieldValue(object, key);
			if (START | END) {
//prn_(regex + ": start " + START + ", end " + START);
				match = START ? value.startsWith(regex) : true;
				match |= END ? value.endsWith(regex) : false;
				match ^= NOT;
			} else {
				Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(value);
				match = NOT ^ matcher.matches();
			}
			if (!match) return false;
		}
		return true;
	}
	
	static <T> T createClone(T ref) {
		Class<?> type = ref.getClass();
		Object obj = newInstance(type);
		Map<String, Field> map = getFieldMap(type);
//prn("U_Fields.createClone", map.keySet());
		for (String fieldName : map.keySet()) {
			Field field = map.get(fieldName);
			if (ignoreFields.contains(fieldName)) continue;
			Object val = getFieldValue(field, ref);
			setFieldValue(obj, val, field);
		}
		return (T) obj;
	}
	
	static TMap<Object> getFieldValueMap(Object object) { 
		return getFieldValueMap(object, getFieldMap(object.getClass()), true);
	}
	
	static LinkedHashMap<String, Object> getFieldValueMap(Object object, TMap<Field> fieldMap) { 
		return getFieldValueMap(object, fieldMap, true);
	}
	
	SSet ignoreFields = new SSet(PARENT, CHILDREN);
	
	boolean DEBUG_VALUES = false;
	
	static TMap<Object> getFieldValueMap(Object object, TMap<Field> fieldMap, boolean noNull) { 
		TMap<Object> map = new TMap<>();
		for (String fieldName : fieldMap.keySet()) {
			if (ignoreFields.contains(fieldName)) continue;
			Field field = fieldMap.get(fieldName);
			Object fieldValue = getFieldValue(field, object);
			if(!noNull || (fieldValue != null)) map.put(fieldName, fieldValue);
		}
		dbg(DEBUG_VALUES, object.getClass().getSimpleName() + "\n" + map);
		return map; 
	}
	
	String CSV_DELIMITER = " , ";
	
	static SPair toCSV(Object object, Collection<String> exclude) {
		TMap<Object> map = getFieldValueMap(object);
		SPair p = new SPair("", "");
		for (String fieldName : map.keySet()) {
			if (exclude.contains(fieldName)) continue;
			p.a += fieldName + CSV_DELIMITER;
			p.b += map.get(fieldName) + CSV_DELIMITER;
		}
		p.a = p.a.substring(0, p.a.lastIndexOf(CSV_DELIMITER));
		p.b = p.b.substring(0, p.b.lastIndexOf(CSV_DELIMITER));
		return p;
	}	

//	static TMap<Object> getFieldValueMap(Object object) { 
//		return getFieldValueMap(object, getFieldMap(object.getClass()).keySet(), true);
//	}
//
//	static LinkedHashMap<String, Object> getFieldValueMap(Object object, Collection<String> fieldNames) { 
//		return getFieldValueMap(object, fieldNames, true);
//	}
//	
//	static TMap<Object> getFieldValueMap(Object object, Collection<String> fieldNames, boolean noNull) { 
//		TMap<Object> map = new TMap<>();
//		for (String fieldName : fieldNames) {
////			Field field = fieldMap.get(fieldName);
//			Object fieldValue = getFieldValue(object, fieldName);
//			if(noNull && (fieldValue != null)) map.put(fieldName, fieldValue);
//		}
//		return map; 
//	}

	static TMap<Field> getFieldMap(Object object) { return  getFieldMap(object.getClass()); }

	static TMap<Field> getFieldMap(Class<?> klass) { // super class' fields after declared fields
		TMap<Field> map = new TMap<>();
		ArrayList<Class<?>> types = new ArrayList<>();
		getSupers(klass, types);
//prn_("getFieldMap types", types);

		for (Class<?> type : types) {
			for (Field field : type.getDeclaredFields()) {
				int M = field.getModifiers();		
				if (!Modifier.isPublic(M)) continue;
				if (Modifier.isStatic(M)) continue;
				if (Modifier.isFinal(M))  continue;
				String fieldName = field.getName();
				map.put(fieldName, field); // override super class' field
			}
		}
		return map;
	}

	static Object getFieldValue(Object parent, String childName) {
		return getFieldValue(parent.getClass(), parent, childName); 
	}

	static Object getFieldValue(Class<?> parentClass, Object parent, String childName) {
		try { 
			Field field = parentClass.getField(childName);
			if (field == null) return null;
			Object value = field.get(parent);
//prn_("U_Fields.getFieldValue: name " + childName + ", type " + field.getType().getSimpleName() + ", value " + value 
//+ ((value == null) ? "" : " of " + value.getClass().getSimpleName()));			
			return value; 
		} catch (Exception ex) { return null; }
	}

	static Object getStaticFieldValue(String typeFullName, String fieldName) {
		Class type = getClassByName(typeFullName);
		return getFieldValue(type, null, fieldName);
	}

	static Object getFieldValue(Field field, Object parent) {
		try { return field.get(parent); } catch (Exception ex) { return null; }
	}

	static boolean setFieldValue(String fieldName, Object parent, Object child) {
		return setFieldValue(parent, child, getField(parent, fieldName));
	}

	static boolean setFieldValue(Object parent, Object child, Field field) {
		try { field.set(parent, child); return true; } catch (Exception ex) { return false; }
	}

	static Field getField(Object container, String fieldName) { return (container == null) ? null :  getField(container.getClass(), fieldName); }
	
	static Field getField(Class<?> containerClass, String fieldName) {
		try { return containerClass.getField(fieldName); } catch (Exception ex) { return null; }		
	}

	static String getFieldName(Object parent, Object child) {
		for (Field field : parent.getClass().getFields()) {
			Object fieldValue = null;
			try { fieldValue = field.get(parent); } catch (Exception ex) {}
			if (fieldValue == null)  continue;
			if (fieldValue == child) return field.getName(); 
		}
		return null;
	}

//	static String getFieldName(Map<String, Field> map, Object parent, Object child) {
//		if (map == null) map = getFieldMap(parent);
//		for (String fieldName : map.keySet()) {
//			Field field = map.get(fieldName);
//			Object fieldValue = getFieldValue(field, parent);
//			if (child == fieldValue) return fieldName;
//		}
//		return null;
//	}

	static Object invokeMethod(Object container, String methodName) { 
		Method method = getMethod(container, methodName);
		try { return method.invoke(container); } catch (Exception ex) {}
		return null; 
	}

	static Object invokeMethod(Object container, Method method) { 
		try { return method.invoke(container); } catch (Exception ex) {}
		return null; 
	}

	static Method getMethod(Object container, String methodName) { return (container == null) ? null :  getMethod(container.getClass(), methodName); }

	static Method getMethod(Class<?> containerClass, String methodName) {
		try { return containerClass.getMethod(methodName); } catch (Exception ex) { return null; }		
	}
	
}

//static boolean setFieldValueByName(Object parent, Object child, String childName) {
//	try { 
//		Field field = parent.getClass().getField(childName);
//		if (field == null) return false;
//		field.set(parent, child); 
//		return true;
//	} catch (Exception ex) { return false; }
//}
