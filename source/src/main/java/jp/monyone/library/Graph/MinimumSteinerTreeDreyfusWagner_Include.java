package jp.monyone.library.Graph;

import static jp.monyone.library.Template.LongINF_Include.INF;
import static jp.monyone.library.Graph.WarshallFloyd_Include.warshallFloyd;

public class MinimumSteinerTreeDreyfusWagner_Include {
	//@start
	// adj -> 隣接行列, ts -> 木に含まれてほしい木
	// 戻り値が INF以上なら, 要求を満たすシュタイナー木は存在しない.
	public static long minimumSteinerTree(long[][] adj, int[] ts){
		final int n = adj.length;
		final int m = ts.length;

		// メモリが勿体無い場合は ワーシャルフロイドの結果を adj に入れる.
		long[][] all_pair = warshallFloyd(adj); //O(n^3)
		long[][] DP = new long[1 << m][n];

		for(int i = 0; i < m; i++){
			for(int j = 0; j < n; j++){
				DP[1 << i][j] = all_pair[ts[i]][j];
			}
		}

		for(int i = 1; i < (1 << m); i++){ // O(2^t)
			if(Integer.bitCount(i) == 1){ continue; } // ペア作れないのはNG

			for(int j = 0; j < n; j++){ // O(n)
				DP[i][j] = INF;
				// i と 0 以外の部分集合を列挙して, ペアで行ける場所の最小値を求める..
				for(int k = (i - 1) & i; k > 0; k = (k - 1) & i){
					DP[i][j] = Math.min(DP[i][j], DP[k][j] + DP[i ^ k][j]);
				} // DP[i][駅] は, iのシュタイナー木を作るコストが入る. (足し算してるから)
			}
			// このままだと, 辺を重複してカウントするので, ワーシャルして排除.
			for(int k = 0; k < n; k++) {
				for (int j = 0; j < n; j++) {
					DP[i][j] = Math.min(DP[i][j], DP[i][k] + all_pair[k][j]);
				}
			}
		}

		return DP[(1 << m) - 1][ts[0]];
	}
	//@end

	// 部分集合の列挙の参考: https://topcoder.g.hatena.ne.jp/jackpersel/20100804/1281196966
}
