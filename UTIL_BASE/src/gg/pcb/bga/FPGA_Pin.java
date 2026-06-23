package gg.pcb.bga;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Text.tokenizeString;

import gg.base.text.Text;

public class FPGA_Pin extends BGA_Pin {

	public String  type;
	public String  power;
	public Integer bank;

	public FPGA_Pin() {}

	public FPGA_Pin(BGA_Row row) { super(row); }

}
