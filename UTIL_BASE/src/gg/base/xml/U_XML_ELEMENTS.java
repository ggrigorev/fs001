package gg.base.xml;

import static gg.base.util.L_Base.ID;
import static gg.base.util.L_Base.VALUE;
import static gg.base.util.U_Classes.JAVA_PRIMITIVES;
import static gg.base.util.U_Print.prn;

import java.util.LinkedHashMap;
import java.util.Vector;

import org.jdom2.Attribute;
import org.jdom2.Element;

//import static gg.xml.U_XML_ATTRIBUTES.*;

public interface U_XML_ELEMENTS  {

	static String getElementStamp(Element e) {
		return getElementStamp(e, true);
	}

	static String getElementStamp(Element e, boolean simple) {
		if (e == null)
			return null;
		String s = "<" + e.getName() + ">";
		String id = e.getAttributeValue(ID);
		if (id != null)
			s += "[" + id + "]";
		if (simple)
			return s;
		Element parent = e.getParentElement();
		if (parent != null)
			s += "{" + getElementStamp(parent) + "}";
		return s;
	}

	static boolean DEBUG_FOUND_PARENT = true;
	static boolean DEBUG_NOT_FOUND_PARENT = true;

	static Element getTopParentElement(Element e) {
		Element p = e.getParentElement();
		while (p != null)
			p = e.getParentElement();
		return e;

	}

	static Element findParentElementOfClass(Element e, String classId) {
		while (e != null) {
			if (e.getName().equals(classId)) {
				prn(DEBUG_FOUND_PARENT, "FOUND parent of class " + classId);
				break;
			}
			e = e.getParentElement();
		}
		prn(DEBUG_NOT_FOUND_PARENT, "NOT FOUND parent of class " + classId);
		return e;
	}

	boolean DEBUG_JAVA_PRIMITIVES = false;// = true;

	static String getJavaPrimitiveValue(Element e) {
		if (e.getChildren().size() != 1) {
			prn(DEBUG_JAVA_PRIMITIVES, "<XML_ELEMENTS>.getJavaPrimitiveValue: "
					+ e.getName() + " children size " + e.getChildren().size()
					+ " != 1 -> " + (e.getChildren().size() != 1));
			return null;
		}
		if (!e.getAttributes().isEmpty()) {
			prn(DEBUG_JAVA_PRIMITIVES, "<XML_ELEMENTS>.getJavaPrimitiveValue: "
					+ e.getName() + " attributes size "
					+ e.getAttributes().size() + " != 0 -> "
					+ (e.getChildren().size() != 0));
			return null;
		}
		Element e0 = (Element) e.getContent(0);
		if (!e0.getChildren().isEmpty()) {
			prn(DEBUG_JAVA_PRIMITIVES, e.getName() + "(" + e0.getName() + ")"
					+ " children size " + e0.getChildren().size() + " != 0 -> "
					+ (e0.getChildren().size() != 0));
			return null;
		}
		if (e0.getAttributes().size() != 1) {
			prn(DEBUG_JAVA_PRIMITIVES, e.getName() + "(" + e0.getName() + ")"
					+ " attributes size " + e0.getAttributes().size()
					+ " != 1 -> " + (e0.getChildren().size() != 1));
			return null;
		}
		String classId = e0.getName();
		if (!JAVA_PRIMITIVES.containsKey(classId)) {
			prn(DEBUG_JAVA_PRIMITIVES, e.getName() + "(" + classId + ") -> " + JAVA_PRIMITIVES.containsKey(classId));
			return null;
		}
		return e0.getAttributeValue(VALUE); // child only attribute is VALUE
	}

	static boolean populateIdAttribute(Element e, String expectedId) {
		// prn("populateIdAttribute: " + e);
		String id = e.getAttributeValue(ID);
		if (id != null) {
			// brk("id " + e + " " + id);
			return false;
		}
		Element e0 = e.getChild(String.class.getSimpleName());
		if (e0 == null) {
			// brk("e0 NULL in " + e);
			return false;
		}
		if (e.indexOf(e0) != 0) {
			// brk("p != 0, " + e.indexOf(e0));
			return false;
		}
		// prn("" + e + " " + e0.getAttributeValue(VALUE));
		if (e0.getAttributes().size() != 1)
			return false;
		id = e0.getAttributeValue(VALUE);
		if (id == null)
			return false;
		if (expectedId != null)
			if (!id.equals(expectedId))
				return false;
		e0.detach();
		e.setAttribute(ID, id);
		return true;
	}

