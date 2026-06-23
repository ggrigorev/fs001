package gg.base;

import static gg.base.util.U_Print.*;

public class IPair extends gg.base.Pair<Integer>{
	
	public IPair() {}
	public IPair(int[] ii) { this(ii[0], ii[1]); }
	public IPair(Integer a, Integer b) { this.a = a; this.b = b; }
	
	@Override public boolean equals(Object o) {
		if (o == null) return false;
//prn_("(" + this + " == " + o + ") = ?" + (o instanceof IPair));
		if (o instanceof IPair) {
			IPair p = (IPair) o;
			boolean r = (p.a == a) & (p.b == b);
//prn_("(" + this + " == " + o + ") = " + r);
			return r;
		}
		return false;
	}
	
}
 