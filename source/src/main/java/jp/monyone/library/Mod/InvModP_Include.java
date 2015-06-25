package jp.monyone.library.Mod;

import static jp.monyone.library.Mod.ModPowRec_Include.mod_pow;

public class InvModP_Include {
	//@start
	public static long mod_inv(long a, long p){
		return mod_pow(a, p - 2, p);
	}
	//@end
}

