package jp.monyone.library.Mod;

public class EulerPhiFunction_Include {
	//@start
	// φ(n) = n (1 - 1/p1) ... (1 - 1/pm) (pkはnの素因数) より
	// 初項から一つづつ展開する -> n -= n / pk を行う事になる.
	public static long eulerPhiFunction(long n) {
		long phi = n;
		for(long p = 2; p * p <= n; p++){
			if(n % p == 0){
				phi -= phi / p;
				while(n % p == 0){ n /= p; }
			}
		}
		if(n > 1){ phi -= phi / n; }

		return phi;
	}
	//@end
}