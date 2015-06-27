package jp.monyone.library.Mod;

import static jp.monyone.library.Mod.ModPowRec_Include.mod_pow;

public class EnumInvModP_Include {
	//@start
	// inv[1] ... inv[N] にそれぞれの mod P での逆元が入る
	public static void mod_inv(long[] inv, int N, long p){
		inv[1] = 1;
		for(int i = 2; i <= N; i++){
			inv[i] = p - (p / i) * inv[(int)(p % i)] % p;
		}
	}
	//@end
}

