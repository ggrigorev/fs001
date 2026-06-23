package gg.pcb.bga;

import static gg.base.util.U_Text.leadZero;
import static gg.pcb.bga.U_BGA.getBgaPinIndexPair;
import static gg.pcb.bga.U_BGA.getBgaPinRefDes;
import static gg.pcb.bga.U_BGA.getBgaRowName;

import static gg.base.util.U_Fields.CSV_DELIMITER;
import static gg.base.util.U_Fields.toCSV;
import static gg.base.util.U_Fields.getFieldName;

import static gg.base.util.U_Print.*;

import gg.base.IPair;
import gg.base.text.SList;
import gg.base.text.SPair;
import gg.base.text.SSet;
import gg.pcb.PcbPin;

public class BGA_Pin extends PcbPin {
	
	public BGA_Row row;
	public int x;

	SList excludeCSV = new SList("row", "x");

	public BGA_Pin() {}

	public BGA_Pin(BGA_Row row) {
		super(row.matrix);
		this.row = row;
		x = row.size();
	}

	public IPair position() { return new IPair(x, row.y); }
	
	public String refDes() { return getBgaPinRefDes(position()); }

	@Override
	public String toString() {
		String ref = refDes();
		IPair  pos = position();
		IPair  rev = getBgaPinIndexPair(ref);
		boolean OK = pos.equals(rev);//pos.toString().equals(rev.toString());
		String s = "BGA Pin " + ref + " (col " + leadZero(x, 2) + ", row " + leadZero(row.y, 2) + ")";// in Matrix(" + row.matrix.width + ", " + row.matrix.hight + ")";
//		s += ", reverse " + rev + ", match " + OK;
		return s;
	}

	public SPair getCSV() { return getCSV(null); }

	public SPair getCSV(SSet exclude) {
		
		if (exclude == null) exclude = new SSet();
		exclude.addAll(excludeCSV);
		
		String refDes = refDes();
		String r = getBgaRowName(refDes);			
		IPair  p = getBgaPinIndexPair(refDes);	
		
		SPair sp = new SPair();
		sp.a = "index";
		sp.b = String.format("%d", index());	
		
		sp.a += CSV_DELIMITER + "refDes";
		sp.b += String.format(CSV_DELIMITER + "%s", refDes);

		sp.a += CSV_DELIMITER + "refRow";
		sp.b += String.format(CSV_DELIMITER + "%s", r);

		sp.a += CSV_DELIMITER + "row";
		sp.b += String.format(CSV_DELIMITER + "%d", p.b);		//	Row, Column

		sp.a += CSV_DELIMITER + "column";
		sp.b += String.format(CSV_DELIMITER + "%d", p.a);		//	Row, 
		
		sp.append(toCSV(this, exclude), CSV_DELIMITER);
//prn_(sp.a);		
//prn_(sp.b);		
//brk();
		return sp;
	}
}


//@Override
//public I_Chip chip() { return row.matrix; }
//
//@Override
//public String name() { return name; }
//
//@Override
//public String net() { return net; }


//@Override
//public int pinIndex(I_Pin pin) {
//	// TODO Auto-generated method stub
//	return 0;
//}
