package gg.base.java;

import java.io.*;
import java.util.*;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.jdom2.Element;

import gg.base.text.*;
import gg.base.xml.sch.UnitLibrary;

import static gg.base.java.L_Java.*;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Files.*;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Classes.*;

import static gg.base.xml.U_XML_IO.*;
//import static gg.vivado.util.L_Vivado.VIVADO_SCHEMA_NAMES;

public interface U_Java {

	String defaultTitleSeparator = "_";
	
	static int compilePackage(String srcPath, String pkgName, String dstPath) {
		srcPath = getFilePath(checkSrcDirectory(getPackagePath(srcPath, pkgName))); // absolute canonical existing directory
		dstPath = getFilePath(checkDstDirectory(dstPath)); // absolute canonical existing directory
		File srcDir = new File(srcPath);
		SList cmd = new SList("-d", dstPath);
		for (File file : srcDir.listFiles()) {
			String filePath = getFilePath(file);
			if (filePath.endsWith(JAVA_EXT)) cmd.add(filePath);
		}
		prn_("cmd", cmd);
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int r = compiler.run(null, null, null, cmd.toArray(new String[0]));
		prn_("U_Java.compilePackage: returns " + r + "\n  SRC> " + srcPath + "\n  DST> " + dstPath);
		return r;
	}

	static File getPackageDir(String path, String pkgName) {
		return new File(getPackagePath(path, pkgName));
	}
	
	static String getPackagePath(String path, String pkgName) {
		return getPackagePath(new File(path), pkgName);
	}

	static File getPackageDir(File dir, String pkgName) {
		return new File(getPackagePath(dir, pkgName));
	}
	
	static String getPackagePath(File dir, String pkgName) {
		String pkgPath = pkgName.replaceAll("\\.", "/");
		File pkgDir = new File(dir, pkgPath);
		try {
			return getFilePath(new File(pkgDir.getCanonicalPath()));
		} catch (Exception ex) {
		}
		return null;
	}

	static SMap xml2java(String srcXmlPath, String dstJavaPath, String javaPackagePath, Collection<String> libNames) {
		SMap files = new SMap();
		File srcDir = checkSrcDirectory(srcXmlPath);
		File dstDir = checkDstDirectory(dstJavaPath);

		for (String libName : libNames) {
			String srcFileName = libName + ".lib.xml";
			File srcFile = new File(srcDir, srcFileName);
			UnitLibrary library = new UnitLibrary(javaPackagePath, libName);
			Element eLibrary = xmlFromFile(srcFile);
			library.addClasses(eLibrary);
			writeJavaUnits(dstDir, library, files);
		}

		return files;
	}

	static String getJavaClassName(String title) {
		return getJavaClassName(title, defaultTitleSeparator);
	}

	static String getJavaClassName(String title, String separator) {
		Text tt = tokenizeString(title, separator);
		for (int i = 0; i < tt.size(); i++) {
			String s = tt.removeFirst();
			String c = s.substring(0, 1);
			s = c.toUpperCase() + s.substring(1);
			tt.add(s);
		}
		return tt.join("", false);
	}