	static void populateIdAttributes(Element e) {
		populateIdAttribute(e, null);
		// prn("populateIdAttributeS: " + e + ", children " +
		// e.getChildren().size());
		for (Element e0 : e.getChildren()) {
			// prn("populateIdAttributeS: " + e + ", child " + e0);
			populateIdAttributes(e0);
		}
	}

	static boolean populateNamedAttribute(Element e) {
		String value = getJavaPrimitiveValue(e);
		if (value == null)
			return false;
		// prn(e.getName() + " " + value);
		Element e0 = (Element) e.removeContent(0);
		e.setAttribute(ID, e.getName());
		e.setName(e0.getName());
		e.setAttribute(VALUE, value);
		return true;
	}

	static void populateNamedAttributes(Element e) {
		// prn(e.getName() + " populateNamedAttributes");
		if (populateNamedAttribute(e))
			return;
		for (Element e0 : e.getChildren())
			populateNamedAttributes(e0);
	}

	static Vector<Element> getAllElements(Element e) {
		return new Vector<Element>(e.getChildren());
	}

	static Vector<Element> detachAllElements(Element e) {
		Vector<Element> V = getAllElements(e);
		for (Element e0 : V)
			e0.detach();
		return V;
	}

	static Vector<Element> getElementsOfClass(Element e, String classId) {
		return new Vector<Element>(e.getChildren(classId));
	}

	static Vector<Element> detachElementsOfClass(Element e,
			String classId) {
		Vector<Element> V = getElementsOfClass(e, classId);
		for (Element e0 : V)
			e0.detach();
		return V;
	}

	static Element detachElementOfClass(Element e, String classId) {
		Element e0 = e.getChild(classId);
		if (e0 != null)
			e.detach();
		return e0;
	}

	static Element detachRequiredElementOfClass(Element e, String classId)
			throws Exception {
		Element e0 = e.getChild(classId);
		if (e0 == null)
			throw new Exception("missed requred element '" + classId + "'");
		e0.detach();
		return e0;
	}

	static Element getRequiredElementOfClass(Element e, String classId)
			throws Exception {
		Element e0 = e.getChild(classId);
		if (e0 == null)
			throw new Exception("missed requred element '" + classId + "'");
		return e0;
	}

	static Element getObjectElement(Element e, String objectId) {
		return getElement(e, null, ID, objectId);
	}

	static LinkedHashMap<String, Element> getObjectElements(Element e) {
		LinkedHashMap<String, Element> M = new LinkedHashMap<String, Element>();
		for (Element e0 : getAllElements(e)) {
			String objectId = e.getAttributeValue(ID);
			if (objectId == null)
				continue;
			M.put(objectId, e0);
		}
		return M;
	}

	static Element detachObjectElement(Element e, String objectId) {
		Element e0 = getElement(e, null, ID, objectId);
		if (e0 != null)
			e0.detach();
		return e0;
	}

	static LinkedHashMap<String, Element> detachObjectElements(Element e) {
		LinkedHashMap<String, Element> M = new LinkedHashMap<String, Element>();
		for (Element e0 : getAllElements(e)) {
			String objectId = e0.getAttributeValue(ID);
			if (objectId == null)
				continue;
			e0.detach();
			M.put(objectId, e0);
		}
		return M;
	}

	static Element getElement(Element e, String classId, String attributeId, String attributeValue) {
		Vector<Element> V = (classId == null) ? getAllElements(e)
				: getElementsOfClass(e, classId);
		for (Element e0 : V) {
			Attribute a = e0.getAttribute(attributeId);
			if (a != null) {
				if (attributeValue == null)
					return e0;
				if (attributeValue.equals(a.getValue()))
					return e0;
			}
		}
		return null;
	}

	static Element detachElement(Element e, String classId, String attributeId, String attributeValue) {
		Element e0 = getElement(e, classId, attributeId, attributeValue);
		if (e0 != null) e0.detach();
		return e0;
	}

	static Element detachRequiredElement(Element e, String classId, String attributeId, String attributeValue) throws Exception {
		Element e0 = getElement(e, classId, attributeId, attributeValue);
		if (e0 == null) throw new Exception("missed requred element (" + classId + ", " + attributeId + " = " + attributeValue + ")");
		e0.detach();
		return e0;
	}

	static Element getRequiredElement(Element e, String classId, String attributeId, String attributeValue) throws Exception {
		Element e0 = getElement(e, classId, attributeId, attributeValue);
		if (e0 == null)
			throw new Exception("missed requred element (" + classId + ", "
					+ attributeId + " = " + attributeValue + ")");
		return e0;
	}

}
