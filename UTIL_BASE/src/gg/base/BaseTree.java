package gg.base;

import static gg.base.util.L_Base.*;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Files.*;
import static gg.base.xml.U_XML_IO.xmlToString;

import java.io.File;

import org.jdom2.Element;

import gg.base.text.*;

public class BaseTree {

	public File rootDir;
	
	public BaseTree parent;
	public String name;
	public String dirName;

	public TMap<BaseTree> children = new TMap<>();
	
	public BaseTree() {}	

	public BaseTree(File dir) {
		this(null, dir.getName());
		rootDir = dir.getParentFile();
	}

	public BaseTree(BaseTree parent, Element e) {
		this(parent, e.getName());
		for (Element eChild : e.getChildren()) new BaseTree(this, eChild);	
	}
	
	public BaseTree(BaseTree parent, String dirName) {
		this.parent = parent;
		this.dirName = dirName;
		this.name = dirName.replace('.', '_');
		if (parent != null) parent.children.put(name, this);
	}
	
	public File parentDir() { return (parent == null) ? rootDir : parent.dir(); } 

	public File dir() { return new File(parentDir(), dirName); }

	public BaseTree add(String childName) { return new BaseTree(this, childName); }
	
	public void build() {
		File dir = dir();
		if (!dir.isDirectory()) dir.mkdirs();
		for (File f : dir.listFiles()) {
//prn_("f " + f + " " + f.isDirectory());
			if (f.isFile()) continue;
			String subName = f.getName();
			String childName = subName.replace('.', '_');
			if (!isIdentifier(childName)) continue;
			BaseTree child = children.get(childName);
			if (child == null) {
				child = new BaseTree(this, subName);
			}
			child.build();
		}
	}

	public Element toXml() {
		Element e = new Element(name);
		if (parent == null) e.setAttribute("root", getFilePath(rootDir));
		if (!dirName.equals(name)) e.setAttribute("dir", dirName);
		for (BaseTree child : children.values()) e.addContent(child.toXml());
		return e;
	}

	public String toString() { return xmlToString(toXml()); }
	
}

/*

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

import gg.base.text.*;
import gg.hierachy.rtl.Item;

import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Files.*;
import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Constructors.*;

public class BaseTree extends Base {

	public static boolean BASE_DEBUG;

	public ArrayList<BaseTree> children = new ArrayList<>(); // keep no-name and tezka 

//	protected Object   parent;
	protected BaseTree tree;
	protected File     dir;
	
//	public void build(Object parent) { 
//		assertion(parent != null, "NULL parent object");
//		this.parent = parent;
//		build();
//	}

	@Override
	public void build() {
		File parentDir;
// Root		assertion(parent != null, "NULL parent object");
		assertion(name != null, "NO name, kind " + kind);
		if (parent instanceof File) {
			parentDir = (File) parent;
		} else if (parent instanceof BaseTree) {
			tree = (BaseTree) parent;
			parentDir = tree.dir;
		} else {
			parentDir = null;
			assertion("Invalid parent object type " + parent.getClass().getSimpleName());			
		}
		assertion(parentDir.isDirectory(), "Invalid parent directory " + getFilePath(parentDir));			
		dir = new File(parentDir, name);
if(BASE_DEBUG) prn_("Tree.build " + name + ": dir " + dir);
//prn_("fields", fields); brk();

		for (String fieldName : fields.keySet()) {
			if (skipFields.contains(fieldName)) continue; // avoid recursion
			
			Field field = fields.get(fieldName);
			Class<?> fieldType = field.getType();
			Object fieldValue = getFieldValue(field, this);

if(BASE_DEBUG) prn_("Tree.build " + name + ", field " + fieldName + " = " + fieldValue);

			File subDir;
			if (fieldValue == null) { // create
				if (BaseTree.class.isAssignableFrom(fieldType)) {
	//				File sub = getSubDir(fieldName);
if(BASE_DEBUG) prn_("Create sub Tree <" + fieldType.getSimpleName() + ">" + fieldName);
					BaseTree subTree = newInstance(fieldType);// BaseTree();
					subTree.name = fieldName;
					subTree.parent = this;
					subTree.build();
					fieldValue = subTree;
					subDir = subTree.dir;
				} else if (File.class.isAssignableFrom(fieldType)) {
					subDir = new File(dir, fieldName);
if(BASE_DEBUG) prn_("Create sub Directory " + subDir);
					if (!subDir.isDirectory()) subDir.mkdirs();
					fieldValue = subDir;
				}
				setFieldValue(this, fieldValue, field); 
			}

			File sub = new File(dir, fieldName); // check directory
			assertion(sub.isDirectory(), "Sub directory is a file " + getFilePath(sub));

		}
	}

}
*/
//	public File getSubDir(String dirName) {
//		File sub = new File(parent, dirName);
//		assertion(!sub.isFile(), "Existing file " + getFilePath(sub));
//		if (!sub.isDirectory()) sub.mkdirs();
//		return sub;
//	}



//if ((fieldValue instanceof BaseTree) || (fieldValue instanceof File)) {
//BaseTree subTree = (BaseTree) fieldValue;
//File sub = new File(dir, fieldName); // check directory
//assertion(sub.isDirectory(), "Sub directory is a file " + getFilePath(sub));
////if (fieldValue instanceof BaseTree) { // hierarchy
////	BaseTree subTree = (BaseTree) fieldValue;
////	subTree.build(sub);
////}
//}			

//		
//		String s = "\n<" + kind + ">[" + name +"] directory " + dir;
//		for (String fieldName : fields.keySet()) {
//			Field field = fields.get(fieldName);
//			Class<?> fieldType = field.getType();
//			Object fieldValue = getFieldValue(field, this);
//			
//			if (fieldValue == null) { // create
//				if (BaseTree.class.isAssignableFrom(fieldType)) {
//					File sub = getSubDir(fieldName);
//					BaseTree subTree = new BaseTree();
//					setFieldValue(this, subTree, field);					
//					fieldValue = subTree;
//				} else if (File.class.isAssignableFrom(fieldType)) {
//					File sub = new File(dir, fieldName);
//					if (!sub.isDirectory()) sub.mkdirs();
//					fieldValue = sub;
//				}
//				setFieldValue(this, fieldValue, field); 
//			}
//			
//			if ((fieldValue instanceof BaseTree) || (fieldValue instanceof File)) {
//				File sub = new File(dir, fieldName); // check directory
//				assertion(sub.isDirectory(), "Sub directory is a file " + getFilePath(sub));
//				if (fieldValue instanceof BaseTree) { // hierarchy
//					BaseTree subTree = (BaseTree) fieldValue;
//					subTree.build(sub);
//				}
//			}			
//		}
//
//		s += "\n    rtl = <" + (srcDirRtl.exists()    ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcDirRtl);	
//		s += "\n    vrf = <" + (srcDirVrf.exists()    ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcDirVrf);	
//		s += "\n    tcl = <" + (srcVivadoTCL.exists() ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoTCL);	
//		s += "\n    brd = <" + (srcVivadoBRD.exists() ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoBRD);	
//		s += "\n    xdc = <" + (srcVivadoXDC.exists() ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoXDC);	
//		s += "\n     ip = <" + (srcVivadoIP.exists()  ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoIP);	
//		s += "\n     bd = <" + (srcVivadoBD.exists()  ? "+" : "-") + "> " + getRelativeFilePath(srcDir, srcVivadoBD);	
//		return s;
//	}

//} //BaseTree subTree = (BaseTree) getFieldValue(field, this); 
