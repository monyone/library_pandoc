package jp.monyone.verify.yukicoder.No519.MinimumGeneralMatchingDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static final long INF = Long.MAX_VALUE / 2 - 1;

	public static long MinimumGeneralMatching(long[][] adj){
		final int n = adj.length;

		long[] DP = new long[1 << n];
		final int INF = Integer.MAX_VALUE / 2 - 1;

		for(int i = 0; i < 1 << n; i++){ DP[i] = INF; }
		DP[0] = 0;

		for(int bit = 0; bit < (1 << n); bit++){
			if(DP[bit] >= INF){ continue; }

			// 立ってない最初の bit だけ立ててて O(N) ループ節約
			int i = 0; for(; (bit & (1 << i)) != 0; i++);
			final int fst = 1 << i;

			for(int j = i + 1; j < n; j++){
				if((bit & (1 << j)) != 0){ continue; }

				final int snd = 1 << j;
				final int next_bit = bit | fst | snd;
				DP[next_bit] = Math.min(DP[next_bit], DP[bit] + adj[i][j]);
			}
		}

		return DP[(1 << n) - 1];
	}

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			final int n = sc.nextInt();
			
			final long max = 1000;
			long[][] adj = new long[n][n];
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					adj[i][j] = max - sc.nextInt();
				}
			}
		
			final long solve = MinimumGeneralMatching(adj);
			System.out.println(max * (n / 2) - solve);
		}
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
