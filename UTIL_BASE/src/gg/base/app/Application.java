package gg.base.app;

//import java.io.*;
//import java.util.*;
//import java.lang.reflect.*;
import org.jdom2.Element;

//import gg.tree.*;

//import static gg.base.util.U_Classes.*;
//import static gg.base.util.U_Files.*;
//import static gg.base.util.U_Print.*;
//import static gg.base.xml.U_XML_IO.*;

public class Application implements I_Application {

	public final Element eRequest;
	
	public Application() { this(null); }

	public Application(Element eRequest) { 
		this.eRequest = eRequest;
//		complex = new Complex(complex, TAP); 
	}

	public void execute() {}

}
