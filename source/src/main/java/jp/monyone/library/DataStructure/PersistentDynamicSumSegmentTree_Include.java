package jp.monyone.library.DataStructure;

public class PersistentDynamicSumSegmentTree_Include {
	//@start
	public static class SegTree {
		private static final long DEFAULT_VALUE = 0;

		private long lower, upper; //[lower, upper)
		private SegTree left, right;
		private long value = DEFAULT_VALUE;

		public SegTree(final long n){
			this(0, Long.highestOneBit(n) << 1l);
		}

		private SegTree(final long lower, final long upper){
			this(lower, upper, DEFAULT_VALUE);
		}

		private SegTree(final long lower, final long upper, final long value){
			this.lower = lower;
			this.upper = upper;
			this.value = value;
		}

		public SegTree update(final long index, final long value){
			if(this.lower == index && this.upper == index + 1){
				return new SegTree(index, index + 1, value);
			}else{
				final SegTree ret_tree = new SegTree(this.lower, this.upper);

				final long middle = (this.lower + this.upper) / 2;
				// [lower, middle) [middle, upper)

				if(index < middle){ //update in [lower, middle)
					ret_tree.left = this.left != null ? this.left : new SegTree(this.lower, middle);
					ret_tree.left = ret_tree.left.update(index, value);
					ret_tree.right = right;
				}else { //update in [middle, upper)
					ret_tree.left = this.left;
					ret_tree.right = this.right != null ? this.right : new SegTree(middle, this.upper);
					ret_tree.right = ret_tree.right.update(index, value);
				}
				ret_tree.value = (ret_tree.left == null ? DEFAULT_VALUE : ret_tree.left.value)
						+ (ret_tree.right == null ? DEFAULT_VALUE : ret_tree.right.value);

				return ret_tree;
			}
		}

		public long query(long lower, long upper){
			if(this.upper <= lower || upper <= this.lower){
				return DEFAULT_VALUE;
			}else if(lower <= this.lower && this.upper <= upper){
				return this.value;
			}else{
				return (left == null ? DEFAULT_VALUE : left.query(lower, upper))
						+ (right == null ? DEFAULT_VALUE : right.query(lower, upper));
			}
		}

	}
	//@end
}

