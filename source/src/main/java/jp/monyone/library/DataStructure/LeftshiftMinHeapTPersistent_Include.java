package jp.monyone.library.DataStructure;

public class LeftshiftMinHeapTPersistent_Include {
	//@start
	public static class LeftshiftHeap<T extends Comparable<? super T>> {

		private static class Node<T> {
			Node<T> left, right;
			T value; // value に null を入れると死ぬ.
			int s;

			public Node(T value){ this.value = value; this.s = 1; }
			public Node(Node<T> n){
				this.left = n.left; this.right = n.right;
				this.value = n.value; this.s = n.s;
			}
		}

		private static <T extends Comparable<? super T>> Node<T> meld(Node<T> a, Node<T> b){
			if(a == null){ return b; }
			if(b == null){ return a; }
			if(a.value.compareTo(b.value) > 0){ return meld(b, a); } // > で Min, < で Max

			Node<T> copyed = new Node<T>(a);
			copyed.right = meld(copyed.right, b);
			if(copyed.left == null || copyed.left.s < copyed.right.s){
				Node<T> tmp = copyed.left; copyed.left = copyed.right; copyed.right = tmp;
			}
			copyed.s = (copyed.right == null ? 0 : copyed.right.s) + 1; //rightの方が必ず低い
			return copyed;
		}

		private Node<T> root;
		public LeftshiftHeap(){ root = null; }
		public LeftshiftHeap(Node<T> n){ root = n; }

		// 基本的な操作
		public LeftshiftHeap<T> add(T value){
			return new LeftshiftHeap<T>(meld(root, new Node(value)));
		}
		public LeftshiftHeap<T> pop(){
			return new LeftshiftHeap<T>(meld(root.left, root.right));
		}
		public LeftshiftHeap<T> merge(LeftshiftHeap<T> other){
			return new LeftshiftHeap<T>(meld(this.root, other.root));
		}
		// ユーティリティ
		public T peek(){ return root.value; }
		public T top(){ return peek(); }
		public boolean isEmpty(){ return root == null; }
	}
	//@end
}
