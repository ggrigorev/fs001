package gg.hierarchy.base.test;

import java.util.ArrayList;
import java.util.Arrays;

import gg.base.text.SMap;
import gg.hierarchy.base.HItem;

public class Item_1 extends HItem {
	
	public Integer i1 = -1;
	public Double  d1 = -1.0;

	public Integer[] aI = new Integer[]{1, 2, 3};

	public int[][] ai = new int[][] {
		new int[]{11, 12, 13},
		new int[]{31, 32, 33},
	};

	public ArrayList<ArrayList<Integer>> ALI = new ArrayList<>();
	
	Integer[] aI_0 = new Integer[]{121, 122, 123};
	Integer[] aI_1 = new Integer[]{221, 222, 223};
	ArrayList<Integer> ALI_0 = new ArrayList<>(Arrays.asList(aI_0));
	ArrayList<Integer> ALI_1 = new ArrayList<>(Arrays.asList(aI_1));

	public SMap map = new SMap("a", "A", "b", "B");
	
	public Item_1() {
		ALI.add(ALI_0);
		ALI.add(ALI_1);
	}

}
