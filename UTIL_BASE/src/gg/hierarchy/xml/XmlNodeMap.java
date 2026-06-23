package gg.hierarchy.xml;

import java.util.Collection;

import gg.base.text.TMap;

public class XmlNodeMap extends TMap<XmlNode>{

	private static final long serialVersionUID = 1L;

	public XmlNodeMap() {}

	public XmlNodeMap(Collection<XmlNode> cc) { super(cc); }

	public String name;

}
