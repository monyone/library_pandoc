package jp.monyone.library.Graph;

import static jp.monyone.library.DataStructure.UnionFind_Include.UnionFind;

public class OfflineLCATarjan_Include {
	//@start
	// DFS で 前順走査(preorder) で 一番近い親を探す. 部分木のマージに, UnionFind を使う .
	public static void dfs(int[] us, int[] vs, int[] lcas, int node, int parent,
	                       boolean[] visited, int[] ancestor, boolean[][] adj, UnionFind uf){
		ancestor[uf.find(node)] = node; // 自分の祖先は自分.
		for(int next = 0; next < adj.length; next++){
			if(next == parent){ continue; }
			if(!adj[node][next]){ continue; }

			dfs(us, vs, lcas, next, node, visited, ancestor, adj, uf);
			uf.union(node, next); // next以下のLCAを探索し終えたので, 部分木を併合.
			ancestor[uf.find(node)] = node; // 併合した祖先は自分(部分木内はもう終えてる)
		}

		visited[node] = true;
		for(int i = 0; i < us.length; i++){
			int not_node = -1; // 片方が自分だった時のもう片方が欲しい
			if(us[i] == node){ not_node = vs[i]; }
			else if(vs[i] == node){ not_node = us[i]; }

			if(not_node >= 0 && visited[not_node]){ // 両方とも評価済みかのチェック
				lcas[i] = ancestor[uf.find(not_node)]; // 両方とも評価済みの場合は一度しかない.
			}
		}
	}

	//
	public static int[] offlineLCA(int[] us, int[] vs, boolean[][] adj){
		final int n = adj.length;
		int[] lca_nodes = new int[n], ancestor = new int[n];
		boolean[] visited = new boolean[n];
		UnionFind uf = new UnionFind(n);

		dfs(us, vs, lca_nodes, 0, -1, visited, ancestor, adj, uf);
		return lca_nodes;
	}
	//@end
}
