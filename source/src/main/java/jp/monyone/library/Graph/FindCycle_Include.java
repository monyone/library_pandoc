package jp.monyone.library.Graph;

public class FindCycle_Include {
	//@start
	public static final int unvisited = 0, visiting = 1, visited = 2;

	public static boolean dfs(int node, boolean[][] adj, int[] state){
		state[node] = visiting;

		for(int i = 0; i < adj.length; i++){
			if(!adj[node][i]){ continue; }
			else if(state[i] == unvisited){
				if(!dfs(i, adj, state)){ //
					state[node] = visited; return false;
				}
			}else if(state[i] == visiting){ //cycle!
				return false;
			}
		}

		state[node] = visited;
		return true;
	}

	public static boolean find_cycle(boolean[][] adj){
		int[] state = new int[adj.length];
		for(int i = 0; i < adj.length; i++){
			state[i] = unvisited;
		}
		for(int i = 0; i < adj.length; i++){
			if(state[i] == unvisited){
				if(!dfs(i, adj, state)){
					return false;
				}
			}
		}
		return true;
	}
	//@end
}
