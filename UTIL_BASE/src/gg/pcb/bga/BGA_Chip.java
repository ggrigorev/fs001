package gg.pcb.bga;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import gg.base.IPair;
import gg.base.text.SList;
import gg.pcb.I_Pin;

import static gg.base.util.U_Print.prn_;
import static gg.pcb.bga.U_BGA.getBgaPinIndexPair;

public class BGA_Chip extends BGA_Matrix {

	private static final long serialVersionUID = 1L;

	public static Byte[] excludeArray = new Byte[] {'I', 'O', 'Q', 'S', 'X', 'Z'};
	
	public static List<Byte> excludeList = Arrays.asList(excludeArray);

	public static SList rowCharList = new SList();
	
	static {
		byte chr = 'A';
		while (true) {
			if (!excludeList.contains(chr)) rowCharList.add("" + Character.valueOf((char) chr));
			if (chr == 'Z') break;
			chr++;
		}
	}

	public final String kind = getClass().getSimpleName();
	
	public String name;
	public String name() { return name; }
	
	public BGA_Chip(String name, int n) { this(name, n, n); }
	
	public BGA_Chip(String name, int width, int hight)  { 
		super(width, hight);
		this.name = name; 
	}

	@Override public TreeMap<Integer, BGA_Pin> pins(){
		 TreeMap<Integer, BGA_Pin> pins = new TreeMap<>();
		 for (BGA_Row row : this) {
			 for (BGA_Pin pin : row) {
				 pins.put(pin.index(), pin);
			 }
		 }
		 return pins;
	}

	@Override
	public String toString() {
		String s = "<" + kind + ">";
		if (name != null) s += "[" + name + "]";
		return s + super.toString();
	}

	@Override
	public I_Pin getPin(String refDes) {
		IPair p = getBgaPinIndexPair(refDes);
		return get(p.b).get(p.a);
	}
	
	@Override
	public BGA_Pin newPin(BGA_Row row) { return new BGA_Pin(row); }
	
}
