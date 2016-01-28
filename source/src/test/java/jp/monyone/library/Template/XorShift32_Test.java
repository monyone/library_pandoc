package jp.monyone.library.Template;

import org.junit.Test;

import static jp.monyone.library.Template.XorShift32_Include.Random;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class XorShift32_Test {

	/* monobit テスト. FIPS PUS 140-2 に従って seed = 1 の時 : 20000 bit の 1 bit出現数をチェック
	*  本当は seed 固定したくないけど ランダムにビルドが落ちると困る. 標準ライブラリよりマシと言えればいい */
	@Test public void monobit_test_only_LSB(){
		Random random = new Random(1);

		final long result = random.ints(2).limit(20000).filter(i -> i == 1).count();
		assertThat(result, allOf(greaterThan(9725l), lessThan(10275l)));
	}

	@Test public void monobit_test_only_MSB(){
		Random random = new Random(1);

		final long result = random.ints().map(i -> (i >> 30)).limit(20000).filter(i -> i == 1).count();
		assertThat(result, allOf(greaterThan(9725l), lessThan(10275l)));
	}

	@Test public void monobit_test_all() {
		Random random = new Random(1);

		final long result = random.ints()
				.flatMap(i -> String.format("%31s", Integer.toBinaryString(i)).replace(" ", "0").chars())
				.limit(20000).filter(i -> i == '1').count();
		assertThat(result, allOf(greaterThan(9725l), lessThan(10275l)));
	}
}

