package gg.base.util;

import static gg.base.util.L_Base.NULL;
import static gg.base.util.U_Constructors.ARGUMENT_SEPARATOR;
import static gg.base.util.U_Print.prn_;

import org.jdom2.Element;

public class ConstructorArguments {

	public Object  [] values = new Object[] { new String[] {} }; 
	public Class<?>[] types  = new Class [] { java.lang.String[].class };

	public ConstructorArguments() { this("");}
	public ConstructorArguments(Element e) { this(e.getText()); }
	public ConstructorArguments(String  s) {
		if (s == null) {
			prn_("ConstructorArguments: args is null");
			return;
		}
		s = s.trim();
		if (s.isEmpty()) {
			prn_("ConstructorArguments: args is empty");
			return;
		}
		if (s.equals(NULL)) {
			prn_("ConstructorArguments: args is NULL symbol");
			return;
		}
		// parse arguments
		String[] args = s.split(ARGUMENT_SEPARATOR);
prn_("ConstructorArguments: args size " + args.length);
		int i = 0;
		for (String arg : args) {
			
			prn_(String.format("%2d %s", i++, arg));
		}
	}
}
