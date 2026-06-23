package gg.pcb;

import java.util.TreeMap;

public interface I_Chip extends I_Name {

	public int size_x();
	public int size_y();
		
	public I_Pin getPin(String refDes);

	public int pinIndex(I_Pin pin);

	default int size() { return size_x() * size_y(); }

	public <T extends I_Pin> TreeMap<Integer, T> pins();
	
}
