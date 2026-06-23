package gg.vivado.base;

import java.io.File;

import gg.base.text.Text;

import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;

public class Vivado_BD {
	
	static int VIVADO_MAJOR_LIMIT = 2020;
	static int VIVADO_MINOR_LIMIT = 1;

	public final VivadoProject project;
	
	public File srcFile;
	public String name;

	public int major;
	public int minor;

	public String version() { 
		if (major < VIVADO_MAJOR_LIMIT) return null;
		if (minor < VIVADO_MINOR_LIMIT) return null;
		return major + "." + minor; 
	}

	public File gen_bd;
	public File gen_ip;
	public File gen_ipshared;
	public File users_bd;
	
	// IP .xci         
	// "SWVERSION": [ { "value": "2023.2_AR000037142_AR000036122" } ],
	
	// BD .tcl 
	// set scripts_vivado_version 2025.1
	// set design_name proton_ecs_axi4_bd

	public Vivado_BD(VivadoProject proj, File f) {
		project = proj;
		srcFile = f;
		if (f.isFile()) {
			Text src = new Text(srcFile, true, true);
			while (!src.isEmpty()) {
				Text st = tokenizeString(src.remove(0));
				if (st.size() == 3) {
					if (st.remove(0).equals("set")) {
						String s = st.remove(0);
						if (s.equals("scripts_vivado_version")) {
							s = st.remove(0);
							String[] ss = s.split(".");
							major = Integer.parseInt(ss[0]);
							minor = Integer.parseInt(ss[1]);
						}
						if (s.equals("design_name")) {
							name = st.remove(0);
							break;
						}
					}
				}
			}
		} else {
			String s = f.getName();
prn("Vivado_BD s = '" + s + "'");
			s.substring(0, s.lastIndexOf(".")); // ".tcl"
			int p = s.lastIndexOf("_v.");
			if (p > 0) {
				String t = s.substring(p+3);
				if ((t.charAt(4) == '.') && (t.length() == 6)) 	try {
					major = Integer.parseInt(t.substring(0, 4));
					minor = Integer.parseInt(t.substring(5));
					name = s.substring(0, p);
				} catch (Exception ex) { assertion(ex, "Invalid Vivado BD file name format: " + f.getName()); }
			} else {
				name = s;
			}
		}
		
//		gen_bd = new File(project.tree.genDir_bd, name);
//		gen_ip = new File(gen_bd, "ip");
//		gen_ipshared = new File(gen_bd, "ipshared");
//		users_bd = new File(project.tree.usrDir_bd, name);
//		
//		project.BDs.put(name, this);
	}
}
