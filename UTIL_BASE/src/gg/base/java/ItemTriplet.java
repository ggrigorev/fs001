package gg.base.java;

import gg.base.text.SMap;

public class ItemTriplet {
	
	public String key;
	public String value;

	public String keySeparator = ":";

	public String type;
	public String name;

	public ItemTriplet(String key, SMap map) { this(key, map.get(key)); }

	public ItemTriplet(String key, String value) {
		this.key = key;
		this.value = value;
		
		if(key.indexOf(keySeparator) < 0) name = key; else {
			String[] ss = key.split(keySeparator);
			type = ss[0];
			name = ss[1];
		}
	}
	
}
