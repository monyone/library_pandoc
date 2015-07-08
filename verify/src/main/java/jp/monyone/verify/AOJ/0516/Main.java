package jp.monyone.verify.AOJ.0516;

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
		
		while(true){
			final int n = sc.nextInt();
			final int k = sc.nextInt();
			
			if(n == 0 && k == 0){
				break;
			}
			
			BIT bit = new BIT(n);
			for(int i = 0; i < n; i++){
				bit.add(i, sc.nextInt());
			}
			
			int max = Integer.MIN_VALUE;
			for(int start = 0; start < n - k + 1; start++){
				max = Math.max(max, bit.sum(start, start + k));
				//System.out.println("[" + start + " " + (start + k) + ") = " + bit.sum(start, start + k));
			}
		
		System.out.println(max);
		}
	}
}

