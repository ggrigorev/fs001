package gg.base.util;

import static gg.base.util.U_Print.brk;
import static gg.base.util.U_Print.prn;
import static gg.base.util.U_Print.prn_;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gg.base.text.SMap;

//import static gg.xml.U_CONSTRUCTORS.*;

/**
 * This class has necessary methods that are used by above classes. Methods
 * include retrieving class paths, class names, classes itself, working with
 * LinkedHashMaps.
 * 
 */
public interface U_Classes {

	static <T> List<T> select(Collection<?> c, T ref) {
		return selectByType(c, (Class<T>) ref.getClass());
	}

	static List<Object> selectPrimitives(Collection<?> c) {
		ArrayList<Object> list = new ArrayList<>();
		for (Object o : c)
			if (isJavaPrimitive(o.getClass()))
				list.add(o);
		return list;
	}

	static <T> List<T> selectByType(Collection<?> c, Class<T> refType) {
		ArrayList<T> list = new ArrayList<>();
		for (Object o : c) {
			Class<?> type = o.getClass();
			if (type.isArray()) {
				ArrayList<Object> a = new ArrayList<>();
				for (int i = 0; i < Array.getLength(o); i++)
					a.add(Array.get(o, i));
				list.addAll(selectByType(a, refType));
			} else {
				if (refType.isAssignableFrom(type))
					list.add(refType.cast(o));
			}
		}
		return list;
	}

//	static <T> List<T> selectByType(Collection<?> c, Class<T> refType) {
//		ArrayList<T> list = new ArrayList<>();
//		for (Object o : c) {
//			Class<?> type = o.getClass();
//			if (type.isArray()) {
//				int n = Array.getLength(o);
//				prn_("Object is ARRAY[" + Array.getLength(o) + "], refType " + refType.getSimpleName());
//				ArrayList<Object> a  = new ArrayList<>();
//				for (int i = 0; i < n; i++) {
//					a.add(Array.get(o, i)) ;
//				}
//				List<T> sub = selectByType(a, refType);
//				prn_("Object is ARRAY[" + Array.getLength(o) + "], size " + sub.size()); brk();
//				list.addAll(sub);
//			} else {
//				if (refType.isAssignableFrom(type)) list.add(refType.cast(o));				
//			}
//		}
//		return list;
//	}

	String CLASS_PATH_DELIMITER = ".";

	LinkedHashSet<String> PACKAGES = new LinkedHashSet<String>();

	static void addClassPath(Class<?> klass) {
		addClassPath(klass.getPackage().getName());
	}

	static void addClassPath(String path) {
		prn(false, "<CLASSES>.addClassPath: " + path); // brk();
		if (path == null)
			return;
		if (!path.endsWith(CLASS_PATH_DELIMITER))
			path += CLASS_PATH_DELIMITER;
		PACKAGES.add(path);
	}

	static void showPackages(String prefix, boolean pause) {
		if (prefix != null) {
			prn("SYSTEM:");
			Package[] packages = Package.getPackages();
			for (Package p : packages) {
				String s = p.getName();
				if (prefix.length() > 0)
					if (!s.startsWith(prefix))
						continue;
				System.out.println(s);
			}
		}
		prn("PACKAGES:", PACKAGES);
		brk(pause, "<Util.CLASSES>.showPackages(" + prefix + ")");
	}

	static void getSupers(Class<?> kind, Collection<Class<?>> types) {
		Class<?> superClass = kind.getSuperclass();
		if ((superClass != null) && !superClass.isAssignableFrom(Object.class))
			getSupers(superClass, types);
		types.add(kind);
	}

	static boolean isKindOf(Class<?> kind, Collection<Class<?>> types) {
		for (Class<?> type : types)
			if (type.isAssignableFrom(kind))
				return true;
		return false;
	}

	LinkedHashMap<String, Class<?>> JAVA_PRIMITIVES = new LinkedHashMap<>(Map.of(Boolean.class.getSimpleName(),
			Boolean.class, Byte.class.getSimpleName(), Byte.class, Short.class.getSimpleName(), Short.class,
			Integer.class.getSimpleName(), Integer.class, Long.class.getSimpleName(), Long.class,
			Double.class.getSimpleName(), Double.class, String.class.getSimpleName(), String.class));

	SMap JAVA_PRIMITIVE_NAME_MAP = new SMap(boolean.class.getName(), Boolean.class.getName(), byte.class.getName(),
			Byte.class.getName(), short.class.getName(), Short.class.getName(), int.class.getName(),
			Integer.class.getName(), long.class.getName(), Long.class.getName(), double.class.getName(),
			Double.class.getName());

	LinkedHashMap<Class<?>, Class<?>> JAVA_PRIMITIVE_WRAPPERS = new LinkedHashMap<>(
			Map.of(boolean.class, Boolean.class, byte.class, Byte.class, short.class, Short.class, int.class,
					Integer.class, long.class, Long.class, double.class, Double.class));

	LinkedHashMap<Class<?>, Class<?>> JAVA_PRIMITIVE_WRAPPERS_REVERSED = new LinkedHashMap<>(
			Map.of(Boolean.class, boolean.class, Byte.class, byte.class, Short.class, short.class, Integer.class,
					int.class, Long.class, long.class, Double.class, double.class));

