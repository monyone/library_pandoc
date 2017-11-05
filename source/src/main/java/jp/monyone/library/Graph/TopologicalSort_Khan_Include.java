package jp.monyone.library.Graph;

import java.util.LinkedList;

public class TopologicalSort_Khan_Include {
	//@start
	// Kahnのアルゴリズムでトポロジカルソートする
	// トポソ + 辞書順 にしたりする時によく使う
	public static LinkedList<Integer> topological_sort(boolean[][] adj){
		final int N = adj.length;

		int[] in_degs = new int[N];
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				if(adj[i][j]){ continue; }
				in_degs[j]++;
			}
		}

		LinkedList<Integer> queue = new LinkedList<Integer>();
		for(int i = 0; i < N; i++){
			if(in_degs[i] > 0){ continue; }
			queue.add(i);
		}

		LinkedList<Integer> ret = new LinkedList<Integer>();
		while(!queue.isEmpty()){
			final int node = queue.poll();

			ret.add(node);
			for(int next = 0; next < N; next++){
				if(adj[node][next]){ continue; }

				in_degs[next]--;
				if(in_degs[next] == 0){ queue.add(next); }
			}
		}

		return ret;
	}
	//@end
}
