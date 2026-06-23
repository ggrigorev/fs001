package gg.base.util;

import static gg.base.util.L_Base.COMMA;
import static gg.base.util.L_Base.EMPTY;
import static gg.base.util.L_Base.NULL;
import static gg.base.util.U_Files.getFilePath;
import static gg.base.util.U_Print.TAP;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Vector;

import gg.base.text.HString;
import gg.base.text.SMap;

import java.lang.SuppressWarnings;

//import static gg.xml.U_XML_IO.*;
/**
 * Class FORMATS is necessary for correctly displayed data.
 */
@SuppressWarnings("rawtypes")
public class U_Formats {

	public static String getTab(int n) {
		String s = "";
		for (int i = 0; i < n; i++) s += TAP;
		return s;
	}

	public static String unbracket(Object o) {
		return unbracket(o.toString());
	}

	public static String unbracket(String s) {
		s = s.trim();
		s = s.substring(1, s.length() - 1);
		return s.trim();
	} // ??? .trim() invalid concatenation of escaped names

	public static <T> Vector<T> getVectorT(T... args) {
		Vector<T> V = new Vector<>();
		if (args != null) for (T o : args) V.add(o);
		return V;
	}

	public static Vector<String> getStringVector(String... args) {
		return getVector(false, args);
	}

	public static Vector<String> getVector(boolean skipNull, String... args) {
		Vector<String> V = new Vector<String>();
		if (args != null)
			for (String s : args)
				if (s != null) {
					V.addAll(unpackStringList(s));
				} else {
					if (skipNull)
						continue;
					V.add(null);
				}
		return V;
	}

	public static Vector<String> unpackStringList(String s) {
		if (s == null)
			return new Vector<String>();
		s = s.trim();
		while (s.endsWith(","))
			s = s.substring(0, s.length() - 1).trim();
		if (s.length() == 0)
			return new Vector<String>();
		boolean flag = s.startsWith("(") && s.endsWith(")");
		flag |= s.startsWith("[") && s.endsWith("]");
		flag |= s.startsWith("{") && s.endsWith("}");
		if (flag) s = unbracket(s);
		if (s.length() == 0) return new Vector<String>();
		Vector<String> v = new Vector<String>();
		for (String value : s.split(",")) {
			value = value.trim();
			// prn("Util.unpackStringList", "'" + value + "'");
			value = value.replaceAll(COMMA, ",");
			if (value.equals(NULL)) value = null;
			v.add(value);
		}
		return v;
	}

	public static SMap unpackSMap(String s) {
		// prn("<base.utils.FORMATS>.unpackSMap: arg '" + s + "'");
		if (s == null) return new SMap();
		s = s.trim();
		while (s.endsWith(","))
			s = s.substring(0, s.length() - 1).trim();
		// prn("<base.utils.FORMATS>.unpackSMap: trim '" + s + "'");
		// prn("<base.utils.FORMATS>.unpackSMap: split " +
		// s.split(",").length);
		if (s.length() == 0) return new SMap();
		boolean flag = s.startsWith("(") && s.endsWith(")");
		flag |= s.startsWith("[") && s.endsWith("]");
		flag |= s.startsWith("{") && s.endsWith("}");
		if (flag) s = unbracket(s);
		SMap m = new SMap();
		for (String s0 : s.split(",")) {
			s0 = s0.trim();
			// prn("<base.utils.FORMATS>.unpackSMap: split trim '" + s0 +
			// "'");
			if (s0.length() == 0)
				continue;
			String[] a = s0.split("=");
			String key = a[0].trim();
			String value = a[1].trim();
			if (value.equals(NULL))
				value = null;
			if (value.equals(EMPTY))
				value = "";
			m.put(key, value);
		}
		// prn("<base.utils.FORMATS>.unpackSMap: map", m);
		// brk("");
		return m;
	}

	public static Vector<Integer> unpackIntegerList(String list) {
		Vector<String> V = unpackStringList(list);
		Vector<Integer> I = new Vector<Integer>();
		for (String s : V) {
			if (s.trim().length() == 0)
				continue;
			I.add(Integer.parseInt(s));
		}
		return I;
	}

