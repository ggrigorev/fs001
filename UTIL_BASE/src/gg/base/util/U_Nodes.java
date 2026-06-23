package gg.base.util;

import static gg.base.util.U_Base.getObjectKind;
import static gg.base.util.U_Classes.selectByType;
import static gg.base.util.U_Classes.selectPrimitives;
import static gg.base.util.U_Fields.getFieldValueMap;
import static gg.base.util.U_Properties.getProperty_children;
import static gg.base.util.U_Properties.getProperty_heap;
import static gg.base.util.U_Properties.getProperty_name;
import static gg.base.util.U_Properties.getProperty_parent;
import static gg.base.util.U_Text.joinText;

import static gg.base.util.U_Print.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import gg.base.text.HText;
import gg.base.text.SSet;
import gg.base.text.Text;
import gg.hierarchy.base.HItem;
import gg.hierarchy.base.HNode;

public interface U_Nodes {
	
//	String H_BUILD_METHOD_NAME	= "build";
	String H_NAME_DELIMETER		= ".";
	
//	static <T> T getNodeParent(Item item, String nodeType) {
//		Object parent = item.parent();
//		if (parent instanceof Node) {
//			Node node = (Node) parent;
//			if (nodeType.equals(node.type)) return node; 
//			if (type.isAssignableFrom(parent.getClass())) return (T) parent; 			
//		}
//		return null; 
//	}

	static <T> T getParent(Object object, Class<T> type) {
		Object parent = getProperty_parent(object);
		if (parent == null) return null; 
		if (type.isAssignableFrom(parent.getClass())) {
			T r = (T) parent;
			//prn("type " + type + "\n\tparent " + parent + ", matches = " + type.isAssignableFrom(parent.getClass())); 
			//brk("U_Nodes.getParent returns " + r);
			return  r;
		}
		return getParent(parent, type); 
	}

	static Object getRoot(Object object) {
		Object parent = getProperty_parent(object);
		if (parent == null) return object; 
		return getRoot(parent); 
	}

	static Text getNames(Collection<?> collection) {
		Text names = new Text();
		for (Object object : collection) {
			String name = getProperty_name(object);
			if (name != null) names.add(name);
		}
		return names;
	}

	static SSet getNamesUnique(Collection<?> collection) {
		return new SSet(getNames(collection));
	}

	static Text hPath(Object object) {
		Text path = new Text();
		hPath(object, path);
		return path;
	}
	
	static void hPath(Object object, Text path) {
		String name = getProperty_name(object);
		if (name == null) return;//name = NULL;//return;
		path.add(0, name);
		Object parent = getProperty_parent(object);
		if (parent != null) hPath(parent, path);
	}
	
	static String hName(Object object) {
		Text path = hPath(object);
		String pName = joinText(H_NAME_DELIMETER, false, path);
		return pName;		
	}
	
	static void hText(Object object, HText ht) {
		List<Object> declared = new ArrayList<>();
		for (Object primitive : staticPrimitives(object)) {
			declared.add(primitive);
		}
	}
	
	static HText hText(Object object) { 
		HText ht = new HText();
		if (object != null) {
			if (object instanceof HItem); else ht.add(getObjectKind(object)); // implicit property
			hText(object, ht);
		}
		return ht;
	}

	
	static boolean skipBuild(String packageName) {
		for (String key : SKIP_BUILD) {
			if (packageName.startsWith(key)) return true;
		}
		return false;
	}

	LinkedHashSet<String> SKIP_BUILD = new LinkedHashSet<>(new Text(
			"java", "com", "org"
	));
	
	static Map<String, Object> getStatics(Object object) { return getFieldValueMap(object); }
	
	static List<Object> getDynamics(Object object) { 
		List<Object> list = new ArrayList<>();
		Map<String, Object> M = getProperty_children(object);
		if (M != null) list.addAll(M.values());
		List<Object> L = getProperty_heap(object);
		if (L != null) list.addAll(L); 
		return list;
	}

	static <T extends HItem> T findChild(String name, Object object, Class<T> refType) { 
		List<T> list = selectAll(object, refType); 
		for (T child : list) if (name.equals(child.name)) return child;
		return null; 
	}
	
	static <T> List<T> selectStatics(Object object, Class<T> refType) { return selectByType(getStatics(object).values(), refType); }

	static <T> List<T> selectDynamics(Object object, Class<T> refType) { return selectByType(getDynamics(object), refType); }

	static <T> List<T> selectAll(Object object, Class<T> refType) { 
		List<T> list = selectStatics(object, refType); 
		list.addAll(selectDynamics(object, refType));
		return list; 
	}