	static Object translateJavaPrimitiveArray(Object orgArray) {
		Class<?> orgType = orgArray.getClass();
		Class<?> orgBase = orgType;
		int dim = 0;
		while (orgBase.isArray()) {
			orgBase = orgBase.getComponentType();
			dim++;
		}

		int length = Array.getLength(orgArray);
//prn_("orgType " + orgType + ", orgBase " + orgBase + ", length " + length);

		Class<?> newBase = JAVA_PRIMITIVE_WRAPPERS.get(orgBase); // base component type
		if (newBase == null)
			newBase = JAVA_PRIMITIVE_WRAPPERS_REVERSED.get(orgBase);

		Class<?> newType = newBase;
		Object newArray = null;
		for (int i = 0; i < dim; i++) {
			newArray = Array.newInstance(newType, length);
			newType = newArray.getClass();
		}
//prn_("newType " + newType + ", newBase " + newBase);

		for (int i = 0; i < length; i++) {
			Object orgValue = Array.get(orgArray, i);
//prn_("orgValue [" + i + "] " + orgValue);
			Object newValue = null;
			if (orgValue != null) {
				if (dim > 1) {
					newValue = translateJavaPrimitiveArray(orgValue);
				} else {
					newValue = orgValue;
				}
			}
			Array.set(newArray, i, newValue);
		}
//		prn_(orgValue + " " + orgValue); 
//		prn_(orgValue.getClass() + "\n\t" + Arrays.deepToString((Object[]) orgValue));
//		prn_(orgValue.getClass() + " " + orgValue.getClass()); 

		return newArray;
	}

	static Object getJavaPrimitiveValue(Class<?> type, String arg) {
		String kind = type.getSimpleName();
		if (kind.equals(Boolean.class.getSimpleName()))
			return Boolean.valueOf(arg); // prn_(kind + " is not NOT Boolean");
		if (kind.equals(Byte.class.getSimpleName()))
			return Byte.valueOf(arg); // prn_(kind + " is not NOT Byte ");
		if (kind.equals(Short.class.getSimpleName()))
			return Short.valueOf(arg); // prn_(kind + " is not NOT Short ");
		if (kind.equals(Integer.class.getSimpleName()))
			return Integer.valueOf(arg); // prn_(kind + " is not NOT Integer");
		if (kind.equals(Long.class.getSimpleName()))
			return Long.valueOf(arg); // prn_(kind + " is not NOT Long ");
		if (kind.equals(Double.class.getSimpleName()))
			return Double.valueOf(arg); // prn_(kind + " is not NOT Double ");
		if (kind.equals(String.class.getSimpleName()))
			return String.valueOf(arg); // prn_(kind + " is not NOT String ");
		return null;
	}

	static Object guessJavaPrimitiveValue(String arg) {
		try {
			return Byte.valueOf(arg);
		} catch (Exception ex) {
		}
		try {
			return Short.valueOf(arg);
		} catch (Exception ex) {
		}
		try {
			return Long.valueOf(arg);
		} catch (Exception ex) {
		}
		try {
			return Double.valueOf(arg);
		} catch (Exception ex) {
		}
		try {
			return Boolean.valueOf(arg);
		} catch (Exception ex) {
		}
		return arg; // String
	}

	static Class<?> getJavaPrimitiveType(Class<?> kind) {
		if (JAVA_PRIMITIVES.containsValue(kind))
			return kind;
		return JAVA_PRIMITIVE_WRAPPERS.get(kind);
	}

	static boolean isJavaPrimitiveName(String typeName) {
		return (typeName.equals(String.class.getSimpleName())) || JAVA_PRIMITIVE_NAME_MAP.containsKey(typeName.toLowerCase());
	}

	static boolean isJavaPrimitive(Class<?> kind) {
		if (JAVA_PRIMITIVES.containsValue(kind))
			return true;
		if (JAVA_PRIMITIVE_WRAPPERS.containsKey(kind))
			return true;
		return false;
	}

	static String getClassName(Class<?> klass) {
		String className = klass.getName();
		TreeMap<Integer, String> matches = new TreeMap<Integer, String>();
		for (String classPath : PACKAGES) {
			if (className.startsWith(classPath)) {
				matches.put(classPath.length(), classPath);
			}
		}
		if (!matches.isEmpty())
			className = className.substring(matches.get(matches.lastKey()).length());
		return className;
	}

	static Class<?> findClass(String classPathName) throws ClassNotFoundException {
//showPackages(null, false);
		try {
			return ClassLoader.getSystemClassLoader().loadClass(classPathName);
		} catch (ClassNotFoundException ex0) {
			for (String classPath : PACKAGES)
				try { // classPath ends with "."
//showPackages("gg", false);
//prn(false, "<CLASSES>.getClass: " + classPath + classPathName);
					return ClassLoader.getSystemClassLoader().loadClass(classPath + classPathName);
				} catch (ClassNotFoundException ex) {
				}
		}
		throw new ClassNotFoundException(classPathName);
	}

	static Class<?> getClassByName(String classPathName) {
		try {
			return findClass(classPathName);
		} catch (ClassNotFoundException ex) {
		}
		return null;
	}
}

//static Object getJavaPrimitiveObject(String value) {
//	try { return Integer.valueOf(value); } catch (Exception ex) {}
//	try { return Double .valueOf(value); } catch (Exception ex) {}
//	if (value.equals(TRUE) || value.equals(FALSE)) return Boolean.valueOf(value);
//	return value;
//}

//boolean DEBUG_SELECT_OBJECTS = false;// = true;

// static final Vector<Class<?>> JAVA_PRIMITIVES = new
// Vector<Class<?>>(Arrays.asList(new Class<?>[]{
// Boolean.class,
// Byte.class,
// Short.class,
// Integer.class,
// Long.class,
// Double.class,
// String.class
// }));

//LinkedHashMap<String, Class<?>> JAVA_PRIMITIVES = new LinkedHashMap<>();
