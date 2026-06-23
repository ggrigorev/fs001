package gg.proj.base;

import java.io.File;

import gg.base.Base;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Files.*;
import static gg.base.util.U_Print.*;

import static gg.proj.util.L_Project.*;
import static gg.vivado.util.L_Vivado.*;

public class BaseProject extends Base {

	public static boolean BASE_DEBUG = true;

	public File homeDir;

	public TreeProject tree = new TreeProject();
	
	public BaseProject(String name, String projectDirPath) {
		this(name, new File(projectDirPath));
	}
	
	public BaseProject(String name, File homeDir) {
		super(name);		
		this.homeDir = homeDir;
	}
	
	@Override
	public void build() {
		super.build();
		if (!homeDir.isDirectory()) homeDir.mkdirs();
		tree.build();
	}

	public static void main(String[] args) {

		prn_("RUN\n");

		BaseProject proj = new BaseProject("AA", "proj");
		proj.build();
prn_("PROJECT", proj.toString());
		prn_("\nDONE");
	}

}
/*
//	public final File dvtDir;
//	public final File configDir;
//	public final File infoDir;
//
//	public final File srcDir;
//	public final File srcDirRtl;
//	public final File srcDirVrf;
//
//	public final File srcVivadoDir;
//	public final File srcVivadoTCL;
//	public final File srcVivadoIP;
//	public final File srcVivadoBD;
//	public final File srcVivadoXDC;
//	public final File srcVivadoBRD;
//		
//	public final File srcQuestaDir;
//	public final File srcQuestaTCL;
//	public final File srcQuestaINC;
//
//	public final File buildDir;

	//	

	public BaseProject(String name, String projectDirPath) {
		this(name, new File(projectDirPath));
	}
	
	public BaseProject(String name, File projectDir) {
		this.name = name;		
		homeDir = projectDir;
		
		srcDir = new File(homeDir, SOURCES);
		
		srcDirRtl = checkDstDirectory(new File(srcDir, RTL));
		srcDirVrf = checkDstDirectory(new File(srcDir, VRF));

		srcVivadoDir = new File(srcDir, VIVADO); // Tool specific
		srcVivadoTCL = checkDstDirectory(new File(srcVivadoDir, TCL));
		srcVivadoXDC = checkDstDirectory(new File(srcVivadoDir, XDC));
		srcVivadoIP  = checkDstDirectory(new File(srcVivadoDir, VIVADO_IP));
		srcVivadoBD  = checkDstDirectory(new File(srcVivadoDir, VIVADO_BD));
		srcVivadoBRD = checkDstDirectory(new File(srcVivadoDir, VIVADO_BRD));

		srcQuestaDir = new File(srcDir, QUESTA); // Tool specific
		srcQuestaTCL = checkDstDirectory(new File(srcVivadoDir, TCL));
		srcQuestaINC = checkDstDirectory(new File(srcVivadoDir, INC));

	}

	@Override
	public String toString() {
		String s = "\nProject <" + name + "> " + homeDir;
		s += "\n    rtl = <" + (srcDirRtl.exists()    ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcDirRtl);	
		s += "\n    vrf = <" + (srcDirVrf.exists()    ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcDirVrf);	
		s += "\n    tcl = <" + (srcVivadoTCL.exists() ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoTCL);	
		s += "\n    brd = <" + (srcVivadoBRD.exists() ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoBRD);	
		s += "\n    xdc = <" + (srcVivadoXDC.exists() ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoXDC);	
		s += "\n     ip = <" + (srcVivadoIP.exists()  ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoIP);	
		s += "\n     bd = <" + (srcVivadoBD.exists()  ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoBD);	
		return s;
	}

}


//public BaseProject(String path) {
//	srcDir = new File(homeDir, SOURCES);
//	srcDirRtl = checkDstDirectory(new File(srcDir, RTL));
//	srcDirVrf = checkDstDirectory(new File(srcDir, VRF));
//	tree = getTree(path);
//}
//public BaseTree getTree(String path) {
//	// TODO Auto-generated method stub
//	return new BaseTree(path);
//}

*/