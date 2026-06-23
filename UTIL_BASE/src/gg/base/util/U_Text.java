package gg.base.util;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.separate;

import java.awt.Point;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import gg.base.text.SSet;
import gg.base.text.Text;

public interface U_Text {
	
	String STARTS_WIDTH = "startsWith__";
	String ENDS_WIDTH   = "endsWith__";
	
	static boolean validString(String s, String... patterns) { return validString(s, Arrays.asList(patterns)); }
	
	static boolean validString(String s, Collection<String> patterns) {
		SSet excludes = new SSet();
		SSet includes = new SSet();
		for (String p : patterns) if (p.startsWith("!")) excludes.add(p.substring(1)); else includes.add(p);
		return validString(s, excludes, includes);
	}
	
	static boolean validString(String s, SSet excludes, SSet includes) {
		for (String pattern : excludes) if (s.indexOf(pattern) >= 0) return false; // exclude match	
		for (String pattern : includes) if (s.indexOf(pattern) >= 0) return true; // include match
		return false; // include mismatch
	}
	
	static String repeat(String s, int n) { 
		String r = ""; 
		for (int i =0; i < n; i ++) r += s;; 
		return r;
	}
	
	String separator = "----------";

	static void separate(Text txt) { separate(1, txt, null); }
	
	static void separate(Text txt, String title) { separate(1, txt, title); }

	static void separate(int length, Text txt, String title) {
		String s = "// ";
		while (length > 0) { s += separator; length--; }
		if (title != null) s += " " + title.toUpperCase();
		txt.add(s + "\n");
	}

	static void addSeparatedText(Text txt, Text sub, String separateTitle) {
		separate(txt, separateTitle);
		txt.addAll(sub);
		txt.add("");
	}

	static String peel(String s) { return s.substring(1, s.length() - 1); }
	
	static String readUntil(Text src, String pattern) { return readUntil(null, src, pattern); }

	static String readLine(Text src) {
		if (src == null) return null; 
		if (src.isEmpty()) return null; 
		String s = src.removeFirst();
		while (s.endsWith("\\")) {
			s = s.substring(0, s.length() - 1).trim();
			if (src.isEmpty()) break;
			s += " " + src.removeFirst();
		}
		return s;
	}
	
	static String readUntil(String s, Text src, String pattern) {
		boolean flag = false;
		if ((s != null) && !s.isEmpty()) { src.add(0, s); }
		s = "";
//		flag = pattern.equals("{") || pattern.equals("}");
		int p = s.indexOf(pattern); 
		while (p < 0) { 
			s += src.remove(0) + " ";
			p = s.indexOf(pattern); if(flag) prn_(p + " " + s); 
			if (src.isEmpty()) break;
		}
		s.trim();		
		if (p < 0) {
			brk("Pattern '" + pattern + "' not found, return\n\t'" + s + "'");
			return s;
		}
		
		String t = s.substring(p+pattern.length()).trim();
		if (!t.isEmpty()) { src.add(0, t); } // prn_("ADD> " + t); 
			
		s = s.substring(0, p).trim(); // pattern is not included	
		return s;
	}

	static Point findEnvelop(String s, String begin, String end) { return findEnvelop(s, begin, end, 0); }

	static Point findEnvelop(String s, String begin, String end, int level) {
		Point p = new Point(s.indexOf(begin), -1); // inclusive
//prn_("start[" + level + "] " + p + " '" + s); if (level > 109) brk();
		if (p.x >= 0) {
			p.y = s.indexOf(end, p.x + 1);
//prn_("end[" + level + "] " + p);
			if (p.y < 0) return null; // no envelop
			int offset = p.x + begin.length();
//prn_("offset[" + level + "] " + offset);
			while (true) {
				int b = s.indexOf(begin, offset);
//prn_("b " + b);
				if (b < 0) {
//prn_("return[" + level + "] " + p);
					return p;
				} else {
					Point p1 = findEnvelop(s.substring(b), begin, end, level + 1);
//prn_("p1[" + level + "] " + p1);
					if (p1 != null) {
						p1.x += b;
						p1.y += b;
					}
					return p1;
				} 				
			}
		}		
		return null;
	}
//	static String[] getEnvelop(String s, String begin, String end) { // begin end are not included 
//		String e = null;
//		String h = null;
//		String t = null;
//		Point p = findEnvelop(s, begin, end);
//		if (p != null) {
//			e = s.substring(p.x + begin.length(), p.y).trim(); //prn_(e);
//			h = s.substring(0, p.x).trim();                   // before begin
//			t = s.substring(p.y + end.length()).trim();       // after end
////			prn_("head '" + h + "'");
////			prn_("tail '" + t + "'");
////			s += h + " " + t;
//		}
//		return new String[] {e, h, t};
//	}

