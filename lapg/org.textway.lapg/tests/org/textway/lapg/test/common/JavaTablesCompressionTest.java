/**
 * Copyright 2002-2012 Evgeny Gryaznov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.textway.lapg.test.common;

import org.junit.Test;
import org.textway.lapg.common.JavaArrayArchiver;

import static org.junit.Assert.*;

public class JavaTablesCompressionTest {

	private void checkDecompression(int[][] a) {
		String c = JavaArrayArchiver.packIntInt(a, 5);
		String starts = a.length + "," + a[0].length + ",\n";
		assertTrue(c.startsWith(starts));
		c = c.substring(starts.length());

		StringBuilder extractedString = new StringBuilder();
		char[] chs = c.toCharArray();
		boolean isstring = false;
		for (char ch : chs) {
			if (ch == '"') {
				isstring = !isstring;
				continue;
			}
			if (isstring) {
				extractedString.append(ch);
			}
		}

		int[][] b = JavaArrayArchiver.unpackIntInt(a.length, a[0].length, extractedString.toString());
		for (int i = 0; i < a.length; i++) {
			for (int e = 0; e < a[0].length; e++) {
				if (a[i][e] != b[i][e]) {
					fail("wrong decompression at " + i + "," + e);
				}
			}
		}
	}

	private void checkDecompression(int[] a) {
		String c = JavaArrayArchiver.packInt(a, 5);

		StringBuilder extractedString = new StringBuilder();
		char[] chs = c.toCharArray();
		boolean isstring = false;
		for (char ch : chs) {
			if (ch == '"') {
				isstring = !isstring;
				continue;
			}
			if (isstring) {
				extractedString.append(ch);
			}
		}
		assertFalse(isstring);

		int[] b = JavaArrayArchiver.unpackInt(a.length, extractedString.toString());
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				fail("wrong decompression at " + i);
			}
		}
	}

	@Test
	public void testCompression1() {
		checkDecompression(new int[][]{
				{1, 2},
				{3, 3}
		});
	}

	@Test
	public void testCompression2() {
		checkDecompression(new int[][]{
				{1, 1},
				{1, 1}
		});
	}

	@Test
	public void testCompression3() {
		checkDecompression(new int[][]{
				{0, 0},
				{0, 1}
		});
	}

	@Test
	public void testCompression4() {
		checkDecompression(new int[][]{
				{0},
				{0}
		});
	}

	@Test
	public void testCompression5() {
		checkDecompression(new int[][]{
				{-789}
		});
	}

	@Test
	public void testCompression6() {
		checkDecompression(new int[][]{
				{1, 2, 3, 4, 5, 6, 7, 8},
				{3, 4, 5, 7, 8, 8, 8, 8}
		});
	}

	@Test
	public void testCompressionBig() {
		checkDecompression(new int[][]{
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
				{1, 2, 3, 4, 5, 6, 7, 8, 4, 4, 4, 4, 4, 4, 4, 4, 5, 6, 2, 1, 1, 1, 1, 1, 1, 43, 345, 345, 345, 34, 34, 345, 34, 345, 34533, 333},
		});
	}

	@Test
	public void testIntCompression1() {
		checkDecompression(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
	}

	@Test
	public void testIntCompressionEmpty() {
		checkDecompression(new int[]{});
	}

	@Test
	public void testIntCompressionOne() {
		checkDecompression(new int[]{-100});
		checkDecompression(new int[]{1});
	}

	@Test
	public void testIntCompressionNearZero() {
		checkDecompression(new int[]{0});
		checkDecompression(new int[]{-1});
	}

	@Test
	public void testIntCompression2() {
		checkDecompression(new int[]{0, 0, 0});
		checkDecompression(new int[]{Integer.MAX_VALUE});
	}

	@Test
	public void testIntCompressionMax() {
		checkDecompression(new int[]{Integer.MAX_VALUE});
	}

	@Test
	public void testIntCompressionMin() {
		checkDecompression(new int[]{Integer.MIN_VALUE});
	}
}