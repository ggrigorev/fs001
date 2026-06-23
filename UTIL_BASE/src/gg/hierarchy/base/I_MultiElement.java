package gg.hierarchy.base;

public interface I_MultiElement {

	public String[] indexes(); // position in parent structure

	public void setMultiPosition(String... rr);
	
	default void setMultiPosition(int... ii) { // explicitly integer
		int n = ii.length;
		String[] pp = new String[n];
		for (int i = 0; i < n; i++) pp[i] = "" + ii[i];
		setMultiPosition(pp); 
	}
	
	default int[] getMultiPosition() { 
		String[] pp = indexes();
		if (pp == null) return new int[]{}; // null - scalar - empty array
		try {
			int n = pp.length;
			int[] ii = new int[n];
			for (int i = 0; i < n; i++) {
				ii[i] = Integer.parseInt(pp[i]);
			}
			return ii; 
		} catch (Exception ex) {}
		return null; // not available 
	}

	default String getMultiPositionString() {
		if (indexes() == null) return "";
		return toVerilog(indexes());
	}

	static String toVerilog(String... pp) {
		String s = "[";
		if (pp.length == 0) return s;
		for (String p : pp) s += p + ":";
		s = s.substring(0, s.lastIndexOf(":")) + "]";
		return s;
	}

}
