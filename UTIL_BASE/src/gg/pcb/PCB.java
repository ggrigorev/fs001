package gg.pcb;

import java.util.TreeMap;

import gg.base.text.*;

import static gg.base.util.U_Base.*;

public class PCB implements I_PCB {

	public TMap<I_Chip> chips = new TMap<>();
	
	public String name;

	public PCB() {}
	
	public PCB(String name) { this.name = name; }

	@Override public String name() { return name; }

	@Override public TMap<I_Chip> chips() { return chips; }
	
	public TMap<SMap> chipMaps() { 
		TMap<SMap> maps = new TMap<>();
		for (I_Chip chip : chips.values()) {
			SMap map = new SMap();
			maps.put(chip.name(), map);
			for (I_Pin pin : chip.pins().values()) {
				String refDes  = pin.refDes();
				String netName = pin.net();
				map.put(refDes, netName);
			}
		}
		return maps; 
	}

	public TMap<SList> chipLists() { 
		TMap<SList> lists = new TMap<>();
		for (I_Chip chip : chips.values()) {
			SList list = new SList();
			lists.put(chip.name(), list);
			TreeMap<Integer, I_Pin> pins = chip.pins();
			int n = pins.size();
			for (int i = 0; i < n; i++) {
				I_Pin pin = pins.get(i);
				assertion(i == pin.index(), "Invalid pin order");
				String netName = pin.net();
				list.add(netName); // include null
			}
		}
		return lists; 
	}

	@Override
	public TMap<SPairList> nets() {
		TMap<SPairList> lists = new TMap<>(); // net name / connections
		for (String chipName : chips.keySet()) {
			I_Chip chip = chips.get(chipName);
			for (I_Pin pin : chip.pins().values()) {
				String refDes  = pin.refDes();
				String netName = pin.net();
				SPairList list = lists.get(netName);
				if (list == null) {
					list = new SPairList();
					lists.put(netName, list);
				}
				SPair pair = new SPair(chipName, refDes);
				list.add(pair);
			}
		}
		return lists; 
	}

//	@Override
//	public TMap<SPairList> netMap() {
//		for (SMap map : chips.values()) {
//			for (String refDes)
//		}
//		return null;
//	}

}
