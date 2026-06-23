package gg.pcb.bga;

import java.util.ArrayList;

import gg.base.IPair;
import gg.pcb.I_Chip;
import gg.pcb.I_Pin;

import static gg.pcb.bga.U_BGA.getBgaPinIndexPair;
import static gg.pcb.bga.U_BGA.getBgaRowIndex;
import static gg.base.util.U_Print.prn_;
import static gg.pcb.bga.U_BGA.getBgaPinIndex;

public abstract class BGA_Matrix extends ArrayList<BGA_Row> implements I_Chip {

	private static final long serialVersionUID = 1L;

	public final String kind = getClass().getSimpleName();

	private int width;
	private int hight;

	public BGA_Matrix() {prn_("BGA_Matrix hight " + hight);}
	
	public BGA_Matrix(int n) {prn_("BGA_Matrix hight " + hight);}// this(n, n); }
	
	public BGA_Matrix(int width, int hight) {
		this.hight = hight;
		this.width = width;
		for(int y = 0; y < hight; y++) new BGA_Row(this);
	}

	public BGA_Row getRow(String refDes) { return get(getBgaRowIndex(refDes)[0]); }

	static boolean deepString = false;// true;

	@Override
	public String toString() {
		String s = "Chip" + kind + "(.width(" + size_x() + "), .hight(" + size_x() + "), size = " + size();
		if (deepString) for (BGA_Row row : this) s+= "\n\t" + row.toString();
		return s;
	}
	
	@Override public int pinIndex(I_Pin pin) { return getBgaPinIndex(pin.refDes(), width); }

	@Override public int size_x() { return width; }

	@Override public int size_y() { return hight; }

//	public abstract BGA_Pin newPin(int y, int x);
//
	@Override
	public I_Pin getPin(String refDes) { 
		IPair p = getBgaPinIndexPair(refDes);
		BGA_Row row = get(p.b);
		BGA_Pin pin = row.get(p.a);
		return pin; 
	}

	public abstract BGA_Pin newPin(BGA_Row row);
	
}
