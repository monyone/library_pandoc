package jp.monyone.library.DataStructure;

import java.lang.reflect.Array;

public class GenericsLazyArraySegmentTree_Include {
	// 流行っている抽象遅延セグ木を作った
	// update<X>(v, l, r) : [l, r) にアップデート
	// query<T>(l, r)    : [l, r) にクエリ問い合わせ
	// 零元を与える事に注意
	
	//@start
	public static class GenericsLazyArraySegmentTree<T, X> {
		@FunctionalInterface public interface QueryFunction<T> {
			T query(T l, T r);
		}
		@FunctionalInterface public interface LazyFunction<X> {
			X query(X l, X r);
		}
		@FunctionalInterface public interface UpdateFunction<T, X>{
			T update(T old, X act, int l, int r);
		}

		int n;
		T[] elem;
		X[] lazy;
		boolean[] push;

		QueryFunction<T> query_func;
		LazyFunction<X> lazy_func;
		UpdateFunction<T, X> update_func;
		T zero_t; X zero_x;

		public GenericsLazyArraySegmentTree(int n_, QueryFunction<T> query_f, LazyFunction<X> lazy_f, UpdateFunction<T, X> update_f, T zero_t, X zero_x) {
			int n = 1;
			while(n < n_){ n *= 2;} this.n = n;

			query_func = query_f;
			lazy_func = lazy_f;
			update_func = update_f;
			this.zero_t = zero_t;
			this.zero_x = zero_x;

			elem = (T[])(Array.newInstance(zero_t.getClass(), this.n * 2 - 1));
			lazy = (X[])(Array.newInstance(zero_x.getClass(), this.n * 2 - 1));
			push = new boolean[this.n * 2 - 1];
			for(int i = 0; i < this.n; i++){ elem[i] = zero_t; lazy[i] = zero_x; }
		}

		private void evaluate_lazy(int k, int l, int r){
			if(!push[k]) { return; }

			elem[k] = update_func.update(elem[k], lazy[k], l, r);
			if (k < n - 1) {
				lazy[k * 2 + 1] = lazy[k * 2 + 2] = lazy[k]; // どのみち足すのは零元
				push[k * 2 + 1] = push[k * 2 + 2] = true;
			}
			push[k] = false; lazy[k] = zero_x;
		}

		public void update_node(int k){ elem[k] = query_func.query(elem[2*k+1], elem[2*k+2]); }

		public void update(X v, int a, int b){ update(v, a, b, 0, 0, this.n); }
		public void update(X v, int a, int b, int k, int l, int r){
			evaluate_lazy(k, l, r);

			if(r <= a || b <= l){ return;
			}else if(a <= l && r <= b){
				lazy[k] = lazy_func.query(lazy[k], v);
				evaluate_lazy(k, l, r);
			}else {
				update(v, a, b, k * 2 + 1, l , (l + r) / 2);
				update(v, a, b, k * 2 + 2, (l + r) / 2, r);
				update_node(k);
			}
		}

		public T query(int a, int b){ return query(a, b, 0, 0, this.n); }
		public T query(int a, int b, int k, int l, int r){
			evaluate_lazy(k, l, r);

			if(r <= a || b <= l){ return zero_t;
			}else if(a <= l && r <= b){ return elem[k];
			}else {
				final T vl = query(a, b, k * 2 + 1, l , (l + r) / 2);
				final T vr = query(a, b, k * 2 + 2, (l + r) / 2, r);
				update_node(k); return query_func.query(vl, vr);
			}
		}
	}
	//@end
}