	static Text staticNames(Object object) { return new Text(getStatics(object).keySet()); }
	
	static Text dynamicNames(Object object) { return getNames(getDynamics(object)); }

	static Text allNames(Object object) { 
		Text names = staticNames(object); 
		names.addAll(dynamicNames(object));
		return names; 
	}

	static List<HItem> staticItems(Object object) { return selectStatics(object, HItem.class); }

	static List<HNode> staticNodes (Object object) { return selectStatics(object, HNode.class); }
	
	static List<Object> staticPrimitives(Object object) { return selectPrimitives(getStatics(object).values()); }
	
	static List<HItem> dynamicItems(Object object) { return selectDynamics(object, HItem.class); }
	
	static List<HNode> dynamicNodes(Object object) { return selectDynamics(object, HNode.class); }
	
	static List<Object> dynamicPrimitives(Object object) { return selectPrimitives(getDynamics(object)); }

//	static void addStaticItem(Item child) { 
//		Node parent = (Node) child.parent(); 
//		child.selfName(); 
//		parent.add(child);
//	}

//	static void getPrimitiveString(Object javaPrimitive) {
//		String s = java;
//		for (Object primitive : staticPrimitives(object)) ht.add(primitive);
//	}
//	
}

//static int cleanHeap() { 
//List<Object>
//int size = heap.size();
//int n = 0; 
//for (int i = 0; i < size; i++) {
//	Object o = heap.remove(0);
//	if (o == null) n++; else heap.add(o);
//}
//return n;
//}
//

//ht.add(getObjectId(object, getProperty_name(object)));
//	public List<Object> dynamics() { // after build
//		ArrayList<Object> list = new ArrayList<>();
//		for (Object o : heap) if (o != null) list.add(o);
//		return list; 
//	}
//
//	public Text dynamicNames() { return Item.getNames(dynamicItems()); }
//
//	public <T> List<T> selectDynamics(Class<T> refType) { return select(dynamics(), refType); }
//	
//	public List<Item> dynamicItems() { return selectDynamics(Item.class); }
//	
//	public List<Node> dynamicNodes() { return selectDynamics(Node.class); }
//	
//	public List<Node> staticNodes () { return selectStatics (Node.class); }
//	
//	public List<?> dynamicPrimitives() { return selectPrimitives(dynamics()); }
//	public List<Object> dynamics() { // after build
//		ArrayList<Object> list = new ArrayList<>();
//		for (Object o : heap) if (o != null) list.add(o);
//		return list; 
//	}
//
//	public <T> List<T> selectStatics(Class<T> refType) { return selectByType(statics.values(), refType); }
//	
//	public Text staticNames() { return new Text(statics.keySet()); }
//
//	public List<Item> staticItems() { return selectStatics(Item.class); }
//
//	public List<?> staticPrimitives() { return selectPrimitives(statics.values()); }
//	
//	static boolean isItem(Object object) {
//		Field field = getField(object, NAME);
//		return (field != null);
//	}



//boolean DEBUG_NAMES		= true;//false;
//boolean DEBUG_PARENTS	= true;//false;
////int hBuildCntr = 0;
//static void hBuild(Object object) {
////	if (object.getClass().getSimpleName().equals("Axi4")) {
////	prn(true, "build of Axi4 "); brk();
////	}
////prn_("------------------ hBuild" + getObjectId(object));			
//
//	Map<String, Object> statics = getStatics(object); // no NULLs
////prn(true, "statics: " + object, statics);			
//	for (String key : statics.keySet()) {
//		Object val = statics.get(key);
//
//		String name = getProperty_name(val);
//		String type = getProperty_type(val);
//
//		if (setProperty_parent(val, object)) { // try to set parent
//			Object parent = getProperty_parent(val);
//			if (DEBUG_PARENTS) {
//				//prn(false, "assign parent for " + getObjectId(val, type, name) + " as expected = " + (parent == object));
//				//brk();
//			}
//		}
//
//		if (name == null) {
//			String fieldName = getFieldName(object, val);
//			if (setProperty_name(val, fieldName)) {
//				String old_name = name;
//				name = getProperty_name(val);
//				if (DEBUG_NAMES) { 
//					//prn(false, "assign name " + old_name + " -> " + getObjectId(val, type, name)); 
//					//brk();
//				}
//			}
//		}
//		
//		if (skipBuild(val.getClass().getPackageName())) continue;
//		if (invokeMethod(val, H_BUILD_METHOD_NAME)) continue;
////		hBuild(val);
//		
//	}
////	if (invokeMethod(object, H_BUILD_METHOD_NAME)) return true;
////	return false;
//}
