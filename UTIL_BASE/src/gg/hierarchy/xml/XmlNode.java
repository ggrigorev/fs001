package gg.hierarchy.xml;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

import gg.base.text.*;
import gg.base.util.U_Fields;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;

import org.jdom2.Element;
import org.jdom2.Attribute;

import static gg.base.util.U_Classes.*;
import static gg.base.util.U_Constructors.*;
import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Properties.*;

import static gg.base.xml.U_XML_IO.*;

import static gg.hierarchy.xml.U_XmlNode.*;

public class XmlNode { // implements I_XmlNode 

	static Element attributeAsElement(String key, String val) {
		Element ae = new Element(key);
		ae.setText(val);
//xmlPrint(ae);
		return ae;
	}

	public final Class<? extends XmlNode> klass = getClass(); // element name
	public final String kind = klass.getSimpleName();

	public final String title; // element name
	public final Text text = new Text(); 

	public String name;

	// dynamic
	public TMap<Object> dynamicAttributes = new TMap<>(); 
	public XmlNodeList dynamicChildren = new XmlNodeList(); // dynamic children
	
	public SSet attributesAsElements = new SSet(); // structure works after build
	
	public XmlNode(String title) { this.title = title; }

	public void build() {}

	public void parseChild(Element e, Element eChild, String childTitle, String childName, Field childField) {
prn_("\tXmlNode.parseChild [" + childTitle + ":" + childName + "], size = [" + eChild.getAttributes().size() + ":" + eChild.getChildren().size() + "], is FIELD " + (childField != null));		
	}

	//Class<T> memberType, String memberTitle, 
//	<" + memberType.getSimpleName() + ":" + memberTitle + ">
	
	public <T extends XmlNode> XmlNodeList parseList(Element e, Element eList, String listTitle, String listName) {
		XmlNodeList list = new XmlNodeList();
		prn_("\t\tXmlNode.parseList [" + listTitle + ":" + listName + "], size = [" + eList.getAttributes().size() + ":" + eList.getChildren().size() + "]");
		for (Element eMember : eList.getChildren()) {
			XmlNode member = createMember(listTitle);
		
			if (!member.title.equals(eMember.getName())) {
				brk("Unexpected child element name '" + eMember.getName() + "', expected member title '" + member.title + "'");
				continue;
			}
			member.build();
			member.parse(eMember);
			list.add(member);
		}
		return list;
	}

	public void parse(Element e) {
		String eTitle = e.getName();
		String eName = e.getAttributeValue(NAME);		
		prn_("XmlNode.parse [" + eTitle + ":" + eName + "], size = [" + e.getAttributes().size() + ":" + e.getChildren().size() + "]");		
		String s = e.getText().trim();
		if (!s.isEmpty()) text.add(s);
		for (Attribute a : e.getAttributes()) {
			String aName = a.getName();
			String aValue = a.getValue();
			setAttribute(aName, aValue);
		}
		for (Element eChild : e.getChildren()) {
			String childTitle = eChild.getName(); // childTitle = childType
			if (attributesAsElements.contains(childTitle)) { // value is the text
				setAttribute(childTitle, eChild.getText());
				continue;
			}
			Field childField = getField(this, childTitle);
			String childName = eChild.getAttributeValue(NAME);
			if(childField == null) childField = getField(this, childName);


			if(childField != null) {

				String fieldName = childField.getName();
				Class<?> fieldType = childField.getType();
				Object fieldValue = getFieldValue(this, fieldName);

				if (XmlNodeList.class.isAssignableFrom(fieldType)) {
					XmlNodeList list = parseList(e, eChild, childTitle, childName);
					if (fieldValue == null) {
						setFieldValue(this, list, childField);
					} else {
						((XmlNodeList) fieldValue).addAll(list);
					}

				} else if (XmlNodeMap.class.isAssignableFrom(fieldType)) {
					XmlNodeList list = parseList(e, eChild, childTitle, childName);
					if (fieldValue == null) {
						setFieldValue(this, new XmlNodeMap(list), childField);
					} else {
						((XmlNodeMap) fieldValue).addAll(list);
					}
				}

			}
			parseChild(e, eChild, childTitle, childName, childField);
		}
	}

	public Object getAttributeValue(String name) {
		Object value = getFieldValue(this, name);// static field
		if (value == null) value = dynamicAttributes.get(name);
		return value;
	}

	public void setAttribute(String name, Object value) {
		Field field = getField(this, name);
		if (field != null) {// static field
			setFieldValue(this, value, field);
prn_("XmlNode.setAttribute field " + field + " = " + value);
		} else {// local dynamic
			if (value == null) {
				dynamicAttributes.remove(name);
prn_("XmlNode.setAttribute dinamic remove '" + name + "'");
			}
			else {
prn_("XmlNode.setAttribute dinamic assign '" + name + "' = " + value);
				dynamicAttributes.put(name, value.toString());
			}
		}
	}
 
