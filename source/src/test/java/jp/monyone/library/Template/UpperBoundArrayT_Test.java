package jp.monyone.library.Template;

import org.junit.Test;

import static jp.monyone.library.Template.UpperBoundArrayT_Include.upper_bound;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UpperBoundArrayT_Test {

	@Test public void basic_usage() {
		String[] array = {"a", "a", "b", "b", "c"};

		assertThat(upper_bound(array, "-"), is(0));
		// それより上で最初にある "a" が出てくる 0 を指す
		assertThat(upper_bound(array, "a"), is(2));
		// 最後の "a" より上である "b" が初めて出る 2 を指す
		assertThat(upper_bound(array, "b"), is(4));
		// 最後の "b" より上である "c" が初めて出る 4 を指す
		assertThat(upper_bound(array, "c"), is(5));
		// 最後の"c" より上の要素がないため, 配列外の 5 を指す.
		assertThat(upper_bound(array, "z"), is(5));
		// それより上の要素がないため, 配列外の5を返す.
	}

	@Test public void test_empty_elements(){
		String[] array = {"b", "b", "d", "d", "d", "g", "g", "i"};

		assertThat(upper_bound(array, "a"), is(0)); // edge case
		assertThat(upper_bound(array, "b"), is(2)); // index : 2
		assertThat(upper_bound(array, "c"), is(2)); // round down to "b"
		assertThat(upper_bound(array, "d"), is(5)); // index : 5
		assertThat(upper_bound(array, "e"), is(5)); // round down to "d"
		assertThat(upper_bound(array, "f"), is(5)); // round down to "d"
		assertThat(upper_bound(array, "g"), is(7)); // index : 7
		assertThat(upper_bound(array, "h"), is(7)); // round down to "h"
		assertThat(upper_bound(array, "i"), is(8)); //
		assertThat(upper_bound(array, "j"), is(8)); // edge case
	}
}

