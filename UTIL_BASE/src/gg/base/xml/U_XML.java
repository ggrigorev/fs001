package gg.base.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.jdom2.Element;
import org.jdom2.Attribute;

import gg.base.text.SMap;
import gg.base.text.SList;

public interface U_XML {
	
    static SMap getAttributeMap(Element e) {
    	SMap map = new SMap();
    	for (Attribute a : e.getAttributes()) map.put(a.getName(), a.getValue());
    	return map;
    }

    static SList getElementNames(Collection<Element> c) {
    	SList list = new SList();
    	for (Element e : c) list.add(e.getName());
    	return list;
    }

    static SList getElementNamePath(Element e) {
    	return getElementNames(getElementPath(e));
    }

    static ArrayList<Element> getElementPath(Element e) {
    	ArrayList<Element> ePath = new ArrayList<>();
    	addElementParent(e, ePath);
		Collections.reverse(ePath);
    	return ePath;
    }

    static void addElementParent(Element e, List<Element> ePath) {
    	if (e == null) return;
   		ePath.add(e);
//prn_("addElementParent " + e.getName());
    	addElementParent(e.getParentElement(), ePath);   	
    }

}
