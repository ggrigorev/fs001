package gg.base.app;

import java.io.*;
import java.util.*;
//import java.lang.reflect.Field;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import gg.base.java.*;
import gg.base.text.*;

import gg.base.xml.sch.*;

import static gg.base.java.U_Java.*;
import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Files.*;

import org.jdom2.Element;
import org.jdom2.Attribute;

import static gg.base.util.U_Constructors.*;
import static gg.base.util.U_Classes.*;
import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Properties.*;

import static gg.base.xml.L_XML.*;
import static gg.base.xml.U_XML_COMPARE.*;
import static gg.base.xml.U_XML_IO.*;

import static gg.base.xml.sch.L_XML_DTD.*;
import static gg.base.xml.sch.U_XML_DTD.*;

import static gg.base.xml.sch.U_SCH_XML.*;
import static gg.base.xml.sch.U_XML_SCH.*;
import static gg.proj.util.L_Project.*;
import static gg.vivado.util.L_Vivado.*;
import static gg.vivado.util.U_Vivado.*;

import static gg.base.util.U_Task.*;

import static gg.base.app.L_TVT.*;
import static gg.base.app.U_TVT.*;

public class BuilderTVT {

	public static void parseQuestaTranscript(String[] args) {
		TextParser parser = new TextParser(args[0], args[0], Arrays.copyOfRange(args, 2, args.length - 2));
		parser.build();
		parser.parse();
		System.exit(0);
	}
	public static int buildTVT(String[] scriptArguments) {
		Text response = new Text();
     	if (scriptArguments.length == 0) scriptArguments = new String[]{ALL};
     	prn_("environment", environment);
	    return runVivado(environment, getFilePath(synDir), VIVADO_MODE_BATCH, getFilePath(vivTclScript), response, scriptArguments);		
	}
	
	public static void main(String[] args) {
		args = new String[]{};
//		parseQuestaTranscript(args);
		
		prn_("RUN\n");

		String[] scriptArguments;
		
		scriptArguments = new String[]{SIMULATION, SANITY, SYNTHESIS, IMPLEMENTATION, BIT_STREAM, EXPORT_HW};
//		scriptArguments = new String[]{TARGET};
		scriptArguments = new String[]{CREATE, TARGET, SIMULATION};
//		scriptArguments = new String[]{CREATE, TARGET, SANITY};
//		scriptArguments = new String[]{SIMULATION};

		buildTVT(scriptArguments);
	    
		prn_("\nDONE");
	}
}

/*
 * 		String workPath;
		String scriptPath;

static TMap<Element> writeSchemaObjects_XML(TMap<Schema> objectMap) { // String boardPath, String schemaVerison,
																		// String packgePath,
	TMap<Element> xmlMap = new TMap<>();

	for (String schemaName : VIVADO_SCHEMA_NAMES) {
		Schema topObject = objectMap.get(schemaName);
		Element eSchema = sch2xml(null, topObject);
		xmlMap.put(schemaName, eSchema);
		prn_();
		xmlPrint(eSchema);
	}

	return xmlMap;
}

boolean MAKE;// = true;

public BuilderTVT() {

	String schemaPath_DTD = VIVADO_DATA + "/" + VIVADO_BOARD_DTD;
	String schemaPath_XML = "schema_lib";
	String objectPath_XML = "schema_xml";

	String classPath   = "bin";
	String javaPath    = "src";
	String packagePath = "gg.vivado.boards";

	if (MAKE) {
		prn_("\n\n ================================================ READ: DTD -> XML");

		SMap xmlOfSchemas = dtd2xml(schemaPath_DTD, schemaPath_XML, VIVADO_SCHEMA_NAMES);

		prn_("XML files", xmlOfSchemas);
	}

	if (MAKE) {
		prn_("\n\n ================================================ GENERATOR: DTD XML -> Java");

		SMap javaFiles = xml2java(schemaPath_XML, javaPath, packagePath, VIVADO_SCHEMA_NAMES);
		prn_("Java files ", javaFiles.keySet());

		File f = new File(javaPath, packagePath);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		for (String schemaName : VIVADO_SCHEMA_NAMES) {
			int r = compilePackage(javaPath, packagePath + "." + schemaName, classPath);
		}
	}

	for (String schemaName : VIVADO_SCHEMA_NAMES)
		gg.base.util.U_Classes.addClassPath(packagePath + "." + schemaName);// U_Classes.PACKAGES
//	gg.base.util.U_Classes.addClassPath(gen.gg.vivado.boards.board.Board.class);
	String pcbName = "kr260_som";
	String pcbVerison = "1.1";

	File hubDir = checkSrcDirectory(VIVADO_BOARD_HUB);
	File schemaDir = srcBoardDir(hubDir, pcbName, pcbVerison);

	//schemaDir = new File("board_xml");
	prn_("\n\n ====== BOARD: Source Directory " + schemaDir);

	prn_("\n\n ================================================ READ: XML -> Objects");
	TMap<Element> xmlSrcMap = new TMap<>();
	TMap<Schema> objectMap = new TMap<>();
	xml2sch(schemaDir, packagePath, xmlSrcMap, objectMap, VIVADO_SCHEMA_NAMES);
	prn_("\n objectMap ", objectMap.keySet());
//	if (true)
//		System.exit(0);

	prn_("\n\n ================================================ GENERATOR: Objects -> XML");
	TMap<Element> xmlDstMap = writeSchemaObjects_XML(objectMap);
	prn_("\n xmlDstMap ", xmlDstMap.keySet());
	for (String objectName : xmlDstMap.keySet()) {
		Element eSrc = xmlSrcMap.get(objectName);
		Element eDst = xmlDstMap.get(objectName);
		File dstFile = new File(objectPath_XML, objectName + XML_EXT);
		xmlPrint(eDst, dstFile);
		Text log = new Text();
		SMap map = compareElements(log, eDst, eSrc);
		String s = objectName + " ";
		if (map.isEmpty()) {
			s += "match";
		} else {
			s += "mismatch " + map.size();
			for (String key : map.keySet()) s += "\n    " + key + " = " + map.get(key);
		}
		prn_(s);
		log.add(s);
		File logDir = checkDstDirectory(new File(objectPath_XML, "log"));
		File logFile = new File(logDir, objectName + ".log");
		log.print(logFile);
	}
	
	
	//

}
*/	

//'C:/AMD.proto/TVT_FPGA/build/vivado/tvt/tvt.srcs/sources_1/bd/tvt_zynq_bd/tvt_zynq_bd.bd'
