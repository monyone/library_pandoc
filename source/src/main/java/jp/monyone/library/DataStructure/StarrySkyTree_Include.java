package jp.monyone.library.DataStructure;

public class StarrySkyTree_Include {
	//@start
	// JOI界隈で人気な, ちょっと特殊なRMQ(Range Maximum Query).
	// 根が全体の最大値で, 子ノードのうちどちらかは 0 で, もう一方は 0 以下を満たす.
	public static class StarrySkyTree {
		private static final long M_INF = Long.MIN_VALUE / 2 + 1;
		private static final long DEFAULT = 0; // 単位元

		int n; long[] add; //addは上記の制約を満たす.

		public StarrySkyTree(int n_) {
			int n = 1;
			while(n < n_){ n *= 2;} this.n = n;
			add = new long[this.n * 2 - 1];
			for(int i = 0; i < this.n * 2 - 1 ; i++){ add[i] = DEFAULT; }
		}

		// [a, b) に v を足す
		public void add(long v, int a, int b){ add(v, a, b, 0, 0, this.n); }
		private void add(long v, int a, int b, int k, int l, int r){
			if(k == 0){ add[k] += v; } // 全体に加算しておく.
			if(a <= l && r <= b) { return; } // 完全に範囲内
			if(r <= a || b <= l) { add[k] -= v; return; } // 完全に範囲外
			// 範囲外を含む -> 二分割して再帰
			add(v, a, b, k * 2 + 1, l, (l + r) / 2);
			add(v, a, b, k * 2 + 2, (l + r) / 2, r);
			// どちらかが 0 になるように, 子供の最大値を子供から引いて親に足す.
			final long child_max = Math.max(add[k * 2 + 1], add[k * 2 + 2]);
			add[k * 2 + 1] -= child_max; add[k * 2 + 2] -= child_max;
			add[k] += child_max;
		}

		// [a, b) の範囲での最大値を求める
		public long max(long a, long b){ return max(a, b, 0, 0, this.n); }
		private long max(long a, long b, int k, int l, int r){
			if(r <= a || b <= l){ return M_INF; } //簡単のため適当に小さい値を返す.
			if(a <= l && r <= b){ return add[k]; } // 子孫のaddは 0 を選べる.

			final long left_max  = max(a, b, k * 2 + 1, l, (l + r) / 2);
			final long right_max = max(a, b, k * 2 + 2, (l + r) / 2, r);
			return Math.max(left_max, right_max) + add[k]; //
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

