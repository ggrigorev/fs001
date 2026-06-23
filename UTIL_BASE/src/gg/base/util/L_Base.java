package gg.base.util;

import java.util.regex.Pattern;

import gg.base.text.SSet;

//import gg.text.Text;

public interface L_Base {

	String userHome = System.getenv("USERPROFILE");

	Pattern identifierPattern = Pattern.compile("\\b[a-zA-Z][a-zA-Z0-9_]*\\b");

    static boolean isIdentifier(String s) {
        if (s == null) return false;
        if (!s.trim().equals(s)) return false;
        if (s.isEmpty()) return false;
        return identifierPattern.matcher(s).matches();
    }

	String PROJECT	= "project"	;
	
	String LIBRARY	= "library"	;
	
	String PATH		= "path"	;
	String FILE		= "file"	;

	String HOME		= "home"	;
	String WORK		= "work"	;

//	String PACKAGE	= "package"	;
//	String CLASS	= "class"	;
//	String INTERFACE= "interface";
	
	String WILD		= "*"		;

	String TOP		= "top"		;

	String ID		= "id"		;

	String KIND		= "KIND"	;
	String TITLE	= "title"	;

	String NAME		= "name"	;
	String TYPE		= "type"	;
	String TEXT		= "text"	;

	String PARENT	= "parent"	;
	String CHILDREN	= "children";

	String HEAP		= "heap"	;
	String BUILD	= "build"	;

	String ARRAY	= "array"	;
	String ITEM		= "item"	;
	String INDEX	= "index"	;
	String SIZE		= "size"	;

	String KEY  	= "key"		;
	String VALUE	= "value"	;
	String DEFAULT	= "default"	;

	String MIN		= "min"		;
	String MAX		= "max"		;

	String RANGE	 = "range";

	String TRUE		= "true"	;
	String FALSE	= "false"	;

	String PLUS		= "+"		;
	String MINUS	= "-"		;
	
	String COMMA	= "comma"	;
	String EMPTY	= "empty"	;
	String NULL		= "" + null	;
	
	String ENTRY	= "entry"	;
	
	String VERSION	= "version"	;
	String REVISION	= "revision";
	String RELEASE	= "release"	;

	String GET 		= "get"		;
	String SET		= "set"		;

	String START	= "start"	;
	String STOP		= "stop"	;

	String DESCRIPTION = "description";
	
//	design
	String ROLE		= "role"	;
	String NET		= "net"	;
	String PORT		= "port"	;
	String WIDTH	= "width"	;

	Boolean LOW = false;
	Boolean HIGH = !LOW;
	
	String HEAD = "head";
	String BODY = "body";
	String TAIL = "tail";
	
	String PARAMS = "params";
	
	String ROUTINE = "routine";
	
	String TEXT_EXT = ".txt";
		
	String DEFAULT_VALUE = DEFAULT + "_" + VALUE;
	String VALUE_TYPE	 = VALUE + "_" + TYPE;
	String VALUE_MIN	 = VALUE + "_" + MIN;
	String VALUE_MAX	 = VALUE + "_" + MAX;

	String INSTANCE_PREFIX = "x_";
	
}
