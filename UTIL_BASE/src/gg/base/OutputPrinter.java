package gg.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static gg.base.util.U_Base.assertion;

public class OutputPrinter {

	public PrintStream out;

	public void open(String s) { open(new File(s)); }
	
	public void open(File f) { 
		try { out = new PrintStream(f); } catch (FileNotFoundException ex) { assertion(ex, "FAILURE"); }
	}

	public void out(String... ss) {
		if (out != null) {
			if (ss.length == 0) out.println( ); 
			for (String s : ss) out.println(s);
		}
	}

	public void close() {
		if (out != null) {
			out.close();
			out = null;
		}		
	}
	
}
