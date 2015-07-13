package jp.monyone.library.DataStructure;

public class SkewMinHeapT_Include {
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

			public String toString(){ // これはデバッグ用なので削ってもいい.
				return "[" + this.left + "] " + this.value + " [ " + this.right + " ]";
			}
		}

		private static <T extends Comparable<? super T>> Node<T> meld(Node<T> a, Node<T> b){
			if(a == null){ return b; }
			if(b == null){ return a; }
			if(a.value.compareTo(b.value) > 0){ return meld(b, a); } // > で Min, < で Max
			a.right = meld(a.right, b);
			{ Node<T> tmp = a.left; a.left = a.right; a.right = tmp; } // swapするだけ!
			return a;
		}

		private Node<T> root = null;
		// 基本的な操作
		public void add(T value){ root = meld(root, new Node<T>(value)); }
		public void pop(){ root = meld(root.left, root.right);}
		public void merge(SkewHeap<T> other){ root = meld(this.root, other.root); }
		// ユーティリティ
		public T poll(){ T val = peek(); pop(); return val; }
		public T peek(){ return root.value; }
		public boolean isEmpty(){ return root == null; }
	}
	//@end
}
