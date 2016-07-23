package jp.monyone.library.Mod;

import static jp.monyone.library.Math.GCDLCM_Include.gcd;

public class ChineseRemainder_CoPrimeSequence_Include {

	//@start
	// O(N^2) より上で適当な、中国剰余定理で互いに素にする関数
	public static boolean make_coprime_sequence(long[] as, long[] ms){
		while(true) {
			boolean updated = false;

			for (int fst = 0; fst < ms.length; fst++) {
				for (int snd = fst + 1; snd < ms.length; snd++) {
					long gcd = gcd(ms[fst], ms[snd]);
					if (gcd == 1) { continue; }

					updated = true;

					if (as[fst] % gcd != as[snd] % gcd) { return false; }

					ms[fst] /= gcd;
					ms[snd] /= gcd;
					while (true) {
						long gt = gcd(ms[fst], gcd);
						if (gt == 1) { break; }

						ms[fst] *= gt;
						gcd /= gt;
					}
					ms[snd] *= gcd;
					as[fst] %= ms[fst];
					as[snd] %= ms[snd];
				}
			}

			if(!updated){ break; }
		}

		return true;
	}
	//@end

	//参考: http://techtipshoge.blogspot.jp/2015/02/blog-post_15.html
}
