package jp.monyone.verify.AOJ.DSL2B;

import java.util.Scanner;

public class Main {
	
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
		
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		
		final int n = sc.nextInt();
		final int q = sc.nextInt();

		BIT bit = new BIT(n);
		for(int tt = 0; tt < q; tt++){
			final int op = sc.nextInt();
			final int x  = sc.nextInt() - 1;
			final int y  = sc.nextInt();

			if(op == 0){
				bit.add(x, y);
			}else{
				System.out.println(bit.sum(x, y));
			}
		}
	}
}

