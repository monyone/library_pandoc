package jp.monyone.verify.AOJ.DSL2A.RMQ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import java.util.Arrays;

public class Main {
	/*
	  TLEチェック用. updateにO(n)以上かかる事の確認のため.
	  途中までは AC するので, Verifyにも一応なっている.
	*/
		
	public static class RMQ {
		long[][] sparse_table; // queryを O(1)にするなら, k をメモ化する事.

		public RMQ(long[] array){ // O(n log n)
			final int depth = Integer.numberOfTrailingZeros(Integer.highestOneBit(array.length));
			sparse_table = new long[depth + 1][]; sparse_table[0] = new long[array.length];
			System.arraycopy(array, 0, sparse_table[0], 0, array.length);

			for(int k = 1; k < sparse_table.length; k++){
				sparse_table[k] = new long[array.length - (1 << k) + 1];
				for(int i = 0; i + (1 << k) <= array.length; i++) { // [i, i + (1 << k))
					// [i, i + (1 << k) -> [i, i + (1 << k) / 2), [i + (1 << k) / 2, i << (k - 1))
					sparse_table[k][i] =
							Math.min(sparse_table[k-1][i], sparse_table[k-1][i + (1 << (k - 1))]);
				}
			}
		}

		public void update(int index, long v){ // index: 0-index
			//update O(n log n) で効率が悪い. SegTreeでどうぞ.
			sparse_table[0][index] = v;
			for(int k = 1; k < sparse_table.length; k++){
				final int begin = Math.max(0, index - (1 << k) + 1);
				final int end = Math.min(sparse_table[k].length, index + (1 << k));
				// 最大で index + [-(1 << k) + 1, (1 << k)) の範囲を更新する必要がある
				for(int i = begin; i < end; i++){
					sparse_table[k][i] =
							Math.min(sparse_table[k - 1][i], sparse_table[k-1][i + (1 << (k - 1))]);
				}
			}
		}

		public long query(int l, int r){ // [l, r) O(log log n)
			final int k = Integer.numberOfTrailingZeros(Integer.highestOneBit(r - l));
			// k -> 区間をオーバーしない最大の2の冪乗
			// left -> [i, i + (1 << k)), right -> [r - (1 << k)] (開区間なので±0)
			return Math.min(sparse_table[k][l], sparse_table[k][r - (1 << k)]);
		}
	}
		
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		final int n = sc.nextInt();
		final int q = sc.nextInt();
		
		long[] array = new long[n];
		Arrays.fill(array, Integer.MAX_VALUE);
		
		RMQ rmq = new RMQ(array);
		for(int tt = 0; tt < q; tt++){
			final int op = sc.nextInt();
			final int x  = sc.nextInt();
			final int y  = sc.nextInt();

			if(op == 0){
				rmq.update(x, y);
			}else{
				System.out.println(rmq.query(x, y + 1));
			}
		}
	}
	
	public static class Scanner {
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

