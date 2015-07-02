package jp.monyone.library.Math;

import static jp.monyone.library.Template.SwapArrayT_Include.swap;
import static jp.monyone.library.Template.ReverseArrayT_Include.reverse;

public class NextPermutation_Include {
	//@start
	// 任意の Comparable<T> を実装したクラスに対する next_permutation
	public static <T extends Comparable<? super T>> boolean next_permutation(T ... perm) {
		for(int i = perm.length - 1; i > 0; i--){
			if(perm[i - 1].compareTo(perm[i]) < 0){
				int j = perm.length;
				while(perm[i - 1].compareTo(perm[--j]) >= 0);

				swap(perm, i - 1, j);
				reverse(perm, i, perm.length); // [i, n)

				return true;
			}
		}

		reverse(perm, 0, perm.length); // [0, n)
		return false;
	}
	//@end
}
