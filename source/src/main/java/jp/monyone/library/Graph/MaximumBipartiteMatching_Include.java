package jp.monyone.library.Graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MaximumBipartiteMatching_Include {
	// http://www.prefield.com/algorithm/graph/bipartite_matching.html

	//@start
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

	public static boolean augment(ArrayList<HashSet<Integer>> adj, int curr, int[] match, boolean[] visited) {
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
	//@end
}
