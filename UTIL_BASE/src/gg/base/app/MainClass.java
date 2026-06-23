package gg.base.app;

import static gg.base.java.L_Java.PACKAGE;
import static gg.base.util.U_Classes.addClassPath;
import static gg.base.util.U_Classes.findClass;
import static gg.base.util.U_Files.getFilePath;
import static gg.base.util.U_Print.prn;
import static gg.base.util.U_Streams.flushStreams;
import static gg.base.xml.U_XML_XIO.hxmlFromFile;

import java.io.File;
import java.lang.reflect.Constructor;

import org.jdom2.Element;

public class MainClass {

	public static void main(String[] args) {
//		Application application = null;
//		String className = "Unknown";
		try {
			File requestFile = new File(args[0]);
			Element eRequest = hxmlFromFile(requestFile);
			
			String s = eRequest.getAttributeValue(PACKAGE);
			String[] ss = s.split("\\s+");
			for (String t : ss) addClassPath(t);
			String applicationClassName = eRequest.getName();
			prn(false, "RUN application " + applicationClassName + "(" + getFilePath(requestFile) + ")" + "\n");

			Class<?> applicationClass = findClass(applicationClassName);
			Constructor<?> constructor = applicationClass.getConstructor(new Class<?>[] { Element.class });
			Object applicationObject = constructor.newInstance(eRequest);
			
			I_Application application = ((I_Application) applicationObject);
			application.execute();
			
			exit();
//			new TestBus();
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			prn(false, "\nFAIL");
			System.exit(0);
		}
		prn(false, "\nDONE");
	}

	static void exit() {
		flushStreams();
		System.gc();
	}
}
