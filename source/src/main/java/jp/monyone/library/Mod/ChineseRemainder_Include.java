package jp.monyone.library.Mod;

import static jp.monyone.library.Mod.InvModM_Include.mod_inv;

public class ChineseRemainder_Include {
	//@start
	//Gauss の方法で求める. 返り値の値の mod を取らないので O(N) くらい
	public static long chinese_remainder(long[] as, long[] ms){
		long prod = 1;
		for(long m : ms){ prod *= m; }

		long ret = 0;
		for(int i = 0; i < ms.length; i++){
			final long M = prod / ms[i];
			final long inv = mod_inv(M % ms[i], ms[i]);

			long a = as[i] - as[i] / prod * prod;
			if(a < 0){ a += prod; }

			ret = (ret + M * inv * a % prod) % prod;
		}

		return ret;
	}
	//@end
}

