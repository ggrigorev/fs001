package gg.base.logic;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import gg.hierarchy.base.HNode;

public class BitVector extends HNode implements I_BitVector {

	protected BitSet bits;

	public Integer logic; // LEAF width - zero means scalar

	@Override public BitSet bits() { return bits; }

//	@Override public Integer logic() { return logic; }

	@Override public List<I_BitVector> members() {
		ArrayList<I_BitVector> list = new ArrayList<>();
		for (Object o : children.values()) { // always unique member name
			if (o instanceof I_BitVector) {
				I_BitVector v = (I_BitVector) o;  // only I_BitVectors
				list.add(v);
			}
		}
		return list;
	}

@Override
public int width() {
	// TODO Auto-generated method stub
	return 0;
}

}


//boolean RTL_STYLE = STYLE_SYSTEM_VERILOG;
//boolean SIMPLE_STRING = false;
//String hexPrefix = HEX_PREFIX_JAVA;

//	@Override public Integer logic() { return logic; }
//
//	@Override public int width() { 
//		if(logic != null) if (logic > 0) return logic;
//		int w = 0;
//		for (Object o : children.values()) if (o instanceof I_BitVector)
//			w += ((I_BitVector) o).width();
//		return w;
//	}




//@Override public void width(int width) { if (width > 0) bits = new BitSet(width); }

//@Override public void addChild(Object o) {
//super.addChild(o); // set parent and add to children (unique child name required)
//if(bits == null) {
//	I_BitVector child = (I_BitVector) o;
//	int w = child.width();
//	bits = new BitSet(w);
//	byte[] bb = child.getBytes();
//	setBytes(bb);
//}
//}


//@Override public void addText(Text text, String tap) {
////super.addText(text, tap); uses toString()
//assemble();
//String s = tap;
//int w = width();
//s += "<" + getClass().getSimpleName() + ">[" + name + "](" + w + ")";
//if (w > 0) if(hexLength() <= HEX_LENGTH_MAX) s += " = 0x" + toHex();
//text.add(s);
//if(!isLeaf()) for (Node child : children()) child.addText(text, tap + TAP);
//}

//@Override public String toString() { 
//return SIMPLE_STRING ? (hexPrefix + alignHex(getBytes(), hexLength())) : ("" + toText());
//}


//@Override public I_Bits[] array() { return new I_Bits[children.size()]; }

//public Bits(Object parentObject, String name, byte[] bytes, int width) {
//super(parentObject, name);
//width(width);
//if(bytes != null) setBytes(bytes);
////prn("<Bits>--------  width " + width);
//}

	
//	@Override
//	public boolean deepFields() { return false; }
//@Override
//public I_Bits[] children() {
//	if (children == null) return null;
//	I_Bits[] r = new I_Bits[children.size()];
//	int i = 0;
//	for (I_Hierarchy<I_Bits> child : children.values()) r[i++] = (I_Bits) child;
//	return r;
//}	


//public String toHex() { 
//byte[] bytes = get();
//prn("BitVector.toHex: bytes = " + Arrays.toString(bytes));
//return alignHex(bytes, hexLength); 
//}
//
//package gg.base.logic;
//
//import java.util.*;
//
//import gg.base.hier.*;
//import gg.base.text.*;
//
//import static gg.base.util.U_Print.*;
//import static gg.base.util.U_Text.*;
//import static gg.base.util.U_Common.*;
//
//public class Bits extends Hierarchy<I_Bits> implements I_Bits {
//	
//	public final BitSet bits;
//	public final int width;
//	
//	public Bits(Object parentObject, int width) { this(parentObject, null, new byte[numberOfBytes(width)], width); }
//	public Bits(Object parentObject, String name, int width) { this(parentObject, name, new byte[numberOfBytes(width)], width); }
//	
//	public Bits(Object parentObject, byte[] bytes) { this(parentObject, null, bytes, bytes.length << 3); }
//	public Bits(Object parentObject, String name, byte[] bytes) { this(parentObject, name, bytes, bytes.length << 3); }
//	
//	public Bits(Object parentObject, long value, int width) { this(parentObject, null, Long.toHexString(value), width); }
//	public Bits(Object parentObject, String name, long value, int width) { this(parentObject, name, Long.toHexString(value), width); }
//
//	public Bits(Object parentObject, int width, String hexValue) { this(parentObject, null, hex2bytes(hexValue), width); }
//	public Bits(Object parentObject, String name, String hexValue, int width) { this(parentObject, name, hex2bytes(hexValue), width); }
//	
//	public Bits(Object parentObject, String name, byte[] bytes, int width) {
//		super(parentObject, name);
//		bits = new BitSet(width);
//		this.width = width;
//		if(bytes != null) setBytes(bytes);
////prn("<Bits>--------  width " + width);
//	}
//
//	@Override
//	public byte[] getBytes() {
//		assemble();
//		byte[] r = new byte[byteSize()];
//		int i = 0;
//		byte[] vectorBytes = bits.toByteArray();
////prn("BitVector.get: vector " + Arrays.toString(vectorBytes));
//		for (byte b : vectorBytes) r[i++] = b; // vector - big-endian
////prn("BitVector.get: result " + Arrays.toString(r));
//		return r;
//	}
//
//	@Override
//	public void setBytes(byte[] bytes) {
////prn("BitVector.set: bytes = " + Arrays.toString(bytes));
//		int p = 0;
//		for (byte b : bytes) {
//			for(int i = 0; i < 8; i++) {
////prn("BitVector.set: byte b = " + Arrays.toString(new byte[]{b}) + ", p = " + p);
//				if ((b & 1) != 0) {
////prn("BitVector.set: ---------------------------------------------------------- bit p = " + p);
//					bits.set(p);
//				}
//				b >>= 1;
//				p++;
//				if(p == width) break;
//			}
//			if(p == width) break;
//		}
//		parse();
//	}
//	
//	@Override
//	public int width() { return width; }
//
//	@Override
//	public I_Bits[] array() { return new I_Bits[children.size()]; }
//
////	@Override
////	public I_Bits[] children() {
////		if (children == null) return null;
////		I_Bits[] r = new I_Bits[children.size()];
////		int i = 0;
////		for (I_Hierarchy<I_Bits> child : children.values()) r[i++] = (I_Bits) child;
////		return r;
////	}	
//
//	@Override
//	public boolean getBit(int index) { return bits.get(index); }
//
//	@Override
//	public void setBit(int index, boolean value) { bits.set(index, value); }
//
//	@Override
//	public void addText(Text text, String tap) {
////		super.addText(text, tap); uses toString()
//		assemble();
//		String s = tap;
//		s += "<" + getClass().getSimpleName() + ">[" + name + "](" + width + ")";
//		if(hexLength() <= HEX_LENGTH_MAX) s += " = 0x" + toHex();
//		text.add(s);
//		if(!isLeaf()) for (I_Bits child : children()) child.addText(text, tap + TAP);
//	}
//	
//	boolean SIMPLE_STRING = false;
//	
//	@Override
//	public String toString() { 
//		return SIMPLE_STRING ? (HEX_PREFIX_JAVA + alignHex(getBytes(), hexLength())) : ("" + toText());
//	}
//	
////	@Override
////	public boolean deepFields() { return false; }
//
//}
//
////public String toHex() { 
////byte[] bytes = get();
////prn("BitVector.toHex: bytes = " + Arrays.toString(bytes));
////return alignHex(bytes, hexLength); 
////}
////
//@Override public boolean isLogic() { if (logic == null) return false; return (logic > 0); }
//
