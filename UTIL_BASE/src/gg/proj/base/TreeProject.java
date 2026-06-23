package gg.proj.base;

import java.io.File;

import gg.base.BaseTree;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Files.*;
import static gg.proj.util.L_Project.*;
import static gg.vivado.util.L_Vivado.*;

public class TreeProject extends BaseTree {

	public File conf;
	public File info;
	
	public TreeProjectSource source;
	public TreeProjectBuild  build;

}

//public void build (BaseProject proj) {
//dvt = checkDstDirectory(new File(proj.home, DVT));
//config = checkDstDirectory(new File(proj.home, CONFIG));
//}
//public final File srcDir;
//public final File srcDir_rtl;
//public final File srcDir_rtl;

