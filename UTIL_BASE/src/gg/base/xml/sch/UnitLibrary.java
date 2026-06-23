package gg.base.xml.sch;

import java.io.*;
import java.util.*;

import org.jdom2.Element;
import org.jdom2.Attribute;

import gg.base.java.ItemTriplet;
import gg.base.text.*;

import gg.hierarchy.xml.*;

import static gg.base.java.L_Java.*;
import static gg.base.java.U_Java.*;
import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Files.*;

import static gg.base.xml.L_XML.*;
import static gg.base.xml.U_XML_IO.*;

import static gg.base.xml.sch.L_XML_DTD.*;

public class UnitLibrary extends ArrayList<SMap> {

	public static boolean DEBUG_UNIT_LIBRARY = false;	

	private static final long serialVersionUID = 1L;

	public String separator = "_";

	public String packagePath; // full package name
	public String packageName; // last package name
	public String className;   // 

	public SMap titleClassMap = new SMap(); // title -> subclass name
//	public SMap arrayLists  = new SMap(); // title -> XmlNode array element subclass name
	// public SMap tit_les = new SMap();

	public String lexiconName;
	public String utilityName;

//	public Text listImports = new Text("java.util.ArrayList");
	public Text nodeImports = new Text("gg.hierarchy.xml");

	public UnitLibrary(String pkgPath, String libName) {
		packageName = libName;
		className = getJavaClassName(libName, separator);
		packagePath = pkgPath + "." + packageName;
		lexiconName = LEXICON_PREFIX + className;
		utilityName = UTILITY_PREFIX + className;
		nodeImports.add("static " + packagePath + "." + lexiconName);
	}

	public SMap add_Package() {
		SMap top = new SMap(); // lexicon
		add(top);

		top.put(PACKAGE + ":" + packagePath, null);

		top.put(IMPORT + ":" + "gg.base.xml.sch", null);
//		top.put(IMPORT + ":static " + pkgName + "." + lexiconName, null);

		top.put(CLASS + ":Package" + className, "extends Schema");
//
//		top.put(CONSTRUCTOR + ":" + name, name.toUpperCase());
		
		return top;
	}
	public void addClasses(Element eLibrary) {

		SMap top = add_Package();
		
//		Element eClasses = eLibrary.getChild(DTD_CLASSES);
//		if (eClasses != null) for (Element eClass : eClasses.getChildren()) {
		for (Element eClass : eLibrary.getChildren()) {
			ItemTriplet item = add_Object(eClass);
			top.put(item.key, item.value);
		}

		add_Lexicon();
		add_Utility();
//		for (Element eClass : eLibrary.getChildren(CLASS)) addClassUnit(eClass);
//		addUtility();
//		addLexicon();
//prn_(unit);brk();
//		xmlPrint(eLibrary);
	}

	public void add_Lexicon() {

		int p = 0;
		SMap unit = new SMap(); // lexicon
		unit.put(PACKAGE + ":" + packagePath, null);
		unit.put(INTERFACE + ":" + lexiconName, null);

		for (String title : titleClassMap.keySet())
			unit.put("String:" + title.toUpperCase(), "\"" + title + "\"");

		unit.put("String:" + packageName.toUpperCase(), "\"" + packageName + "\"");

		add(0, unit);
	}

	public void add_Utility() {

		int p = 0;
		SMap unit = new SMap(); // lexicon
		unit.put(PACKAGE + ":" + packagePath, null);

		unit.put(IMPORT + ":" + "java.util", ArrayList.class.getSimpleName());
		unit.put(IMPORT + ":" + "gg.base.xml.sch", null);
		unit.put(IMPORT + ":static " + packagePath + "." + lexiconName, null);

		unit.put(INTERFACE + ":" + utilityName, null);

		add(0, unit);
	}

	public String lookAhead(Text tt) {
		if (tt.isEmpty())
			return null;
		String t = tt.getFirst();
		if (isIdentifier(t))
			return null;
		tt.removeFirst();
		return t;
	}

