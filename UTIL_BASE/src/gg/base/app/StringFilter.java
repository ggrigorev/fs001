package gg.base.app;

//import org.jdom2.Element;

@FunctionalInterface
public interface StringFilter {
	public boolean accept(String s);
}
