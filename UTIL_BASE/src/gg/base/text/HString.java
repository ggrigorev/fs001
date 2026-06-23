package gg.base.text;

import static gg.base.util.U_Print.TAP;

import java.util.ArrayList;

public class HString {

	public final ArrayList<String> lines;
	public final HString parent;

	public final int n; // hierarchy level
	public final String tap;

	public HString(String s) { this(null, s); }

	public HString(HString parent, String s) {
		this.parent = parent;
		if (parent == null) {
			lines = new ArrayList<>();
			n = 0;
		} else {
			lines = parent.lines;
			n = parent.n + 1;			
		}
		String t = "";
		for (int i = 0; i < n; i++) t += TAP;
		tap = t;
		lines.add(tap + s);
	}

	public HString next(String s) {
		HString hs = new HString(this, s);
		return hs;
	}

	public void add(String s) {
		for (int i = 0; i < n; i++) s = TAP + s;
		lines.add(s);
	}

	public String toString() {
		String s = "";
		if (lines.isEmpty()) return s;
		ArrayList<String> list = new ArrayList<>(lines);
		while (!list.isEmpty()) s += list.remove(0) + "\n";
		return s;
	}

}