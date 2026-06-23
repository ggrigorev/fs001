package gg.hierarchy.base.test;

public class Item_2 extends Item_1 {
	
	public Integer i2;
	public Double d2;

	public Item_1 item_1;

	public void build() { 
		i1 = -i1;
		d1 = -d1;
		i2 = -i1;
		d2 = -d1;
		item_1 = new Item_1();
		item_1.i1 *= 2;
		item_1.d1 *= 2;
		
		super.build(); 
	}

}
