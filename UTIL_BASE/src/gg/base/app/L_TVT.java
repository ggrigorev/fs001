package gg.base.app;

import static gg.base.util.L_Base.*;
import static gg.proj.util.L_Project.*;
import static gg.vivado.util.L_Vivado.*;

import java.io.File;

import gg.base.text.SMap;

public interface L_TVT {


	String TVT  = "tvt";
//	String KRIA = "kria";

	String PART_TVT = "xczu1cg-sbva484-2-i";
//	String PART_KRIA = "xck26-sfvc784-2LV-c";

	boolean forKria = false;//true;//

	SMap environment = new SMap(
		GIT_HOME			, "C:/AMD.proto",
		GIT_NAME			, "TVT_FPGA",
		FPGA_PROJECT		, TVT,
		FPGA_PART			, PART_TVT,
//FPGA_PROJECT		, forKria ? KRIA : TVT,
//FPGA_PART			, forKria ? PART_KRIA : PART_TVT,
//		FPGA_XDBG			, "",
		QUESTA_BIN			, "C:/questasim64_2021.1/win64",
		VIVADO_QUESTA_LIB	, "C:/AMD.compile_simlib/questa"
	);
	
	File repoDir = new File(environment.get(GIT_HOME), environment.get(GIT_NAME));
	
	File srcDir = new File(repoDir, SOURCE);
	File bldDir = new File(repoDir, BUILD);
	
	File srcVivDir = new File(srcDir, VIVADO);
	File vivTclDir = new File(srcVivDir, TCL);
	File vivTclScript = new File(vivTclDir, TVT + TCL_EXT);

	File vrfDir = new File(srcVivDir, "qvrf");
	File vrfTclDir = new File(vrfDir, "qtcl");

	File bldVivDir = new File(bldDir, VIVADO);
	File synDir = new File(bldVivDir, environment.get(FPGA_PROJECT));

	File bldSimDir = new File(bldDir, SIMULATION);
	File simDir = new File(bldSimDir, environment.get(FPGA_PROJECT));
	
}
