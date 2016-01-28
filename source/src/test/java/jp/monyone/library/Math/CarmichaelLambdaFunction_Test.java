package jp.monyone.library.Math;

import org.junit.Test;

import static jp.monyone.library.Math.GCDLCM_Include.gcd;
import static jp.monyone.library.Mod.EulerPhiFunction_Include.eulerPhiFunction;
import static jp.monyone.library.Mod.CarmichaelLambdaFunction_Include.carmichaelLambdaFunction;
import static jp.monyone.library.Mod.ModPowRec_Include.mod_pow;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CarmichaelLambdaFunction_Test {

	// 上限(bound)の範囲内で互いに素な a, n (a < n) に対してカーマイケルのλ関数が
	// a^lambda(n) = 1 (mod n) かつ lambda(n) がオイラーのφ関数以下の値を出す事を確認するテスト
	public void bounded_exhaustive_testing_for_carmichael_theorem(final int bound){
		for(long n = 2; n <= bound; n++) {
			for (long a = 2; a < n; a++) {
				final long gcd = gcd(a, n);
				if(gcd != 1){ continue; } // 互いに素である事が条件なので除外.

				final long phi = eulerPhiFunction(n); // オイラーのφ関数
				final long lambda = carmichaelLambdaFunction(n); // カーマイケルのλ関数

				final long applied_phi_mod    = mod_pow(a, phi, n);
				final long applied_lambda_mod = mod_pow(a, lambda, n);

				assertThat(applied_phi_mod, is(1L));    // a^phi(n) = 1 (mod n) をチェック
				assertThat(applied_lambda_mod, is(1L)); // a^lambda(n) = 1 (mod n) をチェック
				assertThat(lambda, lessThanOrEqualTo(phi)); // 定義上, lambda <= phi なのでチェック
			}
		}
	}

	@Test public void check_carmichael_theorem_satisfied_use_BET(){
		// 計算量の観点から 1000 までの互いに素な値をしらみつぶしにする
		bounded_exhaustive_testing_for_carmichael_theorem(1000);
	}
}

