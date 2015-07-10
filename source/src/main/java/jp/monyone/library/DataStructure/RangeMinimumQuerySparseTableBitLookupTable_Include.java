package jp.monyone.library.DataStructure;

import java.util.LinkedList;

public class RangeMinimumQuerySparseTableBitLookupTable_Include {
	//@start
	public static class RMQ {
		int b_size;
		long[] data;

		long[][] block_table; // block用 sparse table
		// RMQ block_table みたいに Sparse Stable を別個に定義した方が楽.
		int[] in_block_bits; // ブロック内に自分より低い要素がどれくらいあるか?

		public RMQ(long[] array){ // O(n)
			final int n = array.length;
			data = new long[n]; System.arraycopy(array, 0, data, 0, n);

			final int log = Integer.numberOfTrailingZeros(Integer.highestOneBit(n));
			b_size = Math.max(1, log / 2); // 切り捨ててるから, log , log / 2 どっちでも...
			int blocks = (n + b_size - 1) / b_size;
			final int b_size_log = Integer.numberOfTrailingZeros(Integer.highestOneBit(blocks));

			// ここは block分だけの Sparse Table を用意するだけ.
			block_table = new long[b_size_log + 1][]; block_table[0] = new long[blocks];
			for(int i = 0; i < n; i++){
				if(i % b_size == 0){ block_table[0][i / b_size] = data[i]; }
				else {
					block_table[0][i / b_size] =
							Math.min(block_table[0][i / b_size], data[i]);
				}
			}
			for(int k = 1; k < block_table.length; k++){
				block_table[k] = new long[blocks - (1 << k) + 1];
				for(int i = 0; i + (1 << k) <= blocks; i++) {
					block_table[k][i] =
							Math.min(block_table[k-1][i], block_table[k-1][i + (1 << (k - 1))]);
				}
			}

			// in_block_bits を求める.
			in_block_bits = new int[n];
			LinkedList<Integer> stack = new LinkedList<Integer>();
			for(int block = 0; block < blocks; block++){
				stack.clear();

				for(int i = 0; i < b_size; i++){
					final int index = block * b_size + i;
					if(index >= n){ break; }

					while(!stack.isEmpty() && data[stack.getLast()] > data[index]){
						stack.removeLast();
					}

					in_block_bits[index] = (1 << i);
					if(!stack.isEmpty()) {
						in_block_bits[index] |= in_block_bits[stack.getLast()];
					}
					stack.add(index);
				}
			}
		}

		private long min_in_block(int l, int r){
			final int begin_block_l = (l / b_size * b_size);
			return data[begin_block_l + Integer.numberOfTrailingZeros(Integer.lowestOneBit(
					in_block_bits[r - 1] & (~((1 << (l - begin_block_l)) - 1))
			))];
		}

		public void update(int index, long v){
			data[index] = v;
			final int begin_index = index / b_size * b_size;
			final int end_index = Math.min(data.length, begin_index + b_size);
			// update bit
			LinkedList<Integer> stack = new LinkedList<Integer>();
			for(int i = begin_index; i < end_index; i++){
				while(!stack.isEmpty() && data[stack.getLast()] > data[i]){
					stack.removeLast();
				}

				in_block_bits[i] = (1 << (i - begin_index));
				if(!stack.isEmpty()) {
					in_block_bits[i] |= in_block_bits[stack.getLast()];
				}
				stack.add(i);
			}
			// update sparse table
			final int block = index / b_size;
			block_table[0][block] =data[begin_index +
					Integer.numberOfTrailingZeros(Integer.lowestOneBit(in_block_bits[end_index - 1]))];
			for(int k = 1; k < block_table.length; k++){
				final int begin = Math.max(0, block - (1 << k) + 1);
				final int end = Math.min(block_table[k].length, block + (1 << k));
				for(int i = begin; i < end; i++){
					block_table[k][i] =
							Math.min(block_table[k - 1][i], block_table[k-1][i + (1 << (k - 1))]);
				}
			}
		}

		public long query(int l, int r){ // [l, r) O(log log n)
			final int b_l = l / b_size,  b_r = (r - 1) / b_size; // b_l, b_r は閉区間,
			// 実際に Sparse Table で見たい区間は,  [b_l + 1, b_r)
			final int b_k = Integer.numberOfTrailingZeros(Integer.highestOneBit(b_r - b_l - 1));

			// same_block
			if(b_l >= b_r){ return min_in_block(l, r); }

			long ret_value = Long.MAX_VALUE;
			ret_value = Math.min(ret_value, min_in_block(l, (b_l + 1) * b_size));
			ret_value = Math.min(ret_value, min_in_block(b_r * b_size, r));
			// adjust_block
			if(b_l + 1 >= b_r) { return ret_value; }

			ret_value = Math.min(ret_value, block_table[b_k][b_l + 1]);
			ret_value = Math.min(ret_value, block_table[b_k][b_r - (1 << b_k)]);
			return ret_value;
		}
	}
	//@end
}



