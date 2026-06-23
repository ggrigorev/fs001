package gg.base.text;

import static gg.base.util.U_Print.*;

import java.util.ArrayList;

import org.jdom2.Element;

public class SNode {
	
	public static final int tapLength = 2;//TAP_LENGTH;
	
	public ArrayList<SNode> children = new ArrayList<>();
	
	public final SNode parent;

	public String value;
	
	public SNode(SNode parent, String value) { 
		this.parent = parent;
		this.value = value;
		if (parent != null) parent.children.add(this);
	}

	public void add(String... ss) { 
		for (String s : ss) new SNode(this, s);
	}
	
	public void addText(SNode node, int level, Text text) {
		String value = node.value;
		if (value != null) { if (!value.isBlank()) text.add(getSpace(level * tapLength) + value); }
		for (SNode child : node.children) addText(child, level + 1, text);
	}
	
	public Text toText() {
		Text text = new Text();
		addText(this, 0, text);
		return text;
	}
	
	@Override
	public String toString() {
		return toText().toString();
	}

}