	static void writeJavaUnit(File dstDir, SMap items, SMap files) {
		String s;

		SList keys = new SList(items.keySet());
		String firstKey = keys.removeFirst();
		String firstVal = items.remove(firstKey);
		ItemTriplet item = new ItemTriplet(firstKey, firstVal); // package

		assertion(item.type.equals(PACKAGE), "Invalid item type '" + item.type + "', expected '" + PACKAGE + "'");

		Text dst = new Text("/*\n", firstKey, "");

		if (item.value != null)
			dst.add(item.value.trim() + "\n");

		for (String key : items.keySet()) {
			String val = items.get(key);
			if (val != null)
				dst.add(TAP + key + " = " + val);
			else
				dst.add(TAP + key);
		}
		dst.add("\n*/\n");

//prn_(dst);	brk();
		String pkgName = item.name;
		dst.add(PACKAGE + " " + pkgName + ";");

		dst.add("");

		int n = dst.size();
		while (true) {
			String key = keys.removeFirst();
			item = new ItemTriplet(key, items); // imports or class
			if (!item.type.equals(IMPORT))
				break;
			s = item.value == null ? "*" : item.value;
			dst.add(IMPORT + " " + item.name + "." + s + ";");
		}

		if (dst.size() > n)
			dst.add("");

		assertion(item.type.equals(CLASS) || item.type.equals(INTERFACE),
				"Invalid item type '" + item.type + "', expected '" + CLASS + "' or '" + INTERFACE + "'");
		String unitName = item.name;
		s = PUBLIC + " " + item.type + " " + item.name + " ";
//		s = item.type + " " + item.name + " ";
		if (item.value != null)
			s += item.value + " ";
		dst.addAll(s + "{", "");

//		if (item.value != null) dst.add("private static final long serialVersionUID = 1L;"); List extension

		while (!keys.isEmpty()) {
			String key = keys.removeFirst();
			item = new ItemTriplet(key, items); // imports or class
//prn_(key + " --- " + item.type + ", " + item.name + ", " + item.value);			
//			if (item.type.equals(CONSTRUCTOR)) {
////				dst.add("");
//				s = PUBLIC + " " + unitName + "() { super(" + item.value + "); }";
//				dst.add(s);
//			} else if (item.type.equals(TEXT)) {
//				dst.add(item.value);
//			} else 

			if (item.type.startsWith(PRIVATE)) {
//				dst.add("");
				s = item.type + " " + item.name + " = " + item.value + ";";
				dst.add(s);
			} else {// if (isJavaPrimitiveName(item.type)) {
				s = PUBLIC + " " + item.type + " " + item.name + " = " + item.value + ";";
				dst.add(s);
			}
		}

		if (dst.size() > n)
			dst.add("");

		dst.addAll("}", "");

		String dstJavaPath = getPackagePath(dstDir, pkgName);
		File dstJavaDir = checkDstDirectory(dstJavaPath);
		File dstFile = new File(dstJavaDir, unitName + ".java");
		dst.print(dstFile);

		files.put(unitName, getFilePath(dstFile));
	}

	static void writeJavaUnits(String dstPath, List<SMap> units, SMap files) {
		writeJavaUnits(checkDstDirectory(dstPath), units, files);
	}

	static void writeJavaUnits(File dstDir, List<SMap> units, SMap files) {

		String s;
		for (SMap items : units) {
			writeJavaUnit(dstDir, items, files);
//			prn_("items", items);
//			brk();
		}

	}

}

//prn_("isJavaPrimitiveName(" + item.type + ") = " + isJavaPrimitiveName(item.type), JAVA_PRIMITIVE_NAME_MAP);
//if (!isJavaPrimitiveName(item.type)) break;
//if (item.value != null) if (!isPrimitiveValue(item.value)) {
//	s = item.value;
//	if (s.startsWith("(") && s.endsWith(")")) { // group
//		s = s.substring(1, s.length() - 1).trim();
//		String[] ss = s.split("|"); 
//	}
//	Text tt = tokenizeString(item.value);
//	prn_("-------------- NOT isPrimitiveValue " + unitName + "." + item.name  + " = " + item.value);
//} else {
//	item.value = null;
//}

//static String getJavaObjectName(String title, String separator) {
//	return getJavaObjectName(getJavaClassName(title, separator));
//}
//
//static String getJavaObjectName(String className) {
//	return className.substring(0, 1).toLowerCase() + className.substring(1);
//}
//
//static String formatXML(String s) {
//	int p = s.indexOf(">") + 1;
//	Text head = tokenizeString(s.substring(0, p));
//	s = s.substring(p);
//	p = s.lastIndexOf("</");
//	Text body = tokenizeString(s.substring(0, p));
//	body.insertHead(TAP + TAP);
//	String tail = s.substring(p);
//	s = head.removeFirst();
//	if (!head.isEmpty()) {
//		String t = head.removeLast();
//		head.add(t.substring(0, t.length() - 1));
//		head.insertHead(TAP + TAP);
//		head.add(TAP + ">");
//	}
//	head.add(0, s);
//	return TAP + head + "\n" + body + "\n" + TAP + tail;
//}

//
//static String getJavaClassTitle(String className) {
//	return getJavaClassTitle(className, defaultTitleSeparator); 
//}
//
//static String getJavaClassTitle(String className, String separator) {
//	String s = getJavaObjectName(className); // first letter lower case
//	int n = s.length();
//	String r = "";
//	for (int i = 0; i < n; i++) {
//		String L = s.substring(i, i + 1);
//		if (Character.isUpperCase(s.charAt(i))) L = separator + L.toLowerCase();				
//		r += L;
//	}
//	return r;
//}
