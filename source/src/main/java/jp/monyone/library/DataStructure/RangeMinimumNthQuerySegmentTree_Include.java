package jp.monyone.library.DataStructure;

public class RangeMinimumNthQuerySegmentTree_Include {
	//@start
	// SegmentTree(っぽい) 構造で範囲内のn番目の最小値を求める.
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

		public void init(long[] array){ // O( n log n)
			System.arraycopy(array, 0, segs[depth - 1], 0, n);
			for(int d = depth - 2, size = 2; d >= 0; d--, size *= 2){
				for(int begin = 0; begin < this.n; begin += size){
					final int middle = begin + size / 2;
					final int end = Math.min(begin + size, this.n);

					this.merge(d, begin, middle, end);
				}
			}
		}

		public void update(int k, long a){ // 多分 O(n)
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

		public int query(int a, int b, long v, int d, int l, int r){ // O(log^2 n)
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

		public long query(int a, int b, int nth){ // O(log^3 n)
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
	//@end

}

