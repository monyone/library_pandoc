package jp.monyone.library.DataStructure;

public class SkewMinHeapTPersistent_Include {
	//@start
	public static class SkewHeap<T extends Comparable<? super T>> {

		private static class Node<T> {
			Node<T> left, right;
			T value; // value に null を入れると死ぬ.

			public Node(T value){ this.value = value; }
			public Node(Node<T> n){
				this.left = n.left; this.right = n.right;
				this.value = n.value;
			}
		}

		private static <T extends Comparable<? super T>> Node<T> meld(Node<T> a, Node<T> b){
			if(a == null){ return b; }
			if(b == null){ return a; }
			if(a.value.compareTo(b.value) > 0){ return meld(b, a); } // > で Min, < で Max
			Node<T> copyed = new Node<T>(a);
			copyed.right = meld(copyed.right, b);
			{ Node<T> tmp = copyed.left; copyed.left = copyed.right; copyed.right = tmp; }
			return copyed;
		}

		private Node<T> root = null;
		public SkewHeap(){ this.root = null; }
		public SkewHeap(Node<T> n){ this.root = n; }
		// 基本的な操作
		public SkewHeap<T> add(T value){
			return new SkewHeap<T>(meld(root, new Node(value)));
		}
		public SkewHeap<T> pop(){
			return new SkewHeap<T>(meld(root.left, root.right));
		}
		public SkewHeap<T> merge(SkewHeap<T> other){
			return new SkewHeap<T>(meld(this.root, other.root));
		}
		// ユーティリティ
		public T peek(){ return root.value; }
		public T top(){ return peek(); }
		public boolean isEmpty(){ return root == null; }
	}
	//@end
}
