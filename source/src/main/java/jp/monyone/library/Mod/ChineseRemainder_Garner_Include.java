package jp.monyone.library.Mod;

import static jp.monyone.library.Mod.InvModM_Include.mod_inv;

public class ChineseRemainder_Garner_Include {
	//@start
	public static long chinese_remainder(long[] as, long[] ms){
		long prod = 1, sum = 0;
		long v = 0;

		for(int i = 0; i < ms.length; i++){
			long sub = as[i] - (sum % ms[i]);
			if(sub < 0){ sub += ms[i]; }

			v = (sub * mod_inv(prod, ms[i])) % ms[i];

			sum += (v * prod);
			prod *= ms[i];
		}

		return sum;
	}
	//@end

	// 参考: http://pekempey.hatenablog.com/entry/2015/11/07/210000
}

