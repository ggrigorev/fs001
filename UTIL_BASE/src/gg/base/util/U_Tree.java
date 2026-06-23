package gg.base.util;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Files.getFilePath;
import static gg.base.util.U_Print.TAP;
import static gg.base.util.U_Print.prn_;
import static gg.base.util.U_Print.brk;
import static gg.base.util.U_Text.tokenizeString;

import java.io.File;

import gg.base.BaseTree;
import gg.base.text.SList;
import gg.base.text.SSet;
import gg.base.text.STable;
import gg.base.text.Text;

public interface U_Tree {

	int fileTableWidth  = tokenizeString("public File instance = new File ( dir(), name ) ;").size();
	int childTableWidth = tokenizeString("public TYPE instance = new TYPE ( args ) ;").size();
	String classPrefix = "T";
	
	static void generateJavaTreeClass_Constructor(String packageName, int level, String className, BaseTree tree, Text java) {
		Text tmp = new Text();

		java.add("package " + packageName + ";\n");
		java.add("import java.io.File;\n");
		java.add("import gg.base.BaseTree;\n");

		java.add("public class " + className + " extends BaseTree {\n");
		if (level == 0) {
			String s = "new File(\"" + getFilePath(tree.rootDir) + "\")";
			tmp.add("public " + className + "() { this(" + s + "); }\n");

			tmp.add("public " + className + "(File rootDir) {");
			tmp.add(TAP + "super(null, \"" + tree.name + "\");");			
			tmp.add(TAP + "this.rootDir = rootDir;");
			tmp.add("}");
			tmp.insertHead(TAP);
			java.addAll(tmp);			
		} else {
			String s = TAP + "public " + className + "(BaseTree parent) { super(parent, \"" + tree.name + "\"); }";
			java.add(s);			
		}
	}

	static void generateJavaTreeClass(int maxLevel, String packageName, File javaDstDir, int level, String className, BaseTree tree, SSet exclude, SList files) {

		Text java = new Text();
		Text tmp = new Text();
		
		STable fileTable  = new STable(fileTableWidth);
		fileTable.setInterval(1);

		STable childTable = new STable(childTableWidth);
		childTable.setInterval(1);
		childTable.L[1] = true; // type name
		childTable.L[5] = true;
		
		generateJavaTreeClass_Constructor(packageName, level, className, tree, java);
		
		level++;
		for (BaseTree childTree : tree.children.values()) {
			if (exclude.contains(childTree.name)) continue;
			if (childTree.children.isEmpty() | (level == maxLevel)) {
				String s = "public File " + childTree.name + " = new File ( dir(), \"" + childTree.name + "\" ) ;";				
				fileTable.nextRow(s);
			} else {
				String childClassName = classPrefix + level + "_" + childTree.name;
				String s = "public " + childClassName + " " + childTree.name + " = new " + childClassName + " ( this ) ;";
				childTable.nextRow(s);
				generateJavaTreeClass(maxLevel, packageName, javaDstDir, level, childClassName, childTree, exclude, files);
			}
		}
		if (! fileTable.isEmpty()) {
			java.add("");
			tmp = fileTable.toText();
			tmp.insertHead(TAP);
			java.addAll(tmp);			
		}
		if (!childTable.isEmpty()) {
			java.add("");
			tmp = childTable.toText();
			tmp.insertHead(TAP);
			java.addAll(tmp);			
		}
		java.add("\n}\n");

		File dstFile = new File(javaDstDir, className + ".java");
		java.print(dstFile);
	
		files.add(getFilePath(dstFile));
	}

	static SList generateJavaTreePackage(int maxLevel, String rootPath, String javaSrcPath, String packageName, SSet exclude) {	
		File root = new File(rootPath);
		assertion(root.isDirectory(), "NOT Directory " + rootPath);

		SList files = new SList();
		int level = 0;

		BaseTree tree = new BaseTree(root);
		tree.build();

		File javaDstDir = new File(javaSrcPath, packageName.replace('.', '/'));
		if (!javaDstDir.isDirectory()) javaDstDir.mkdirs(); 
		prn_(javaDstDir + " " + javaDstDir.isDirectory());		
		
		String treeClassName = classPrefix + level + "_" + tree.name;
		generateJavaTreeClass(maxLevel, packageName, javaDstDir, level, treeClassName, tree, exclude, files);

		return files;
	}


}
