package jp.monyone.library.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Chu_Liu_Edmonds_Include {
	//@start
	// root から有向最小全域木のコストを求める
	public static long Chu_Liu_Edmonds(final int V, int root, ArrayList<Map<Integer, Long>> rev_adj) {
		// 各頂点に入る最小の辺をそれぞれ求める
		long[] min_costs = new long[V];
		int[] min_pars = new int[V];
		min_costs[root] = 0;
		min_pars[root] = root;

		for (int start = 0; start < V; start++) {
			if (start == root) { continue; }
			long min_cost = -1;
			int min_par = -1;

			for (final Map.Entry<Integer, Long> entry : rev_adj.get(start).entrySet()) {
				final int par = entry.getKey();
				final long cost = entry.getValue();

				if (min_cost < 0 || min_cost > cost) {
					min_par = par;
					min_cost = cost;
				}
			}

			min_pars[start] = min_par;
			min_costs[start] = min_cost;
		}

		// 閉路を 1 個検出する
		boolean has_cycle = false;
		int[] belongs = new int[V];
		for(int i = 0; i < V; i++){ belongs[i] = i; }

		boolean[] is_cycle = new boolean[V];

		for (int start = 0; start < V; start++) {
			if(min_pars[start] < 0){ continue; }

			boolean[] used = new boolean[V];
			int curr = start;
			while (!used[curr] && min_pars[curr] != curr) {
				used[curr] = true;
				curr = min_pars[curr];
			}

			// 閉路が出来ている
			if (curr != root) {
				belongs[curr] = V;
				is_cycle[curr] = true;

				int cycle_start = curr;
				while (min_pars[curr] != cycle_start) {
					belongs[min_pars[curr]] = V;
					is_cycle[min_pars[curr]] = true;
					curr = min_pars[curr];
				}

				has_cycle = true;

				break;
			}else{
				// 閉路と関係ない
			}
		}

		if(!has_cycle){
			long sum = 0;
			for(int i = 0; i < V; i++){
				sum += Math.max(0, min_costs[i]);
			}

			return sum;
		}


		long cycle_sum = 0;
		for(int i = 0; i < V; i++){
			if(!is_cycle[i]){ continue; }
			cycle_sum += min_costs[i];
		}

		ArrayList<Map<Integer, Long>> next_rev_adj = new ArrayList<Map<Integer, Long>>();
		for(int i = 0; i <= V; i++){
			next_rev_adj.add(new HashMap<Integer, Long>());
		}
		for(int start = 0; start < V; start++){
			final int start_belongs = belongs[start];
			for(final Entry<Integer, Long> entry : rev_adj.get(start).entrySet()){
				final int prev = entry.getKey();
				final int prev_belongs = belongs[prev];


				if(start_belongs == prev_belongs){ continue; }
				else if(is_cycle[start]){
					if(!next_rev_adj.get(start_belongs).containsKey(prev_belongs)){
						next_rev_adj.get(start_belongs).put(prev_belongs, rev_adj.get(start).get(prev) - min_costs[start]);
					}else{
						final long old_cost = next_rev_adj.get(start_belongs).get(prev_belongs);
						next_rev_adj.get(start_belongs).put(prev_belongs, Math.min(old_cost, rev_adj.get(start).get(prev) - min_costs[start]));
					}
				}else{
					if(!next_rev_adj.get(start_belongs).containsKey(prev_belongs)){
						next_rev_adj.get(start_belongs).put(prev_belongs, rev_adj.get(start).get(prev));
					}else{
						final long old_cost = next_rev_adj.get(start_belongs).get(prev_belongs);
						next_rev_adj.get(start_belongs).put(prev_belongs, Math.min(old_cost, rev_adj.get(start).get(prev)));
					}
				}
			}
		}

		return cycle_sum + Chu_Liu_Edmonds(V + 1, root, next_rev_adj);
	}
	//@end
}
