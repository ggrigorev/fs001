package gg.hierarchy.base;

import java.util.Collection;
import java.util.Map;

import gg.base.text.TMap;

public class Nodes extends TMap<I_Node> {

	private static final long serialVersionUID = 1L;

	public Nodes() {}
	
	public Nodes(Map<String, ? extends I_Node> map) { super(map); } 
	
	public Nodes(Collection<? extends I_Node> cc) { super(cc); } 
	
	public Nodes(I_Node... aa) { super(aa); }

}
