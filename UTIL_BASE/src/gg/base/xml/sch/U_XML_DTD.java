package gg.base.xml.sch;

import java.io.File;
import java.util.*;

import java.util.Objects;
import java.util.Stack;

import org.jdom2.Element;
import org.jdom2.Attribute;

import gg.base.text.*;

import static gg.base.java.L_Java.*;
import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Classes.getClassByName;
import static gg.base.util.U_Constructors.newInstance;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Files.*;

import static gg.base.xml.L_XML.*;
import static gg.base.xml.U_XML.*;
import static gg.base.xml.U_XML_ELEMENTS.*;
import static gg.base.xml.U_XML_IO.*;

import static gg.base.xml.sch.L_XML_DTD.*;

import static gg.base.java.L_Java.*;
import static gg.base.java.U_Java.*;

public interface U_XML_DTD {

	boolean DEBUG_XML_DTD = false;

	static SMap dtd2xml(String srcPath, String dstPath, String... schemaNames) {
		return dtd2xml(srcPath, dstPath, Arrays.asList(schemaNames));
	}
	
	static SMap dtd2xml(String srcPath, String dstPath, Collection<String> schemaNames) {
		SMap files = new SMap();
		File srcDir = checkSrcDirectory(srcPath);
		File dstDir = checkDstDirectory(dstPath);
		dtd2xml(srcDir, dstDir, files, schemaNames);
		return files;
	}

	//prn_("dtd2xml.srcFile " + srcFile);
//	for (File srcFile : srcDir.listFiles()) if (srcFile.isFile() && srcFile.getName().endsWith(DTD_EXT))  {
//		String fileName = getFileName(srcFile);
//		dtd2xml(srcFile, dstDir, fileName, files);
//	}

	static void dtd2xml(File srcDir, File dstDir, SMap files, String... schemaNames) {
		dtd2xml(srcDir, dstDir, files, Arrays.asList(schemaNames));
	}
	
	static void dtd2xml(File srcDir, File dstDir, SMap files, Collection<String> schemaNames) {
		for (String schemaName : schemaNames) {
			File srcFile = new File(srcDir, schemaName + DTD_EXT);
			String fileName = getFileName(srcFile);
			dtd2xml(srcFile, dstDir, fileName, files);
		}
	}

	static void dtd2xml(File srcFile, File dstDir, String fileName, SMap files) {
		Element eFile = new Element(DTD_VIEW);
		eFile.setAttribute(NAME, fileName);
		eFile.setAttribute(FILE, getFilePath(srcFile));

		Text src = new Text(srcFile, true, true);
		Text err = new Text();

		while (!src.isEmpty()) {
			String s = src.removeFirst().trim();
			if (s.isEmpty()) continue;
			if (s.startsWith(DTD_ELEMENT)) eFile.addContent(dtd2xml_Element(src, DTD_ELEMENT, s)); else
			if (s.startsWith(DTD_ATTLIST)) eFile.addContent(dtd2xml_Element(src, DTD_ATTLIST, s)); else
			if (s.startsWith(XML_COMMENT_START)) eFile.addContent(dtd2xml_Comment(src, s)); else 
			err.add(s);
		}

		if (!err.isEmpty()) {
			err.print();
			brk(fileName);
		}

		File dtdFile = new File(dstDir, fileName + ".dtd" + XML_EXT);
		xmlPrint(eFile, dtdFile);
		files.put(fileName, dtdFile.getAbsolutePath());
		
		Element eLibrary = dtd2xml_File(eFile);
		
		File libFile = new File(dstDir, fileName + ".lib" + XML_EXT);
		xmlPrint(eLibrary, libFile);
		files.put(fileName, libFile.getAbsolutePath());

//		return eSch;
	}


