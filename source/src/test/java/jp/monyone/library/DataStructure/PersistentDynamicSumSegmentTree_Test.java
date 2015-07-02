package jp.monyone.library.DataStructure;

import static jp.monyone.library.DataStructure.PersistentDynamicSumSegmentTree_Include.SegTree;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.*;

public class PersistentDynamicSumSegmentTree_Test {

	@Test public void up_query_test(){
		long[] test_array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		SegTree segtree = new SegTree(Long.MAX_VALUE / 2);

		long partial_sum = 0;
		assertThat(segtree.query(0, 0), is(partial_sum));

		for(int i = 0; i < test_array.length; i++) {
			segtree = segtree.update(i, test_array[i]);
			partial_sum += test_array[i];

			assertThat(segtree.query(0, i + 1), is(partial_sum));
		}
	}

	@Test public void down_query_test(){
		long[] test_array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		SegTree segtree = new SegTree(Long.MAX_VALUE / 2);

		long partial_sum = 0;
		assertThat(segtree.query(test_array.length - 1, test_array.length), is(partial_sum));

		for(int i = test_array.length - 1; i >= 0; i--) {
			segtree = segtree.update(i, test_array[i]);
			partial_sum += test_array[i];

			assertThat(segtree.query(i, test_array.length), is(partial_sum));
		}
	}
}

