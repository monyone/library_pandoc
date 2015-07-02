package jp.monyone.library.Template;

public class SwapArrayT_Include {
	//@start
	public static <T> void swap(T[] array, int fst, int snd){
		T tmp = array[fst];
		array[fst] = array[snd];
		array[snd] = tmp;
	}
	//@end
}