	static Element dtd2xml_Comment(Text src, String s) {
		while (!s.endsWith(XML_COMMENT_END)) s += " " + src.removeFirst();
		s = s.substring(XML_COMMENT_START.length());
		s = s.substring(0, s.length() - XML_COMMENT_END.length());
		s = s.replace('<', ' ');
		s = s.replace('>', ' ');
		s = "<" + XML_COMMENT + ">" + s + "</" + XML_COMMENT + ">";
		Element e = xmlFromString(s);
		return e;
	}
	
	static Element dtd2xml_Element(Text src, String start, String s) {
		int p = s.indexOf(">");
		String tail = s.substring(p+1).trim(); 
		if(!tail.isEmpty()) src.add(0, tail);
		s = s.substring(start.length(), p);
		Text tt = tokenize(s);
		String type = null;
		switch (start) {
			case DTD_ELEMENT : { type = XML_ELEMENT; break; }
			case DTD_ATTLIST : { type = XML_ATTRIBUTE; break; }
			default : assertion("invalid DTD element start '" + start + "'");
		}
		s = "<" + type + ">" + tt.join(" ", false) + "</" + type + ">";
		Element e = xmlFromString(s);
		return e;
	}

	static Element dtd2xml_File(Element eDTD) {
		
		Element eLibrary = new Element(PACKAGE);
		eLibrary.setAttribute(NAME, eDTD.getAttributeValue(NAME));
		
		for (Element e : eDTD.getChildren()) {
			String type = e.getName();
			if (type.equals(XML_ELEMENT)) {
				dtdElement(e, eLibrary);
			} else if (type.equals(XML_ATTRIBUTE)) {
				dtdAttribute(e, eLibrary);
			} // else ignore ? check comment ?
		}
		
		for (Element eClass : eLibrary.getChildren()) {
			xmlSimplex(eClass);
			xmlComplex(eClass);
		}
		
//		xmlLibrary(eLibrary);
		
		return eLibrary;
	}

	static Element dtdElement(Element e, Element eLibrary) {
		Text tt = tokenize(e.getText());
		String className = tt.removeFirst();
		assertion(eLibrary.getChild(className) == null, "Not unique schema ELEMENT (Class) name '" + className + "' in " + getElementNames(eLibrary.getChildren()));

//		Element eClass = new Element(CLASS);
//		eLibrary.addContent(eClass);
//		eClass.setAttribute(NAME, className);

		Element eClass = new Element(className);
		eLibrary.addContent(eClass);

		Element eChildren = new Element(XML_CHILDREN);
		eClass.addContent(eChildren);
		eChildren.setText(tt.join(" ", false));
		return eClass;
	}
	
//	GENERATOR fieldName = fieldName.replaceAll("\\.", DOT);

	static Element dtdAttribute(Element e, Element eLibrary) {
//xmlPrint(e);
//xmlPrint(eLibrary);
		Text tt = tokenize(e.getText());
		String className = tt.removeFirst();
		String fieldName = tt.removeFirst();
		Element eClass = eLibrary.getChild(className);
//		Element eClass = getElement(eLibrary, CLASS, NAME, className);
		assertion(eClass != null, "Class <" + className + "> not found");
		
		Element eField = new Element(XML_ATTRIBUTE);
		eClass.addContent(eField);
		eField.setAttribute(NAME, fieldName);
//		String fieldType = "";
//		eField.setAttribute(TYPE, fieldType);
		
		Element eSchema = new Element(VALUE);		
		eField.addContent(eSchema);
		eSchema.setText(tt.join(" ", false));
		
		return eField;
	}
	
	static String getRepeater(Text tt) {
		if (!tt.isEmpty())  // look ahead
			if (isRepeater(tt.getFirst())) 
				return tt.removeFirst();
		return null;
	}

