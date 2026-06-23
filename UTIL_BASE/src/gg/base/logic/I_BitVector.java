package gg.base.logic;

import static gg.base.util.U_Base.numberOfBytes;
import static gg.base.util.U_Base.numberOfHexSymbols;
import static gg.base.util.U_Text.BIN_PREFIX_VERILOG;
import static gg.base.util.U_Text.DEC_PREFIX_VERILOG;
import static gg.base.util.U_Text.HEX_PREFIX_JAVA;
import static gg.base.util.U_Text.HEX_PREFIX_VERILOG;
import static gg.base.util.U_Text.alignBin;
import static gg.base.util.U_Text.alignHex;
import static gg.base.util.U_Text.hex2bytes;

import java.util.BitSet;
import java.util.List;

import gg.base.Uint;

public interface I_BitVector {// hierarchical bit structures for registers

	boolean STYLE_VERILOG = false;
	boolean STYLE_SYSTEM_VERILOG = !STYLE_VERILOG;
	
	int HEX_LENGTH_MAX = 120;
	
	public BitSet bits();

	public int width(); // simplex if not null
	
	public List<I_BitVector> members(); // complex
//
//	default boolean isLogic() { return (arrayCount(logic()) > 0); }

	default void assemble() { // fields to vector
		int offset = 0;
		for (I_BitVector member : members()) { 
			member.assemble();
			field2vector(member, offset); 
			offset += member.width(); 
		}; 
	}

	default void parse() { // vector to fields  
		int offset = 0;
		for (I_BitVector member : members()) { 
			vector2field(member, offset);
			member.parse();
			offset += member.width(); 
		}
	}
	
	default void vector2field(I_BitVector field, int offset) {
		int p = offset;
		for(int i = 0; i < field.width(); i++) field.setBit(i, getBit(p++));
	}
	
	default void field2vector(I_BitVector field, int offset) {
		int p = offset;
		for(int i = 0; i < field.width(); i++) setBit(p++, field.getBit(i));
	}

	default byte[] getBytes() {
		assemble();
		int size = byteSize();
		if (size == 0) return new byte[] {};
		byte[] r = new byte[size];
		int i = 0;
		byte[] vectorBytes = bits().toByteArray();
//prn("BitVector.get: vector " + Arrays.toString(vectorBytes));
		for (byte b : vectorBytes) r[i++] = b; // vector - big-endian SAME ORDER?
//prn("BitVector.get: result " + Arrays.toString(r));
		return r;
	}

	default void setBytes(byte[] bytes) {
//prn("BitVector.set: bytes = " + Arrays.toString(bytes));
		BitSet v = bits();
		int w = width();
		int p = 0;
		v.clear();
		for (byte b : bytes) {
			for(int i = 0; i < 8; i++) {
//prn("BitVector.set: byte b = " + Arrays.toString(new byte[]{b}) + ", p = " + p);
				if ((b & 1) != 0) {
//prn("BitVector.set: ---------------------------------------------------------- bit p = " + p);
					v.set(p);
				}
				b >>= 1;
				p++;
				if(p == w) break;
			}
			if(p == w) break;
		}
		parse();
	}
	
	default boolean getBit(int index) { return bits().get(index); }

	default void setBit(int index, boolean value) { bits().set(index, value); }

	default void unpack(byte[] bytes) { // byte[] to bits -> bits to fields
		setBytes(bytes);
		parse();
	}
	
	default byte[] pack() { // fields to bits -> bits to byte[]
		assemble();
		return getBytes();
	}
	
	default int byteSize() { return numberOfBytes(width()); }
	default int hexLength() { return numberOfHexSymbols(width()); }

	default long	getLong()	{ return getUint().longValue() ;}
	default int		getInt()	{ return getUint().intValue()  ; }
	default short	getShort()	{ return getUint().shortValue(); }
	default byte	getByte()	{ return getUint().byteValue() ; }
	
	default void setBytes(long value) {
//		prn("I_Bits.setBytes(long value): value = " + value);
		String hex = Long.toHexString(value);
//		prn("I_Bits.setBytes(long value): hex = " + hex);
		setBytes(hex); 
	}
	default void setBytes(String hex) { setBytes(hex2bytes(hex)); }

	default Uint getUint() { return new Uint(getBytes()); } // BigInteger little-endian

	default String toHex       () { return                                alignHex(getBytes(), hexLength()).toUpperCase(); }
	default String toJavaHex   () { return           HEX_PREFIX_JAVA    + alignHex(getBytes(), hexLength()).toUpperCase(); }
	default String toVerilogHex() { return width() + HEX_PREFIX_VERILOG + alignHex(getBytes(), hexLength()).toUpperCase(); }
	default String toVerilogDec() { return width() + DEC_PREFIX_VERILOG + getUint(); }
	default String toVerilogBin() { return width() + BIN_PREFIX_VERILOG + alignBin(getBytes(), width()); }

}


