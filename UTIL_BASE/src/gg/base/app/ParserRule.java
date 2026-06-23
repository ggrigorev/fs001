package gg.base.app;

import org.jdom2.Element;
import org.jdom2.Attribute;

public abstract class ParserRule implements StringFilter {

	public Element eRule;
	
	public ParserRule(Element eRule) {
		this.eRule = eRule;
	}
//	@Override
//	public boolean accept(String s, Element e) {
//		// TODO Auto-generated method stub
//		return false;
//	}

}
