package gg.base.xml.test;

import static gg.base.util.U_Print.prn_;
import static gg.base.xml.U_XML_IMPORT.xmlClone;
import static gg.base.xml.U_XML_EXPORT.xmlExport;
import static gg.base.xml.U_XML_IO.xmlPrint;

import gg.base.text.SMap;
import gg.base.text.Text;

public class XmlTest {

	public String name = "T0";
	
	public Text text = new Text();
	public SMap smap = new SMap("a", "0", "b", "1");
	
	public static void main(String[] args) {
		XmlTest t0 = new XmlTest();
		t0.text.add("line_0");
		xmlPrint(xmlExport(t0, 0));
		
		prn_("\n\n\n");		
		XmlTest t1 = (XmlTest) xmlClone(t0, 0);
		xmlPrint(xmlExport(t1, 0));
		
		prn_("\n\n\n");		
		t1.name = "T1";
		t1.text.add("line_1");
		xmlPrint(xmlExport(t1, 0));
	}

}
