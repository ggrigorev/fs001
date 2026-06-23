package gg.base.text;

import static gg.base.util.U_Text.tokenizeString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class SSet extends LinkedHashSet<String> implements I_Text {

	private static final long serialVersionUID = 1L;

	public final String kind = getClass().getSimpleName();

	public SSet() {}
	
	public SSet(Collection<String> c) { super(c); } 
		
	public SSet(String... a) { super(Arrays.asList(a)); }

	@Override public void append(Object o) { add("" + o); }

}
