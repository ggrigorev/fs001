package gg.base.util;

import static gg.base.util.U_Base.getStackTrace;
//import static gg.base.util.U_Base.getStackTrace;
import static gg.base.util.U_Print.TAP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import gg.base.OutputPrinter;
import gg.base.text.Text;

public interface U_Print {

	int TAP_LENGTH = 4;

	static String getSpace(int length) { if (length > 0) return String.format("%" + length + "s", ""); return (length < 0) ? null : "";}
	static String getTap(int level) { return getSpace(level * TAP_LENGTH); }
	static String getTap() { return getSpace(TAP_LENGTH); }

	String TAP = getTap();

	static String str() { return "";  }
	static String str(int tab) { return getTap(tab); }
	static String str(int tab, Object o) { return getTap(tab) + o; }

	static void out()                  {                             log.out( ); stdOut.println( ); }
	static void out(int tab)           { String s = getTap(tab)    ; log.out(s); stdOut.println(s); }
	static void out(int tab, Object o) { String s = getTap(tab) + o; log.out(s); stdOut.println(s); }

	OutputPrinter log = new OutputPrinter();

	PrintStream stdOut = System.out;
	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

	boolean TRACE_DEFAULT = false;
		
	static void dbg(Object o) { dbg(true, false, o); }
	static void dbg(boolean enable, Object o) { dbg(enable, false, o); }
	static void dbg(boolean enable, boolean pause, Object o) {
		if (!enable) return;
		String s = "\t------------------ DEBUG ------------------\n";
		if (o instanceof String) {
			if (!s.isBlank()) s += "\t" + o + "\n";
		} else s += "\t" + o + "\n";
		
		if (pause) {
			prn_(s); 
//			new Exception("DEBUG").printStackTrace(stdOut);
			brk("DEBUG");
		} else prn(true, s); 
	}
	
	static void prn(Object o) { prn(TRACE_DEFAULT, o); }
	static void prn(String title, Object o) { prn(TRACE_DEFAULT, title, o); }

	static void trc() { prn_("TRACE: "); (new Exception()).printStackTrace(System.out); }

	static void trc(int n) {
		List<StackTraceElement> stack = getStackTrace();
		int m = stack.size();
		String s = "Stack [" + (n+1) + " of " + m + "] ";
		if (m <= n) n = m - 1;
		prn_(s + stack.get(n) + ", printed by " + stack.get(n - 1));
//		String s =  + ", called by " += stack.get(n);
		//String s = "Stack [" + n + "  of " + m + "] Printed by " + stack.get(n - 1) + ", called by ";
	//	if (m > n) s  else s += stack.get(stack.size() - 1);
	}

	int TRACE_BACK_STEPS = 6;
	
	static void prn(int trace, String title, Object o) {
		if(trace < 0) trc(); else {
			if(trace == 0) trc(TRACE_BACK_STEPS); else trc(trace);
		}
		prn_(title, o);
	}

	static void prn(boolean trace, Object o) {
		if(trace) trc(); else trc(TRACE_BACK_STEPS);
		prn_(o);
	}

	static void prn(boolean trace, String title, Object o) {
		if(trace) trc(); else trc(TRACE_BACK_STEPS);
		prn_(title, o);
	}

	static void prn() { out();  }
	static void prn_() { out();  }

	static void prn_(String title, Object o) { 
		prn_("\n" + title); prn_(o); prn(); 
	}

	static void prn_(Object o) { prn_(0, o); }
	
	//stdOut.println(".................. <" + e.getClass().getSimpleName() + "> ... " + e); 
	//out(tab, "======" + tab + "======= Object " + o.getClass().getSimpleName() + " = " + o);
	static void prn_(int tab, Object o) {
		if (o == null) { out(tab, o); return; }
		Collection<?> c = null;
		if (o instanceof Collection) {
			c = (Collection<?>) o;
			out(tab, "---------------- Collection " + c.size() + ", " + c.getClass().getSimpleName());
		} 
		else if (o.getClass().isArray()) {
			c = Arrays.asList((Object[]) o); 
			out(tab, "---------------- Array " + c.size() + ", " + c.getClass().getSimpleName()); //brk();
		}
		if (c != null) {
			for(Object e : c) { 
//				out(tab + 1, "" + e); 
				prn_(tab + 1, e); 
			}
		} else if (o instanceof Map) {
			Map<?, ?> m = (Map<?, ?>) o;
			out(tab, "---------------- Map " + m.size() + ", " + m.getClass().getSimpleName());
			for(Object k : m.keySet()) out(tab + 1, k + " = " + m.get(k));
		} else {
			out(tab, o);
		}
	}

	static String brk() { return brk(true, ""); }

	static String brk(boolean pause) { return brk(true, ""); }
	
	static String brk(String prompt) { return brk(true, prompt); }
	static void brk_(String msg) { prn(msg); brk(true, ""); }

	static String brk(boolean trace, String prompt) {
//		if (!pause) return "";
		if(trace) trc(TRACE_BACK_STEPS);
		stdOut.print(prompt + " -- PAUSE >"); 
		String s = null;
		try { s = stdIn.readLine(); } catch (IOException e) { e.printStackTrace(stdOut); System.exit(0); } 
		return s;
	}
	
	static Text prn_t(int tab, Object o) {
		Text txt  = new Text();
		prn_t(tab, o, txt);
		return txt;
	}
	
	static Text prn_t(String title, Object o) { 
		Text txt  = new Text();
		txt.add(title); 
		prn_t(0, o, txt); 
		return txt;
	}

	static void prn_t(int tab, Object o, Text txt) {
		if (o == null) { txt.add(str(tab, "" + o)); return; }
		Collection<?> c = null;
		if (o instanceof Collection) {
			c = (Collection<?>) o;
			txt.add(str(tab, "---------------- Collection " + c.size() + ", " + c.getClass().getSimpleName()));
		} 
		else if (o.getClass().isArray()) {
			c = Arrays.asList((Object[]) o); 
			txt.add(str(tab, "---------------- Array " + c.size() + ", " + c.getClass().getSimpleName())); //brk();
		}
		if (c != null) {
			for(Object e : c) { 
				prn_t(tab + 1, e, txt); 
			}
		} else if (o instanceof Map) {
			Map<?, ?> m = (Map<?, ?>) o;
			txt.add(str(tab, "---------------- Map " + m.size() + ", " + m.getClass().getSimpleName()));
			for(Object k : m.keySet()) txt.add(str(tab + 1, k + " = " + m.get(k)));
		} else {
			txt.add(str(tab, o));
		}
	}

}
