package gg.proj.util;

import java.io.File;
import java.util.*;

import gg.base.text.SMap;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.proj.util.L_Project.*;

public interface L_Project {

	String TCL = "tcl";
	String TCL_EXT = "." + TCL;

	String TCL_LINE_COMMENT = "#";
	
	String SRC = "src";
	String RTL = "rtl";
	String VRF = "vrf";
	String INC = "inc";
	
	String XSIM = "xsim";
	
	String VCS = "vcs";
	String VCS_INI = "synopsys_sim.setup";
	String VLOGAN = "vlogan";
	String VHDLAN = "vhdlan";

	String QUESTA = "questa";
	String QUESTA_INI = "modelsim.ini";
	String VLIB = "vlib";
	String VMAP = "vmap";
	String VLOG = "vlog";
	String VCOM = "vcom";
	String VSIM = "vsim";
	String VOPT = "vopt";
	
	String[] QUESTA_COMMANDS_ARRAY = new String[]{VLIB, VMAP, VLOG, VCOM, VSIM, VOPT};
	ArrayList<String> QUESTA_COMMANDS = new ArrayList<>(Arrays.asList(QUESTA_COMMANDS_ARRAY));

	String SECUREIP = "secureip";
	String UNISIMS  = "unisims";
	String UNIMACRO = "unimacro";
	String RETARGET = "retarget";
	
	String[] VIVADO_PRIMITVES_ARRAY = new String[]{UNISIMS, UNIMACRO, RETARGET};
	ArrayList<String> VIVADO_PRIMITVES = new ArrayList<>(Arrays.asList(VIVADO_PRIMITVES_ARRAY));

	String VIVADO_SECUREIP_CELL_LIST = SECUREIP + "_cell.list.f";
	String VIVADO_GLOBAL_MODULE_FILE = "glbl.v";

	String XPM_CDC		= "xpm_cdc";
	String XPM_FIFO		= "xpm_fifo";
	String XPM_MEMORY	= "xpm_memory";
	String[] VIVADO_XPMS_ARRAY = new String[]{XPM_CDC, XPM_FIFO, XPM_MEMORY};
	ArrayList<String> VIVADO_XPMS = new ArrayList<>(Arrays.asList(VIVADO_XPMS_ARRAY));

// see CFG	String VIVADO_HOME = "VIVADO_HOME";
	String VIVADO_DEBUG_PREFIX = "h_dbg";

	static File pwd() { return new File(System.getProperty("user.dir")); }

	String REPO   = "repo"	;
	String BRANCH = "branch";
	
	String MAKE = "make";
	String TOOL = "tool";
	String AMIQ = "amiq";
	String DVT  = "dvt";
	
	String FPGA = "fpga";
	String FPGA_PRJ = FPGA + "_prj"; // Vivado project name	
	String FPGA_TOP = FPGA + "_top"; // Vivado top module name	
	
	String REPO_HOME = "REPO_HOME";	
	String REPO_NAME = "REPO_NAME";	

	String GIT_HOME  = "GIT_HOME";
	String GIT_NAME  = "GIT_NAME";
	
	String FPGA_PROJECT = "FPGA_PROJECT";
	String FPGA_PART	= "FPGA_PART";
	String FPGA_XDBG	= "FPGA_XDBG";
	
	String QUESTA_BIN  = "QUESTA_BIN";
	String VIVADO_QUESTA_LIB  = "VIVADO_QUESTA_LIB";

	SMap envMap  = new SMap();
	SMap pathMap = new SMap();

	static String env(String key) { return envMap.get(key); }

	String XIL_DEFAULTLIB = "xil_defaultlib";
	String XPM = "xpm";
	String XILINX_VIP = "xilinx_vip";

	int LIB_NONE	= 0;
	int LIB_VERILOG	= 1;
	int LIB_VHDL	= 2;
	int LIB_MIXED	= 3;
	
	String ALL = "all";
	String SOURCE = "source";	
	String CREATE = "create";
	String TARGET = "target";
	String SIMULATION = "simulation";
	String SANITY = "sanity";
	String SYNTHESIS = "synthesis";
	String IMPLEMENTATION = "implementation";
	String BIT_STREAM = "bit_dtream";
	String EXPORT_HW = "export_hw";
	
	String XDC = "XDC";
	String IP = "IP";
	String VHDL = "VHDL";
	String HEADER = "Header";
	String VERILOG = "Verilog";
	String SYSTEM_VERILOG = "SystemVerilog";
	String VERILOG_HEADER = VERILOG + HEADER;

}
/*	
	String xmlTail_Args = "_args.xml";
	String xmlTail_Proj = "_proj.xml";

	static String getRepoName(SMap argMap) { return argMap.get(REPO) + "." + argMap.get(BRANCH); }
	
	static String setRepoPath(SMap argMap) { 
		String homePath = argMap.get(HOME);
//		prn("argMap", argMap);
//		prn("\t System.getenv(" + GIT_HOME + ") = " + System.getenv(GIT_HOME) + "\n\t argMap.get(" + HOME + ") = " + homePath);
		if (homePath == null) {
			String sysRepo = System.getenv(GIT_HOME);
			assertion((sysRepo != null), "No environment(+" + GIT_HOME + ")");
			homePath= new File(sysRepo).getParentFile().getAbsolutePath();
		}
//		
//		
//		else {
//			if (!homePath.equals(sysGitHome)) {
//				prn("WARNING: Mismatch \n\t args HOME " + homePath + "\n\t GIT_HOME " + System.getenv(GIT_HOME));
//			}
//		}
		File homeDir  = new File(homePath);
		assertion(homeDir.isDirectory(), "Home directory doesn't exist\n\t" + homePath);
		String repoName = getRepoName(argMap);
		File repoDir  = new File(homeDir, repoName);
		String repoPath = repoDir.getAbsolutePath();
		assertion(repoDir.isDirectory(), "Repository directory doesn't exist\n\t" + repoPath);
		envMap.put(GIT_HOME, repoPath);
		return repoPath;
	}
*/