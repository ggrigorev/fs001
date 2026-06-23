package gg.base.text;

public class SPair extends gg.base.Pair<String>{
	
	public SPair() {}
	public SPair(String a, String b) { this.a = a; this.b = b; }

	public void insert(SPair p, String delimiter) { a = p.a + delimiter + a; b = p.b + delimiter + b;}
	public void append(SPair p, String delimiter) { a += delimiter + p.a; b += delimiter + p.b;}

	public String join() { return a + b; }
	public String join(String delimiter) { return a + delimiter + b; }

}
