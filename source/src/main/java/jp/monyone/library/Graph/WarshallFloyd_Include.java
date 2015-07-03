package jp.monyone.library.Graph;

public class WarshallFloyd_Include {
	//@start
	public static void warshallFloyd(long[][] adj){
		final int n = adj.length;

		for(int k = 0; k < n; k++){
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					adj[i][j] = Math.min(adj[i][j], adj[i][k] + adj[k][j]);
				}
			}
		}
	}
	//@end
}