	static Text getGroup(Text tt) {
		Text group = new Text();
		String g = tt.join(" ", false); 
		assertion(tt.removeFirst().equals("("), "not a group '" + g + "', MISSED OPENING '('");
		boolean closed = false;
		String groupRepeater = null;
		while (true) {
			String t = null;
			
			if (tt.getFirst().equals("(")) { // look ahead for sub group
				Text sub = getGroup(tt);
				group.addAll(sub);
				continue; // must be not empty
			}
			
			t = tt.removeFirst();
			
			if (isSeparator(t)) continue;

			if (t.equals(PCDATA)) t = t.substring(1); 

			if (isIdentifier(t)) { 
				String repeater = getRepeater(tt);
				if (repeater != null) t += ":" + repeater;
				group.add(t); 
				continue; 
			}

			closed = t.equals(")");
			if (closed) break;

			assertion(tt.isEmpty(), "not a group '" + g + "', MISSED CLOSING ')', no more tokens");
		}
		assertion(closed, "not a group '" + g + "', MISSED CLOSING ')'");
		
		groupRepeater = getRepeater(tt);
		
		if (groupRepeater != null) {
			int n = group.size();
			for (int i = 0; i < n; i++ ) {
				String s = group.removeFirst();
				group.add(s + ":" + groupRepeater);
			}
		}

//		prn_("getGroup.group: " + g, group);
//		brk();
		return group;
	}

//	static Element getObjects(Element eLibrary) {
//		Element eObjects = new Element(DTD_OBJECTS);
//		ArrayList<Element> classes = new ArrayList<>(eLibrary.getChildren());
//		for (Element eClass : classes) {
////prn_("getNodes " + eLibrary.getAttributeValue(NAME) + "." + eClass.getName());
//
//			Element eSimplex = eClass.getChild(XML_SIMPLEX); // objects
//			if (eSimplex == null) {
////prn_("   has no attributes");
//				continue;
//			}
////			Element eName = eSimplex.getChild(NAME);
////			if (eName == null) {
//////prn_("   has no attribute Name");
////				continue;
////			}
//
//			eClass.detach();
//			eObjects.addContent(eClass);
//		}
//		
////		prn_("checkArrays.nodes", getElementNames(eNodes.getChildren()));
////		brk();
//		return eNodes;
//	}
	
//	static Element getArrays(Element eLibrary) {
//		Element eArrays = new Element(DTD_ARRAYS);
//		ArrayList<Element> classes = new ArrayList<>(eLibrary.getChildren());
//		for (Element eClass : classes) {
////prn_("checkArrays " + eLibrary.getAttributeValue(NAME) + "." + eClass.getName());
//			if (eClass.getChild(XML_SIMPLEX) != null) {
////prn_("   has attributes");
//				continue;
//			}
//			Element eComplex = eClass.getChild(XML_COMPLEX); // objects
//			if (eComplex == null) {
////prn_("   has no objects");
//				continue;
//			}
//			ArrayList<Element> objects = new ArrayList<>(eComplex.getChildren());
//			int n = objects.size();
//			if (n > 1) {
////prn_("   has " + n +" objects");
//				continue;
//			}
//			Element eObject = objects.get(0);
//			String isArray = eObject.getAttributeValue(DTD_ARRAY);
//			if (isArray == null) {
////prn_("   has ARRAY attribute");
//					continue;
//			}
//			String arrayTitle = eClass.getName();
//			String elementTitle = eObject.getName();
//			prn_("getArrays: ----------------------------------------- ARRAY " + arrayTitle + ":" + elementTitle);
//			Element eArray = new Element(arrayTitle);
//			eArrays.addContent(eArray);
//			eArray.setAttribute(DTD_ARRAY, eObject.getName());
//			eClass.detach(); // detach from library classes
//		}
		
//		prn_("checkArrays.arrays", getElementNames(eArrays.getChildren()));
//		brk();
//		return eArrays;
//	}

//	static void xmlLibrary(Element eLibrary) {
////		Element eArrays = getArrays(eLibrary);
////		Element eObjects = new Element(DTD_CLASSES);
//		ArrayList<Element> classes = new ArrayList<>(eLibrary.getChildren());
//		for (Element eClass : classes) {
////			eClass.detach();
//			String title = eClass.getName();
//			if (DEBUG_XML_DTD) prn_("U_XML_DTD.xmlLibrary: eClass ----------------------------------------- DTD_OBJECT " + title);
////			eObjects.addContent(eClass);
//		}
////		eLibrary.addContent(eArrays);
////		eLibrary.addContent(eObjects);
//		
//	}

