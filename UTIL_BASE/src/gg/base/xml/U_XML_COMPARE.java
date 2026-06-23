package gg.base.xml;

import static gg.base.util.L_Base.ID;
import static gg.base.util.L_Base.VALUE;
import static gg.base.util.U_Classes.JAVA_PRIMITIVES;
import static gg.base.util.U_Print.*;

import static gg.base.xml.U_XML.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Vector;

import gg.base.text.*;

import org.jdom2.Attribute;
import org.jdom2.Element;

public interface U_XML_COMPARE {

	static SMap compareElements(Text log, Element e0, Element e1) {
		SMap map = new SMap();
		compareElements(log, e0, e1, map);
		return map;
	}
	
	static void compareAttributes(Text log, SMap a0, SMap a1, SMap map, String n0, String n1) {
		SList keys = new SList(a0.keySet());
		int i = 0;
		while (!keys.isEmpty()) {
			String k0 = keys.removeFirst();
			String v0 = a0.remove(k0);  log.add("     " + k0 + " = " + v0);
			if (a1.containsKey(k0)) {
				String v1 = a1.remove(k0);
				if (!v0.equals(v1)) map.put(n0 + ":" + k0 + "=" + v0, n1 + ":" + k0 + "=" + v1);
			} else {
				map.put(n0 + ":" + k0 + "=" + v0, n1 + ":missed key " + i);
			}
			i++;
		}		
	}
	
	static void compareElements(Text log, Element e0, Element e1, SMap map) {
		String name_0 = e0.getName();
		String name_1 = e1.getName();
		String n0 = new Text(getElementNamePath(e0)).join(".", false); log.add(n0);
		String n1 = new Text(getElementNamePath(e1)).join(".", false);
		if (!name_0.equals(name_1)) map.put(n0, n1);
		SMap a0 = getAttributeMap(e0);
		SMap a1 = getAttributeMap(e1);
		compareAttributes(log, a0, a1, map, n0, n1);
		ArrayList<Element> c0 = new ArrayList<>(e0.getChildren());
		ArrayList<Element> c1 = new ArrayList<>(e1.getChildren());
		while (!c0.isEmpty()) {
			if (c1.isEmpty()) break;
			compareElements(log, c0.remove(0), c1.remove(0), map);
		}
	}

}
