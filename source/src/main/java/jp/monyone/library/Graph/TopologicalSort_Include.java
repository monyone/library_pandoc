package jp.monyone.library.Graph;

import java.util.LinkedList;

public class TopologicalSort_Include {
	//@start
	public static final int unvisited = 0, visiting = 1, visited = 2;

	public static boolean dfs(int node, boolean[][] adj, int[] state, LinkedList<Integer> list){
		state[node] = visiting;

		for(int i = 0; i < adj.length; i++){
			if(!adj[node][i]){ continue; }
			else if(state[i] == unvisited){
				if(!dfs(i, adj, state, list)){ //
					state[node] = visited; return false;
				}
			}else if(state[i] == visiting){ //cycle!
				return false;
			}
		}

		state[node] = visited;
		list.addFirst(node);
		return true;
	}

	public static boolean topological_sort(boolean[][] adj, LinkedList<Integer> list){
		int[] state = new int[adj.length];
		for(int i = 0; i < adj.length; i++){
			state[i] = unvisited;
		}
		for(int i = 0; i < adj.length; i++){
			if(state[i] == unvisited){
				if(!dfs(i, adj, state, list)){
					return false;
				}
			}
		}
		return true;
	}
	//@end
}
