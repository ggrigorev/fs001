package gg.base.xml.sch;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import org.jdom2.Element;
import org.jdom2.Attribute;

import gg.base.Base;
import gg.base.text.*;

import static gg.base.util.L_Base.*;
import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;
import static gg.base.util.U_Files.*;

import static gg.base.java.U_Java.*;
 
// reflection
import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Classes.*;
import static gg.base.util.U_Constructors.*;

// XML
import static gg.base.xml.L_XML.*;
import static gg.base.xml.U_XML_IO.*;

import static gg.base.xml.sch.U_SCH_XML.*;

public class Schema extends Base {

	public String title;

	Integer level;
	
//	public void fromElement(Element e) { xml2sch(this, e); }

	public Element toElement() { return sch2xml(this); }
	
	@Override
	public String toString() {
		String s = "<" + kind + ">[" + title  + "] ";
		return s + getFieldValueMap(this).printToString(level == null ? 0 : level, "fields");
	}
	
}

//public Element toXML(Element eParent) {
//Element e = new Element(title);
//if (eParent != null) eParent.addContent(e);
//TMap<Object> fieldMap = getFieldValueMap(this);
//for (String fieldName : fields.keySet()) {
//	if (SCHEMA_XML_IGNORE_FIELDS.contains(fieldName)) {
//		prn_("Ignore XML field '" + fieldName + "'");
//		continue;
//	}
//	Object fieldValue = fieldMap.get(fieldName);
//	if (fieldValue == null) {
////prn_("Ignore NULL field '" + fieldName + "'");
//		prn_("Primitive '" + fieldName + "' = NULL");
//		continue;
//	}
//	Field field = fields.get(fieldName);
//	Class<?> fieldType = field.getType();
//	if (Schema.class.isAssignableFrom(fieldType)) {
//		Schema child = (Schema) fieldValue;
//		Element eChild = child.toXML(e);
//		prn_("Child '" + fieldName + "' -> '" + eChild.getName() + "'");
//	} else {
//		e.setAttribute(fieldName, "" + fieldValue);
//		prn_("Primitive '" + fieldName + "' = '" + fieldValue + "'");
//	}
//}
//return e;
//}

//prn_("fields", fields.keySet()); 

//public String title() { return getJavaClassTitle(kind); }


//	public void parse(int level, Element e) {
//		prn_(getTap(level) + "SchClass.parse: <" + kind + ">[" + title + "](" + level + ")");
//		for (Element eChild : e.getChildren()) {
//			String childName = eChild.getName();
//			Object childObject = parseObject(level + 1, eChild);
//			assertion((childObject != null), "Unexpecte NULL child");
//			if (childObject instanceof SchClass) {
//				SchClass child = (SchClass) childObject;
//				Field childField = fields.get(child.title);
//				assertion((childField != null), "Unexpecte NULL field '" + child.title + "' from\n" + fields.keySet());
//				Class<?> fieldType = childField.getType();
//				Object fieldValue = getFieldValue(childField, this);
//				if (Collection.class.isAssignableFrom(fieldType)) {
//					prn_(getTap(level + 1) + "SchClass.parse: SCHEMA CHILD of {" + child.kind + "} -> add to field of LIST " + fieldType.getSimpleName());					
//					if (fieldValue == null) {
//						setFieldValue(this, newInstance(fieldType), childField);	
//						fieldValue = getFieldValue(childField, this);
//					}
//					((Collection) fieldValue).add(child);	
//				} else {
//					prn_(getTap(level + 1) + "SchClass.parse: SCHEMA CHILD of <" + child.kind + "> -> assign to field of " + fieldType.getSimpleName());					
//					setFieldValue(this, child, childField);	
//				}
//			} else {
//				Field childField = fields.get(childName);
//				assertion((childField != null), "Unexpecte NULL field '" + childName + "' from\n" + fields.keySet());
//				Class<?> fieldType = childField.getType();
//				Object fieldValue = getFieldValue(childField, this);
//				prn_(getTap(level + 1) + "SchClass.parse: child of <" + childObject.getClass().getSimpleName() + ">, field of " + fieldType.getSimpleName());
//				brk();
//			}
//
//		}
////brk();
//	}
	
//	assertion((childObject instanceof SchClass), "Unexpecte child of " + childObject.getClass().getSimpleName());
//			String childTitle = eChild.getName();
////prn_();
//			String childType = getJavaClassName(childTitle);
//			Class<?> childClass = getClassByName(childType);
//			assertion(childClass != null, "Cannnot find class '" + childType + "'");
//			Object childObject = newInstance(childClass);
//			if (childObject instanceof SchClass) {
//				SchClass child = (SchClass) childObject;
//				child.parse(level + 1, eChild);
//			}
//			String fieldName = childType;//getJavaObjectName(childType, "_");
//			brk();
