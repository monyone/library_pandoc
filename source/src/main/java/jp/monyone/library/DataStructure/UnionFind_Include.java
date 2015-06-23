package jp.monyone.library.DataStructure;

public class UnionFind_Include{
	//@start
	public static class UnionFind{
		int[] par; //
		
		public UnionFind(int n){
			par = new int[n];
			for(int i = 0; i < n; i++){ par[i] = -1; }
		}
		
		public int find(int x){
			if(par[x] < 0){
				return x;
			}else{
				return par[x] = find(par[x]);
			}
		}
		
		public boolean union(int x, int y){
			x = find(x);
			y = find(y);
			
			if(x != y){
				if(par[y] < par[x]) {  // 多い方が根になるようにスワップする.
					int tmp = x; x = y; y = tmp;
				}
				par[x] += par[y];
				par[y] = x;
				return true;
			}else{
				return false;
			}
		}
		
		public boolean same(int x, int y){
			return find(x) == find(y);
		}
		
		public int size(int x){
			return -par[find(x)];
		}
	}
	//@end
}

