package gg.base.text;

import static gg.base.util.U_Base.*;
import static gg.base.util.U_Print.*;
import static gg.base.util.U_Text.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class STable {
	
	public static STable alignMap(SMap map) { return alignMap(map, new SPair(" = ", null)); }
	
	public static STable alignMap(SMap map, SPair E1) {
		STable stab = new STable(2);
		stab.L[0] = true;
		stab.E[1] = E1;//new SPair(" = ", null);
		for (String key : map.keySet()) {
			String val = map.get(key);
			String[] row = stab.nextRow();
			row[0] = key.trim();
			row[1] = (val == null) ? "" : val.trim();
		}
		return stab;
	}
	
	public static STable alignList(Collection<String> list) { return alignList(list, true); }
	
	public static STable alignList(Collection<String> list, boolean L) {
		STable stab = new STable(1);
		stab.L[0] = L;
		for (String s : list) {
			String[] row = stab.nextRow();
			row[0] = s.trim();
		}
		return stab;
	}
	
	public final int       N; // number of columns
	public final boolean[] L; // align to left
	public final SPair[]   E; // column edges
	public final Boolean[] G; // edges for empty column: NULL - edge length space, true - edge, false - none
	
	public TreeMap<Integer, Text> blockComments = new TreeMap<>();
	public TreeMap<Integer, String> lineComments = new TreeMap<>();
	public ArrayList<String[]> rows = new ArrayList<>();
	
	public void setAlign(Map<Integer, Boolean> map) {
		for (int i = 0; i < N; i++) { Boolean B = map.get(i); if (B != null) L[i] = B; }
	}

	public void setEdges(Map<Integer, SPair> map) {
		for (int i = 0; i < N; i++) { SPair P = map.get(i); if (P != null) E[i] = P; }
	}

	// Text arguments
	String rowTail = "";
	String rowTailSpace = "";
	boolean rowTailLast = true;
	String interval = "";
	
	public STable(int numberOfColumns) { 
		N = numberOfColumns;
		L = new boolean[N]; // filled false, default - align to left
		E = new   SPair[N];
		G = new Boolean[N]; // filled false, default - disable edges for empty column 
	}

	public int size() { return rows.size(); }
	public boolean isEmpty() { return rows.isEmpty(); }
	
	public void setInterval(int n) {
		interval = "";
		while (interval.length() < n) interval += " ";
	}

	public void setRowTail(String tail, boolean last) {
		rowTail = tail;
		rowTailSpace = "";
		while (tail.length() > rowTailSpace.length()) rowTailSpace += " ";
		rowTailLast = last;
	}

	public void addBlockComment(String... ss) { addBlockComment(Arrays.asList(ss)); }
	
	public void addBlockComment(Collection<String> cc) {
		int i = rows.size();
		Text txt = blockComments.get(i);
		if (txt == null) { txt = new Text(); blockComments.put(i,  txt); }
		txt.addAll(cc); 
	}

	public void addLineComment(String s) {
		int i = rows.size();
		String t = lineComments.get(i);
		if (t != null) s = t + " " + s;
		lineComments.put(i, s);
	}

	public String[] nextRow() {
		String[] row = new String[N];
		for (int i = 0; i < N; i++) row[i] = "";
		rows.add(row);
		return row; 
	}

	public String[] nextRow(String s) {
		
		String[] row = s.split(" ");// new String[N];
		assertion(row.length == N, "invalid number of tokens " + row.length + ", expected " + N + ": \n\t " + s);
		rows.add(row);
		return row; 
	}

	public String modifyColumn(int i, int j, String s) {
		return (s == null) ? "" : s.trim();				
	}

	public void modifyRow(int i, String[] row) {
		for(int j = 0; j < N; j++) row[j] = modifyColumn(i, j, row[j]);
	}

	public void modify() {			
		for(int i = 0; i < rows.size(); i++) modifyRow(i, rows.get(i));
	}

	public int[] getColumnWidths() {
		modify();
		int[] W = new int[N]; // filled 0
		for(String[] ss : rows) {
			for(int i = 0; i < N; i++) {
				int L = ss[i].length();
				if (W[i] < L) W[i] = L;
			}
		}
		return W;
	}

	public String getFormatString(int[] W, String[] row) {
		String f = "";
		for (int i = 0; i < N; i++) if (W[i] > 0) {  // not empty column ""
			String t = "%";
			if (L[i]) t += "-";
			t += W[i];				
			t += "s";
			t += interval;
			
			if (E[i] != null) {
				if (row[i].isEmpty()) { 
//if (i == 8) brk(row[6]);
					if (G[i] == null) {
						// skip edges
					} else if (G[i]) {
						// apply edges
						if (E[i].a != null) t  = E[i].a + t;
						if (E[i].b != null) t += E[i].b;						
					} else {
						// space edges
						if (E[i].a != null) t  = repeat(" ", E[i].a.length()) + t;
						if (E[i].b != null) t += repeat(" ", E[i].b.length());												
					}
				} else {
//if (i == 6) prn_("-------------------------------------------------------------------------'" + row[6] + "' " + i + " " + row[i]);//if (row[6].indexOf("ex_stage_t") >= 0) 
					// apply edges
					if (E[i].a != null) t  = E[i].a + t;
					if (E[i].b != null) t += E[i].b;
				}
			}
			f += t;
		}
//prn_(f, Arrays.toString(row));
		return f;
	}

	public Text toText() {
		Text text = new Text();
		int M = rows.size();

		int [] W = getColumnWidths();
		
		int C = 0; // clear columns
		for (int a : W) if (a == 0) C++;

		for(int j = 0; j < M; j++) {
			String[] row = rows.get(j);

			String f = getFormatString(W, row);
			
			Object[] oo = new Object[N - C];
			
			int b = 0;
			for(int i = 0; i < N; i++) if (W[i] > 0) oo[b++] = row[i];
			
			String s = String.format(f, oo);
			
			Text comment = blockComments.get(j);
			if (comment != null) {
				Text tmp = new Text(comment);
				tmp.insertHead("// ");
				text.addAll(tmp);
			}
			
			boolean last = (j == (M - 1));
			
			if (last) {
				if (rowTailLast) s += rowTail;
			} else {
				s += rowTail;
			}
			
			String c = lineComments.get(j);
			if (c != null) {
				if (last && !rowTailLast) s += rowTailSpace;				
				s += " // " + c;
			}
			
			text.add(s);
		}
//prn(text);
		return text;
	}
		
	@Override public String toString() { return toText().join("\n", false); }

}


