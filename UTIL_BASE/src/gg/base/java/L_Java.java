package gg.base.java;

import static gg.base.java.L_Java.JAVA_KEYWORDS;
import static gg.base.util.L_Base.INSTANCE_PREFIX;

import java.util.Arrays;
import java.util.List;

public interface L_Java {
	
	String JAVA_VIEW = "java";
	String JAVA_EXT = "." + JAVA_VIEW;
	
    // Access & Class Modifiers
    String ABSTRACT   = "abstract";
    String FINAL      = "final";
    String NATIVE     = "native";
    String PRIVATE    = "private";
    String PROTECTED  = "protected";
    String PUBLIC     = "public";
    String STATIC     = "static";
    String STRICTFP   = "strictfp";
    String SYNCHRONIZED = "synchronized";
    String TRANSIENT  = "transient";
    String VOLATILE   = "volatile";

    // Control Flow
    String IF         = "if";
    String ELSE       = "else";
    String SWITCH     = "switch";
    String CASE       = "case";
    String DEFAULT    = "default";
    String WHILE      = "while";
    String DO         = "do";
    String FOR        = "for";
    String BREAK      = "break";
    String CONTINUE   = "continue";
    String RETURN     = "return";
    String THROW      = "throw";
    String THROWS     = "throws";
    String TRY        = "try";
    String CATCH      = "catch";
    String FINALLY    = "finally";
    String ASSERT     = "assert";

    // Primitive Types
    String BOOLEAN    = "boolean";
    String BYTE       = "byte";
    String CHAR       = "char";
    String SHORT      = "short";
    String INT        = "int";
    String LONG       = "long";
    String FLOAT      = "float";
    String DOUBLE     = "double";

    // Object & Class Structure
    String CLASS      = "class";
    String INTERFACE  = "interface";
    String ENUM       = "enum";
    String EXTENDS    = "extends";
    String IMPLEMENTS = "implements";
    String PACKAGE    = "package";
    String IMPORT     = "import";
    String SUPER      = "super";
    String THIS       = "this";
    String NEW        = "new";

    // Literals & Operators
    String NULL       = "null";
    String TRUE       = "true";
    String FALSE      = "false";
    String INSTANCEOF = "instanceof";

    // Module System (Java 9+)
    String MODULE     = "module";
    String OPEN       = "open";
    String OPENS      = "opens";
    String REQUIRES   = "requires";
    String EXPORTS    = "exports";
    String USES       = "uses";
    String PROVIDES   = "provides";
    String TRANSITIVE = "transitive";
    String TO         = "to";
    String WITH       = "with";

    // Reserved but Unused
    String CONST      = "const";
    String GOTO       = "goto";

    // Special Case (Java 9+)
    String UNDERSCORE = "_";

  //------------------------------------------------------------

    String[] JAVA_KEYWORDS_ARRAY = {
	    ABSTRACT,
	    FINAL,
	    NATIVE,
	    PRIVATE,
	    PROTECTED,
	    PUBLIC,
	    STATIC,
	    STRICTFP,
	    SYNCHRONIZED,
	    TRANSIENT,
	    VOLATILE,

	    IF,
	    ELSE,
	    SWITCH,
	    CASE,
	    DEFAULT,
	    WHILE,
	    DO,
	    FOR,
	    BREAK,
	    CONTINUE,
	    RETURN,
	    THROW,
	    THROWS,
	    TRY,
	    CATCH,
	    FINALLY,
	    ASSERT,

	    BOOLEAN,
	    BYTE,
	    CHAR,
	    SHORT,
	    INT,
	    LONG,
	    FLOAT,
	    DOUBLE,

	    CLASS,
	    INTERFACE,
	    ENUM,
	    EXTENDS,
	    IMPLEMENTS,
	    PACKAGE,
	    IMPORT,
	    SUPER,
	    THIS,
	    NEW,

	    NULL,
	    TRUE,
	    FALSE,
	    INSTANCEOF,

	    MODULE,
	    OPEN,
	    OPENS,
	    REQUIRES,
	    EXPORTS,
	    USES,
	    PROVIDES,
	    TRANSITIVE,
	    TO,
	    WITH,

	    CONST,
	    GOTO,

	    UNDERSCORE
	};

    List<String> JAVA_KEYWORDS = Arrays.asList(JAVA_KEYWORDS_ARRAY);
 
//------------------------------------------------------------
    
//	String FIELD		= "field";
//	String CONSTRUCTOR	= "constructor";
//	String METHOD		= "method";

	String LEXICON_PREFIX = "L_";
	String UTILITY_PREFIX = "U_";

	String DOT = "__DOT__";

}
