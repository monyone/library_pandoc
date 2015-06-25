package jp.monyone.library.Puzzle;

public class Nim_Include {
	//@start
	// 0枚で終わると負け(最後の一枚を取った人が勝ちの場合)
	public static boolean Nim(long ... nim) {
		long ret = 0;
		for(final long n : nim){ ret ^= n; }

		return ret != 0;
	}
	//@end
}
