package jp.monyone.library.Math;

import static jp.monyone.library.Math.GCDLCM_Include.gcd;
import static jp.monyone.library.Mod.EulerPhiFunction_Include.eulerPhiFunction;
import static jp.monyone.library.Mod.ModPowRec_Include.mod_pow;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EulerPhiFunction_Test {

	// 上限(bound)の範囲内で互いに素な a, n (a < n) に対して a^phi(n) = 1 (mod n) が成り立つかテスト
	public void bounded_exhaustive_testing_for_euler_theorem(final int bound){
		for(long n = 2; n <= bound; n++){
			for(long a = 2; a < n; a++){
				final long gcd = gcd(a, n);
				// オイラーの定理は互いに素である事が必要条件なので除外.
				if(gcd != 1){ continue; }

				final long applied_mod = mod_pow(a, eulerPhiFunction(n), n);

				assertThat(applied_mod, is(1L)); // 定理通りに a^phi(n) = 1 (mod n) が成り立つか
			}
		}
	}

	@Test public void check_euler_theorem_satisfied_use_BET(){
		// 計算量の観点から 1000 までの互いに素な値をしらみつぶしにする
		bounded_exhaustive_testing_for_euler_theorem(1000);
	}
}

