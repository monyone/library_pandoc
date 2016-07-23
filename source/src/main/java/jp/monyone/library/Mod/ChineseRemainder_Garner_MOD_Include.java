package jp.monyone.library.Mod;

import static jp.monyone.library.Mod.InvModM_Include.mod_inv;

public class ChineseRemainder_Garner_MOD_Include {
	//@start
	// O(N^2). ms[i] は互いに素でなくてはならない.
	// Π(ms[i]) がオーバーフローする場合でも, 最後に適切に MOD を取れば OK
	public static long chinese_remainder(long[] as, long[] ms, long MOD){
		long[] vs = new long[ms.length];
		long[] sums = new long[ms.length];
		long[] invs = new long[ms.length];

		for(int i = 0; i < ms.length; i++){
			invs[i] = 1; // mod(ms[i]) の中で 1 / (ms[0] * ms[1] ... ms[i - 1]) を求める
			sums[i] = as[i]; // mod(ms[i]) の中で, a[i] - v[0] - v[1]ms[0] ... を求める

			long mult = 1; // mod(ms[i]) の中で, ms[0] * ... * ms[i - 2] を求める
			for(int j = 0; j < i; j++){
				sums[i] -= (vs[j] * mult) % ms[i];
				if(sums[i] < 0){ sums[i] += ms[i]; }

				invs[i] = (invs[i] * mod_inv(ms[j], ms[i])) % ms[i];
				mult = (mult * ms[j]) % ms[i];
			}

			vs[i] = (sums[i] * invs[i]) % ms[i];
		}

		// MOD を取る前に x = 0 かどうかを知る場合, ここで ∀i.(vs[i] == 0) チェック
		{
			boolean flg = true;
			for (final long v : vs) {
				if (v != 0) {
					flg = false;
					break;
				}
			}
			if (flg) { // ここに来るなら 全ての条件を x = 0 で満たす.
				return 0; // 正整数が欲しいなら、Π(ms[i]) の MOD が答え
			}
		}

		long ret = 0;
		for(int i = 0; i < ms.length; i++){
			long mult = 1;
			for(int j = 0; j < i; j++){
				mult *= ms[j]; mult %= MOD; // ここも MOD
			}

			ret += (vs[i] * mult) % MOD; ret %= MOD; // ここも MOD
		}

		return ret;
	}
	//@end

	// 参考: http://pekempey.hatenablog.com/entry/2015/11/07/210000
}

