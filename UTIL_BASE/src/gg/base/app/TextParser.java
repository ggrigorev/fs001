package gg.base.app;

import java.util.*;
import gg.base.text.*;

import org.jdom2.Element;
import org.jdom2.Attribute;

import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;
import static gg.base.xml.U_XML_IO.*;

public class TextParser {
	
	public Text src;
	public final int size;
	public SMap err = new SMap();
	
	public Element eRules;
	public ArrayList<ParserRule> rules;
	
	String WARNING = "warning";
	String ERROR = "error";
	
	public TextParser(String srcPath, String xmlPath, String... toolPath) {
		src = new Text(srcPath, false, true);
		size = src.size();
		eRules = xmlFromFile(xmlPath);
		for (String toolName :  toolPath) eRules = eRules.getChild(toolName);
	}

	public void build() {
		for (Element eRule : eRules.getChildren() ) {
			ParserRule rule = new ParserRule(eRule) {

				@Override
				public boolean accept(String s) {
					// TODO Auto-generated method stub
					return false;
				}
				
			};
		}
	}
	
	

	public void parse() {
		while (!src.isEmpty()) {
			String s = readLine(src);
			if (s.toLowerCase().indexOf(WARNING) >= 0) {
				if (!check(s)) {
					int line = size - src.size(); 
					err.put("" + line, s);
				}
			}
		}
		
	}

	public boolean check(String s) {
		for (ParserRule rule : rules) {
			if (!rule.accept(s)) return false;
			if (!rule.accept(s)) return false;
		}
		return true;
	}
}
