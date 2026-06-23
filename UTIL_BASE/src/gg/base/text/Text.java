package gg.base.text;

import static gg.base.util.U_Base.assertion;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Files.*;
import static gg.base.util.U_Text.joinText;

import java.io.*;
import java.util.*;

public class Text extends Vector<String> implements I_Text {

	private static final long serialVersionUID = 1L;

	public final String kind = getClass().getSimpleName();

	public Text(Object srcObject, boolean clean, boolean close) {
		try { 
			
			InputStream stream = null;
			if (srcObject instanceof InputStream) stream = (InputStream) srcObject; else close = false;
			
			BufferedReader src;
			if (srcObject instanceof String)    src = new BufferedReader(new FileReader(new File(srcObject.toString()))); else
			if (srcObject instanceof File)      src = new BufferedReader(new FileReader((File) srcObject));               else
			if (stream != null) 				src = new BufferedReader(new InputStreamReader(stream)); else  
			src = (BufferedReader) srcObject;
			
			String s;
			while ((s = src.readLine()) != null) {
				if (clean) { s = s.trim(); if (s.isEmpty()) continue; }
				add(s);
			}
			
			src.close();
			if (close) stream.close();

		} catch (IOException e) { e.printStackTrace(stdOut); System.exit(0); }
	}


	public Text() { }
	public Text(String... a) { super(Arrays.asList(a)); }
	public Text(Collection<String> c) { if(c != null) addAll(c); }

	@Override public String toString() {
		//System.out.println("Text.toString: size " + this.size());
//		String s = "";
//		for (String L : this) s += L + "\n";
//		return s;
		return join("\n", false);
	}

	public void addAll(String... a) { addAll(Arrays.asList(a)); }
	public void addAll(int index, String... a) { addAll(index, Arrays.asList(a)); }
	
	public void addTab(           String... a) { addTab(    1, Arrays.asList(a)); }
	public void addTab(int level, String... a) { addTab(level, Arrays.asList(a)); }
	
	public void addTab(           Collection<String> c) { addTab(1, c); }
	public void addTab(int level, Collection<String> c) { 
		String tab = "";
		while (level > 0) { tab += TAP; level--; }
		for (String s :c) add(tab + s);
	}
	
	public String getLast() { int n = size() - 1; return (n < 0) ? null : get(n); }
	public String removeLast() { int n = size() - 1; return (n < 0) ? null : remove(n); }

	public void uniquefy() { SSet set = new SSet(this); clear(); addAll(set); }

	ArrayList<String> tokens = new ArrayList<>();
	
	public String nextToken() { 
		while (tokens.isEmpty()) {
			if (isEmpty()) return null;
			StringTokenizer st = new StringTokenizer(remove(0));
			while (st.hasMoreTokens()) tokens.add(st.nextToken());
		}
		return tokens.remove(0);
	}

	public void print() { print(System.out); }

	public void print(File dstFile) {
		PrintStream dst = null;
		try { dst = new PrintStream(dstFile); } catch (FileNotFoundException ex) {
			assertion(ex, "Cannot create print file " + dstFile);
		}
//brk("Text.print: dst file exists " + dstFile.exists() + "\n" + getFilePath(dstFile));
		print(dst);
		dst.close();
	}

	public void print(PrintStream out) {
		for (String s : this) out.println(s);
	}

	public void replaceAll(SMap map) { for (String key : map.keySet()) replaceAll(key, map.get(key)); }

	public void replaceAll(String key, String value) {
		int n = size();
		for (int i = 0; i < n; i++) {
			String s = remove(0);
			s = s.replaceAll(key, value);
			add(s);
		}		
	}

	public void replaceWords(SMap map) { for (String word : map.keySet()) replaceWord(word, map.get(word)); }

	public void replaceWord(String word, String replacement) {
		int n = size();
		for (int i = 0; i < n; i++) {
			String s = remove(0);
			s = s.replaceAll("\\b" + word + "\\b", replacement);
			add(s);
		}		
	}

	public String removeLine() {
		String s = "\\";
		while (s.endsWith("\\")) {
//			prn_("1 '" + s + "'");
			s = s.substring(0, s.length() - 1);
//			prn_("2 '" + s + "'");
			s += " " + remove(0);
//			prn_("3 '" + s + "'");
//			brk();
		}
//		prn_("line '" + s + "'");
//		brk();
		return s.trim(); 
	}


	public void join(boolean reverse, Text other) { join(reverse, other, null); }
	
	public void join(boolean reverse, Text other, String body) { // body - delimiter		
		if (body == null) body = ""; 
		int n = size(); 
		assertion(other.size() == n, "Invalid other Text size " + other.size() + ", expected " + n);
		for(int i = 0; i <n; i++) {
			String head = reverse ? other.remove(0) : remove(0); 
			String tail = reverse ? remove(0) : other.remove(0);
			if (head == null) head = "";
			if (tail == null) tail = "";
			add(head + body + tail); 
		}
		
	}

	public String join() { return join(" "); }

	public String join(String delimiter) { return join(delimiter, false); }

	public String join(String delimiter, boolean lastDelimiter) { return joinText(delimiter, lastDelimiter, this); }
	
	public void insertHead(String head) { int n = size(); for(int i = 0; i <n; i++) add(head + remove(0)); }
	public void insertHead(String head, String keep) { 
		int n = size(); 
		for(int i = 0; i <n; i++) {
			if(get(0).trim().startsWith(keep)) add(remove(0)); 
			else add(head + remove(0)); 
		}
	}
	
	public void appendTail(String tail) { int n = size(); for(int i = 0; i <n; i++) add(remove(0) + tail); }

	public void trim() { int n = size(); for(int i = 0; i <n; i++) add(remove(0).trim()); }

	public void clearLast(char chr) { add(removeLast().replace(chr, ' ')); }

	@Override
	public void append(Object o) { add("" + o); }


}
