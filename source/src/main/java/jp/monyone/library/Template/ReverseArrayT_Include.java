package jp.monyone.library.Template;

import static jp.monyone.library.Template.SwapArrayT_Include.swap;

public class ReverseArrayT_Include {
	//@start
	public static <T> void reverse(T[] array, int begin, int end){ // [begin, end)
		for(int i = begin, j = end - 1; i < j; i++, j--) { swap(array, i, j); }
	}
	//@end
}
