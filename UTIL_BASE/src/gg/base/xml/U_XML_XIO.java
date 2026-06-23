package gg.base.xml;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Files.findFileDeep;
import static gg.base.util.U_Files.getFilePath;
import static gg.base.util.U_Formats.unpackStringList;
import static gg.base.util.U_Print.prn;
import static gg.base.xml.L_XML.HIERARCHY_DIRECTORY;
import static gg.base.xml.L_XML.HIERARCHY_FILE;
import static gg.base.xml.L_XML.HIERARCHY_REFERENCE;
import static gg.base.xml.U_XML_ATTRIBUTES.detachAttributeValues;
import static gg.base.xml.U_XML_ATTRIBUTES.setAttributes;
import static gg.base.xml.U_XML_COMMENTS.detachAllComments;
import static gg.base.xml.U_XML_IO.xin;
import static gg.base.xml.U_XML_IO.xmlFromFile;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Parent;

public interface U_XML_XIO {

	static boolean DEBUG_MISSED_FILES = true;

	static Element hxmlFromFile(String path) {
		return hxmlFromFile(new File(path), null);
	}

	static Element hxmlFromFile(File f) {
		return hxmlFromFile(f, null);
	}

	/*
	 * file f contains XML element with hierarchy references - 
	 * dirs - search directories, prent directory of f will be added
	 * returns element from file 
	 */
	static Element hxmlFromFile(File f, Collection<File> dirs) {
		File dir = f.getParentFile();
		if (dirs == null) dirs = new Vector<File>();
		if (!dirs.contains(dir)) {
			if (dirs instanceof Vector) ((Vector<File>) dirs).add(0, dir); // for Vector - first
			else dirs.add(dir);
		}
		Document d = null;
		try { d = xin.build(f); } catch (Exception ex) {
			assertion(ex, "fail to read XML from file '" + getFilePath(f) + "'");
		}
		Element e = d.getRootElement();
		xmlExpandHierarchy(e, dirs);
		return d.detachRootElement();
	}

	static void xmlExpandHierarchy(Element e, Collection<File> dirs) {
//		prn(false, xmlToString(e));
		prn(false, e);
		// pause("dirs" + dirs);
		if (dirs == null) dirs = new Vector<File>();
		String s = e.getAttributeValue(HIERARCHY_DIRECTORY);
		if (s == null) s = "";
		Vector<String> hpath = new Vector<>(unpackStringList(s));
		e.removeAttribute(HIERARCHY_DIRECTORY);
		for (String path : hpath) dirs.add(new File(path));
		for (Element eChild : new Vector<Element>(e.getChildren())) xmlExpandHierarchy(eChild, dirs);
		File f = null;
		Attribute aHfile = e.getAttribute(HIERARCHY_FILE);
		if (aHfile != null) {
			f = new File(aHfile.getValue().trim()); // reference is an explicit  file name
			if (!f.isFile()) prn(DEBUG_MISSED_FILES, "xmlExpandHierarchy: missed reference file '" + getFilePath(f) + "' doesn't exist");
			prn(false, "xmlExpandHierarchy: explisit reference HIERARCHY_FILE " + getFilePath(f));
		} else {
			Attribute aHref = e.getAttribute(HIERARCHY_REFERENCE);
			if (aHref != null) {
				String reference = aHref.getValue().trim();
				for (File dir : dirs) {
					f = findReferenceFile(dir, reference, e);
					if(f != null) break;
				}
//				f = findReferenceFile(reference, e, dirs);
				prn(false, "xmlExpandHierarchy: HIERARCHY_REFERENCE reference " + reference);
				if (f != null) prn(false, "xmlExpandHierarchy: HIERARCHY_REFERENCE file " + getFilePath(f));
				prn(false, "xmlExpandHierarchy: HIERARCHY_REFERENCE directories ", dirs);
				if (f != null) {
					Element e0 = hxmlFromFile(f, dirs);
					e0.setName(e.getName());
					prn(false, e0);
					e.removeAttribute(HIERARCHY_REFERENCE);
					Map<String, String> M = new LinkedHashMap<String, String>();
					detachAttributeValues(e0, M); // detach all, reference first
					detachAttributeValues(e, M); // override if any
					setAttributes(e0, M);
					e0.setAttribute(HIERARCHY_FILE, getFilePath(f));

					Parent eParent = e.getParent();
					int p = eParent.indexOf(e);
					e.detach();
					if (eParent instanceof Element) {
						((Element) eParent).addContent(p, e0);
					} else if (eParent instanceof Document) {
						((Document) eParent).addContent(p, e0);
					}
				} else
					prn(DEBUG_MISSED_FILES, "xmlExpandHierarchy: missed reference '" + reference + "' not found in " + dirs);
			}

		}
	}

	static File findReferenceFile(File dir, String reference, Element e) {//,	Collection<File> dirs
		Attribute aHref = e.getAttribute(HIERARCHY_REFERENCE);
		if (aHref == null)
			return null;

		File f = null;

//		f = findFile(reference, dirs); // reference is an explicit file name
		f = findFileDeep(dir, reference); // reference is an explicit file name
		if (f != null)
			return f;

		String referenceClass = e.getName();
//		f = findFile(reference + "." + referenceClass, dirs);
		f = findFileDeep(dir, reference + "." + referenceClass);
		if (f != null)
			return f;

		Element eParent = e.getParentElement();
		String parentClass = eParent.getName();
//		f = findFile(parentClass + "." + reference, dirs);
		f = findFileDeep(dir, parentClass + "." + reference);
		if (f != null)
			return f;

		return null;
	}

	static Element xmlRead(String path) throws Exception { // from one
																	// directory
		return xmlRead(new File(path), new File[] {});
	}

	static Element xmlRead(String path, File dir) throws Exception {
		File f = findFileDeep(dir, path); // deep
		if (f == null) throw new Exception("XML file NOT found\n" + path);
//		return xmlRead(new File(path), dirs);
		return xmlRead(f, dir);
	}

	static Element xmlRead(File f, File... dirs) throws Exception {
		Vector<File> searchDirs = new Vector<File>(Arrays.asList(dirs));
		searchDirs.add(f.getParentFile());
//		Element e = xmlFromFile(f);
//		xmlRead(e, searchDirs);
		return xmlRead(f, searchDirs);
	}

	static Element xmlRead(File f, Collection<File> searchDirs) throws Exception {
		Element e = xmlFromFile(f);
		xmlRead(e, searchDirs);
		return e;
	}

	static void xmlRead(Element e, Collection<File> searchDirs)
			throws Exception {
		// prn("xmlRead: searchDirs", searchDirs);
		xmlExpandHierarchy(e, searchDirs);
		// xmlUngroupAll(e);
		detachAllComments(e);
	}

}
