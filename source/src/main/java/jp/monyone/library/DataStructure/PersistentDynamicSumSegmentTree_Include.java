package jp.monyone.library.DataStructure;

public class PersistentDynamicSumSegmentTree_Include {
	//@start
	public static class SegTree {
		private static final long DEFAULT = 0; // 単位元

		private long lower, upper; // [lower, upper)
		private SegTree left, right;
		private long value = DEFAULT; // 初期値は単位元

		public SegTree(final long n){ this(0, Long.highestOneBit(n) << 1l); }

		private SegTree(long lower, long upper){ this(lower, upper, DEFAULT); }
		private SegTree(final long lower, final long upper, final long value){
			this.lower = lower; this.upper = upper; this.value = value;
		}

		private static long get(SegTree s){return s==null ? DEFAULT : s.value;}

		public SegTree update(final long index, final long value){
			if(this.lower == index && this.upper == index + 1){
				return new SegTree(index, index + 1, value);
			}else{// split to [lower, middle) and [middle, upper)
				final long middle = (this.lower + this.upper) / 2;
				final SegTree ret = new SegTree(this.lower, this.upper);

				if(index < middle){ //update in [lower, middle)
					ret.left = this.left != null ? this.left
							: new SegTree(this.lower, middle);
					ret.left = ret.left.update(index, value);
					ret.right = right;
				}else{ //update in [middle, upper)
					ret.left = this.left;
					ret.right = this.right != null ? this.right
							: new SegTree(middle, this.upper);
					ret.right = ret.right.update(index, value);
				}
				ret.value = get(ret.left) + get(ret.right);

				return ret;
			}
		}

		public long query(long lower, long upper){
			if(this.upper <= lower || upper <= this.lower){
				return DEFAULT;
			}else if(lower <= this.lower && this.upper <= upper){
				return this.value;
			}else{
				final long middle = (this.lower + this.upper) / 2;
				final long left_sum = left == null ? DEFAULT : left.query(lower, middle);
				final long right_sum = right == null ? DEFAULT : right.query(middle, upper);
				return left_sum + right_sum;
			}
		}
	}
	//@end
}