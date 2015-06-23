package jp.monyone.library.DataStructure;

public class BIT_Include{
	//@start
	public static class BIT {
		int[] dat;
		
		public BIT(int n){
			dat = new int[n + 1];
		}
		
		public void add(int k, int a){ // k : 0-indexed
			for(int i = k + 1; i < dat.length; i += i & -i){
				dat[i] += a;	
			}
		}
		
		public int sum(int s, int t){ // [s, t)
			if(s > 0) return sum(0, t) - sum(0, s);
			
			int ret = 0;
			for(int i = t; i > 0; i -= i & -i) {
				ret += dat[i];
			}
			return ret;
		}
		
		public int get(int k){ // k : 0-indexed
			int p = Integer.highestOneBit(dat.length - 1);
			for(int q = p; q > 0; q >>= 1, p |= q){
				if( p >= dat.length || k < dat[p]) p ^= q;
				else k -= dat[p];
			}
			return p;
		}
	}
	//@end
}

