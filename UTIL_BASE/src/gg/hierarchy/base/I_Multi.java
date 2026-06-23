package gg.hierarchy.base;

import gg.base.IPair;
import gg.base.text.SPair;

public interface I_Multi extends I_MultiElement {

//	public int[] indexes(); // position in parent structure
	public String[] indexes(); // position in parent structure

	public SPair[] ranges(); // null - scalar

	static String toVerilog(SPair... pp) {
		String s = "";
		for (SPair p : pp) s += "[" + p.a + ":" + p.b + "]";
		return s;
	}

	public void setMultiRanges(SPair... rr);
	
	default void setMultiRanges(IPair... pp) { // explicitly integer
		int n = pp.length;
		SPair[] ranges = new SPair[n];
		for (int i = 0; i < n; i++) ranges()[i] = new SPair("" + pp[i].a, "" + pp[i].b);
		setMultiRanges(ranges); 
	}
	
	default void setMultiSizes(int... sizes) {  // dimension sizes
		int n = sizes.length;
		SPair[] ranges = new SPair[n];
		for (int i = 0; i < n; i++) ranges()[i] = new SPair("" + (sizes[i] - 1), "0");
		setMultiRanges(ranges); 
	}
	
	default IPair[] getMultiRanges() { 
		if (ranges() == null) return new IPair[]{}; // null - scalar - empty array
		try {
			int n = ranges().length;
			IPair[] rr = new IPair[n];
			for (int i = 0; i < n; i++) {
				int a = Integer.parseInt(ranges()[i].a);
				int b = Integer.parseInt(ranges()[i].b);
				rr[i] = new IPair(a, b);
			}
			return rr; 
		} catch (Exception ex) {}
		return null; // not available 
	}

//	public void setMultiSize(int... sizes) { 

	default int[] getMultiSizes() { 
		IPair[] rr = getMultiRanges();
		if (rr == null) return null; // not available - JEP expression
		int n = rr.length;
		int[] ww = new int[n];
		for (int i = 0; i < n; i++) {
			if ((rr[i].a < 0) || (rr[i].b < 0) ) return null; // not available - JEP expression
			int w = rr[i].a - rr[i].b;
			ww[i] = (w > 0) ? (1 + w) : (1 - w);
		}
		return ww; // scalar - empty array
	}

	default int flatMultiSize() { 
		int[] ww = getMultiSizes();
		if (ww == null) return -1;    // not available
		if (ww.length == 0) return 0; // scalar - empty array
		int aw = 1;
		for (int w : ww) aw *= w;
		return aw;
	}

	default String getMultiString() {
		if (ranges() == null) return "";
		return toVerilog(ranges());
	}

}
