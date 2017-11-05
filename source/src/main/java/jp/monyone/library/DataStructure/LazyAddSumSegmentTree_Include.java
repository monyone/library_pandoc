package jp.monyone.library.DataStructure;

public class LazyAddSumSegmentTree_Include {
	// 以下のクエリに対応するデータ構造
	// add(v, l, r) : [l, r) に v を足すクエリ
	// sum(l, r)    : [l, r) の合計を取得するクエリ
	
	//@start
	public static class LazyAddSumSegmentTree{
		int n;
		long[] dat, lazy;

		public LazyAddSumSegmentTree(int n_) {
			int n = 1;
			while(n < n_){ n *= 2;} this.n = n;
			dat = new long[this.n * 2 - 1];
			lazy = new long[this.n * 2 - 1];
		}

		private void evaluate_lazy(int k, int l, int r){
			dat[k] += lazy[k] * (r - l);
			if(k < n - 1){
				lazy[2 * k + 1] += lazy[k]; lazy[2 * k + 2] += lazy[k];
			}
			lazy[k] = 0;
		}

		public void update_node(int k){ dat[k] = dat[2*k+1] + dat[2*k+2]; }

		public void add(long v, int a, int b){ add(v, a, b, 0, 0, this.n); }
		public void add(long v, int a, int b, int k, int l, int r){
			evaluate_lazy(k, l, r);

			if(r <= a || b <= l){ return;
			}else if(a <= l && r <= b){
				lazy[k] += v; evaluate_lazy(k, l, r);
			}else {
				add(v, a, b, k * 2 + 1, l , (l + r) / 2);
				add(v, a, b, k * 2 + 2, (l + r) / 2, r);
				update_node(k);
			}
		}

		public long sum(int a, int b){ return sum(a, b, 0, 0, this.n); }
		public long sum(int a, int b, int k, int l, int r){
			evaluate_lazy(k, l, r);

			if(r <= a || b <= l){ return 0;
			}else if(a <= l && r <= b){ return dat[k];
			}else {
				long v1 = sum(a, b, k * 2 + 1, l , (l + r) / 2);
				long v2 = sum(a, b, k * 2 + 2, (l + r) / 2, r);
				update_node(k); return v1 + v2;
			}
		}
	}
	//@end
}