	public static Integer[] unpackIntegerArray(String list) {
		Vector<Integer> I = unpackIntegerList(list);
		return I.toArray(new Integer[] {});
	}

	public static Vector<Double> unpackDoubleList(String list) {
		Vector<String> V = unpackStringList(list);
		Vector<Double> I = new Vector<Double>();
		for (String s : V) {
			if (s.trim().length() == 0)
				continue;
			I.add(Double.parseDouble(s));
		}
		return I;
	}

	public static Double[] unpackDoubleArray(String list) {
		Vector<Double> I = unpackDoubleList(list);
		return I.toArray(new Double[] {});
	}

	public static String packDigitally(long L) {
		if (((L >> 40) != 0) && (L & ((1L << 40) - 1)) == 0L)
			return "" + (L >> 40) + "T";
		if (((L >> 30) != 0) && (L & ((1L << 30) - 1)) == 0L)
			return "" + (L >> 30) + "G";
		if (((L >> 20) != 0) && (L & ((1L << 20) - 1)) == 0L)
			return "" + (L >> 20) + "M";
		if (((L >> 10) != 0) && (L & ((1L << 10) - 1)) == 0L)
			return "" + (L >> 10) + "K";
		return "" + L;
	}

	public static long unpackDigitally(String s) {
		s = s.toUpperCase().trim();
		int order = 0;
		if (Character.isLetter(s.charAt(s.length() - 1))) {
			String suffix = s.substring(s.length() - 1);
			s = s.substring(0, s.length() - 1);
			if (suffix.toUpperCase().equals("K")) {
				order = 10;
			} else if (suffix.toUpperCase().equals("M")) {
				order = 20;
			} else if (suffix.toUpperCase().equals("G")) {
				order = 30;
			} else if (suffix.toUpperCase().equals("T")) {
				order = 40;
			} else
				s = s + suffix; // force parser exception
		}
		return Long.parseLong(s) << order;
	}

	public static String formatMap(Map<?, ?> M, String msg) {
		HString hs = new HString("MAP " + M.getClass().getSimpleName()
				+ ", size " + M.size() + " " + ((msg == null) ? "" : msg));
		addMapLines(hs, M);
		return hs.toString();
	}

	public static void addMapLines(HString hs, Map<?, ?> M) {
		for (Object key : M.keySet()) {
			Object value = M.get(key);
			hs.next(key + " = " + value);
			// if (value != null) hs.next(key + " = " + value.toString());
			// else hs.next(key.toString());
		}
		if (M.isEmpty())
			hs.next("EMPTY");
	}

	public static String formatCollection(Collection<String> C, String msg) {
		HString hs = new HString("COLLECTION " + C.getClass().getSimpleName()
				+ ", size " + C.size() + " " + ((msg == null) ? "" : msg));
		addCollectionLines(hs, C);
		return hs.toString();
	}

	public static void addCollectionLines(HString hs, Collection C) {
		for (Object value : C)
			hs.next("" + value);
		if (C.isEmpty())
			hs.next("EMPTY");
	}

	public static String formatArray(Object[] A, String msg) {
		HString hs = new HString("ARRAY of " + A[0].getClass().getSimpleName()
				+ " " + ((msg == null) ? "" : msg));
		addCollectionLines(hs, Arrays.asList(A));
		return hs.toString();
	}

	public static String format(Object o) {
		return format(o, null);
	}

	@SuppressWarnings({ "unchecked" })
	public static String format(Object o, String msg) {
		if (o == null)
			return (msg == null) ? "NULL" : msg + " NULL";
		if (o instanceof File)
			return "'" + getFilePath((File) o) + "'";
		if (o instanceof Map)
			return formatMap((Map) o, msg);
		if (o instanceof Collection)
			return formatCollection((Collection) o, msg);
//		if (o instanceof Element)
//			try { return xmlToString((Element) o); } catch (Exception ex) {
//				assertion(ex, "format(Element)");
//			}
		return o.toString();
	}

}