	public SMap getStaticAttributes() {
		SMap map = new SMap();
		TMap<Object> staticMembers = getFieldValueMap(this);
		for (String key : staticMembers.keySet()) {
			Object o = staticMembers.get(key);
			if (o == null) continue;
			Class<?> t = o.getClass();
			if (!isJavaPrimitive(t)) continue;
//			map.put(key, t.getSimpleName() + ":" + o);
			map.put(key, "" + o);
		}
		return map;
	}

	public SMap getDynamicAttributes() {
		SMap map = new SMap();
		for (String key : dynamicAttributes.keySet()) {
			Object o = dynamicAttributes.get(key);
			if (o == null) continue;
			Class<?> t = o.getClass();
			if (!isJavaPrimitive(t)) continue;
//			map.put(key, t.getSimpleName() + ":" + o);
			map.put(key, "" + o);
		}
		return map;
	}

	public SMap getAttributes() {
		SMap map = getStaticAttributes();
		map.addAll(getDynamicAttributes());
		return map;
	}

	public void addChild(XmlNode child) {
		String childName = (String) getProperty(child, NAME);
		if (childName == null) childName = child.title;
		Field field = getField(this, childName);
		if (field != null) {// static field
			Class<?> fieldType = field.getType();
			if (!fieldType.isAssignableFrom(child.getClass())) {
				brk("child field '" + childName + "' found, type " + fieldType.getSimpleName());
			}
			setFieldValue(this, child, field);
		} else {// local dynamic
			dynamicChildren.add(child);
		}
	}

	public XmlNode getChild(String childName) {
		Object o = getFieldValue(this, childName);// static field
		if (o != null) {
			if (o instanceof XmlNode) return (XmlNode) o;
		} 
		for (XmlNode child : dynamicChildren) {
			String nodeName = (String) getProperty(child, NAME);
			if (nodeName == null) nodeName = child.title;
			if (nodeName.equals(childName)) return child;
		}
		return null;
	}

	public ArrayList<Object> getStaticChildren() {
		ArrayList<Object> list = new ArrayList<>();
		TMap<Object> staticMembers = getFieldValueMap(this);
		for (String key : staticMembers.keySet()) {
			Object o = staticMembers.get(key);
			if (o == null) continue;
			list.add(o);
//			if (o instanceof XmlNode) list.add((XmlNode) o); // static XmlNode field
//			if (o instanceof XmlNodeList) list.add((XmlNodeList) o); // static XmlNodeList field			
//			if (o instanceof XmlNodeMap) list.add((XmlNodeMap) o); // static XmlNodeMap field						
		}
		return list;
	}

	public ArrayList<Object> getChildren() {
		ArrayList<Object> list = getStaticChildren();
		list.addAll(dynamicChildren);
		return list;
	}

	public Element getElement() {
		Element eNode = new Element(title);
		SMap map = getAttributes();		
//prn_("getElement.getAttributes map", map);
//brk();
		for (String key : map.keySet()) {
			String val = map.get(key);
			if (attributesAsElements.contains(key)) {
				Element attributeAsElement = attributeAsElement(key, val);
				eNode.addContent(attributeAsElement);
//				xmlPrint(attributeAsElement);
//				xmlPrint(eNode);
//brk("attributeAsElement " + eNode.getContentSize());
			} else { 
				eNode.setAttribute(key, val);
			}
		}
		ArrayList<Object> list = getChildren();
//prn_("Children : ", list);
		for (Object o : list) {			
//prn_("Memeber of : " + o.getClass().getSimpleName());
			
			if (o instanceof XmlNode) {
				XmlNode child = (XmlNode) o; // static XmlNode field
				eNode.addContent(child.getElement());
				continue;
			} 
			
			if (o instanceof XmlNodeList) {
				XmlNodeList childList = (XmlNodeList) o;
				if (!childList.isEmpty()) {
					Element eList;
					if (childList.name == null) {
						eList = eNode;
					} else {
						eList = new Element(childList.name);
						eNode.addContent(eList);
					}
					for (XmlNode child : childList) eList.addContent(child.getElement());
				}
				continue;
			}

			if (o instanceof XmlNodeMap) {
				XmlNodeMap childMap = (XmlNodeMap) o;
				if (!childMap.isEmpty()) {
					Element eList;
					if (childMap.name == null) {
						eList = eNode;
					} else {
						eList = new Element(childMap.name);
						eNode.addContent(eList);
					}
					for (XmlNode child : childMap.values()) eList.addContent(child.getElement());
				}
				continue;
			}

			if (o instanceof Text) {
				String fieldName = getFieldName(this, o);
				if (attributesAsElements.contains(fieldName)) {
					Text txt = (Text) o;
					String val = txt.toString();
					eNode.addContent(attributeAsElement(fieldName, val));
				}
			}
			
		}
		
		String s = text.toString().trim();
		if (!s.isEmpty()) {
			eNode.addContent(s);
//brk("getElement text '" + s + "', size " + text.size()+ ", content " + eNode.getContentSize());
		}
		
		return eNode;
	}

}

