package gg.base.util;


import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Print.prn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFrame;

public interface U_Streams {

	static boolean DEBUG_READER = false;// = true;

	static boolean DEBUG_READERS = false;// = true;
	static boolean DEBUG_PRINTERS = false;// = true;
	static boolean DEBUG_FRAMES = false;// = true;
	static boolean PAUSE_ON_CLOSE_FRAMES = false;// = true;

	static final Map<BufferedReader, Object> readers = new LinkedHashMap<BufferedReader, Object>();

	static BufferedReader getReader(String path, Collection<File> searchDirs) {
		Vector<File> searchList = new Vector<File>();
		searchList.add(new File(path));
		if (searchDirs != null) for (File dir : searchDirs) searchList.add(new File(dir, path));
		return getFirstReader(searchList);
	}

	static BufferedReader getFirstReader(Collection<File> fileList) {
		for (File f : fileList) {
			BufferedReader br = getReader(f);
			if (br != null) {
				readers.put(br, f);
				if (DEBUG_READERS) prn(false, "<STREAMS>.getFirstReader " + f);
				return br;
			}
		}
		return null;
	}

	static BufferedReader getReader(String path) throws Exception { return getReader(new File(path)); }

	static BufferedReader getReader(String path, File searchDir) { return getReader(new File(searchDir, path)); }

	static BufferedReader getReader(File f) {
		// if (DEBUG_READER) System.out.print("XIO.getReader FILE '" +
		// getPath(f) + "'");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			if (DEBUG_READERS) prn(false, "<U_Streams>.getReader file exists " + f);
			readers.put(br, f);
			if (DEBUG_READERS) prn(false, "<U_Streams>.getReader " + f);
			return br;
		} catch (Exception ex) {
			if (DEBUG_READERS) prn(false, "<U_Streams>.getReader file doesn't exist " + f);
			if (DEBUG_READER) System.out.println("");
		}
		return null;
	}

	static void closeReader(BufferedReader br) {
		if (br == null) return;
		try { br.close(); } catch (IOException ex) { assertion(ex, "FAILT to close reader"); }
		Object o = readers.remove(br);
		if (DEBUG_READERS) prn(false, "<U_Streams>.closeReader " + o);
	}

	static void flushReaders() {
		if (!readers.isEmpty()) {
			if (DEBUG_READERS) prn(false, "<U_Streams>.flushReaders " + readers.size(), readers);
			for (BufferedReader br : new Vector<BufferedReader>(readers.keySet())) {
				try { closeReader(br); } catch (Exception ex_) {
					if (DEBUG_READERS) prn(false, "<U_Streams>.flushReaders FAIL " + br, readers.get(br));
				}
			}
		}
	}

	static final Map<PrintStream, Object> printers = new LinkedHashMap<PrintStream, Object>();

	static PrintStream getPrinter(String path) throws Exception {
		return getPrinter(new File(path));
	}

	static PrintStream getPrinter(File f) throws Exception {
		PrintStream ps = new PrintStream(f);
		printers.put(ps, f);
		if (DEBUG_PRINTERS) prn(false, "<U_Streams>.getPrinter: " + f);
		return ps;
	}

	static Object closePrinter(PrintStream ps) throws Exception {
		ps.close();
		Object o = printers.remove(ps);
		if (DEBUG_PRINTERS) prn(false, "<U_Streams>.closePrinter: " + o);
		return o;
	}

	static void flushPrinters() {
		if (!printers.isEmpty()) {
			if (DEBUG_PRINTERS) prn(false, "<U_Streams>.flushPrinters: " + printers.size(), printers);
			for (PrintStream ps : new Vector<PrintStream>(printers.keySet())) {
				try { closePrinter(ps); } catch (Exception ex) {
					if (DEBUG_PRINTERS) prn(false, "<U_Streams>.flushPrinters: FAIL " + ps, printers.get(ps));
				}
			}
		}
	}

	static final Vector<JFrame> frames = new Vector<>();

	static void closeFrame(JFrame frame) throws Exception {
		frame.setVisible(false);
		frame.dispose();
		if (DEBUG_PRINTERS) prn(false, "<U_Streams>.closeFrame: " + frame);
	}

	static void flushFrames() {
		if (!frames.isEmpty()) {
			prn(DEBUG_FRAMES, "<STREAMS>.flushFrames: " + frames.size(), frames);
			prn(PAUSE_ON_CLOSE_FRAMES, "<U_Streams>.flushFrames: you have " + frames.size() + " active frames");

			while (!frames.isEmpty()) {
				JFrame frame = frames.remove(0);
				try { closeFrame(frame); } catch (Exception ex) {
					prn(DEBUG_FRAMES, "<STREAMS>.flushFrames: FAIL " + frame, frames);
				}
			}
		}
	}

	static void flushStreams() {
		flushFrames();
		flushReaders();
		flushPrinters();
	}

}