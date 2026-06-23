package gg.base.text;

import java.util.Collection;

public interface I_Text {
	
	public void append(Object o);
	
	default void appendAll(Object... a) { for (Object o : a) append(o); }
	default void appendAll(Collection<?> c) { for (Object o : c) append(o); }

}
