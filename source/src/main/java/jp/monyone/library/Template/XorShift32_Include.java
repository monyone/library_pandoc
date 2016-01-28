package jp.monyone.library.Template;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class XorShift32_Include {
	//@start
	// 2^31 - 1 の周期だと期待してる XorShift32
	public static class Random {
		private int seed; // 0 になったら死ぬ.

		public Random(){ this((int)(System.nanoTime()));}
		public Random(int seed){ this.seed = seed; }

		private void forward(){
			seed = seed ^ (seed << 13);
			seed = seed ^ (seed >>> 17);
			seed = seed ^ (seed << 5);
		}

		public int next(){ forward(); return (seed & 0x7FFFFFFF); }
		public int nextInt(int l, int r){ return l + (next() % (r - l)); }
		public int nextInt(int n){ return nextInt(0, n); }

		public IntStream ints(){ return IntStream.generate(() -> next()); }
		public IntStream ints(int l, int r){ return IntStream.generate(() -> nextInt(l, r));}
		public IntStream ints(int n){ return IntStream.generate(() -> nextInt(n)); }
	}
	//@end
}
