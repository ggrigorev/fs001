package gg.base.util;

import static gg.base.util.U_Classes.findClass;
import static gg.base.util.U_Print.prn;

import java.lang.SuppressWarnings;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Class CONSTRUCTORS include methods that help create a constructor from
 * parameters or to debug various constructor problems.
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public interface U_Constructors {//extends XML_XIO {

	boolean DEBUG_NEW_CONSTRUCTOR = false;// = true;
	boolean DEBUG_NEW_INSTANCE = false;

	String ARGUMENT_SEPARATOR = ";";
	String ARGUMENT_TYPE_VALUE_SEPARATOR = "::";

//	static <T> T newInstance(String className, String[] args) {
//	}
	
	static <T> T newInstance(String className, String... args) {
		Class objectClass = null;
		try { objectClass = findClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		prn(DEBUG_NEW_INSTANCE, "args.length = " + args.length);
		Class[] argTypes = new Class[args.length];
		Object[] argValues = new Class[args.length];
		for (int i = 0; i < args.length; ) {
			String[] ss = args[i].split(ARGUMENT_TYPE_VALUE_SEPARATOR);
			argTypes[i] = argValues[i].getClass();
		}
		return (T) newInstance(getConstuctor(objectClass, argTypes), argValues);
	}

	static <T> T newInstance(Class objectClass) { return newInstance(objectClass, new Object[]{}); }
	
	static <T> T newInstance(Class objectClass, Object[] argValues) {
		Class[] argTypes = new Class[argValues.length];
		for (int i = 0; i < argValues.length; i++) argTypes[i] = argValues[i].getClass();
		return (T) newInstance(getConstuctor(objectClass, argTypes), argValues);
	}

	static Constructor getConstuctor(Class objectClass, Class[] argTypes) {
		try { return objectClass.getConstructor(argTypes);
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		} catch (SecurityException ex) { 
			ex.printStackTrace();
		}
		return null;
	}

	static Object newInstance(Constructor constructor, Object[] argValues) {
		Object object = null;
		try {
			object = constructor.newInstance(argValues);
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
		return object;
	}

}

//static Constructor getConstuctorEmpty(Class objectClass) {
//try { return objectClass.getConstructor();
//} catch (NoSuchMethodException ex) {
//} catch (SecurityException ex) { }
//return null;
//}
//

//	static Object newInstance(Class objectClass, String arg) {
//		Constructor stringConstructor = getStringConstuctor(objectClass);
//		if (stringConstructor == null) return null;
//		return newInstance(stringConstructor, arg);
//	}
//
//	static Object newInstance(Constructor stringConstructor, String arg) {
//		Object object = null;
//		try {
//			object = stringConstructor.newInstance(arg);
//		} catch (InstantiationException ex) {
//			ex.printStackTrace();
//		} catch (IllegalAccessException ex) {
//			ex.printStackTrace();
//		} catch (IllegalArgumentException ex) {
//			ex.printStackTrace();
//		} catch (InvocationTargetException ex) {
//			ex.printStackTrace();
//		}
//		return object;
//	}
//
//}

//static Object newInstance(Class objectClass, String arg) {
//Constructor constructor = getConstuctorString(objectClass);
//if (constructor == null) return null;
//return newInstance(constructor, arg);
//}
//

//	static Constructor getNodeConstuctor(Class objectClass) {
//		try { return objectClass.getConstructor(Object.class, String.class, Object[].class);
//		} catch (NoSuchMethodException ex) {
//		} catch (SecurityException ex) { }
//		return null;
//	}

//	static Object newInstance(Class objectClass, Object parent, String id, Object... args) {
//		prn(DEBUG_NEW_CONSTRUCTOR, "________________________________________________", (Object[]) objectClass.getConstructors());
//		Constructor nodeConstuctor = getNodeConstuctor(objectClass);
//		if (nodeConstuctor == null) return null;
//		return newInstance(nodeConstuctor, parent, id, args);
//	}
//
//	static Object newInstance(Constructor nodeConstuctor, Object parent,
//			String id, Object... args) {
//		Object object = null;
//		try {
//			object = nodeConstuctor.newInstance(parent, id, args);
//		} catch (InstantiationException ex) {
//			ex.printStackTrace();
//		} catch (IllegalAccessException ex) {
//			ex.printStackTrace();
//		} catch (IllegalArgumentException ex) {
//			ex.printStackTrace();
//		} catch (InvocationTargetException ex) {
//			ex.printStackTrace();
//		}
//		prn(DEBUG_NEW_INSTANCE,
//				"________________________________________________ object "
//						+ object);
//		return object;
//	}
//
//	static Constructor getStringConstuctor(Class objectClass) {
//		try {
//			return objectClass.getConstructor(String.class);
//		} catch (NoSuchMethodException ex) {
//		} catch (SecurityException ex) {
//		}
//		return null;
//	}

