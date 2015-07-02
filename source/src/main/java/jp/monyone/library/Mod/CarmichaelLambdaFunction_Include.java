package jp.monyone.library.Mod;

import static jp.monyone.library.Math.GCDLCM_Include.lcm;

public class CarmichaelLambdaFunction_Include {
	//@start
	// λ(2^k) = 1 (k == 1), 2 (k == 2), 2^(k - 2) (k >= 3)
	// λ(p^k) = p^(k-1) * (p - 1)
	// λ(p1^k1...pn^kn) = lcm(λ(p1^k1), ... λ(pn^kn))
	public static long carmichaelLambdaFunction(long n) {
		long lambda = 1;
		if(n % 8 == 0){ n /= 2; } // 2^(k-2)になる時は, 先に2で割って奇素数(k-1乗)と同じにする.
		for(int p = 2; p <= n; p++){
			if(n % p == 0){
				long l = p - 1; // (p-1)部分を先に代入しとく. (2^k でも 1 になるので無問題)
				n /= p; while(n % p == 0){ n /= p; l *= p; } // (k-1)乗する.
				lambda = lcm(lambda, l);
			}
		}
		return lambda;
	}
	//@end
}