/*
	static Object getChildNode(XmlNode parent, String childTitle, String childName) { // build dynamic node 
		Class<?> parentType = parent.getClass();
prn_("parent type "+ parentType.getSimpleName() + ", childTitle "+ childTitle + ", childName "+ childName);
		TMap<Field> fieldMap = getFieldMap(parentType);
prn_("fieldMap", fieldMap);
		TMap<Object> fieldValueMap = getFieldValueMap(parent);
prn_("fieldValueMap", fieldValueMap);
		Field childField = null;
		if (childName != null) {
			childField = getField(parent, childName);
			if (childField != null) prn_("childField by name '" + childName + "', fields ");
			else prn_("field map keyset contains name '" + childName + "' is " + fieldMap.containsKey(childName));
		} else {
			if (childTitle != null) {
				childField = getField(parent, childTitle);			
				if (childField != null) prn_("childField by title '" + childTitle + "'");
				else prn_("field value map keyset contains title '" + childName + "' is " + fieldValueMap.containsKey(childName));
			}
		}
		if (childField != null) {
			String fieldName = childField.getName();;
			Class<?> fieldType = childField.getClass();
			Object fieldValue = getFieldValue(childField, parent);
			prn_("childField <" + childField + "> = " + fieldValue);
			if (fieldValue == null) {
				if (XmlNode.class.isAssignableFrom(fieldType)) {// direct node
prn_("Create XmlNode");
					XmlNode node = newInstance(fieldType.getName(), new String[]{childTitle});
					node.build();
					setFieldValue(parent, node, childField);
					return node;
				} else if (XmlNodeList.class.isAssignableFrom(fieldType)) {
prn_("Create XmlNodeList");
					XmlNodeList list = new XmlNodeList(); 
					setFieldValue(parent, list, childField);
					return list;
				} else if (XmlNodeMap.class.isAssignableFrom(fieldType)) {
prn_("Create XmlNodeMap");
					XmlNodeMap map = new XmlNodeMap(); 
					setFieldValue(parent, map, childField);
					return map;
				}  				
			} else {
				Class<?> fieldValueType = fieldValue.getClass();
				if (fieldValue instanceof XmlNode) {// direct node
prn_("Get XmlNode");
					XmlNode node = (XmlNode) fieldValue;
					return node;
				} else if (fieldValue instanceof XmlNodeList) {
prn_("Get XmlNodeList");
					XmlNodeList list = (XmlNodeList) fieldValue;
					return list;
				} else if (fieldValue instanceof XmlNodeMap) {
prn_("Get XmlNodeMap");
					XmlNodeMap map = (XmlNodeMap) fieldValue;
					return map;
				}  				
			}
		} else {
				
			prn_("childField title '" + childTitle + ", or name '" + childName + "' NOT found");
		
			
		}
		return null;
	}
	
	public void parseList(XmlNodeList list, Element eList) {
		
	}
	public void parseMap(XmlNodeMap map, Element eMap) {
		
	}
	public void parse(Element e) {
//		this.title = e.getName();
		String s = e.getText().trim();
		if (!s.isEmpty()) text.add(s);
		for (Attribute a : e.getAttributes()) {
			String aName = a.getName();
			String aValue = a.getValue();
			setAttribute(aName, aValue);
		}
		for (Element eChild : e.getChildren()) {
			String childTitle = eChild.getName(); // childTitle = childType
			if (attributesAsElements.contains(childTitle)) { // value is the text
				setAttribute(childTitle, eChild.getText());
				continue;
			}
			String childName = eChild.getAttributeValue(NAME);
			Object childObject = getChildNode(this, childTitle, childName);
			assertion(childObject != null, "");
			if (childObject instanceof XmlNode) {
				XmlNode child = (XmlNode) childObject;
				child.parse(eChild);
				addChild(child);
			}
			if (childObject instanceof XmlNodeList) {
				XmlNodeList child = (XmlNodeList) childObject;
				parseList(child, eChild);
				addChild(child);
			}
			if (childObject instanceof XmlNodeMap) {
				XmlNodeMap child = (XmlNodeMap) childObject;
				parseMap(child, eChild);
				addChild(child);
			}
		}
	}
*/
//{
//int before = eNode.getContentSize();
//xmlPrint(eNode);
//
//eNode.addContent(s);// removes all before add setText(s);
////int after = eNode.getContentSize();
////xmlPrint(eNode);
////brk("setText '" + s + "', before " + before + ", after " + after);
//eNode.addContent(new Element("MY_ELEMENT").setText("aaa"));
//eNode.addContent(attributeAsElement("MY_ELEMENT", "aaa"));
//xmlPrint(eNode);
//try { xout.output(eNode, System.out); } catch (IOException ex) {
//	assertion(ex, "U_XML_IO.xmlPrint");
//}
//brk("getElement " + eNode.getContentSize());
