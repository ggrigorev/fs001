package gg.base.text;

import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;

public class SList extends ArrayList<String> implements I_Text {

	private static final long serialVersionUID = 1L;

	public final String kind = getClass().getSimpleName();

	public SList() {}
	
	public SList(Collection<String> c) { super(c); } 
		
	public SList(String... a) { super(Arrays.asList(a)); }

	public void addAll(String... a) { addAll(Arrays.asList(a)); }
	public void addAll(int index, String... a) { addAll(index, Arrays.asList(a)); }

	@Override
	public void append(Object o) { add("" + o); }

}