//default void assemble() { // fields to vector
//	int offset = 0;
//	if (!isLeaf()) for (I_Hierarchy<I_Bits> childBits : children()) {
//		I_Bits child = (I_Bits) childBits;
//		if (!child.isLeaf()) child.assemble();
//		field2vector(child, offset); 
//		offset += child.width(); 
//	}; 
//}
//
//default void parse() { // vector to fields  
//	int offset = 0;
//	if (!isLeaf()) for (I_Hierarchy<I_Bits> childBits : children()) { 
//		I_Bits child = (I_Bits) childBits;
//		vector2field(child, offset);
//		if (!child.isLeaf()) child.parse();
//		offset += child.width(); 
//	}
//}

//static void vector2field(I_Bits vector, I_Bits field, int offset) {
//int p = offset;
//for(int i = 0; i < field.width(); i++) field.setBit(i, vector.getBit(p++));
//}
//
//static void field2vector(I_Bits vector, I_Bits field, int offset) {
//int p = offset;
//for(int i = 0; i < field.width(); i++) vector.setBit(p++, field.getBit(i));
//}


//byte[] bytes = get();

//default BitVector getField(int offset, int width) {
//BitVector field = new BitVector(width);
//getField(field, offset);
//return field;
//}

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
//public interface I_Bits extends I_Hierarchy<I_Bits> { // hierarchical bit structures
//
//	static boolean STYLE_VERILOG = false;
//	static boolean STYLE_SYSTEM_VERILOG = !STYLE_VERILOG;
//	
//	static boolean RTL_STYLE = STYLE_SYSTEM_VERILOG;
//	
//	int HEX_LENGTH_MAX = 120;
//	
//	default void vector2field(I_Bits field, int offset) {
//		int p = offset;
//		for(int i = 0; i < field.width(); i++) field.setBit(i, getBit(p++));
//	}
//	
//	default void field2vector(I_Bits field, int offset) {
//		int p = offset;
//		for(int i = 0; i < field.width(); i++) setBit(p++, field.getBit(i));
//	}
//
//	public boolean getBit(int index);
//	
//	public void setBit(int index, boolean value);
//
//	public byte[] getBytes();
//	public void setBytes(byte[] bytes);
//
//	default void unpack(byte[] bytes) { // byte[] to vector -> vector to fields
//		setBytes(bytes);
//		parse();
//	}
//	
//	default byte[] pack() { // fields to vector -> vector to byte[]
//		assemble();
//		return getBytes();
//	}
//	
//	default void assemble() { // fields to vector
//		int offset = 0;
//		if (!isLeaf()) for (I_Hierarchy<I_Bits> childBits : children()) {
//			I_Bits child = (I_Bits) childBits;
//			if (!child.isLeaf()) child.assemble();
//			field2vector(child, offset); 
//			offset += child.width(); 
//		}; 
//	}
//
//	default void parse() { // vector to fields  
//		int offset = 0;
//		if (!isLeaf()) for (I_Hierarchy<I_Bits> childBits : children()) { 
//			I_Bits child = (I_Bits) childBits;
//			vector2field(child, offset);
//			if (!child.isLeaf()) child.parse();
//			offset += child.width(); 
//		}
//	}
//	
//	public int width();
//
//	default int byteSize() { return numberOfBytes(width()); }
//	default int hexLength() { return numberOfHexSymbols(width()); }
//
//	default long	getLong()	{ return getUint().longValue() ;}
//	default int		getInt()	{ return getUint().intValue()  ; }
//	default short	getShort()	{ return getUint().shortValue(); }
//	default byte	getByte()	{ return getUint().byteValue() ; }
//	
//	default void setBytes(long value) {
////		prn("I_Bits.setBytes(long value): value = " + value);
//		String hex = Long.toHexString(value);
////		prn("I_Bits.setBytes(long value): hex = " + hex);
//		setBytes(hex); 
//	}
//	default void setBytes(String hex) { setBytes(hex2bytes(hex)); }
//
//	default Uint getUint() { return new Uint(getBytes()); } // BigInteger little-endian
//
//	default String toHex() { 
//		return alignHex(getBytes(), hexLength()); 
//	}
//}
//
////static void vector2field(I_Bits vector, I_Bits field, int offset) {
////int p = offset;
////for(int i = 0; i < field.width(); i++) field.setBit(i, vector.getBit(p++));
////}
////
////static void field2vector(I_Bits vector, I_Bits field, int offset) {
////int p = offset;
////for(int i = 0; i < field.width(); i++) vector.setBit(p++, field.getBit(i));
////}
//
//
////byte[] bytes = get();
//
////default BitVector getField(int offset, int width) {
////BitVector field = new BitVector(width);
////getField(field, offset);
////return field;
////}
//
//@Override public 
//public int width();
//public void width(int width);

//public void assemble(); // fields to vector
//public void parse(); // vector to fields  

//extends I_Hierarchy { 

//public List<I_BitVector> children();
//public boolean isLeaf();
//public byte[] getBytes();
//public void setBytes(byte[] bytes);


//public boolean isLogic(); // primitive - no children

