package jp.monyone.library.DataStructure;

import java.util.Random;

public class LeftshiftMinHeapT_Include {
	//@start
	public static class LeftshiftHeap<T extends Comparable<? super T>> {

		private static class Node<T> {
			Node<T> left, right;
			T value; // value に null を入れると死ぬ.
			int s;

			public Node(T value){ this.value = value; this.s = 1; }
		}

		private static <T extends Comparable<? super T>> Node<T> meld(Node<T> a, Node<T> b){
			if(a == null){ return b; }
			if(b == null){ return a; }
			if(a.value.compareTo(b.value) > 0){ return meld(b, a); } // > で Min, < で Max

			a.right = meld(a.right, b);
			if(a.left == null || a.left.s < a.right.s){
				Node<T> tmp = a.left; a.left = a.right; a.right = tmp;
			}
			a.s = (a.right == null ? 0 : a.right.s) + 1; //交換してるから, rightの方が低い
			return a;
		}

		private Node<T> root = null;
		// 基本的な操作
		public void add(T value){ root = meld(root, new Node<T>(value)); }
		public void pop(){ root = meld(root.left, root.right);}
		public void merge(LeftshiftHeap<T> other){ root = meld(this.root, other.root); }
		// ユーティリティ
		public T poll(){ T val = peek(); pop(); return val; }
		public T peek(){ return root.value; }
		public T top(){ return peek(); }
		public boolean isEmpty(){ return root == null; }
	}
	//@end
}
