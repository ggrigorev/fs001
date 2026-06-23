package gg.base.text;

import java.util.*;

import static gg.base.util.U_Properties.getProperty_name;

import static gg.base.util.U_Print.*;

public class TMap<T> extends LinkedHashMap<String, T> {

	private static final long serialVersionUID = 1L;

	public final String kind = getClass().getSimpleName();

	public TMap() {}
	
	public TMap(Map<String, ? extends T> map) { putAll(map); } 
	
	public TMap(Object... oo) { 
		boolean flag = false;
		String key = null;
		T value = null;
		for (Object o : oo) {
			if (flag) {
				key = (String)  o;
			} else {
				value = (T) o;
				put(key, value);
			}
			flag = !flag;
		}			
	}

	//public TMap(T... aa) { this(Arrays.asList(aa)); }

	public TMap(Collection<? extends T> cc) {
		TMap<T> tmp = new TMap<>(); 
		for (T t : cc) tmp.add(t);
		if (tmp.size() < cc.size()) {
			//prn(); 
			brk("not unique names");
		}
		 putAll(tmp);
	}
	
	public T add(T value) { return super.put(getProperty_name(value), value); }
	public void addAll(Collection<T> cc) { for (T t : cc) add(t); }

	public T getFirst() { return get(new Text(keySet()).get(0)); }
	public T getLast() { return get(new Text(keySet()).getLast()); }
	public T get(int index) { return get(new Text(keySet()).get(index)); }

	public String printToString(int level, String s) {
		if (isEmpty()) return s += " : EMPTY";
		s += " :\n";
		for(String k : keySet()) {
			s +=(getTap(level + 1) + k + " = " + get(k)) + "\n";
		}
		return getTap(level) + s;
	}
}