	public ItemTriplet add_Object(Element eClass) {
		
		String classTitle = eClass.getName(); // title is an instance name when used getJavaObjectName()
if (SCHEMA_DTD_SPECIAL_FIELDS.contains(classTitle)) classTitle = INSTANCE_PREFIX + classTitle;
//		String objectTitle = eClass.getAttributeValue(XML_OBJECT); // title is an instance name when used
//		if (objectTitle != null) objectTitle = getJavaObjectName(objectTitle, "_"); // title is an instance name when used
		String nodeClassName = getJavaClassName(classTitle, separator);// "Class" +

		titleClassMap.put(classTitle, nodeClassName);

		SMap unit = new SMap();
		add(unit);

		unit.put(PACKAGE + ":" + packagePath, xmlToString(eClass));

		unit.put(IMPORT + ":" + "java.util", "ArrayList");
		unit.put(IMPORT + ":" + "gg.base.xml.sch", null);
		unit.put(IMPORT + ":static " + packagePath + "." + lexiconName, null);
		unit.put(IMPORT + ":static " + packagePath + "." + utilityName, null);

		unit.put(CLASS + ":" + nodeClassName, "extends Schema");

		Element eSimplex = eClass.getChild(XML_SIMPLEX); // primitive members
		Element eComplex = eClass.getChild(XML_COMPLEX); // object members
		if (eSimplex != null) for (Element eField : eSimplex.getChildren()) {
			String fieldName = eField.getName().replaceAll("\\.", DOT);
			String fieldType = String.class.getSimpleName();
			String fieldValue = null;
			// parse schema for type and value
			unit.put(fieldType + ":" + fieldName, fieldValue);
		}
//
//		boolean importArray = false;
		//if (fieldName.toUpperCase().indexOf("PCDATA") >= 0) brk(fieldType + " " + fieldName + " = " + fieldValue);

		//model_parameters = ModelParameter
		if (eComplex != null) for (Element eField : eComplex.getChildren()) {
//			boolean isArray = false;
			String fieldName = eField.getName().replaceAll("\\.", DOT);
			String fieldValue = null;
			if (SCHEMA_DTD_SPECIAL_FIELDS.contains(fieldName)) fieldName = INSTANCE_PREFIX + fieldName;
			if (PCDATA.equals("#" + fieldName)) {
				unit.insert(1, new SMap(IMPORT + ":" + "gg.base.text", "Text"));
				
			//brk(fieldName + " in " + nodeClassName);
				unit.put("Text:" + fieldName, "new Text()");
				continue;
			}
	//		String fieldType = null;//arrayLists.get(fieldName);

//			if (fieldType != null) { // array list
////brk(fieldName + " = " + fieldType);
//				isArray = true;
//			} else {
//			}
			
			String fieldType = eField.getAttributeValue(XML_ARRAY);
if (SCHEMA_DTD_SPECIAL_FIELDS.contains(fieldType)) fieldType = INSTANCE_PREFIX + fieldType;

			boolean isArray = (fieldType != null);
if (SCHEMA_DTD_SPECIAL_FIELDS.contains(fieldType) || SCHEMA_DTD_SPECIAL_FIELDS.contains(fieldName)) brk(fieldType + ":" + fieldName + " = " + fieldValue);
			if (isArray) {
				fieldType = "ArrayList<" + getJavaClassName(fieldType) + ">";
				fieldValue = "new ArrayList<>()";
			} else {
				fieldType = getJavaClassName(fieldName);
			}
if (fieldValue != null) if (fieldValue.toUpperCase().indexOf("PCDATA") >= 0) brk(fieldType + " " + fieldName + " = " + fieldValue);

			// parse schema for type and value
			if (DEBUG_UNIT_LIBRARY) prn_(fieldType + ":" + fieldName + " = " + fieldValue);
			unit.put(fieldType + ":" + fieldName, fieldValue);
//if (fieldName.equals("model_parameters")) brk(nodeClassName + "> " + fieldName + " = " + fieldType);
		}

		ItemTriplet item = new ItemTriplet(nodeClassName + ":" + classTitle, "" + null);//"new " + nodeClassName + "()");
		return item;
	}
}

//Element eArrays = eLibrary.getChild(DTD_ARRAYS);
//if (eArrays != null) for (Element eArray : eArrays.getChildren()) {
//	add_Array(eArray);
////	ItemTriplet item = add_Array(eArray);
////	top.put(item.key, item.value);
//}

//{
//isArray = true;
//fieldType = attrArray;
//}
//if (fieldName.equals("ip_preset")) {
//xmlPrint(eField);
////brk(nodeClassName + "> " + fieldName + " = " + fieldType + ", isArray " + isArray);
//}

//importArray = true;

//if (importArray) {
//unit.put(IMPORT + ":" + "java.util", "ArrayList");
//}

//unit.put(CONSTRUCTOR + ":" + nodeClassName, title.toUpperCase());


