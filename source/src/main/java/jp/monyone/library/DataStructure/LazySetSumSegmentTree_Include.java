package jp.monyone.library.DataStructure;

public class LazySetSumSegmentTree_Include {
	//@start
	// 以下のクエリに対応するデータ構造
	// set : [l, r) の値を一定にするクエリ
	// sum : [l, r) の合計を取得するクエリ
	public static class LazySetSumSegmentTree {
		int n;

		long[] dat, lazy;
		boolean[] push;

		public LazySetSumSegmentTree(int n_) {
			int n = 1;
			while(n < n_){ n *= 2;} this.n = n;

			dat = new long[this.n * 2 - 1];
			lazy = new long[this.n * 2 - 1];
			push = new boolean[this.n * 2 - 1];
		}

		// [a, b) の区間で set の遅延してる分を評価する
		private void evaluate_lazy(int k, int l, int r){
			if(!push[k]){ return; }

			dat[k] = lazy[k] * (r - l);
			if(k < n - 1){
				lazy[k * 2 + 1] = lazy[k * 2 + 2] = lazy[k];
				push[k * 2 + 1] = push[k * 2 + 2] = true;
			}

			lazy[k] = 0;
			push[k] = false;
		}

		private void update_node(int k){
			dat[k] = dat[k * 2 + 1] + dat[k * 2 + 2];
		}

		public void set(long v, int a, int b){ set(v, a, b, 0, 0, this.n); }
		public void set(long v, int a, int b, int k, int l, int r){
			evaluate_lazy(k, l, r);
			if(r <= a || b <= l){ return;
			}else if(a <= l && r <= b){
				lazy[k] = v;
				push[k] = true;
				evaluate_lazy(k, l, r);
			}else{
				set(v, a, b, k * 2 + 1, l, (l + r) / 2);
				set(v, a, b, k * 2 + 2, (l + r) / 2, r);
				update_node(k);
			}
		}

		public long sum(int a, int b){ return sum(a, b, 0, 0, this.n); }
		public long sum(int a, int b, int k, int l, int r){
			evaluate_lazy(k, l, r);
			if(r <= a || b <= l){ return 0;
			}else if(a <= l && r <= b){ return dat[k];
			}else{
				final long v1 = sum(a, b, k * 2 + 1, l, (l + r) / 2);
				final long v2 = sum(a, b, k * 2 + 2, (l + r) / 2, r);
				update_node(k); return v1 + v2;
			}
		}
	}
	//@end
}

