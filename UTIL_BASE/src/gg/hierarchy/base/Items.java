package gg.hierarchy.base;

import java.util.Collection;
import java.util.Map;

import gg.base.text.TMap;

public class Items extends TMap<I_Item> {

	private static final long serialVersionUID = 1L;

	public Items() {}
	
	public Items(Map<String, ? extends I_Item> map) { super(map); } 
	
	public Items(Collection<? extends I_Item> cc) { super(cc); } 
	
	public Items(I_Item... aa) { super(aa); }

}
