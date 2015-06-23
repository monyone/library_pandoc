package jp.monyone.library.Mod;

public class ModPowRec_Include {
	//@start
	public static long mod_pow(long a, long e, long m){
		if(e == 0){
			return 1;
		}else if(e % 2 == 0){
			long ret = mod_pow(a, e / 2, m);
			return (ret * ret) % m;
		}else{
			return (mod_pow(a, e - 1, m) * a) % m;
		}
	}
	//@end
}

