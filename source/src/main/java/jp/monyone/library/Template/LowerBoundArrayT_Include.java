package jp.monyone.library.Template;

public class LowerBoundArrayT_Include {
	//@start
	public static <T extends Comparable<? super T>> int lower_bound(T[] array, T key){
		int lower = -1, upper = array.length;
		// keep array[lower] < key && key <= array[upper]
		while(upper - lower > 1){
			final int mid = (lower + upper) / 2;
			final int comp = array[mid].compareTo(key);

			if(comp < 0){ lower = mid; }
			else if(comp >= 0){ upper = mid;}
		}

		return upper;
	}
	//@end
}
