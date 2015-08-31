/*
 * Copyright 2011 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 */
package ch.ethz.globis.pht.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import ch.ethz.globis.pht.util.Bits;
import ch.ethz.globis.pht.util.BitsInt;

/**
 * 
 * @author ztilmann
 *
 */
public class TestBitsIntWrite {

	private static final int BITS = 32;
	
	@Test
	public void testCopy1() {
		int[] t = new int[2];
		BitsInt.writeArray(t, 0, 64, 0x0000FFFF0000FFFFL);
		check(t, 0xFFFF, 0xFFFF);
	}
	
	
	@Test
	public void testCopy2() {
		int[] t = new int[2];
		BitsInt.writeArray(t, 0, 64, 0x00000F0F0000F0F0L);
		check(t, 0x0F0F, 0xF0F0);
	}
	
	
	@Test
	public void testCopy3() {
		//int[] s = newBA(0x0F, 0xF0000000);
		int[] t = new int[2];
		//BitsInt.copyBitsLeft(s, 28, t, 24, 32);
		BitsInt.writeArray(t, 24, 32, 0xFF000000L);
		check(t, 0xFF, 0);
	}
	
	
	@Test
	public void testCopy4() {
		//int[] s = newBA(0x0F, 0xF0000000);
		int[] t = new int[2];
		//BitsInt.copyBitsLeft(s, 28, t, 32, 32);
		BitsInt.writeArray(t, 32, 32, 0xFF000000L);
		check(t, 0, 0xFF000000);
	}

	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopy5() {
		//int[] s = newBA(0x00, 0x00);
		int[] t = newBA(0xFF, 0xFF000000);
		//BitsInt.copyBitsLeft(s, 28, t, 28, 8);
		BitsInt.writeArray(t, 28, 8, 0x0L);
		check(t, 0xF0, 0x0F000000);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopy6() {
		//int[] s = newBA(0xF0, 0x0F000000);
		int[] t = newBA(0x0F, 0xF0000000);
		//BitsInt.copyBitsLeft(s, 28, t, 28, 8);
		BitsInt.writeArray(t, 28, 8, 0x0F00F000000L);
		check(t, 0x00, 0x00);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopySingle1() {
		int[] s = newBA(0xAAAAAAAA, 0xAAAAAAAA);
		//BitsInt.copyBitsLeft(s, 28, s, 27, 1);
		BitsInt.writeArray(s, 27, 1, 0x1L);
		check(s, 0xAAAAAABA, 0xAAAAAAAA);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopySingle2() {
		int[] s = newBA(0xAAAAAAAA, 0xAAAAAAAA);
		//BitsInt.copyBitsLeft(s, 27, s, 28, 1);
		BitsInt.writeArray(s, 28, 1, 0x0L);
		check(s, 0xAAAAAAA2, 0xAAAAAAAA);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopySingle3a() {
		//int[] s = newBA(0x0008, 0x00);
		int[] t = newBA(0x00, 0x00);
		//BitsInt.copyBitsLeft(s, 28, t, 27, 1);
		BitsInt.writeArray(t, 27, 1, 0x1L);
		check(t, 0x0010, 0x00);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopySingle3b() {
		//int[] s = newBA(0xFFFFFFF7, 0xFFFFFFFF);
		int[] t = newBA(0xFFFFFFFF, 0xFFFFFFFF);
		//BitsInt.copyBitsLeft(s, 28, t, 27, 1);
		BitsInt.writeArray(t, 27, 1, 0x0L);
		check(t, 0xFFFFFFEF, 0xFFFFFFFF);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopySingle4a() {
		//int[] s = newBA(0x0010, 0x00);
		int[] t = newBA(0x00, 0x00);
		//BitsInt.copyBitsLeft(s, 27, t, 28, 1);
		BitsInt.writeArray(t, 28, 1, 0x1L);
		check(t, 0x08, 0x00);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopySingle4b() {
		//int[] s = newBA(0xFFFFFFEF, 0xFFFFFFFF);
		int[] t = newBA(0xFFFFFFFF, 0xFFFFFFFF);
		//BitsInt.copyBitsLeft(s, 27, t, 28, 1);
		BitsInt.writeArray(t, 28, 1, 0x0L);
		check(t, 0xFFFFFFF7, 0xFFFFFFFF);
	}
	
	@Test
	public void testCopyShift1_OneByteToTwoByte() {
		int[] s = newBA(0xAAAAAAAA, 0x0AAAAAAA);
		//BitsInt.copyBitsLeft(s, 28, s, 30, 4);
		BitsInt.writeArray(s, 30, 4, 0xAL);
		check(s, 0xAAAAAAAA, 0x8AAAAAAA);
	}
	
	@Test
	public void testCopyShift1_TwoByteToOneByte() {
		int[] s = newBA(0xAAAAAAAA, 0xF5AAAAAA);
		//BitsInt.copyBitsLeft(s, 30, s, 32, 4);
		BitsInt.writeArray(s, 32, 4, 0xBL);
		check(s, 0xAAAAAAAA, 0xB5AAAAAA);
	}
	
//	@Test
//	public void testCopyLong1() {
//		int[] s = newBA(0x000A, 0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA);
//		BitsInt.copyBitsLeft(s, 28, s, 33, 65);
//		check(s, 0x000A, 0xD5555555, 0x55555555, 0x6AAAAAAA);
//	}
	
	@Test
	public void testCopySplitForwardA() {
		//int[] s = newBA(0x000F, 0xF0000000);
		int[] t = newBA(0x0000, 0x00000000);
		//BitsInt.copyBitsLeft(s, 28, t, 29, 8);
		BitsInt.writeArray(t, 29, 8, 0xFFL);
		check(t, 0x0007, 0xF8000000);
	}
	
	@Test
	public void testCopySplitForwardB() {
		//int[] s = newBA(0xFFFFFFF0, 0x0FFFFFFF);
		int[] t = newBA(0xFFFFFFFF, 0xFFFFFFFF);
		//BitsInt.copyBitsLeft(s, 28, t, 29, 8);
		BitsInt.writeArray(t, 29, 8, 0x00L);
		check(t, 0xFFFFFFF8, 0x07FFFFFF);
	}
	
	@Test
	public void testCopySplitBackwardA() {
		//int[] s = newBA(0x000F, 0xF0000000);
		int[] t = newBA(0x0000, 0x00000000);
		//BitsInt.copyBitsLeft(s, 28, t, 27, 8);
		BitsInt.writeArray(t, 27, 8, 0xFFL);
		check(t, 0x001F, 0xE0000000);
	}
	
	@Test
	public void testCopySplitBackwardB() {
		//int[] s = newBA(0xFFFFFFF0, 0x0FFFFFFF);
		int[] t = newBA(0xFFFFFFFF, 0xFFFFFFFF);
		//BitsInt.copyBitsLeft(s, 28, t, 27, 8);
		BitsInt.writeArray(t, 27, 8, 0x00L);
		check(t, 0xFFFFFFE0, 0x1FFFFFFF);
	}
	
	
	@Test
	public void testCopyLeftA() {
		int[] s = newBA(0xFFFFFFFF, 0x00, 0x00);
		//BitsInt.copyBitsLeft(s, 32, s, 30, 62);
		BitsInt.writeArray(s, 30, 62, 0x00L);
		check(s, 0xFFFFFFFC, 0x00, 0x00);
	}
	
	
	@Test
	public void testCopyLeftB() {
		int[] s = newBA(0x00, 0xFFFFFFFF, 0xFFFFFFFF);
		//BitsInt.copyBitsLeft(s, 32, s, 30, 62);
		BitsInt.writeArray(s, 30, 62, 0xFFFFFFFFFFFFFFFFL);
		check(s, 0x03, 0xFFFFFFFF, 0xFFFFFFFF);
	}
	
//	@Test
//	public void testCopyLeftBug1() {
//		short[] s = newBA(-27705, 31758, -32768, 0x00);
//		short[] t = newBA(-28416, 0x00, 0x00, 0x00);
//		System.out.println("l=" + BitsInt.toBinary(1327362106));
//		System.out.println("src=" + BitsInt.toBinary(s));
//		System.out.println("trg=" + BitsInt.toBinary(new long[]{-28416, 0x00, 0x1C77, 0xC0E8}, 16));
//		BitsInt.copyBitsLeft(s, 7, t, 35, 27);
//		System.out.println("trg=" + BitsInt.toBinary(t));
//		check(t, -28416, 0x00, 0x1C77, 0xC0E8);
//	}
	
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopyRSingle2() {
		int[] s = newBA(0xAAAAAAAA, 0xAAAAAAAA);
		//BitsInt.copyBitsRight(s, 27, s, 28, 1);
		BitsInt.writeArray(s, 28, 1, 0x0L);
		check(s, 0xAAAAAAA2, 0xAAAAAAAA);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopyRSingle4a() {
		//int[] s = newBA(0x0010, 0x0000);
		int[] t = newBA(0x0000, 0x0000);
		//BitsInt.copyBitsRight(s, 27, t, 28, 1);
		BitsInt.writeArray(t, 28, 1, 0x1L);
		check(t, 0x0008, 0x0000);
	}
	
	/**
	 * Check retain set bits.
	 */
	@Test
	public void testCopyRSingle4b() {
		//int[] s = newBA(0xFFFFFFEF, 0xFFFFFFFF);
		int[] t = newBA(0xFFFFFFFF, 0xFFFFFFFF);
		//BitsInt.copyBitsRight(s, 27, t, 28, 1);
		BitsInt.writeArray(t, 28, 1, 0x0L);
		check(t, 0xFFFFFFF7, 0xFFFFFFFF);
	}
	
	@Test
	public void testCopyRShift1_OneByteToTwoByte() {
		int[] s = newBA(0xAAAAAAAA, 0x0AAAAAAA);
		//BitsInt.copyBitsRight(s, 28, s, 30, 4);
		BitsInt.writeArray(s, 30, 4, 0xAL);
		check(s, 0xAAAAAAAA, 0x8AAAAAAA);
	}
	
	@Test
	public void testCopyRShift1_TwoByteToOneByte() {
		int[] s = newBA(0xAAAAAAAA, 0xF5AAAAAA);
		//BitsInt.copyBitsRight(s, 30, s, 32, 4);
		BitsInt.writeArray(s, 32, 4, 0xBL);
		check(s, 0xAAAAAAAA, 0xB5AAAAAA);
	}
	

	@Test
	public void testInsert1_OneByteToTwoByte() {
		int[] s = newBA(0xAAAAAAAA, 0xAAA00000);
		BitsInt.insertBits(s, 28, 5);
		check(28, 5, s, 0xAAAAAAAA, 0xD5550000);
	}
	
	@Test
	public void testInsert1_TwoByteToOneByte() {
		int[] s = newBA(0xAAAAAAAA, 0xAAA00000);
		BitsInt.insertBits(s, 28, 3);
		check(28, 3, s, 0xAAAAAAAB, 0x55540000);
	}
	
	@Test
	public void testInsert1_OneByteToTwoByteBIG() {
		int[] s = newBA(0xAAAAAAAA, 0xCCCCCCCC, 0xCCCCCCCC, 0xAAA00000);
		int[] s2 = s.clone();
		BitsInt.insertBits(s, 28, 5+64);
		insertBitsSlow(s2, 28, 5+64);
		check(28, 5+64, s, s2);
	}
	
	@Test
	public void testInsert1_TwoByteToOneByteBIG() {
		int[] s = newBA(0xAAAAAAAA, 0xCCCCCCCC, 0xCCCCCCCC, 0xAAA00000);
		BitsInt.insertBits(s, 28, 3+64);
		check(28, 3+64, s, 0xAAAAAAAB, 0x55540000);
	}
	
	@Test
	public void testInsert2() {
		int[] s = newBA(0xAAAAAAAA, 0xAAA00000);
		BitsInt.insertBits(s, 32, 1);
		check(s, 0xAAAAAAAA, 0xD5500000);
	}
	
	@Test
	public void testInsert3() {
		int[] s = newBA(0xAAAAAAAA, 0xAAA00000);
		BitsInt.insertBits(s, 31, 1);
		check(s, 0xAAAAAAAA, 0x55500000);
	}
	
	@Test
	public void testInsert4() {
		int[] s = newBA(0xAAAAAAAA, 0x5555);
		BitsInt.insertBits(s, 0, 32);
		check(0, 32, s, 0xAAAAAAAA, 0xAAAAAAAA);
	}
	
	@Test
	public void testInsert5() {
		int[] s = newBA(0xAAAAAAAA, 0xAA550000);
		BitsInt.insertBits(s, 32, 32);
		check(32, 32, s, 0xAAAAAAAA, 0xAA550000);
	}
	
	@Test
	public void testInsert_Bug1() {
		int[] s = newBA(0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA);
		BitsInt.insertBits(s, 97, 5);
		check(s, 0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA, 0xA9555555);
	}
	
	@Test
	public void testInsert_Bug2() {
		int[] s = newBA(0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA);
		BitsInt.insertBits(s, 64, 35);
		check(s, 0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA, 0xB5555555);
	}
	
	@Test
	public void testInsert_Bug2b() {
		int[] s = newBA(0xAAAAAAAA, 0x0000);
		BitsInt.insertBits(s, 0, 35);
		check(s, 0xAAAAAAAA, 0x15555555);
	}
	
	@Test
	public void testInsert_Bug2c() {
		int[] s = newBA(0xAAAAAAAA);
		BitsInt.insertBits(s, 0, 3);
		check(s, 0xB5555555);
	}
	
	@Test
	public void testInsertRandom() {
		Random r = new Random(0);
		for (int i = 0; i < 100000; i++) {
			int LEN = r.nextInt(4)+1;
			int[] s = newBaPattern(LEN, 0xAAAAAAAA);
			int[] x = s.clone();
			int start = r.nextInt(LEN*32);
			int maxIns = LEN*32-start;
			int ins = r.nextInt(maxIns);
			BitsInt.insertBits(s, start, ins);
			BitsInt.insertBits1(x, start, ins);
//			System.out.println("s=" + start + " i=" + ins);
//			System.out.println("x=" + BitsInt.toBinary(x));
//			System.out.println("s=" + BitsInt.toBinary(s));
			check(x, s);
		}
	}
	
	@Test
	public void testInsert_BugI1() {
		int[] s = newBA(0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA);
		int[] t = newBA(0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA, 0xAAAAAAAA);
		BitsInt.insertBits(s, 22, 4);
		BitsInt.insertBits1(t, 22, 4);
		check(s, t);
	}
	

	
	@Test
	public void testCopyLeftRandom() {
		Random rnd = new Random();
		int BA_LEN = 6;
		int[] ba = new int[BA_LEN];
		for (int i1 = 0; i1 < 1000000; i1++) {
			//populate
			for (int i2 = 0; i2 < ba.length; i2++) {
				ba[i2] = rnd.nextInt();
			}
			
			//clone
			int[] ba1 = Arrays.copyOf(ba, ba.length);
			int[] ba2 = Arrays.copyOf(ba, ba.length);
			
			int start = rnd.nextInt(8) + 8;  //start somewhere in fourth short
			int nBits = rnd.nextInt(8 * 3); //remove up to two shorts 
			//compute
			//System.out.println("i=" + i1 + " start=" + start + " nBits=" + nBits);
			BitsInt.copyBitsLeft(ba1, start+nBits, ba1, start, ba1.length*BITS-start-nBits);
			//compute backup
			removetBitsSlow(ba2, start, nBits);
			
			//check
			if (!Arrays.equals(ba2, ba1)) {
				System.out.println("i=" + i1 + " start=" + start + " nBits=" + nBits);
				System.out.println("ori. = " + BitsInt.toBinary(ba));
				System.out.println("act. = " + BitsInt.toBinary(ba1));
				System.out.println("exp. = " + BitsInt.toBinary(ba2));
				System.out.println("ori. = " + Arrays.toString(ba));
				System.out.println("act. = " + Arrays.toString(ba1));
				System.out.println("exp. = " + Arrays.toString(ba2));
				fail();
			}
		}
	}
	
	private void insertBitsSlow(int[] ba, int start, int nBits) {
		int bitsToShift = ba.length*BITS - start - nBits;
		for (int i = 0; i < bitsToShift; i++) {
			int srcBit = ba.length*BITS - nBits - i - 1;
			int trgBit = ba.length*BITS - i - 1;
			BitsInt.setBit(ba, trgBit, BitsInt.getBit(ba, srcBit));
		}

	}
	
	private void removetBitsSlow(int[] ba, int start, int nBits) {
		int bitsToShift = ba.length*BITS - start - (nBits);
		for (int i = 0; i < bitsToShift; i++) {
			int srcBit = start + (nBits) + i;
			int trgBit = start + i;
			BitsInt.setBit(ba, trgBit, BitsInt.getBit(ba, srcBit));
		}
	}
	
	private void check(int[] t, long ... expected) {
		for (int i = 0; i < expected.length; i++) {
			assertEquals("i=" + i + " | " + BitsInt.toBinary(expected, 32) + " / " + 
					BitsInt.toBinary(t), expected[i], t[i]);
		}
	}

	private void check(int posIgnore, int lenIgnore, int[] t, int ... expected) {
		for (int i = 0; i < expected.length; i++) {
			if (posIgnore / 32 <= i && i <= (posIgnore + lenIgnore)/32) {
				int mask = 0xFFFFFFFF;
				if (posIgnore / 32 == i) {
					mask = (mask<<(posIgnore%32)) >>> (posIgnore%32);
				}
				if (i == (posIgnore + lenIgnore)/32) {
					int end = (posIgnore+lenIgnore) % 32;
					mask = (mask >>> (32-end)) << (32-end);
				}
				mask = ~mask;
				//System.out.println("c-mask: "+ Bits.toBinary(mask));
				t[i] &= mask;
				expected[i] &= mask;
			}
			//System.out.println("i=" + i + " \nex= " + BitsInt.toBinary(expected, 32) + " \nac= " + 
			//		BitsInt.toBinary(t));
			assertEquals("i=" + i + " | " + BitsInt.toBinary(expected, 32) + " / " + 
					BitsInt.toBinary(t), expected[i], t[i]);
		}
	}

	private void check(int[] t, int[] s) {
		for (int i = 0; i < s.length; i++) {
			assertEquals("i=" + i + " | " + BitsInt.toBinary(s) + " / " + 
					BitsInt.toBinary(t), s[i], t[i]);
		}
	}


	private int[] newBA(int...ints) {
		int[] ba = new int[ints.length];
		for (int i = 0; i < ints.length; i++) {
			ba[i] = ints[i];
		}
		return ba;
	}

	private int[] newBaPattern(int n, int pattern) {
		int[] ba = new int[n];
		for (int i = 0; i < n; i++) {
			ba[i] = pattern;
		}
		return ba;
	}
}
