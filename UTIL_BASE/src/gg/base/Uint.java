package gg.base;

import static gg.base.util.U_Text.reverseBytes;

import java.math.BigInteger;
import java.util.Arrays;

public class Uint extends BigInteger { // make it little-endian

	private static final long serialVersionUID = 1L;

	public Uint(String val, int radix) { super(val, radix); }

	public Uint(byte[] val) { // value little-endian -> super big-endian
		super(reverseBytes(Arrays.copyOf(val, val.length + 1)));
	}

	@Override public byte[] toByteArray() { // super big-endian -> value little-endian
		byte[] val = super.toByteArray();
		return reverseBytes(val);
	}

}