/*
 * public void addClass_Unit(Element eClass) { //DTD XML "ELEMENT"
 * //xmlPrint(eUnit);
 * 
 * List<Element> primitives = eClass.getChildren(XML_ATTRIBUTE);
 * 
 * String schema = eClass.getChild(DTD_SCHEMA).getTextNormalize();
 * //prn_("class schema: " + schema);
 * 
 * / Text tt = tokenizeString(schema); //prn_("class schema tokens", tt);
 * 
 * Text nodes = new Text(); Text texts = new Text(); Text lists = new Text();
 * 
 * SMap unit = new SMap(); add(unit);
 * 
 * 
 * 
 * String t = tt.removeFirst(); if (t.equals("EMPTY")) assertion(tt.isEmpty(),
 * "");
 * 
 * String groupRepeater = null;
 * 
 * if (!tt.isEmpty()) { String groupStart = t; String groupEnd =
 * tt.removeLast(); if (!groupEnd.equals(")")) { assertion(isRepeater(groupEnd),
 * "Must be a repeater {'?', '*', '+'}, actual '" + groupEnd + "'");
 * groupRepeater = groupEnd; groupEnd = tt.removeLast(); }
 * assertion(groupStart.equals("(") && groupEnd.equals(")"),
 * "Must be a group in (), actual '" + groupStart + " " + groupEnd + "'" );
 * 
 * //xmlPrint(eUnit);prn_("class schema: " + schema, tt); while (!tt.isEmpty())
 * { t = tt.removeFirst(); assertion(t.equals(PCDATA) || isIdentifier(t),
 * "Must be #PCDATA or an identifier as a member of a group, actual '" + t + "'"
 * ); nodes.add(t);
 * 
 * t = lookAhead(tt); if (t == null) continue; if (isSeparator(t)) continue;
 * assertion(isRepeater(t),
 * "Must be a repeater of a previous member of a group, actual '" + t + "'" );
 * lists.add(nodes.removeLast()); prn_("List " + lists.getLast()); t =
 * lookAhead(tt); if (t == null) continue; assertion(isSeparator(t),
 * "Must be a separator of a previous member+repeater of a group, actual '" + t
 * + "'" ); } } if (groupRepeater != null) { lists.addAll(nodes); prn_("Lists ",
 * nodes); nodes.clear(); };
 * 
 * unit.put(PACKAGE + ":" + pkgName,
 * xmlToString(eUnit));//xmlToText(eUnit).join(" ", false));
 * 
 * for (String imp : imports) unit.put(IMPORT + ":" + imp, null); if
 * (!lists.isEmpty()) unit.put(IMPORT + ":" + "java.util",
 * ArrayList.class.getSimpleName());
 * 
 * unit.put(CLASS + ":" + unitName, "extends " + XmlNode.class.getSimpleName());
 * 
 * for (Element e : primitives) { String fieldName =
 * e.getAttributeValue(NAME).replaceAll("\\.", DOT); String fieldType=
 * String.class.getSimpleName(); String fieldValue= null; String fieldSchema =
 * e.getChild(DTD_SCHEMA).getTextNormalize(); Text fieldTokens =
 * tokenizeString(fieldSchema); // parse schema for type and value
 * unit.put(fieldType + ":" + fieldName, fieldValue); } unit.put(CONSTRUCTOR +
 * ":" + unitName, title.toUpperCase());
 * 
 * for (String type: nodes) { String fieldName = type.replaceAll("\\.", DOT);
 * String fieldType= getJavaUnitName(type, separator); String fieldValue= "new "
 * + fieldType + "()"; unit.put(fieldType + ":" + fieldName, fieldValue); }
 * 
 * for (String type: lists) { String fieldName = type.replaceAll("\\.", DOT);
 * String fieldType= getJavaUnitName(type, separator); String fieldValue=
 * "new ArrayList<>()"; unit.put("ArrayList<" + fieldType + ">:" + fieldName,
 * fieldValue); }
 * 
 * 
 * //prn_("unit", unit); //brk("unitName " + unitName); }
 * 
 * }
 */

//{ prn_("Next Separator no repeater " + t);  continue; }
//prn_("Next must to be a Repeater " + t);
//				if (!isRepeater(t)) {xmlPrint(eUnit); prn_("class schema: " + schema); }	

//boolean noAttributes = aa.isEmpty();
//boolean hasAttributes = !noAttributes;
//
//boolean noChildren = content.equals("EMPTY");
//boolean hasChildren = !noChildren;
//
//if (hasChildren) {
//	String t = 
//	for ()
//	children.addAll();
//}
//boolean hasSigleChild = (children.size() == 1);
//
//boolean hasLists = content.startsWith("(") && content.endsWith(")*");

