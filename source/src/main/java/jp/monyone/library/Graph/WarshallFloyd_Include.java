package jp.monyone.library.Graph;

import static jp.monyone.library.Graph.AdjMat_Include.init_adj;

public class WarshallFloyd_Include {
	//@start
	// 引数を破壊したくないので, このような長いコードになっている.
	public static long[][] warshallFloyd(long[][] adj){
		final int n = adj.length;

		long[][] ret = init_adj(n);
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				ret[i][j] = Math.min(ret[i][j], adj[i][j]); // 自己辺を0にするため.
			}
		}
		// ここからワーシャルフロイド(破壊的でよい, 自己辺0 ならこれだけ抜き出す)
		for(int k = 0; k < n; k++){
			for(int i = 0; i < n; i++){
				for(int j = 0; j < n; j++){
					ret[i][j] = Math.min(ret[i][j], ret[i][k] + ret[k][j]);
				}
			}
		}
		// ここまで
		return ret;
	}
	//@end
}