	static String[] getEnvelop(String s, String begin, String end) { // begin end are not included 
		return getEnvelop(s, begin, end, false);
	}
		
	static String[] getEnvelop(String s, String begin, String end, boolean enveloped) { 
		String e = null;
		String h = null;
		String t = null;
		Point p = findEnvelop(s, begin, end);
		if (p != null) {
			e = s.substring(p.x + begin.length(), p.y).trim(); //prn_(e);
			h = s.substring(0, p.x).trim();                   // before begin
			t = s.substring(p.y + end.length()).trim();       // after end
			if (enveloped) e = begin + " " + e + " " + end;
//			prn_("head '" + h + "'");
//			prn_("tail '" + t + "'");
//			s += h + " " + t;
		}
		return new String[] {e, h, t};
	}

	public static Text toText(Collection<?> c) { 
		Text list = new Text();
		for(Object e : c) list.add((e == null) ? null : "" + e); 
		return list;
	}

	static String sortableDate() { return sortableDate("~", ".", ""); }

	static String sortableDate(String head, String body, String tail) {
		Date date = new Date();
		String s = "";
		s += head + (1900 + date.getYear()); 
		s += body + leadZero(date.getMonth() + 1, 2);
		s += body + leadZero(date.getDate(), 2);
		s += body + leadZero(date.getHours(), 2);
		s += body + leadZero(date.getMinutes(), 2);
		s += tail;
		return s;
	}

	static String alignLeft(String s, String delimiter, int n) { while(s.length() < n) s += delimiter; return s; }

	static String alignLeft(String s, int n) { return alignLeft(s, " ", n); }

	static String alignRight(String s, String delimiter, int n) { while(s.length() < n) s = delimiter + s; return s; }

	static String alignRight(String s, int n) { return alignRight(s, " ", n); }

	static String leadZero(int i, int n) { return alignRight("" + i, "0", n); }

    static byte[] reverseBytes(byte[] bytes) {
    	byte[] r = new byte[bytes.length];
    	int i = bytes.length - 1;
    	for (byte b : bytes) r[i--] = b;
    	return r;
    }

	String HEX_PREFIX_JAVA = "0x";
	String HEX_PREFIX_VERILOG = "'h";
	String DEC_PREFIX_VERILOG = "'d";
	String BIN_PREFIX_VERILOG = "'b";
	
	static byte[] hex2bytes(String hexValue) { 
//prn("U_Text.hex2bytes: hexValue = " + hexValue);
		if (hexValue.startsWith(HEX_PREFIX_JAVA)) hexValue = hexValue.substring(hexValue.length());
//prn("U_Text.hex2bytes: hexValue = " + hexValue);
		BigInteger val = new BigInteger(hexValue, 16);
		return reverseBytes(val.toByteArray());
	}

    static String alignBin(byte[] bytes, int n) { // number of BIN symbols, 8 per byte
//prn("U_Text.alignHex: bytes = " + Arrays.toString(bytes));

    	String s = "";
    	for (byte b : bytes) {
     		s = alignBin(0xFF & b, 8) + s;
    	}
    	while (n > 0) { s = "0" + s; n--; }
    	return s;
    }

    static String alignHex(byte[] bytes, int n) { // number of HEX symbols, 2 per byte
//prn("U_Text.alignHex: bytes = " + Arrays.toString(bytes));

    	String s = "";
    	for (byte b : bytes) {
    		int m = (n > 1) ? 2 : 1;
    		s = alignHex(0xFF & b, m) + s;
    		n -= m;
    		if(n == 0) break;
    	}
    	while (n > 0) { s = "0" + s; n--; }
    	return s;
    }

    static String alignBigHex(BigInteger value, int n) { // number of HEX symbols, 2 per byte
     	String s = value.toString(16);
    	if (s.length() > n) s = s.substring(s.length() - n, s.length());
    	else while (s.length() < n) s = "0" + s;
    	return s;
    }

    static String alignHex(long value, int n) { // number of HEX symbols, 2 per byte
     	String s = Long.toHexString(value);
    	if (s.length() > n) s = s.substring(s.length() - n, s.length());
    	else while (s.length() < n) s = "0" + s;
    	return s;
    }

