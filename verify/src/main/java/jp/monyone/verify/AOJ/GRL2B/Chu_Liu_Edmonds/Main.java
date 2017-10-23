package jp.monyone.verify.AOJ.GRL2B.Chun_Liu_Edmonds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Map;

import java.util.Scanner;

public class Main {
	
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
		
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		final int V = sc.nextInt();
		final int E = sc.nextInt();
		final int S = sc.nextInt();		
		
		ArrayList<Map<Integer, Long>> fwd_adj = new ArrayList<Map<Integer, Long>>();
		ArrayList<Map<Integer, Long>> rev_adj = new ArrayList<Map<Integer, Long>>();
		for (int i = 0; i < V; i++) {
			fwd_adj.add(new HashMap<Integer, Long>());
		}
		for (int i = 0; i < V; i++) {
			rev_adj.add(new HashMap<Integer, Long>());
		}
		
		for (int i = 0; i < E; i++) {
			final int s = sc.nextInt();
			final int t = sc.nextInt();
			final long w = sc.nextLong();

			if (!fwd_adj.get(s).containsKey(t)) {
				fwd_adj.get(s).put(t, w);
			} else {
				fwd_adj.get(s).put(t, Math.min(w, fwd_adj.get(s).get(t)));
			}

			if (!rev_adj.get(t).containsKey(s)) {
				rev_adj.get(t).put(s, w);
			} else {
				rev_adj.get(t).put(s, Math.min(w, rev_adj.get(t).get(s)));
			}
		}

		System.out.println(Chu_Liu_Edmonds(V, S, rev_adj));
	}
}
