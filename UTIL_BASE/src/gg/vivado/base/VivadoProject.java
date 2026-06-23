package gg.vivado.base;

import java.io.*;
import java.util.*;

import gg.base.text.*;
import gg.proj.base.*;
//import gg.vivado.Vivado_BD;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.proj.util.L_Project.*;
import static gg.base.util.U_Files.*;
import static gg.vivado.util.L_Vivado.*;

public class VivadoProject extends BaseProject {

	public final File buildDir;

	public final File vivadoWorkDir;
	public final File questaWorkDir;

	public final File dvtProjectDir; // DVT project dir

	public final VivadoTree tree;
	
	public final TMap<Vivado_BD> BDs = new TMap<>();
	
	public VivadoProject(String name, String vivadoToolDirPath, String projectDirPath) {
		this(name, new File(vivadoToolDirPath), new File(projectDirPath));
	}
	
	public VivadoProject(String name, File vivadoToolDir, File repoDir) {
		super(name, new File(repoDir, name));

		buildDir = new File(homeDir, BUILD);
		
		vivadoWorkDir = checkDstDirectory(new File(buildDir, VIVADO));
		questaWorkDir = checkDstDirectory(new File(buildDir, QUESTA));
		dvtProjectDir = checkDstDirectory(new File(buildDir, AMIQ + "_" + name.toUpperCase()));
		
		tree = new VivadoTree(this, vivadoToolDir); 
	}

	@Override
	public void build() {
		super.build();
		if (!homeDir.isDirectory()) homeDir.mkdirs();
		tree.build();
	}

	@Override
	public String toString() {
		String s = "Vivado " + super.toString();
		s += "\n" + tree + "\n";
		s += "\n Questa = <" + (questaWorkDir.exists()  ? "+" : "-") + "> " + getRelativeFilePath(buildDir, questaWorkDir);	
		s += "\n DVT    = <" + (dvtProjectDir.exists()  ? "+" : "-") + "> " + getRelativeFilePath(buildDir, dvtProjectDir);	
		return s;
	}

}
