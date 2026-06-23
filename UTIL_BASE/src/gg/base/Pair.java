package gg.base;

public class Pair<T> {
	
	public T a, b;

	public void reverse() { T t = a; a = b; b = t; }

	@Override
	public String toString() {
		String s = getClass().getSimpleName() + "<";
		if(a != null) s += a.getClass().getSimpleName(); else
			if(b != null) s += b.getClass().getSimpleName(); else s += "?";
		return s  + "> [a = " + a + ", b = " + b + "]";
	}
	
}
