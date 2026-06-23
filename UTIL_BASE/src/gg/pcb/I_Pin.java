package gg.pcb;

import gg.base.text.SPair;
import gg.base.text.SSet;

import static gg.base.util.U_Fields.toCSV;

public interface I_Pin extends I_Name {

	public I_Chip chip();
	
	public String refDes(); // primary

	public String net();

	default int index() { return chip().pinIndex(this); }

	default SPair pcbPtr() { return new SPair(chip().name(), "" + index()); }
	default SPair pcbRef() { return new SPair(chip().name(), refDes()); }

	default SPair getCSV(SSet exclude) { return toCSV(this, exclude); }

}
