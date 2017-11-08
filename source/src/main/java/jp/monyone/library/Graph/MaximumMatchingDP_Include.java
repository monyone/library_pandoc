package jp.monyone.library.Graph;

import static jp.monyone.library.Template.IntINF_Include.INF;

public class MaximumMatchingDP_Include {
	// n x n のグラフの最大マッチング(重み最小) を求める
	//@start
	public static int solve_DP(int[][] adj){
		final int n = adj.length;

		int[] DP = new int[1 << n];
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
	//@end
}
