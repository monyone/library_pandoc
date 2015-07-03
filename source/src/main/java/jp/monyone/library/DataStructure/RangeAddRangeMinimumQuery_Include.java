package jp.monyone.library.DataStructure;

public class RangeAddRangeMinimumQuery_Include {
	//@start
	// 初期値は [0,n) = 0, 範囲minが手抜きなので,番兵(INF)が必要.
	public static class RMQ {
		private static final long INF = Long.MAX_VALUE / 2 - 1;
		private static final long DEFAULT = 0; // 単位元

		int n; long[] min, add; // add[k]...範囲に足した値. min[k]...その時点での最小値

		public RMQ(int n_) {
			int n = 1;
			while(n < n_){ n *= 2;} this.n = n;
			min = new long[this.n * 2 - 1]; add = new long[this.n * 2 - 1];
			for(int i = 0; i < this.n * 2 - 1 ; i++){
				min[i] = DEFAULT; add[i] = DEFAULT;
			}
		}

		// [a, b) に v を足す
		public void add(long v, int a, int b){ add(v, a, b, 0, 0, this.n); }
		private void add(long v, int a, int b, int k, int l, int r){
			if(r <= a || b <= l){ return; } // 完全に範囲外
			if(a <= l && r <= b) { // 完全に範囲内
				add[k] += v;
			}else{ //範囲外を含む -> 二分割して再帰
				add(v, a, b, k * 2 + 1, l, (l + r) / 2);
				add(v, a, b, k * 2 + 2, (l + r) / 2, r);
			}
			min[k] = (k >= (n - 1) ? DEFAULT : Math.min(min[k * 2 + 1], min[k * 2 + 2])) + add[k];
		}

		// [a, b) の範囲での最小値を求める
		public long min(long a, long b){ return min(a, b, 0, 0, this.n); }
		private long min(long a, long b, int k, int l, int r){
			if(r <= a || b <= l){ return INF; } //簡単のため適当に大きい値を返す.
			if(a <= l && r <= b){ return min[k]; } //

			final long left_min  = min(a, b, k * 2 + 1, l, (l + r) / 2);
			final long right_min = min(a, b, k * 2 + 2, (l + r) / 2, r);
			return Math.min(left_min, right_min) + add[k]; //
		}

		// [index] での値を求める
		public long value(int index){
			int k = index + this.n - 1;
			long value = add[k];
			while(k > 0){ k = (k - 1) / 2; value += add[k]; }
			return value;
		}
	}
	//@end
}

