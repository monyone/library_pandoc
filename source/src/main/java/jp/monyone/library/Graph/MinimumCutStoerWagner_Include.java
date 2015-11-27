package jp.monyone.library.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MinimumCutStoerWagner_Include {
	//@start
	public static long minimumCut(final int n, long[][] adj){
		ArrayList<Integer> uf = new ArrayList<Integer>();
		for(int i = 0; i < n; i++){
			uf.add(i);
		}
		Set<Integer> set = new HashSet<Integer>(), ret = null;

		long cut = Long.MAX_VALUE;
		for(int m = n; m > 1; m--){
			long[] ws = new long[m];
			int u = 0, v = 0;
			long w = 0;

			for(int k = 0; k < m; k++){
				u = v;

				v = 0;
				{
					long tmp_max = ws[0];
					for(int i = 1; i < m; i++){
						if(tmp_max < ws[i]){
							tmp_max = ws[i];
							v = i;
						}
					}
				}

				w = ws[v];
				ws[v] = -1;

				for(int i = 0; i < m; i++){
					if(ws[i] >= 0){
						ws[i] += adj[uf.get(v)][uf.get(i)];
					}
				}
			}

			for(int i = 0; i < m; i++){
				adj[uf.get(i)][uf.get(u)] += adj[uf.get(i)][uf.get(v)];
				adj[uf.get(u)][uf.get(i)] += adj[uf.get(v)][uf.get(i)];
			}

			set.add(uf.get(v));
			if(cut > w){
				cut = w;
				ret = new HashSet<Integer>(set);
			}

			uf.remove(v);
		}

		return cut;
	}
	//@end
}
