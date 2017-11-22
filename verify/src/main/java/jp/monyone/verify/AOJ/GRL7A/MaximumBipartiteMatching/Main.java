package jp.monyone.verify.AOJ.GRL7A.MaximumBipartiteMatching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

import java.util.Arrays;

public class Main {
		
	// フォードファルカーソン法で二部マッチングを O(V*(V+E)) で解く
	// L <- 左側の頂点の左端 : [0, L)
	public static int maximumBipartiteMatching(int L, ArrayList<HashSet<Integer>> adj){
		boolean[] visited = new boolean[adj.size()];
		int[] match = new int[adj.size()];
		Arrays.fill(match, -1);

		int count = 0;
		for(int curr = 0; curr < L; curr++){
			Arrays.fill(visited, false);

			if(augment(adj, curr, match, visited)){ count++; }
		}

		return count;
	}

	private static boolean augment(ArrayList<HashSet<Integer>> adj, int curr, int[] match, boolean[] visited) {
		if(curr < 0){return true; }

		for(final int next : adj.get(curr)) {
			if(visited[next]){ continue; }

			visited[next] = true;
			if(augment(adj, match[next], match, visited)) {
				match[curr] = next;
				match[next] = curr;
				return true;
			}
		}

		return false;
	}
		
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		final int X = sc.nextInt();
		final int Y = sc.nextInt();
		final int E = sc.nextInt();
		
		final int size = X + Y;
		ArrayList<HashSet<Integer>> adj = new ArrayList<HashSet<Integer>>();
		for(int i = 0; i < size; i++){ adj.add(new HashSet<Integer>()); }
		
		for(int i = 0; i < E; i++){
			final int x = sc.nextInt();
			final int y = sc.nextInt();
			
			adj.get(x).add(X + y);
		}
		
		System.out.println(maximumBipartiteMatching(X, adj));
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

