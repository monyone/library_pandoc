package jp.monyone.library.Mod;

import static jp.monyone.library.Mod.ModPowRec_Include.mod_pow;

public class InvModM_Include {
	//@start
	// a and m must be co-prime.
	public static long mod_inv(long a, long m){
		return (a == 1 ? 1 : (1 - m*mod_inv(m%a, a)) / a + m);
	}
	//@end
}

