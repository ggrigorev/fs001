package gg.hierarchy.xml;

import gg.base.text.TMap;

import static gg.base.util.U_Constructors.newInstance;

public interface U_XmlNode {
	
	TMap<Object> memberMap = new TMap<>();
	
	static XmlNode createMember(String listTitle) {
		Object o = memberMap.get(listTitle);
		if (o == null) return null;
		if (o instanceof String) return new XmlNode((String) o);
		if (o instanceof Class) return newInstance((Class) o); // constructor without arguments
		return null;
	}

}
