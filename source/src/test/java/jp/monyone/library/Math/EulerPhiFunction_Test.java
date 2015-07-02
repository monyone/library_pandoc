package jp.monyone.library.Math;

import static jp.monyone.library.Math.GCDLCM_Include.gcd;
import static jp.monyone.library.Mod.EulerPhiFunction_Include.eulerPhiFunction;
import static jp.monyone.library.Mod.ModPowRec_Include.mod_pow;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class EulerPhiFunction_Test {

	@Test public void check_euler_theorem_satisfied(){
		for(long mod = 2; mod < 1000; mod++) {
			for (long a = 2; a < mod; a++) {
				final long gcd = gcd(a, mod);
				if(gcd != 1){ continue; } // 互いに素である事が条件なので除外.

				final long phi = eulerPhiFunction(mod);
				final long mod_pow = mod_pow(a, phi, mod);

				assertThat(mod_pow, is(1L)); // a^phi(m) = 1 (m) をチェック
			}
		}
	}
}

