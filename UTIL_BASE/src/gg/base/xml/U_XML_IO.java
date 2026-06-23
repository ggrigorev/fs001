package gg.base.xml;

import static gg.base.util.L_Base.ID;
import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Print.stdOut;
import static gg.base.util.U_Text.tokenizeString;
import static gg.base.xml.L_XML.XML_EXT;
import static gg.base.xml.U_XML_EXPORT.*;

//import gg.base.top.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import gg.base.text.Text;

public interface U_XML_IO { // XML_GROUP

	SAXBuilder xin = new SAXBuilder();
	XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());

	static Document xmlDocFromFile(String path) { return xmlDocFromFile(new File(path)); } 
	static Document xmlDocFromFile(File f) { try { return xin.build(f); } catch (Exception ex) { assertion(ex, ""); return null; } }
	
	static Element xmlFromFile(String path) { return xmlFromFile(new File(path)); }

	static Element xmlFromFile(File f) {
		if (f == null) return null;
		if (!f.isFile()) return null;
		Format format = xout.getFormat();
		Element e = null;
		try { e = xin.build(f).detachRootElement(); } catch (Exception ex) {
			assertion(ex, "");
		}
		return e;
	}

	static File xmlToFile(Element e) { return xmlToFile(e, null); }

	static String xmlFileName(Element e) {
		String classId = e.getName();
		String id = e.getAttributeValue(ID);
		if (id == null) return classId + XML_EXT;
		return id + "." + classId;
	}

	static File xmlToFile(Element e, File f) {
		// Util.prn("<XML_IO>.xmlToFile: input file " + f);
		if (f == null) {
			f = new File(xmlFileName(e));
		} else if (f.isDirectory()) {
			f = new File(f, xmlFileName(e));
		}
		try { 
			FileOutputStream fos = new FileOutputStream(f);
			xout.output(e, fos); 
			fos.close();
		} catch (Exception ex) { 
			assertion(ex, "");
		}
		// fos.write("\n");
		// Util.prn("<XML_IO>.xmlToFile: file " + f);
		return f;
	}

	static Element xmlFromStream(InputStream is) {
		Element e = null;
		try { e = xin.build(is).detachRootElement(); } catch (Exception ex) { 
			assertion(ex, "");
		}
		return e;
	}

	static void xmlToStream(Element e, OutputStream os) {
		try { xout.output(e, os); } catch (Exception ex) { 
			assertion(ex, "");
		}		
	}

	static void xmlPrint(Element e, File f) {
		try { xout.output(e, new PrintStream(f)); } catch (IOException ex) {
			assertion(ex, "U_XML_IO.xmlPrint");
		}
	}

	static void xmlPrint(String msg, Element e) { stdOut.println(msg); xmlPrint(e); }
	static void xmlPrint(Object o) {
		Element e;
		if (o instanceof Element) e = (Element) o; else e = xmlExport(o, 0);
		xmlPrint(e, stdOut); 
		stdOut.println(); 
	}

//	static void xmlPrint(Element e) { xmlPrint(e, System.out); }

	static void xmlPrint(Element e, PrintStream out) {
		try { xout.output(e, out); } catch (IOException ex) {
			assertion(ex, "U_XML_IO.xmlPrint");
		}
	}

	static Text xmlToText(Element e) {
		return tokenizeString(xmlToString(e), "\n");
	}

	static String xmlToString(Element e) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		xmlToStream(e, baos);
		return baos.toString();
	}

	
	static String xmlToShortString(Element e) { // attributes
		Element e0 = e.clone();
		e0.removeContent();
		return xmlToString(e0);
	}

	static Element xmlFromString(String s) {
		ByteArrayInputStream bais = new ByteArrayInputStream(s.getBytes());
		Element e = xmlFromStream(bais);
		return e;
	}

	
}
