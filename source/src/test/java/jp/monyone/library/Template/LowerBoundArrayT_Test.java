package jp.monyone.library.Template;

import org.junit.Test;

import static jp.monyone.library.Template.LowerBoundArrayT_Include.lower_bound;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LowerBoundArrayT_Test {

	@Test public void basic_usage() {
		String[] array = {"a", "a", "b", "b", "c"};

		assertThat(lower_bound(array, "-"), is(0));
		// それより上の "a" に切り上げされる.
		assertThat(lower_bound(array, "a"), is(0));
		// 最初の"a" である 0 を指す
		assertThat(lower_bound(array, "b"), is(2));
		// 最初の"b" である 2 を指す
		assertThat(lower_bound(array, "c"), is(4));
		// 最初の"c" である 4 を指す
		assertThat(lower_bound(array, "z"), is(5));
		// それより上の要素がないため, 配列外の5を返す.
	}

	@Test public void test_empty_elements(){
		String[] array = {"b", "b", "d", "d", "d", "g", "g", "i"};

		assertThat(lower_bound(array, "a"), is(0)); // edge case
		assertThat(lower_bound(array, "b"), is(0)); // index : 0
		assertThat(lower_bound(array, "c"), is(2)); // round up to "c"
		assertThat(lower_bound(array, "d"), is(2)); // index : 2
		assertThat(lower_bound(array, "e"), is(5)); // round up to "g"
		assertThat(lower_bound(array, "f"), is(5)); // round up to "g"
		assertThat(lower_bound(array, "g"), is(5)); // index : 5
		assertThat(lower_bound(array, "h"), is(7)); // round up to "i"
		assertThat(lower_bound(array, "i"), is(7)); // index : 7
		assertThat(lower_bound(array, "j"), is(8)); // edge case
	}
}

