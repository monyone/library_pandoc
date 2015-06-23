package jp.monyone.library.Mod;

public class ModPowLoop_Include{
	//@start
	public static long mod_pow(long a, long e, long m){
		long ret = 1;
		for(; e > 0; e /= 2){
			if (e % 2 != 0) ret = (ret * a) % m;
			a = (a * a) % m;
		}
		return ret;
	}
	//@end
}

