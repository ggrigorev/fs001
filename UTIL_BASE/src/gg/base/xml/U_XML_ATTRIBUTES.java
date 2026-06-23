package gg.base.xml;

import static gg.base.util.U_Print.prn;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.jdom2.Attribute;
import org.jdom2.Element;

//import gg.text.*;

public interface U_XML_ATTRIBUTES {//extends TEXTS {

	boolean DEBUG_ATRIBUTE_LIST = false;// = true;

	static Vector<Attribute> getAttributes(Element e) {
		return new Vector<Attribute>(e.getAttributes());
	}

	static Vector<Attribute> detachAttributes(Element e) {
		Vector<Attribute> V = getAttributes(e);
		for (Attribute a : V)
			a.detach();
		return V;
	}

	static LinkedHashMap<String, String> detachAttributeValues(Element e) { //
		LinkedHashMap<String, String> M = new LinkedHashMap<String, String>();
		detachAttributeValues(e, M, null);
		return M;
	}

	static void detachAttributeValues(Element e, Map<String, String> M) { //
		detachAttributeValues(e, M, null);
	}

	static String detachAttributeValue(Element e, String attributeId) { // detach
		Attribute a0 = e.getAttribute(attributeId);
		if (a0 == null)
			return null;
		a0.detach();
		return a0.getValue();
	}

	static String detachRequiredAttributeValue(Element e,
			String attributeId) throws Exception { // detach
		String s = detachAttributeValue(e, attributeId);
		if (s == null)
			throw new Exception("missed requred attribute '" + attributeId
					+ "'");
		return s;
	}

	static void detachAttributeValues(Element e, Map<String, String> M,
			Collection<String> list) { // detach
		for (Attribute a0 : new Vector<Attribute>(e.getAttributes())) {
			boolean pass = true;
			if (list != null)
				pass = list.contains(a0.getName());
			if (pass) {
				prn(DEBUG_ATRIBUTE_LIST, " attribute: '" + a0.getName()
						+ "' = '" + a0.getValue() + "', pass = " + pass);
				a0.detach();
				M.put(a0.getName(), a0.getValue());
			}
		}
	}

	static void setAttributes(Element e, Map<String, String> M) {
		for (String key : M.keySet()) {
			e.setAttribute(key, M.get(key));
		}
	}

}