//public String interval = " ";

//public void space() { space(""); }
//
//public void space(String comment) { rows.add(new String[] {comment}); }

//for(String[] ss : rows) if (ss != null) { String line = ""; for (String s : ss) line += " " + s; }//prn_(line); }
//if (ss == null) continue;
//prn("STYable.colWidth col = " + col); 
//ss[col] = "logic";
//oo[i] = (oo[i] == null) ? "" : oo[i]; // unexpected

//boolean notEmptyColumn[] = new boolean[N]; // filled false
//for (int i = 0; i < N; i++) if (!cols[i].isEmpty()) notEmptyColumn[i] = true;

//	return s.trim();
//	v = modifyColumn(col, v);
//	s += String.format(f, v);
//	public Text toText() {
//		
//		colWidth();
//		
//		Text text = new Text();
//		for(String[] ss : rows) {
////prn_("toText row", ss);	
//			String s = "";
//			if (ss.length == 1) { String c = ss[0]; if (acceptLine(c)) text.add(c); continue; }// comment
//			for(int col = 0; col < N; col++) {
//				if (W[col] == 0) continue;
//				String f, v;
//				if (R[col]) {
//					f = "%-" + W[col] + "s";
//					v = alignLeft(ss[col], W[col]);
//					v = interval  + v;
//					
//				} else {
//					f = "%" + W[col] + "s";
//					v = alignRight(ss[col], W[col]);
////	??				v += interval;					
//				}
//				v = modifyColumn(col, v);
//				s += String.format(f, v);
//			}			
////prn_("'" + s + "'"); 
//			s = modifyRow(s);
////prn_("'" + s + "'"); 
//			text.add(s);
//		}
////brk_("rows.size() = " + rows.size());
//		
//		return text;
//	}
//
//	public boolean acceptLine(String s) { return (s.startsWith("//") && (s.indexOf("\n") < 0)); }
//	
//	public String modifyRow(String v) { return v; }//prn_("modifyRow '" + v + "'"); 
//	public String modifyColumn(int col, String v) { return v; }//prn_("modifyRow + '" + v + "'"); 
//
//	public void fillRow(int index, String value) { for (int i = 0; i < N; i++) rows.get(index)[i] = value; }
//
//	public void numRow() { String[] row = nextRow(); for (int i = 0; i < N; i++) row[i] = "" + i; }
//	
//	public void fillColumn(int index, String value) { for (String[] row : rows) row[index] = value; }
