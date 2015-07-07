package jp.monyone.library.DataStructure;

public class RangeMinimumNthQuerySegmentTree_Include {
	//@start
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

		public long query(int a, int b, int nth){ // [a, b) , nth -> 1-indexed
			// Long.MAX_VALUE や Long.MIN_VALUE が入ったりするとバグル.
			long lower = segs[0][0] - 1, upper = segs[0][segs[0].length - 1] + 1;
			while(upper - lower > 1){
				final long middle = (lower + upper) / 2;
				final int ret = query(a, b, middle, 0, 0, this.n);
				if(ret < nth){ lower = middle; }
				else{ upper = middle; }
			}
			return upper;
		}
	}
	//@end

}

