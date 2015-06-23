package jp.monyone.library.DataStructure;

public class WeightedUnionFind_Include{
	//@start
	public static class WeightedUnionFind{
		int[] par; // 親の番号
		int[] ws;  // 親との重みの差
		
		public WeightedUnionFind(int n){
			par = new int[n];
			ws  = new int[n];
			for(int i = 0; i < n; i++){ par[i] = -1; }
		}
		
		public int find(int x){
			if(par[x] < 0){
				return x;
			}else{
				final int parent = find(par[x]);
				ws[x] += ws[par[x]];
				return par[x] = parent;
			}
		}
		
		public int weight(int x){
			find(x); 
			return ws[x];
		}
		
		public boolean union(int x, int y, int w){ // x <-(w)- y (x + w = y)
			w += weight(x); 
			w -= weight(y);
			x = find(x); y = find(y);
			
			if(x != y){
				if(par[y] < par[x]) {  // 多い方が根になるようにスワップする.
					int tmp = x; x = y; y = tmp; w = -w;
				}
				par[x] += par[y]; par[y] = x;
				ws[y] = w;
				return true;
			}else{
				return false;
			}
		}
		
		public boolean same(int x, int y){
			return find(x) == find(y);
		}
		
		public Integer diff(int x, int y){ // x - y を求める. 比較不能ならnull.
			if(!same(x, y)){ return null; }
			return this.weight(x) - this.weight(y);
		}
		// size()はUnionFindと同じなので省略.
	}
	//@end
}

