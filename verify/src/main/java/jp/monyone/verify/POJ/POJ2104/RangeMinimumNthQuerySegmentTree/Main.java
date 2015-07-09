package jp.monyone.verify.POJ.POJ2104.RangeMinimumNthQuerySegmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	// 蟻本に出てきた, SegmentTreeと似た構造の, n番目の最小値を出すデータ構造.
	// POJ2104 で Verify 出来てはいる. けど, 二分探索でLongの境界値 / 2 くらいのがあるとバグル.
	// init -> O(n log n) , update(k, v) -> O(n) くらい? , query(a, b, nth) -> O(log^3 n)
	public static class RMNthQ {
		int n, depth;
		long[][] segs;

		public RMNthQ(int n_) {
			this.n = n_;
			this.depth = Integer.numberOfTrailingZeros(Integer.highestOneBit(n_)) + 2;
			segs = new long[depth][n_];
		}

		private void merge(int d, int begin, int middle, int end){
			for(int d_pos = begin, s1_pos = begin, s2_pos = middle; d_pos < end; d_pos++){
				if(s1_pos >= middle){ segs[d][d_pos] = segs[d + 1][s2_pos++]; }
				else if(s2_pos >= end) { segs[d][d_pos] = segs[d + 1][s1_pos++]; }
				else if(segs[d + 1][s1_pos] <= segs[d + 1][s2_pos]) {
					segs[d][d_pos] = segs[d + 1][s1_pos++];
				}else{
					segs[d][d_pos] = segs[d + 1][s2_pos++];
				}
			}
		}

		public void init(long[] array){
			System.arraycopy(array, 0, segs[depth - 1], 0, n);
			for(int d = depth - 2, size = 2; d >= 0; d--, size *= 2){
				for(int begin = 0; begin < this.n; begin += size){
					final int middle = begin + size / 2;
					final int end = Math.min(begin + size, this.n);

					this.merge(d, begin, middle, end);
				}
			}
		}

		public void update(int k, long a){
			segs[depth - 1][k] = a;
			for(int d = depth - 2, size = 2; d >= 0; d--, size *= 2){
				final int begin = (k / size) * size, middle = begin + size / 2;
				final int end  = Math.min(begin + size, this.n);

				this.merge(d, begin, middle, end);
			}
		}

		private static int upper_bound(long[] array, long key, int begin, int end){
			int lower = begin - 1, upper = end;
			while(upper - lower > 1){
				final int mid = (lower + upper) / 2;

				if(array[mid] <= key){ lower = mid; } else { upper = mid;}
			}
			return upper;
		}

		public int query(int a, int b, long v, int d, int l, int r){
			if(r <= a || b <= l){
				return 0;
			}else if(a <= l && r <= b){
				return (upper_bound(segs[d], v, l, r) - l);
			}else {
				final int size = 1 << (depth - d - 1);
				return query(a, b, v, d + 1, l, l + size / 2)
						+ query(a, b, v, d + 1, l + size / 2, Math.min(l + size, n));
			}
		}

		public long query(int a, int b, int nth){
			int lower_index = -1, upper_index = this.n; //(l, u]
			while(upper_index > lower_index + 1) {
				final int middle_index = (lower_index + upper_index) / 2;
				final int ret = query(a, b, segs[0][middle_index], 0, 0, this.n);
				if (ret < nth) { lower_index = middle_index; }
				else { upper_index = middle_index; }
			}
			return segs[0][upper_index];
		}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		
		final int n = sc.nextInt();
		final int m = sc.nextInt();
		
		long[] array = new int[n];
		for(int i = 0; i < n; i++){
			array[i] = sc.nextInt();
		}
		RMNthQ nth = new RMNthQ(n);
		nth.init(array);
		
		for(int i = 0; i < m; i++){
			final int l = sc.nextInt();
			final int r = sc.nextInt();
			final int k = sc.nextInt();
			
			System.out.println(nth(l - 1, r, k));
		}
				
		sc.close();
	}
	// TODO: Template.Scanner
	
	public static class Scanner {
	    private BufferedReader br;
	    private StringTokenizer tok;

	    public Scanner(InputStream is) throws IOException {
	        br = new BufferedReader(new InputStreamReader(is));
	    }

	    private void getLine() throws IOException {
	        while (!hasNext()) { tok = new StringTokenizer(br.readLine()); }
	    }

	    private boolean hasNext() {
	        return tok != null && tok.hasMoreTokens();
	    }

	    public String next() throws IOException {
	        getLine(); return tok.nextToken();
	    }

	    public int nextInt() throws IOException {
	        return Integer.parseInt(next());
	    }
	    // 他のnextXXXもXXX.parseXXX()メソッドを使って作れるので省略
	    
	    public void close() throws IOException {
	        br.close();
	    }
	}
}
