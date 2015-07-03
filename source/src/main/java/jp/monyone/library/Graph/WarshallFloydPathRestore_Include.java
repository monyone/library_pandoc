package jp.monyone.library.Graph;

public class WarshallFloydPathRestore_Include {
	//@start
	// next[i][j] := i -> j へ行く際に, 次に通過する点
	// for(int cur = start; cur != goal; cur = next[cur][goal]) と順番に辿れる.
	public static int[][] warshallFloyd(long[][] adj){
		final int n = adj.length;

		int[][] next = new int[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				next[i][j] = j;
			}
		}

		for(int k = 0; k < n; k++){
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					if(adj[i][j] < adj[i][k] + adj[k][j]){
						adj[i][j] = adj[i][k] + adj[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}

		return next;
	}
	//@end

	// 参考: http://zeosutt.hatenablog.com/entry/2015/05/05/045943
}
