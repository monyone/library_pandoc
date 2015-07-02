package jp.monyone.library.Math;

import org.junit.Test;

import static jp.monyone.library.Math.GCDLCM_Include.gcd;
import static jp.monyone.library.Mod.EulerPhiFunction_Include.eulerPhiFunction;
import static jp.monyone.library.Mod.CarmichaelLambdaFunction_Include.carmichaelLambdaFunction;
import static jp.monyone.library.Mod.ModPowRec_Include.mod_pow;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CarmichaelLambdaFunction_Test {

	@Test public void check_euler_theorem_satisfied(){
		for(long mod = 2; mod < 1000; mod++) {
			for (long a = 2; a < mod; a++) {
				final long gcd = gcd(a, mod);
				if(gcd != 1){ continue; } // 互いに素である事が条件なので除外.

				final long phi = eulerPhiFunction(mod);
				final long lambda = carmichaelLambdaFunction(mod);
				final long mod_pow = mod_pow(a, lambda, mod);

				assertThat(mod_pow, is(1L)); // a^lambda(m) = 1 (m) をチェック
				assertThat(lambda, lessThanOrEqualTo(phi)); // 定義上, lambda <= phi なのでチェック
			}
		}
	}
}

