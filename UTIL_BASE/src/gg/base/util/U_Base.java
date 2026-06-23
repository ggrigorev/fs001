package gg.base.util;

import static gg.base.util.U_Print.*;
import static gg.base.util.U_Print.stdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import gg.base.text.Text;

public interface U_Base {
	
	static int positive(int n) { return (n > 0) ? n : 0; }
	static int positiveSize(int n) { return (n < 0) ? 0 : (n > 0) ? n : 1; }
	
//	int SCALAR_SIZE = 0; // 1 is a vector [0:0]

//	static int arrayCount(Integer n) { return (n == null) ? 0 : (n == SCALAR_SIZE) ? 1 : n; } // 1 is a vector [0:0]

	static String getObjectKind(Object o) {
		return (o != null) ? o.getClass().getSimpleName() : ("" + o);
	}
	
	static String getObjectId(Object o, String type, String name) {
		String s = "<" + getObjectKind(o) + ">";
		s += "[" +  type + "::" + name + "]";
		return s;
	}

	static int numberOfBytes(int width) { return (width + 7) >> 3; }
	
	static int numberOfHexSymbols(int width) { return (width + 3) >> 2; }

	static void assertion(String msg) { assertion(false, msg); } // unconditional

	static void assertion(boolean mustBeTrue, String msg) { // conditional
		if (mustBeTrue) return;	
		assertion(new Exception(msg), msg);
	}

	static void assertion(Exception ex) { assertion(ex, "UNEXPECTED"); } // unconditional

	static void assertion(Exception ex, String msg) { // unconditional
		prn_(ex.getMessage());
		ex.printStackTrace(stdOut); 
		prn_("ERROR: " + msg);
		System.exit(0);
	}

	static void failure(String msg) {
		msg = "FAILURE: " + msg;
		Exception ex = new Exception(msg);
		ex.printStackTrace(stdOut); 
		prn_(msg);
		System.exit(0);
	}

	static ArrayList<StackTraceElement> getStackTrace() {
		return new ArrayList<>(Arrays.asList(Thread.currentThread().getStackTrace()));
	}

	static void compare(String aName, Collection<String> a, String bName, Collection<String> b, boolean showDiff, boolean showCommon) {
		Text A = new Text(a);
		Text B = new Text(b);
		Text C = new Text(a);
		
		A.removeAll(b);
		B.removeAll(a);
		C.retainAll(b);
		
		prn_("A: " + aName);
		prn_("B: " + bName);

		prn_("A: " + A.size() + " + " + C.size() + " = " + a.size());
		prn_("B: " + B.size() + " + " + C.size() + " = " + b.size());
		
		if (showCommon) {
			prn_("Common", C);
		}

		if (showDiff) {
			if (!A.isEmpty()) prn_("A: " + "\n" + str(1, A));
			if (!B.isEmpty()) prn_("B: " + "\n" + str(1, B));
		}
		
	}
	
}
