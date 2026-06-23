package gg.vivado.util;

import static gg.base.util.U_Task.*;
import static gg.base.util.U_Files.*;
import static gg.base.xml.L_XML.XML_EXT;
import static gg.base.xml.U_XML_IO.xmlFromFile;
import static gg.base.xml.sch.U_XML_SCH.xml2sch;
import static gg.vivado.util.L_Vivado.VIVADO_BOARD_HUB_DIR;
import static gg.vivado.util.L_Vivado.VIVADO_SCHEMA_NAME_BOARD;
import static gg.vivado.util.L_Vivado.VIVADO_SCHEMA_NAME_PART0_PINS;
import static gg.vivado.util.L_Vivado.VIVADO_SCHEMA_NAME_PRESET;
import static gg.vivado.util.L_Vivado.VIVADO_TOOL_EXE;

import java.io.File;

import org.jdom2.Element;

import gg.base.text.SList;
import gg.base.text.SMap;
import gg.base.text.Text;
import gg.vivado.boards.*;

import gg.vivado.boards.board.Board;
import gg.vivado.boards.board.Component;
import gg.vivado.boards.board.Components;
import gg.vivado.boards.part0_pins.PartInfo;
import gg.vivado.boards.preset.IpPresets;

public interface U_Vivado {

	String BOARD_PACKAGES = "gg.vivado.boards";

	static File srcBoardDir(File hubDir, String boardName, String boardVerison) {
		File dir = new File(hubDir, boardName);
		dir = new File(dir, boardVerison);
		dir = checkSrcDirectory(dir);
		return dir;
	}
	//VIVADO_BOARD_HUB_DIR
	static void readXmlBoardPart(VivadoPCB pcb, File hubDir, String partName, String partVersion) {
		readXmlBoardPart(pcb, srcBoardDir(hubDir, partName, partVersion));
	}
	
	static void readXmlBoardPart(VivadoPCB pcb, File srcDir) { // Vivado hub + part name + version
		File boardFile = new File(srcDir, VIVADO_SCHEMA_NAME_BOARD + XML_EXT);
		boardFile = checkFile(boardFile);
		Element eBoard = xmlFromFile(boardFile);
		pcb.board = (Board) xml2sch(eBoard, BOARD_PACKAGES, VIVADO_SCHEMA_NAME_BOARD);
		
		if (pcb.board.preset_file != null) {
			File presetFile = new File(srcDir, pcb.board.preset_file);
			presetFile = checkFile(presetFile);
			Element ePreset = xmlFromFile(presetFile);
			pcb.presets = (IpPresets) xml2sch(ePreset, BOARD_PACKAGES, VIVADO_SCHEMA_NAME_PRESET);
		}
		
		for (Components components : pcb.board.components) {
			for (Component component : components.component) {
				if (component.pin_map_file != null) {
					File partFile = new File(srcDir, component.pin_map_file);
					partFile = checkFile(partFile);
					Element ePart = xmlFromFile(partFile);
					PartInfo partInfo = (PartInfo) xml2sch(ePart, BOARD_PACKAGES, VIVADO_SCHEMA_NAME_PART0_PINS);
					pcb.parts.put(component.name, partInfo);
				}
			}			
		}
	}
/*
<tool>/bin/vivado.bat 

Syntax:
vivado  [-mode <arg>] [-init] [-source <arg>] [-script <arg>] [-nojournal]
        [-appjournal] [-journal <arg>] [-nolog] [-applog] [-log <arg>]
        [-version] [-tclargs <arg>] [-tempDir <arg>] [-verbose] [<project>]

Usage:
  Name           Description
  --------------------------
  [-mode]        Invocation mode, allowed values are 'gui', 'tcl', and 'batch'. Default: gui
  [-init]        Source vivado.tcl file
  [-source]      Source the specified Tcl file
  [-script]      Execute the specified script file
  [-nojournal]   Do not create a journal file
  [-appjournal]  Open journal file in append mode
  [-journal]     Journal file name. Default: vivado.jou
  [-nolog]       Do not create a log file
  [-applog]      Open log file in append mode
  [-log]         Log file name. Default: vivado.log
  [-version]     Output version information and exit
  [-tclargs]     Arguments passed on to tcl argc argv
  [-tempDir]     Temporary directory name.
  [-verbose]     Suspend message limits during command execution
  [<project>]    Load the specified project (.xpr) or design checkpoint (.dcp) file
 */
    public static int runVivado(SMap environment, String workPath, String mode, String scriptPath, Text response, String... scriptArguments) {
    	File exe = checkFile(VIVADO_TOOL_EXE());
    	File directory = checkDstDirectory(workPath);
     	
    	String xpr = environment.get("FPGA_PROJECT") + ".xpr"; // project file name
     	if (!(new File(directory, xpr)).isFile()) xpr = null;
     	
    	SList command = new SList(getFilePath(exe)); 
    	if (mode != null) command.addAll("-mode", mode);
    	
    	if (scriptPath != null) {
    		File tcl = checkFile(new File(scriptPath)); 
    		command.addAll("-script", getFilePath(tcl));
    	}
     	if (scriptArguments.length > 0) {
       		command.add("-tclargs");
       		command.addAll(scriptArguments);
    	}
     	
    	if (xpr != null) command.add(xpr);
       	return run(response, environment, directory, command);
    }
    
//    public static int runVivadoGui(Text response, SMap environment, String workPath, String projectName) {
//    	File exe = checkFile(VIVADO_TOOL_EXE());
//    	File directory = checkDstDirectory(workPath);
//    	SList command = new SList(
//    			getFilePath(exe), 
//    			"-mode", "batch",
//                "-source", 
//                getFilePath(tcl)
//    			);
//    	if (scriptArguments.length > 0) {
//       		command.add("-tclargs");
//       		command.addAll(scriptArguments);
//    	}
//       	return run(response, environment, directory, command);
//    }
//    

}
