package jp.monyone.library.DataStructure;

import java.util.Arrays;

public class RangeMinimumNthQueryNotSuccinctWaveletTree_Include {
	//@start
	public static class RMNthQ {
		int n, log;
		long[][] data;
		int[][] left;

		public RMNthQ(int n_) {
			this.n = n_;
			this.log = Integer.numberOfTrailingZeros(Integer.highestOneBit(n)) + 2;
			data = new long[log][n];
			left = new int[log - 1][n];
		}

		private void build(int d, int begin, int end){
			if(begin + 1 >= end){ return; }
			final int mid = (begin + end) / 2;
			final long mid_val = data[log - 1][mid];

			int l_sum = 0;
			for(int pos = begin; pos < end; pos++){
				if(data[d][pos] < mid_val){
					data[d + 1][begin + (l_sum++)] = data[d][pos];
				}else{
					data[d + 1][mid + (pos - begin - l_sum)] = data[d][pos];
				}
				left[d][pos] = l_sum;
			}
			build(d + 1, begin, begin + l_sum);
			build(d + 1, begin + l_sum , end);
		}

		public void init(long[] array){ // O( n log n)
			System.arraycopy(array, 0, data[0], 0, n);
			System.arraycopy(array, 0, data[log - 1], 0, n);
			Arrays.sort(data[log - 1]);
			build(0, 0, n);
		}

		public long nth(int a, int b, int nth){ return nth(a, b, nth, 0, 0, n); }
		public long nth(int a, int b, int nth, int d, int l, int r){ // [l, r)
			if(a + 1 >= b){ return data[d][a]; }

			final int left_a_before = a == l ? 0 : left[d][a - 1];
			final int right_a_before = left[d][b - 1]; // ) -> ]
			final int total_left_count = left[d][r - 1];
			final int in_left_count = right_a_before - left_a_before;

			if(in_left_count >= nth){ // go left
				final int n_l = l, n_r = l + total_left_count;
				final int n_a = n_l + left_a_before;
				final int n_b = n_a + in_left_count;
				final int n_nth = nth;

				return nth(n_a, n_b, n_nth, d + 1, n_l, n_r);
			}else{ // go right
				final int n_l = l + total_left_count, n_r = r;
				final int n_a = n_l + ((a - l) - left_a_before);
				final int n_b = n_a + ((b - a) - in_left_count);
				final int n_nth = nth - in_left_count; //

				return nth(n_a, n_b, n_nth, d + 1, n_l, n_r);
			}
		}
	}
	//@end

}

