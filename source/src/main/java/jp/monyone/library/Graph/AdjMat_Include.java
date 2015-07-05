package jp.monyone.library.Graph;

public class AdjMat_Include {
	//@start
	public static final long INF = Long.MAX_VALUE / 2 - 1;

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
