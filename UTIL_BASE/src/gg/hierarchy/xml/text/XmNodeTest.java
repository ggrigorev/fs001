package gg.hierarchy.xml.text;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import gg.base.text.*;
import gg.hierarchy.xml.XmlNode;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;

import org.jdom2.Element;

import static gg.base.util.U_Classes.*;
import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Properties.*;

import static gg.base.xml.U_XML_IO.*;

public class XmNodeTest {

	public XmNodeTest(String[] args) {
		XmlNode_0 node_0 = new XmlNode_0();
		node_0.setAttribute("static_attr_0", "BB");
		node_0.setAttribute("dynamic_attr_0", "CC");
		XmlNode node_1 = new XmlNode("simple");
		node_1.setAttribute("int_attr_0", Integer.valueOf(10));
		node_0.addChild(node_1);
		xmlPrint(node_0);
		xmlPrint(node_0.getElement());
	}

	public static void main(String[] args) {
		String repoName = "Repo_TVT";
		XmlNode tree = new XmlNode(repoName);
		XmlNode sourceTree = addSourceTree(tree);
		xmlPrint(tree);
		xmlPrint(tree.getElement());
	}

	public static XmlNode addSourceTree(XmlNode parent) {
		XmlNode node = new XmlNode("source");
		parent.addChild(node);
		return node;
	}

}
