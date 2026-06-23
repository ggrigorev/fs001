package gg.hierarchy.base;

public interface I_Item extends I_Multi {

	public Object parent();
	public void parent(Object parent);

	public String type(); public void type(String type);
	public String name(); public void name(String name);

}
