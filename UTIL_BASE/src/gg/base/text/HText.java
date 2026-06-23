package gg.base.text;

import static gg.base.util.U_Print.TAP;
import static gg.base.util.U_Print.stdOut;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;

public class HText extends Vector<Object> { // contains lines, Text, and HText elements

	private static final long serialVersionUID = 1L;

	public HText() { super(); }

	public HText(Object... a) { this(Arrays.asList(a)); }

	public HText(Collection<?> c) { super(c); }

	private void append(Text text) {
		for (Object o : this) 
			if (o instanceof HText) for (String s : ((HText) o).toText()) text.add(TAP + s); // text.addAll((HyperText) o).append(level++, text); 
			else if (o instanceof Text) for(String s : ((Text) o)) text.add(TAP + s); 
			else text.add("" + o);
	}

	public Text toText() { Text text = new Text(); append(text); return text; }

	@Override public String toString() { return toText().toString(); }

	public void print() { print(stdOut); }
	
	public void print(PrintStream out) { out.println(toString()); }
	
}
