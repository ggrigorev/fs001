package gg.base.xml;

import gg.base.text.*;

//import java.util.regex.Pattern;

import gg.base.util.L_Base;

public interface L_XML extends L_Base {

	String XML_VIEW = "xml";
	String XML_EXT = "." + XML_VIEW;

	String XML_COMMENT_START = "<!--";
	String XML_COMMENT_END  = "-->";

	String XML_ELEMENT	 = "element";
	String XML_ATTRIBUTE = "attribute";
	String XML_COMMENT	 = "comment";
	String XML_CHILDREN	 = "children";

	String XML_SIMPLEX = "simplex";
	String XML_COMPLEX = "complex";

	String XML_CLASSES = "classes";
	String XML_OBJECT  = "object";

	String XML_ARRAYS = "arrays";
	String XML_ARRAY  = "array";
	
	String XML_REPEAT = "repeat";

	String HIERARCHY_REFERENCE = "href";
	String HIERARCHY_DIRECTORY = "hdir";
	String HIERARCHY_FILE = "hfile";
 
	static SSet XML_PRIMITIVE_NAMES = new SSet(
			SMap.class.getSimpleName(),
			SSet.class.getSimpleName(),
			SList.class.getSimpleName(),
			Text.class.getSimpleName()
	);

	static SMap XML_PRIMITIVE_NAME_MAP = new SMap(
			SMap.class.getSimpleName() , SMap.class.getName() ,
			SSet.class.getSimpleName() , SMap.class.getName() ,
			SList.class.getSimpleName(), SList.class.getName(),
			Text.class.getSimpleName() , Text.class.getName()
	);


}