	static void xmlComplex(Element eClass) {
		Element eComplex = new Element(XML_COMPLEX);
		Element eChildren = eClass.getChild(XML_CHILDREN); // selectClass schema text
		eChildren.detach();

		String txt = eChildren.getText();
		if (txt.equals(DTD_EMPTY)) return;
		
		Text group = getGroup(tokenizeString(txt));
//prn_("processComplex.group", group);

		if (group.size() > 0) {
			eClass.addContent(eComplex);
			for (String s : group) {
				String[] ss = s.split(":");
				String title = ss[0];
				Element e = new Element(title);
				eComplex.addContent(e);
				s = "";
				int n = ss.length;
				if (n > 1) {
					for (int i = 1; i < n; i++) s += ss[i];
					if (s.equals("*") || s.equals("+")) {
						if (DEBUG_XML_DTD) prn_("U_XML_DTD.xmlComplex e----------------------------------------- DTD_ARRAY " + title);
						e.setAttribute(XML_ARRAY, title); 
					} else {
						if (DEBUG_XML_DTD) prn_("U_XML_DTD.xmlComplex e----------------------------------------- DTD_REPEAT " + title);
						e.setAttribute(XML_REPEAT, s);
					}
				} else {
					if (DEBUG_XML_DTD) prn_("U_XML_DTD.xmlComplex e----------------------------------------- DTD_OBJECT " + title);
				}
			}
		}
	}

	static void xmlSimplex(Element eClass) {
		ArrayList<Element> attributes = new ArrayList<>(eClass.getChildren(XML_ATTRIBUTE));
		if (attributes.isEmpty()) return;
		
		Element eSimplex = new Element(XML_SIMPLEX);
		
		for (Element eAttribute : attributes) {
			eAttribute.detach();
			Attribute a = eAttribute.getAttribute(NAME);
			String aName = a.getValue();
			a.detach();

			eAttribute.setName(aName);			
			eSimplex.addContent(eAttribute);
			
			Element eValue = eAttribute.getChild(VALUE);
			eValue.detach();
			
			Text tt = tokenizeString(eValue.getText());
			while (!tt.isEmpty()) {
				String t = tt.removeLast();
				if (t.startsWith("#")) {
					eAttribute.setAttribute(DTD_PRESENCE, t.substring(1));
				} else if (t.startsWith("\"") && t.endsWith("\"")) {
					eAttribute.setAttribute("DEFAULT", t.substring(1, t.length() - 1));
				} else if (t.equals(")")) {
					tt.add(t);
					Text group = getGroup(tt);
					eAttribute.setAttribute("ENUMERATION", "" + group.join(":"));
				} else {
					eAttribute.setAttribute(VALUE, t);
				}
				
			}
		}
		
		if (eSimplex.getChildren().size() > 0) eClass.addContent(eSimplex);
	}

	static Text tokenize(String s) {
		s = s.replaceAll("\\(", " ( ");
		s = s.replaceAll("\\)", " ) ");
		s = s.replaceAll("\\*", " * ");
		s = s.replaceAll("\\+", " + ");
		s = s.replaceAll("\\?", " ? ");
		s = s.replaceAll("\\,", " : ");
		Text tt = tokenizeString(s);
		tt.trim();
		return tt;
	}

}

//if (aName.equals(NAME)) {
//continue; // XmlNode ignore later
//} else {
//
//}

//eAttribute.setAttribute(VALUE, v);
//Element e = new Element(getJavaObjectName(ss[0], "_"));
//brk(DTD_REPEAT + " '" + s + "', complex field of '" + title + "'");
