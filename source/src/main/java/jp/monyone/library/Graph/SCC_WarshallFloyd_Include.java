package jp.monyone.library.Graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static jp.monyone.library.Template.LongINF_Include.*;
import static jp.monyone.library.Graph.WarshallFloyd_Include.*;

public class SCC_WarshallFloyd_Include {
	//@start
	// WarshallFloyd して adj[i][j] != INF && adj[j][i] != INF なら同じ成分.
	public static ArrayList<Set<Integer>> StrongConnectedComponents(long[][] adj){
		final int n = adj.length;

		long[][] wf_adj = warshallFloyd(adj);
		ArrayList<Set<Integer>> sets = new ArrayList<Set<Integer>>();
		for(int i = 0; i < n; i++){
			Set<Integer> set = new HashSet<Integer>();
			set.add(i);

			sets.add(set);
		}

		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				if(adj[i][j] != INF && adj[j][i] != INF){
					sets.get(i).add(j);
					sets.get(j).add(i);
				}
			}
		}

		return sets;
	}
	//@end
}
