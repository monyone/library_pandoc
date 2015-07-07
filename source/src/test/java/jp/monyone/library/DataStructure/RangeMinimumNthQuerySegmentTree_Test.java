package jp.monyone.library.DataStructure;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;

import static jp.monyone.library.DataStructure.RangeMinimumNthQuerySegmentTree_Include.RMNthQ;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RangeMinimumNthQuerySegmentTree_Test {

	@Test public void test_compare_sorted_array(){
		long[] array = new long[100];
		for(int i = 0; i < array.length; i++){
			array[i] = i * i;
		}

		RMNthQ nth = new RMNthQ(array.length);
		nth.init(array);

		for(int begin = 0; begin < array.length; begin++){
			for(int end = begin + 1; end <= array.length; end++){
				for(int kth = 1; kth <= (end - begin); kth++) {
					// ソート済みの配列に関しては, [begin, end) の nth minimun は, begin + nth - 1 である.
					assertThat(nth.query(begin, end, kth), is(array[begin + (kth - 1)]));
				}
			}
		}
	}

	@Test public void test_compare_actually_sort_in_range(){
		long[] array = new long[100];
		Random rnd = new Random(0); // seed固定
		for(int i = 0; i < array.length; i++){
			array[i] = rnd.nextLong() / 2; // nth の方の二分探索で死ぬから.
		}

		RMNthQ nth = new RMNthQ(array.length);
		nth.init(array);

		for(int begin = 0; begin < array.length; begin++){
			for(int end = begin + 1; end <= array.length; end++){
				long[] sub_array = new long[(end - begin)];
				System.arraycopy(array, begin, sub_array, 0, (end - begin));
				Arrays.sort(sub_array);

				for(int kth = 1; kth <= (end - begin); kth++){
					assertThat(nth.query(begin, end, kth), is(sub_array[kth - 1]));
				}
			}
		}
	}

	@Test public void POJ2104_Sample(){
		// K-th number
		long[] array = {1,5,2,6,3,7,4};
		int[][] query ={ //l, r, kth, answer
			{2, 5, 3, 5},
			{4, 4, 1, 6},
			{1, 7, 3, 3}
		};

		RMNthQ nth = new RMNthQ(array.length);
		nth.init(array);

		for(final int[] q : query) {
			// convert [] -> [)
			assertThat(nth.query(q[0] - 1, q[1], q[2]), is((long)(q[3])));
		}
	}
}

