package gg.pcb.bga;

public class FPGA_Chip extends BGA_Chip {

	private static final long serialVersionUID = 1L;

	public FPGA_Chip(String name, int n) { this(name, n, n); }

	public FPGA_Chip(String name, int x, int y) { super(name, x, y); }

	@Override
	public BGA_Pin newPin(BGA_Row row) { return new FPGA_Pin(row); }
	
}
