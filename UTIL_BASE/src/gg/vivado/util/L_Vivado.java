package gg.vivado.util;

import static gg.base.util.U_Print.*;
import static gg.proj.util.L_Project.*;
import static gg.base.util.U_Files.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import gg.base.text.SMap;
import gg.base.text.SSet;
import gg.base.text.TMap;

public interface L_Vivado {

	String VIVADO_HOME = System.getenv("VIVADO_HOME");
	String VIVADO_DATA = System.getenv("VIVADO_DATA");

	String VIVADO = "vivado";
	
//	String VIVADO_TOOL_25_2 = "C:/AMDDesignTools/2025.2/Vivado";
//	String VIVADO_TOOL_24_2 = "C:/AMD.tools/Vivado/2024.2";

//	static String VIVADO_TOOL_PATH() { 
//		SMap env = new SMap(System.getenv());
//		prn_("VIVADO_TOOL_PATH() env", env); 
//		prn_("VIVADO_HOME " + VIVADO_HOME); 
//		prn_("env.get(VIVADO_HOME) " + env.get("VIVADO_HOME")); 
//		return System.getenv(VIVADO_HOME); 
//	}
	static File VIVADO_TOOL_DIR() { return new File(VIVADO_HOME); }
	
	static File VIVADO_TOOL_BIN() { return new File(VIVADO_TOOL_DIR(), "bin"); }
	static File VIVADO_TOOL_EXE() { return checkFile(new File(VIVADO_TOOL_BIN(), "vivado.bat")); } 

	String VIVADO_MODE_BATCH = "batch";
	String VIVADO_MODE_GUI   = "gui";
	
	String VIVADO_TOOL_DATA = "data";
	String VIVADO_TOOL_BOARDS = VIVADO_TOOL_DATA + "/xhub/boards/XilinxBoardStore/boards";

	String VIVADO_BRD = "brd";
	String VIVADO_BD  = "bd";
	String VIVADO_IP  = "ip";

	String[] VIVADO_FILE_KINDS_ARRAY = new String[] {ALL, SYNTHESIS, SIMULATION};
	ArrayList<String> VIVADO_FILE_KINDS = new ArrayList<>(Arrays.asList(VIVADO_FILE_KINDS_ARRAY));

	String[] VIVADO_FILE_TYPES_ARRAY = new String[] {XDC, IP, VHDL, VERILOG_HEADER, VERILOG, SYSTEM_VERILOG};
	ArrayList<String> VIVADO_FILE_TYPES = new ArrayList<>(Arrays.asList(VIVADO_FILE_TYPES_ARRAY));

	String VIVADO_SOURCES_TCL    = "sources.tcl";	
	String VIVADO_COMPILE_VCS    = "compile.sh";
	String VIVADO_COMPILE_QUESTA = "_compile.do";

	// boards
	String PIN_DEVICE_INDEX		= "pin_device_index";
	String PIN_DEVICE_REFDES	= "pin_device_refdes";
	String PIN_DEVICE_FUNCTION	= "pin_device_function";

	String PIN_DESIGN_PORT		= "pin_design_port";

	String VIVADO_PIN_LOCATION	= "loc";
	String VIVADO_PACKAGE_PIN	= "PACKAGE_PIN";
	String VIVADO_IO_BANK = "bank";

	String VIVADO_BOARD_DTD = "boards/board_schemas/current";
	String VIVADO_BOARD_HUB = "xhub/boards/XilinxBoardStore/boards/Xilinx";

	File VIVADO_BOARD_HUB_DIR = new File(VIVADO_DATA, VIVADO_BOARD_HUB);
	File VIVADO_BOARD_DTD_DIR = new File(VIVADO_DATA, "boards");

	String VIVADO_SCHEMA_NAME_BOARD      = "board";
	String VIVADO_SCHEMA_NAME_PART0_PINS = "part0_pins";
	String VIVADO_SCHEMA_NAME_PRESET     = "preset";

	SSet VIVADO_SCHEMA_NAMES = new SSet(VIVADO_SCHEMA_NAME_BOARD, VIVADO_SCHEMA_NAME_PART0_PINS, VIVADO_SCHEMA_NAME_PRESET);

}

//SSet VIVADO_SCHEMA_NAMES = new SSet(VIVADO_SCHEMA_NAME_PRESET);

//TMap<Class<?>> VIVADO_SCHEMA_MAP = new TMap<>();

//SSet SCHEMA_VIVADO_NAMES = new SSet("preset");

//File srcDir = checkSrcDirectory(new File(VIVADO_BOARDS_DIR, srcPath));

