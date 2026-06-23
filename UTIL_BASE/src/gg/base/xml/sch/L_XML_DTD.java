package gg.base.xml.sch;

import static gg.base.util.L_Base.*;
import static gg.base.java.L_Java.*;

import java.util.regex.Pattern;

import gg.base.text.*;

public interface L_XML_DTD {

	String DTD_VIEW = "dtd";
	String DTD_EXT = "." + DTD_VIEW;
	
	String DTD_ELEMENT = "<!ELEMENT";
	String DTD_ATTLIST = "<!ATTLIST";
	String DTD_EMPTY = "EMPTY";

	String DTD_PRESENCE = "presence";
	
	String DTD_SCHEMA  = "schema";
	
	String CHOICE	= "|";
	String SEQUENCE	= ":"; // ",";

	SSet SCHEMA_VALUE_SEPARATORS = new SSet(
			CHOICE,
			SEQUENCE
		);

    static boolean isSeparator(String s) {
    	return (SCHEMA_VALUE_SEPARATORS.contains(s));
    }
    
	String ZERO_OR_MORE	= "*";
	String ONE_OR_MORE	= "+";
	String ZERO_OR_ONE	= "?";

	SSet SCHEMA_VALUE_REPEATERS = new SSet(
			ZERO_OR_MORE,
			ONE_OR_MORE	,
			ZERO_OR_ONE
		);

    static boolean isRepeater(String s) {
    	return (SCHEMA_VALUE_REPEATERS.contains(s));
    }
    
	String NMTOKEN	= "NMTOKEN";
	String CDATA	= "CDATA";
	String PCDATA	= "#PCDATA";

	SSet SCHEMA_VALUE_PRIMITIVES = new SSet(
		NMTOKEN	,
		CDATA	,
		PCDATA
	);

	String REQUIRED	= "#REQUIRED";
	String OPTIONAL	= "#IMPLIED";
	String FIXED	= "#FIXED";

	SSet SCHEMA_VALUE_PRESENCE = new SSet(
		REQUIRED,
		OPTIONAL,
		FIXED
	);

    String NMTOKEN_REGEX = "^[A-Za-z0-9._:-]+$";
    Pattern NMTOKEN_PATTERN = Pattern.compile(NMTOKEN_REGEX);

    String ENUMERATION_REGEX = "^\\(\\s*[A-Za-z0-9._:-]+(\\s*\\|\\s*[A-Za-z0-9._:-]+)*\\s*\\)$";
    Pattern ENUMERATION_PATTERN  = Pattern.compile(ENUMERATION_REGEX);

	static boolean isPrimitiveValue(String value) { return SCHEMA_VALUE_PRIMITIVES.contains(value); }
 
    static boolean isNMTOKEN(String s) {
        if (s == null) return false;
        if (!s.trim().equals(s)) return false;
        if (s.isEmpty()) return false;
        return NMTOKEN_PATTERN.matcher(s).matches();
    }

    static boolean hasENUMERATION(String s) {
        if (s == null) return false;
        if (!s.trim().equals(s)) return false;
        if (s.isEmpty()) return false;
        return ENUMERATION_PATTERN.matcher(s).matches();
    }

	SSet SCHEMA_XML_IGNORE_FIELDS = new SSet(TITLE); // prevent self loop
	SSet SCHEMA_DTD_SPECIAL_FIELDS = new SSet(FILE, INTERFACE); // modify for Java
	
	static String getSpecialName(String title) {
		return (SCHEMA_DTD_SPECIAL_FIELDS.contains(title) ? INSTANCE_PREFIX : "") + title;
	}

	static String getSpecialTitle(String name) {
		if (!name.startsWith(INSTANCE_PREFIX)) return name;
		String s = name.substring(INSTANCE_PREFIX.length());
		return SCHEMA_DTD_SPECIAL_FIELDS.contains(s) ? s : name;
	}

}
