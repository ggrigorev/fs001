package gg.hierarchy.base;

import static gg.base.util.U_Fields.getFieldName;
import static gg.base.util.U_Nodes.getStatics;
import static gg.base.util.U_Nodes.hText;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
//import static gg.base.xml.U_XML_IO.*;
import static gg.base.util.U_Properties.getProperty_name;
import static gg.base.util.U_Properties.setProperty_name;
import static gg.base.util.U_Properties.setProperty_parent;

import java.lang.reflect.Array;
import java.util.Map;

import gg.base.text.HText;
import gg.base.text.SPair;

public class HItem implements I_Item, I_Multi {

	public final String kind = getClass().getSimpleName();

	protected Object parent;

	public Object parent() { return parent; }
	public void parent(Object parent) { this.parent = parent; }

	public String type;
	public String type() { return type; } public void type(String type) { this.type = type; }

	public String name;
	public String name() { return name; }  public void name(String name) { this.name = name; }

	public String[] indexes;	
	@Override public void setMultiPosition(String... pp) { indexes = pp; }
	@Override public String[] indexes() { return indexes; }
	
	public SPair[] ranges;
	@Override public void setMultiRanges(SPair... rr) { ranges = rr; }
	@Override public SPair[] ranges() { return ranges; }

	public String selfName() {
		if ((name == null) && (parent != null)) name = getFieldName(parent, this);
		return name;
	}
	
	public static boolean DEBUG_ITEM_BUILD = false;//true;//
	
	public void build() { 
if(DEBUG_ITEM_BUILD) prn("BEFORE hBuild of " + kind); //brk();

		Map<String, Object> statics = getStatics(this); // no NULLs
if(DEBUG_ITEM_BUILD) prn(true, "statics: ", statics);			
		for (String key : statics.keySet()) {
			Object val = statics.get(key);
			if (val.getClass().isArray()) {
if(DEBUG_ITEM_BUILD) prn("Item.build: ARRAY " + key); //brk();
				for (int i = 0; i <  Array.getLength(val); i++) {
					Object e = Array.get(val, i);
					build(e , key + i);
if(DEBUG_ITEM_BUILD) prn("Item.build: element " + getProperty_name(e));
				}
//brk();
			} else {
				build(val, key);
			}
		}
	}

	private void build(Object val, String key) { 
		setProperty_parent(val, this);

		String name = getProperty_name(val);
		if (name == null) setProperty_name(val, key);
		
		if (val instanceof HItem) ((HItem) val).build(); 
	}

	public String id() { return getObjectId(this, type, name); }

	@Override public String toString() {
		String s = id();
		return s; 
	}

//	@Override public String toString() { return h_text().toString(); }
	
	public HText h_text() { 
		HText h = hText(this);
		h.add("--" + id());
		return h; 
	}
	
}
