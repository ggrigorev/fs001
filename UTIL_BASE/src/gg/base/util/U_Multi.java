package gg.base.util;

import java.util.*;

//import gg.base.hier.Item;
//import gg.base.hier.Node;
import gg.base.text.SPair;
//import gg.rtl.base.code.VerilogRange;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Constructors.newInstance;

public interface U_Multi {

	public static SPair parseMulti(String s) {
		return parseMulti(s, (s.startsWith("[")));
	}

	public static SPair parseMulti(String s, boolean enveloped) {
		SPair sp = new SPair();
		s = s.trim();
		if (enveloped) {
			assertion(s.startsWith("["), "Invalid randge format '" + s + "'");
			assertion(s.endsWith("]"), "Invalid randge format '" + s + "'");
			s = s.substring(1, s.length() - 1);// prn(s);
		}
		int p = s.indexOf(":");
		assertion((p >= 0), "Invalid randge format '" + s + "'");
		sp.a = s.substring(0, p).trim(); // prn_("leftSymbol '" + range.leftSymbol + "'");
		sp.b = s.substring(p + 1).trim(); // prn_("rightSymbol '" + range.rightSymbol + "'");
		return sp;
	}

}

//	static <T extends Node> T create(Class<T> type, String name, String tail, int... width) {
//		ArrayList<SPair> ranges = new ArrayList<>();
//		for (int w : width) ranges.add(new SPair("" + (w - 1), "0"));
//		return create(type, name, tail, ranges);
//	}
//
//	static <T extends Node> T create(Class<T> type, String name, String tail, SPair... ranges) { return create(type, name, tail, Arrays.asList(ranges)); }
//	
//	static <T extends Node> T create(Class<T> type, String name, String tail, Collection<SPair> ranges) {
//		ArrayList<SPair> list = new ArrayList<>(ranges);
//		T top = newInstance(type);
//		top.name = name + "_" + tail;
//		top.range = list.remove(0);
//		T parent = top;
//		while (!list.isEmpty()) {
//			T child = newInstance(type);
//			child.range = list.remove(0);
//			child.name = name + "_" + list.size() + "_" + tail;
//			parent.add(child);
//			parent = child;
//		}
//		return top;
//	}

//	public static int width(Item item) {
//		if (item.range == null) return 0; // scalar
//		try {
//			int L = Integer.parseInt(item.range.a);
//			int R = Integer.parseInt(item.range.b);
//			int W = L - R;
//			if (W < 0) W = 0 - W;
//			W++;
//			return W; // positive
//		} catch (Exception ex) {
//			return -1; // not available
//		}
//	}

//	public static String toVerilog(SPair... pp) {
//		String s = "";
//		for (SPair p : pp) s += "[" + p.a + ":" + p.b + "]";
//		return s;
//	}
