package jp.monyone.library.DataStructure;

import org.junit.Test;

import java.util.Random;

import static jp.monyone.library.DataStructure.RangeMinimumQuerySparseTableBitLookupTable_Include.RMQ;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RangeMinimumQuerySparseTableBitLookupTable_Test {

	@Test public void one_element_test(){
		long[] value = {114514};
		RMQ rmq = new RMQ(value);

		assertThat(rmq.query(0, 1), is(114514L));
	}

	@Test public void two_element_distinct_test(){
		long[] value = {114, 514};
		RMQ rmq = new RMQ(value);

		assertThat(rmq.query(0, 1), is(114L));
		assertThat(rmq.query(1, 2), is(514L));
		assertThat(rmq.query(0, 2), is(114L));
	}

	@Test public void two_element_distinct_reverse_test(){
		long[] value = {514, 114};
		RMQ rmq = new RMQ(value);

		assertThat(rmq.query(0, 1), is(514L));
		assertThat(rmq.query(1, 2), is(114L));
		assertThat(rmq.query(0, 2), is(114L));
	}

	// O(n) naive RMQ
	private static long naive_solution(long[] array, int l, int r){ // [l, r)
		long min = Long.MAX_VALUE;
		for(int i = l; i < r; i++){
			min = Math.min(min, array[i]);
		}
		return min;
	}

	@Test public void compare_naive_solution_only_query_random(){
		long[] array = new long[1000];
		Random random = new Random(1); // fix seed
		for(int i = 0; i < array.length; i++){
			array[i] = random.nextLong();
		}
		RMQ rmq = new RMQ(array);

		for(int i = 0; i < array.length; i++){
			for(int j = i + 1; j <= array.length; j++){
				assertThat(rmq.query(i, j), is(naive_solution(array, i, j)));
			}
		}
	}

	@Test public void compare_naive_solution_update_random(){
		long[] array = new long[300];
		Random random = new Random(2); // fix seed
		for(int i = 0; i < array.length; i++){
			array[i] = random.nextLong();
		}
		RMQ rmq = new RMQ(array);

		for(int index = 0; index < array.length; index++) {
			for (int i = 0; i < array.length; i++) {
				for (int j = i + 1; j <= array.length; j++) {
					assertThat(rmq.query(i, j), is(naive_solution(array, i, j)));
				}
			}
			final long next = random.nextLong();
			array[index] = next; rmq.update(index, next);
		}
	}
}

