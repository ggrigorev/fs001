package gg.hierarchy.base;

import static gg.base.util.U_Print.*;

import static gg.base.util.U_Fields.getFieldName;
import static gg.base.util.U_Nodes.hText;
import static gg.base.util.U_Properties.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import gg.base.text.HText;

public class HNode extends HItem implements I_Node {

	public LinkedHashMap<String, Object> children = new LinkedHashMap<>();
	
	public List<Object> heap = new ArrayList<Object>();

	public static boolean DEBUG_ADD = false;//true;//
	
	public void add(Object val) { 
		if (val == null) return;
		
		String key = getProperty_name(val);
		if (key == null) key = getFieldName(this, val); //prn("field name " + key); 
		setProperty_name(val, key);
		key = getProperty_name(val);
		
//		if (val instanceof Item) {
//			Item item = (Item) val;
//			if (item.name == null) item.name = getFieldName(this, val); // selfName()
//			key = item.name;
//		} else {
//		}
		if ((key != null) && !children.containsKey(key)) { 
//			addChildrenDebug(key, val);
			if (DEBUG_ADD) prn("Node.add Parent<" + kind + ">[" + name + "]: child <" + val.getClass().getSimpleName() + ">[" + key + "] = " + val);
			children.put(key, val); 
		} else {
			if (DEBUG_ADD) prn("Node.add Parent<" + kind + ">[" + name + "]: to heap <" + val.getClass().getSimpleName() + ">[" + key + "] = " + val);
//			addHeapDebug(val);
			heap.add(val);
		}
//prn_(getProperty_parent(val) == null);
		
		boolean flag = setProperty_parent(val, this);
//prn_("setProperty_parent(val, this) = " + flag + " " + (this == null));
//prn("Node of " + kind + ".add(child of " + val.getClass().getSimpleName() + "): child.parent is NULL == " + (getProperty_parent(val) == null));
//prn("parent is null = " + getProperty_parent(val) == null);
//brk();	
	}

//	public void addHeapDebug(Object val) {
//		prn_("Node.add " + id() + " to heap " + val);
//	}
//
//	public void addChildrenDebug(String key, Object val) {
//		prn_("Node.add child " + key + " " + val);
//	}

	@Override public void build() {
		int size = heap.size();
		for (int i = 0; i < size; i++) {
			Object o = heap.remove(0);
			prn(o);
			if (o instanceof HItem) {
				HItem item = (HItem) o;//item.h_text().print();
				item.build();
				add(item);
			} else heap.add(o);
		}
		super.build();
	}

	public HText h_text() { 
		HText h = super.h_text();
		int i = 0;
		for (String k : children.keySet()) {
			Object o = children.get(k);
//prn(o);
			if (o instanceof HItem) {
				HItem item = (HItem) o;//item.h_text().print();
				h.add("child item[" + i + "]");
				h.add(item.h_text());
			} else h.add("child object[" + i + "] " + o);
			i++;
		}
		i = 0;
		for (Object o : heap) {
//			Object o = children.get(k);
//prn(o);
			if (o instanceof HItem) {
				HItem item = (HItem) o;//item.h_text().print();
				h.add("heep item[" + i + "]");
				h.add(item.h_text());
			} else h.add("heep object[" + i + "] " + o);
			i++;
		}
		return h; 
	}

	public void print() { 
		HText h = h_text();
		h.add("+");
		h.print(); 
	}

}

//import static gg.base.util.U_Print.getTap;
//import static gg.base.util.U_Properties.*;

//import gg.base.hier.intf.I_Node;
//import gg.base.text.Text;

//implements I_Node {
//public boolean isRoot() { return (parent == null); }
//public boolean isLeaf() { return true; }
//		

//	public void addText(Text text, String tap) { text.add(tap + getId()); }
//	
//	public void addText(Text text, int level) { addText(text, getTap(level)); }
//
//	public Text toText(int level) { Text text = new Text(); addText(text, level); return text; }
//
//	public Text toText(String tap) { Text text = new Text(); addText(text, tap); return text; }
//
//	public Text toText() { return toText(""); }


