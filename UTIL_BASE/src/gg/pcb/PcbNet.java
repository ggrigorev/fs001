package gg.pcb;

//import java.util.ArrayList;

import gg.base.text.SPairList;

public class PcbNet implements I_Net {
	
	public String name;

	public SPairList connections;

	@Override public String name() { return name; }
	
}
