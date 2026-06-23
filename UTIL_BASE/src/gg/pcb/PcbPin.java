package gg.pcb;

import gg.base.text.SPair;

public abstract class PcbPin implements I_Pin {

	private I_Chip chip;
	
	public String name;
	
	@Override public String name() { return name; }

	public String net;

	public PcbPin() {}

	public PcbPin(I_Chip chip) { this.chip = chip; }
	
	@Override
	public I_Chip chip() { return chip; }

	@Override public String net() { return net; }
	
	public abstract String refDes();

}

//@Override
//public String refDes() {
//	// TODO Auto-generated method stub
//	return null;
//}
//
