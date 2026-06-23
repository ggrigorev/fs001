package gg.base.text;

import static gg.base.java.L_Java.IMPORT;
import static gg.base.util.U_Text.tokenizeString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;

public class SMap extends LinkedHashMap<String, String> {

	private static final long serialVersionUID = 1L;

	public static SMap sortMapAsStrings(Map<?, ?> map) {
		TreeMap<String, String> tmap = new TreeMap<>();
		for (Object key : map.keySet()) tmap.put("" + key, "" + map.get(key));
		return new SMap(tmap);
	}

	public final String kind = getClass().getSimpleName();

	public SMap() {}
	
	public SMap(Map<?, ?> map) { addAll(map); } 
	
	public SMap(Collection<?> c) {
		LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
		ArrayList<Object> list = new ArrayList<>(c);
		if (list.size() == 1) list.addAll(tokenizeString("" + list.remove(0)));
		while (list.size() > 1) map.put(list.remove(0), list.remove(0));
		addAll(map);
	}
	
	public SMap(String... a) { this(Arrays.asList(a)); }

	public void addAll(Map<?, ?> map) { 
		for(Object k : map.keySet()) {
			Object v = map.get(k);
			String key = (k == null) ? null : "" + k;
			String val = (v == null) ? null : "" + v;
			put(key, val); 
		}
	}
	
	public void replaceAll(Map<?, ?> map) { 	
		LinkedHashSet<String> keys = new LinkedHashSet<>(keySet());
		for(Object k : map.keySet()) {
			String p = "" + k;
			String r = "" + map.get(k);
			for(String key : keys) {
				String v = get(key);
				String val = v.replaceAll(p, r);
				put(key, val); 
			}
		}
	}
	
	public String getFirst() { return new Text(values()).get(0); }
	public String getLast() { return new Text(values()).getLast(); }
	public String get(int index) { return new Text(values()).get(index); }

	public void insert(int p, SMap map) {
		SList keys = new SList(keySet());
		SList vals = new SList(values());
		clear();
		int i = 0;
		while (!keys.isEmpty()) {
			if (i == p) putAll(map);
			put(keys.removeFirst(), vals.removeFirst());
			i++; 
		}
	}

}
