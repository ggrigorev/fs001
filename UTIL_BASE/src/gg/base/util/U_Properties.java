package gg.base.util;

import static gg.base.util.L_Base.CHILDREN;
import static gg.base.util.L_Base.GET;
import static gg.base.util.L_Base.HEAP;
import static gg.base.util.L_Base.NAME;
import static gg.base.util.L_Base.PARENT;
import static gg.base.util.L_Base.SET;
import static gg.base.util.L_Base.TYPE;
import static gg.base.util.U_Print.brk;
import static gg.base.util.U_Print.prn_;
import static gg.base.util.U_Text.camelText;
import static gg.base.util.U_Text.joinText;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//import gg.base.hier.*;
import gg.base.text.Text;

import java.lang.SuppressWarnings;

public interface U_Properties {

	static List<String> nameList(String... ss) { return nameList(Arrays.asList(ss)); }

	static Text nameList(List<String> cc) {
		Text list = new Text();
		list.add(joinText("", false, cc));
		list.add(joinText("_", false, cc));
		list.add(camelText(cc));
//prn_(list);		
		return list;
	}

	static Text getters(String... ss) { 
		Text t = new Text(ss);
		String s = joinText("", false, t);
		t.add(0, GET); 
		t = nameList(t);
		t.add(0, s); 
		return t; 
	}
	
	static List<String> setters(String... ss) { 
		Text t = new Text(ss);
		String s = joinText("", false, t);
		t.add(0, SET); 
		t = nameList(t);
		t.add(0, s); 
		return t; 
	}

	static List<Method> methodList(Class<?> klass, List<String> mathodNames) { 
		List<Method> list = new ArrayList<>(); 
		for (String n : mathodNames) {
			Method m = null;
			try { m = klass.getMethod(n); } catch (Exception ex) {}
			if (m != null) list.add(m);
		}
		return list; 
	}
	
	static Object getProperty(Object o, String key) { 
boolean debug = key.equals(""); //PARENT
if (debug) prn_("getProperty object " + o);
		if (o == null) return null;
		try {
if (debug) prn_("getProperty try field in " + o.getClass());
			Field f = o.getClass().getField(key);
if (debug) prn_("getProperty field " + f);
			Object v = f.get(o); 
if (debug) prn_("getProperty field value " + v);
//return (String) v;
return v;
		} catch (Exception ex) { if(debug) { ex.printStackTrace(); brk(); } }
		List<Method> mm = methodList(o.getClass(), getters(key));
if (debug) prn_("getProperty methods in ", mm);
//prn_(mm);		
		for (Method m : mm) try {
if (debug) prn_("getProperty try method " + m);
			Object v = m.invoke(o); 
if (debug) prn_("getProperty method return " + v);
//return (String) v;
return v;
		} catch (Exception ex) {}

		return null;
	}

	// no arguments
	static boolean invokeMethod(Object o, String methodName) {
		try {
			Method m = o.getClass().getMethod(methodName);
			Object r = m.invoke(o); 
			return true;
		} catch (Exception ex) {}
		return false;
	}

	// single arguments
	static boolean invokeMethod(Object o, String methodName, Class<?> type, Object value) {
		try {
			Method m = o.getClass().getMethod(methodName, new Class<?>[] { type });
			Object r = m.invoke(o, new Object[] { value }); 
			return true;
		} catch (Exception ex) {}
		return false;
	}

	static boolean setProperty(Object o, String key, Class<?> type, Object value) {
//		if (key.equals(PARENT)); prn(false, "assign parent for " + getObjectId(o)); brk();// + " as expected = " + (parent == object));

		if (o == null) return false;
//if (key.equals(PARENT))	prn_("setProperty(" + "object of " + o.getClass().getSimpleName() + ", value of " + value.getClass().getSimpleName(), 
//		getNames(Arrays.asList(o.getClass().getMethods())));	
//getFieldMap(o));	
		try {
			Field f = o.getClass().getField(key);
			f.set(o, value); 
//if (key.equals(PARENT))	prn_("SUCCESS field " + key);
			return true;
		} catch (Exception ex) {}
//if (key.equals(PARENT))	prn_("FAIL field " + key);
		for (String methodName : setters(key)) try {
			if (invokeMethod(o, methodName, type, value)) {
//if (key.equals(PARENT))	prn_("SUCCESS method " + methodName);
				return true;
			}
//if (key.equals(PARENT))	prn_("FAIL method " + methodName);
		} catch (Exception ex) {}
		return false;
	}

	static String getProperty_name(Object o) { return (String) getProperty(o, NAME); }	
	static boolean setProperty_name(Object o, String value) { return setProperty(o, NAME, String.class, value); }

	static String getProperty_type(Object o) { return (String) getProperty(o, TYPE); }	
	static boolean setProperty_type(Object o, String value) { return setProperty(o, TYPE, String.class, value); }

	static Object getProperty_parent(Object object) { return getProperty(object, PARENT); }	
	static boolean setProperty_parent(Object object, Object parent) {
//		if (object instanceof Item) {
//			Item item = (Item) object;
//			prn_("setProperty_parent");
//			prn_("object <" + item.kind + ">[" + item.name + "]");
//			if (parent instanceof Item) {
//				item = (Item) parent;
//				prn_("parent <" + item.kind + ">[" + item.name + "]");				
//			}
//			
//		}
		return setProperty(object, PARENT, Object.class, parent); 
	}

	@SuppressWarnings("unchecked")
	static Map<String, Object> getProperty_children(Object o) { return (Map<String, Object>) getProperty(o, CHILDREN); }

	@SuppressWarnings("unchecked")
	static List<Object> getProperty_heap(Object o) { return (List<Object>) getProperty(o, HEAP); }

	static String getObjectId(Object o) { return (o == null) ? ("" + o) : U_Base.getObjectId(o, getProperty_type(o), getProperty_name(o)); }
	
}
