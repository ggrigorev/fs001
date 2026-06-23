package gg.base;

import static gg.base.util.U_Fields.*;
import static gg.base.util.U_Print.*;
import static gg.base.xml.U_XML_IO.*;

import java.lang.reflect.Field;

import gg.base.text.*;

import org.jdom2.Element;
import org.jdom2.Attribute;

public class Base {

	public static final SSet skipFields = new SSet("name", "dir");

	public static boolean BASE_DEBUG = true;

	public final String kind = getClass().getSimpleName();
	public final TMap<Field> fields = getFieldMap(getClass());

	public Object parent;
	public String name;

	public Base() {}
	public Base(String name) { this.name = name; }

	public void build() {}

	public void addXml(Element e) {
		TMap<Object> valueMap = getFieldValueMap(this);
if(BASE_DEBUG) prn_("Base.fields", fields);
		for (String fieldName : valueMap.keySet()) {
			if (skipFields.contains(fieldName)) continue;
			Field field = fields.get(fieldName);
			Object fieldValue = getFieldValue(field, this);
			if (fieldValue == null) continue;
		}
	}

	public Element toXml() { 
		Element e = new Element(kind + "." + name);
		addXml(e);
		return e; 
	}
	
	public void addText(Text txt) {//int level
		TMap<Object> valueMap = getFieldValueMap(this);
if(BASE_DEBUG) prn_("Base.fields", fields);
		for (String fieldName : valueMap.keySet()) {
			if (skipFields.contains(fieldName)) continue;
			Field field = fields.get(fieldName);
			Object fieldValue = getFieldValue(field, this);
			if (fieldValue == null) continue;
			if (fieldValue instanceof Base) {
				Base baseObject = (Base) fieldValue;
				Text sub = baseObject.toText();//level + 1);
				sub.insertHead(TAP);
				txt.addAll(sub);
			} else {
				Class<?> fieldType = field.getType();
				Class<?> valueType = fieldValue.getClass();
				String s = valueType.getSimpleName();
				if (fieldType != fieldValue.getClass()) s += ":" + fieldType.getSimpleName();
				txt.addAll(TAP + "<" + s + "> " + fieldValue);
			}
		}
	}
	
	public Text toText() { 
		Text txt = new Text();
		addText(txt);
		return txt; 
	}
	
	static boolean useXML = true;
	
	@Override
	public String toString() {
		Text txt = useXML ? xmlToText(toXml()) : toText();
		return txt.toString();
	}
	
}



//if (level > 0) {
//	String tap = getTap(level); 
//	txt.insertHead(tap);
//}
//return txt;