//if (hasChildren)
//if ( noAttributes &&  noChildren) prn_("UNEXPECTED ------ " + title); else 
//if ( noAttributes && !noChildren) prn_("LISTs ONLY ------ " + title); else 
//if (!noAttributes &&  noChildren) prn_("LEAF NODE ------ " + title); else 
//if (!noAttributes && !noChildren) prn_("LISTs NODE ------ " + title); 
//unit.put(IMPORT + ":" + "static gg.base.xml.sch.L_XML_DTD", null);

//boolean isSingleEmptyList    = noAttributes && hasSigleChild && (children.getFirst().endsWith("*") || hasLists);
//
//boolean isSingleNotEmptyList = noAttributes && hasSigleChild && (children.getFirst().endsWith("+"));
//
//boolean isSingleList = isSingleNotEmptyList || isSingleEmptyList;

//if (isSingleList) {
//	String listMemeberTitle = children.get(0).replaceAll("\\+", "").replaceAll("\\*", "");
//	String listMemeberType = getJavaUnitName(listMemeberTitle, "_");
//	String listType = ArrayList.class.getSimpleName() + "<" + listMemeberType + ">";
//	unit.put(CLASS + ":" + unitName, "extends " + listType);
//	
//} else {
//}

//	unit.put(IMPORT + ":" + pkg, null);
//
//	public void writeUnits(File dstDir, TMap<File> files) {
//
//		String s;
//		for (SMap items : this) {
//		}
//	}

/*
 * unit.put(TEXT + ":" + (p++),
 * "static <T extends XmlNode> ArrayList<T> createArray (String nodeKind) {");
 * unit.put(TEXT + ":" + (p++), TAP +"switch(nodeKind) {"); for (String title :
 * titleClassMap.keySet()) { String s = "case " + title.toUpperCase() + " : "; s
 * += "{ array = new " + titleClassMap.get(title) + "(); break; }";
 * unit.put(TEXT + ":" + (p++), TAP + TAP + s); } unit.put(TEXT + ":" + (p++),
 * TAP + "};"); // close switch unit.put(TEXT + ":" + (p++), TAP +
 * "return node;"); unit.put(TEXT + ":" + (p++), "}"); // close method
 * 
 * unit.put(TEXT + ":" + (p++),
 * "static <T extends XmlNode> T createNode (String nodeKind) {"); unit.put(TEXT
 * + ":" + (p++), TAP +"XmlNode node = null;"); unit.put(TEXT + ":" + (p++), TAP
 * +"switch(nodeKind) {"); for (String title : titleClassMap.keySet()) { String
 * s = "case " + title.toUpperCase() + " : "; s += "{ node = new " +
 * titleClassMap.get(title) + "(); break; }"; unit.put(TEXT + ":" + (p++), TAP +
 * TAP + s); } unit.put(TEXT + ":" + (p++), TAP + "};"); // close switch
 * unit.put(TEXT + ":" + (p++), TAP + "return (T) node;"); unit.put(TEXT + ":" +
 * (p++), "}"); // close method
 */
//titleClassMap.put(title, arrayClassName);
//
//SMap unit = new SMap();
//add(unit);
//
//unit.put(PACKAGE + ":" + pkgName, xmlToString(eArray));
//unit.put(IMPORT + ":" + "java.util", ArrayList.class.getSimpleName());
//
//unit.put(CLASS + ":" + arrayClassName,
//		"extends " + ArrayList.class.getSimpleName() + "<" + arrayElementType + ">");
//
//unit.put("private static final long:serialVersionUID", "1L");
//
//ItemTriplet item = new ItemTriplet(arrayClassName + ":" + title, "new " + arrayClassName + "()");
//return item;
//
////prn_("------------------------ addUnit_Array\n" + xmlToString(eArray), unit);
////brk();
////unit.put(CLASS + ":" + className, "extends " + XmlNode.class.getSimpleName());
//}
//public void addNodeUnit(Element eNodeClass, SMap classUnit) { // DTD XML "ELEMENT"
//// Has attributes
//}
//
//if (objectTitle != null) objectTitle = getJavaObjectName(objectTitle, "_"); // title is an instance name when used

//public void add_Array(Element eArray) {
//String title = eArray.getName(); // title is an instance name when used getJavaObjectName()
////String objectTitle = eArray.getAttributeValue(DTD_ARRAY); // title is an instance name when used
//////String arrayClassName = getJavaClassName(title, separator);// "Class" +
////String arrayElementType = getJavaClassName(objectTitle, separator);
//String arrayElementTitle = eArray.getAttributeValue(DTD_ARRAY); // title is an instance name when used
//arrayLists.put(title, arrayElementTitle);
////prn_("arrayLists", arrayLists);brk();
//}