    static String alignBin(long value, int n) { // number of HEX symbols, 2 per byte
     	String s = Long.toBinaryString(value);
    	if (s.length() > n) s = s.substring(s.length() - n, s.length());
    	else while (s.length() < n) s = "0" + s;
    	return s;
    }

	static boolean containsWord(String p, String s) {
		Pattern pattern = Pattern.compile(".*\\b" + p + "\\b.*"); // if s contains exact WORD portName
		return pattern.matcher(s).matches();
	}

	static Text tokenizeString(String s) { return tokenizeString(s, null); } // standard white space delimiters

	static String tokenFirst(String s) { return tokenizeString(s, null).get(0); }
	static String tokenLast(String s) { return tokenizeString(s, null).getLast(); }

	static String tokenFirst(String s, String delimiters) { return tokenizeString(s, delimiters).get(0); }
	static String tokenLast(String s, String delimiters) { return tokenizeString(s, delimiters).getLast(); }
	
	static Text tokenizeString(String s, String delimiters) {
		Text tokens = new Text();
		StringTokenizer st = (delimiters == null) ? new StringTokenizer(s) : new StringTokenizer(s, delimiters);
		while (st.hasMoreTokens()) tokens.add(st.nextToken());
		return tokens;
	}

	static String joinText(String delimiter, boolean lastDelimiter, String... a) {
		return joinText(delimiter, lastDelimiter, Arrays.asList(a));
	}

	static String joinText(String delimiter, boolean lastDelimiter, Collection<?> c) {
		String s = "";
		for (Object e : c) { s += e + delimiter; }//prn_("U_Text.joinText: '" + s + "'"); //dbg(e.toString().indexOf("rst_n") >= 0, "e = '" + e + "', s = '" + s + "'"); }
		if (delimiter.isEmpty()) return s;
		if (!lastDelimiter) while(s.endsWith(delimiter)) s = s.substring(0, s.length() - delimiter.length()); 
//prn_("joinText(lastDelimiter = " + lastDelimiter + ") = '" + s + "'");
		return s;
	}

	static String camelText(String... aa) {
		return camelText(Arrays.asList(aa));
	}

	static String camelText(List<String> cc) {
		String s = "";
		for (String c : cc) s += s.isEmpty() ? c.toLowerCase() : capitolize(c);
		return s;
	}
	
	static String capitolize(String s) { return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase(); }

	static String insertToString(String s, String before, int index) {
		int p = s.indexOf(before);
//		prn("getTableMax.table.length", table.length);
		s = s.substring(0, p) + index + s.substring(p);
		return s;
	}

	static String insertToString(String s, String v, int start, int n) {
		String head = s.substring(0, start);
		String tail = s.substring(start);
		String body = "";
		for (int i = 0; i < n; i++) body += v;
		return head + body + tail;
	}
	
	static int[] getTableMax(String[][] table) {
//prn("getTableMax.table.length", table.length);
		int[] max = new int[table[0].length]; 
//prn("getTableMax.max.length", max.length); // columns width 
		for (String[] row : table) {
			int col = 0;
			for (String s : row) {
//prn("getTableMax.row[" + col + "]", s);
				int len = s.length(); 
				if (max[col] < len) max[col] = len;
				col++;
			}
		}
		return max;
	}

	static Text alignList(Collection<String> list) {
		if (list.isEmpty()) return new Text();
//prn("alignList", list);
		int size = list.size();
		String[][] table = new String[size][1];
		int i = 0;
		for (String s : list) table[i++][0] = s; //{ prn("alignList table[" + i +"][0] = ", s); table[i++][0] = s; }
		int[] max = getTableMax(table);
		Text text = new Text();
		for (String s : formatTable( table, max, null)) text.add(s);
		return text;
	}

	static Text formatTable(String[][] table, int[] max, boolean[] align) {
		int rows = table.length;
		int cols = max.length;
		if (align == null) align = new boolean[cols];
		Text text = new Text();
		for (int row = 0; row < rows; row++) {
			String r = "";
			for (int col = 0; col < cols; col++) {
//prn(row + " " + col + " '" + table[row][col] + "'");
				String c = table[row][col];
				int n = max[col] - c.length();
				while (n > 0) { 
					if (!align[col]) c = c + " "; // align to left
					else             c = " " + c; // align to right
					n--;
				} 
				r += c +  " ";                   // space column delimiter
			}
			text.add(r);
		}
		return text;
	}
	
}
