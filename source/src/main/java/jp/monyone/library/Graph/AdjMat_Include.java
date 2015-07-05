package jp.monyone.library.Graph;

import static jp.monyone.library.Template.LongINF_Include.INF;

public class AdjMat_Include {
	//@start
	// INF という値は適当に大きい値を突っ込んでおく.
	// 二つ足しても平気な Long.MAX_VALUE / 2 - 1 を良く使う.
	public static long[][] init_adj(final int n){
		long[][] ret = new long[n][n];
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				ret[i][j] = i == j ? 0 : INF;
			}
		}
		return ret;
	}
	//@end
}
