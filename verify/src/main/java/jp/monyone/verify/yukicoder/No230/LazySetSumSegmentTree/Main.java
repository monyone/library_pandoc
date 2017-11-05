package jp.monyone.verify.yukicoder.No230.LazySetSumSegmentTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

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
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		final int N = sc.nextInt();
		final int Q = sc.nextInt();
		
		long a_score = 0, b_score = 0;
		
		LazySetSumSegmentTree a_seg = new LazySetSumSegmentTree(N);
		LazySetSumSegmentTree b_seg = new LazySetSumSegmentTree(N);
		
		for(int i = 0; i < Q; i++){
			final int x = sc.nextInt();
			final int l = sc.nextInt();
			final int r = sc.nextInt() + 1;
			
			if(x == 0){
				final long a_team = a_seg.sum(l, r);
				final long b_team = b_seg.sum(l, r);
				
				if(a_team > b_team){
					a_score += a_team;
				}else if(a_team < b_team){
					b_score += b_team;
				}
			
			}else if(x == 1){
				a_seg.set(1, l, r);
				b_seg.set(0, l, r);
			}else if(x == 2){
				a_seg.set(0, l, r);
				b_seg.set(1, l, r);
			}
		}
		
		a_score += a_seg.sum(0, N);
		b_score += b_seg.sum(0, N);
		
		System.out.println(a_score + " " + b_score);
	}
	
	public static class Scanner implements AutoCloseable {
		private BufferedReader br;
		private StringTokenizer tok;

		public Scanner(InputStream is) {
			br = new BufferedReader(new InputStreamReader(is));
		}

		private void getLine() {
			try {
				while (!hasNext()) {tok = new StringTokenizer(br.readLine());}
			} catch(IOException e){ /* ignore */ }
		}

		private boolean hasNext() {
			return tok != null && tok.hasMoreTokens();
		}

		public String next() {
			getLine(); return tok.nextToken();
		}

		public int nextInt(){
			return Integer.parseInt(next());
		}
		// 他のnextXXXもXXX.parseXXX()メソッドを使って作れるので省略

		public void close() {
			try{ br.close(); } catch (IOException e){ /*ignore*/ }
		}
	}
	
}
