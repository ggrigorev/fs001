package gg.pcb.bga;

import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.leadZero;

import gg.base.IPair;
import gg.base.text.SList;

import static gg.pcb.bga.L_BGA.*;

public interface U_BGA {

	static BGA_Pin getBgaPin(BGA_Chip bga, String refDes) {
		IPair p = getBgaPinIndexPair(refDes);
		BGA_Pin pin = bga.get(p.a).get(p.b);
		return pin;
	}
	
	static String getBgaColName(int x) {
		return getBgaColName(x, 99);
	}
	
	static String getBgaColName(int x, int w) {
		return leadZero(x+1, (int) Math.ceil(Math.log10(w)));
	}
	
	static String getBgaRowName(int y) {
//prn_("rowCharList", rowCharList);
		String s = "";
		int r = y /BGA_Chip.rowCharList.size();
//prn_("BGA_RowCharMap.getRowName("+ y + ") r = " + r); //brk();
		if (r > 0) s += getBgaRowName(r - 1);
		int m = y % BGA_Chip.rowCharList.size();
		s += BGA_Chip.rowCharList.get(m);
		return s;
	};

	static int getBgaPinIndex(String refDes, int width) { // number of columns in BGA
		IPair p = getBgaPinIndexPair(refDes);
		int i = p.b * width + p.a;
//prn_(refDes + " " + p + " " + i);
		return i;
	}

	static String getBgaRowName(String refDes) {
		String s = refDes;
		String row = "";
		while(!Character.isDigit(s.charAt(0))) {
			row += s.substring(0, 1);
			s = s.substring(1);
		}
		return row;
	}

	static int[] getBgaRowIndex(String refDes) { // number of columns in BGA
		int n = 0;
		int row = 0;
		String s = refDes;
		while(!s.isEmpty() & !Character.isDigit(s.charAt(0))) {
			String chr = s.substring(0, 1);
			s = s.substring(1);
			row *= BGA_Chip.rowCharList.size();
			row += BGA_Chip.rowCharList.indexOf(chr) + 1;
			n++;
//prn_(n + " " + refDes + ", chr '" + chr + "', row " + row);
		}
		row--;
		return new int[]{row, n};
	}

	static IPair getBgaPinIndexPair(String refDes) {
		int[] ii = getBgaRowIndex(refDes);
		int row = ii[0];
		String s = refDes.substring(ii[1]);
		int col = Integer.parseInt(s) - 1;
		IPair p = new IPair(col, row);
//prn_(refDes + " " + p);
		return p;
	}

	static String getBgaPinRefDes(int x, int y) { return getBgaPinRefDes(new IPair(x, y)); }

	static String getBgaPinRefDes(IPair p) {
		return getBgaRowName(p.b) + getBgaColName(p.a);
	}

}
