package jp.monyone.library.Mod;

public class EnumEulerPhiFunction_Include {
	//@start
	//
	public static long[] eulerPhiFunction(int n) { // [0, n]
		boolean[] is_prime = new boolean[n + 1];
		long[] phis = new long[n + 1];
		for(int i = 0; i <= n; i++){
			is_prime[i] = true;
			phis[i] = i;
		}
		is_prime[0] = is_prime[1] = false;

		for(int i = 2; i <= n; i++){
			if(is_prime[i]){
				phis[i] -= phis[i] / i;
				for(int j = i * 2; j <= n; j += i){
					is_prime[j] = false;    
					phis[j] -= phis[j] / i;
				}
			}
		}

		return phis;
	}
	//@end
}