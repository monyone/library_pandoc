package jp.monyone.library.DataStructure;

public class RangeMinimumQuerySparseTable_Include {
	//@start
	public static class RMQ {
		long[][] sparse_table; // queryを O(1)にするなら, k をメモ化する事.

		public RMQ(long[] array){ // O(n log n)
			final int depth = Integer.numberOfTrailingZeros(Integer.highestOneBit(array.length));
			sparse_table = new long[depth + 1][]; sparse_table[0] = new long[array.length];
			System.arraycopy(array, 0, sparse_table[0], 0, array.length);

			for(int k = 1; k < sparse_table.length; k++){
				sparse_table[k] = new long[array.length - (1 << k) + 1];
				for(int i = 0; i + (1 << k) <= array.length; i++) { // [i, i + (1 << k))
					// [i, i + (1 << k) -> [i, i + (1 << k) / 2), [i + (1 << k) / 2, i << (k - 1))
					sparse_table[k][i] =
							Math.min(sparse_table[k-1][i], sparse_table[k-1][i + (1 << (k - 1))]);
				}
			}
		}

		public void update(int index, long v){ // index: 0-index
			//update O(n log n) で効率が悪い. SegTreeでどうぞ.
			sparse_table[0][index] = v;
			for(int k = 1; k < sparse_table.length; k++){
				final int begin = Math.max(0, index - (1 << k) + 1);
				final int end = Math.min(sparse_table[k].length, index + (1 << k));
				// 最大で index + [-(1 << k) + 1, (1 << k)) の範囲を更新する必要がある
				for(int i = begin; i < end; i++){
					sparse_table[k][i] =
							Math.min(sparse_table[k - 1][i], sparse_table[k-1][i + (1 << (k - 1))]);
				}
			}
		}

		public long query(int l, int r){ // [l, r) O(log log n)
			final int k = Integer.numberOfTrailingZeros(Integer.highestOneBit(r - l));
			// k -> 区間をオーバーしない最大の2の冪乗
			// left -> [i, i + (1 << k)), right -> [r - (1 << k)] (開区間なので±0)
			return Math.min(sparse_table[k][l], sparse_table[k][r - (1 << k)]);
		}
	}
	//@end
}

