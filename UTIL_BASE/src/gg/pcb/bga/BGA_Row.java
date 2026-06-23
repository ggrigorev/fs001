package gg.pcb.bga;

import static gg.base.util.U_Text.leadZero;
import static gg.pcb.bga.U_BGA.getBgaRowName;

import java.util.ArrayList;

public class BGA_Row extends ArrayList<BGA_Pin> {

	private static final long serialVersionUID = 1L;

	public BGA_Matrix matrix;
	
	public int y;

	public BGA_Row(BGA_Matrix matrix) {
		this.matrix = matrix;
		this.y = matrix.size();
		matrix.add(this);
		for(int x = 0; x < matrix.size_x(); x++) add(matrix.newPin(this));			
	}

	static boolean deepString = false;//true;
	
	@Override
	public String toString() {
		String s = "BGA Row[" + leadZero(this.y, 2) + "] = '" + getBgaRowName(this.y) + "'";//, Matrix(" + matrix.size_x() + ", " + matrix.size_y() + ")";
if (deepString) for (BGA_Pin pin : this) s+= "\n\t\t" + pin.toString();
		return s;
	}
	
}
