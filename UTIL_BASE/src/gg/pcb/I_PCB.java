package gg.pcb;

import gg.base.text.*;

public interface I_PCB extends I_Name {
	public TMap<I_Chip>  chips();
	public TMap<SPairList> nets(); // key = net.name, value = list of connected pins pairs chip.name + pin.refDes OR pin.index
}
