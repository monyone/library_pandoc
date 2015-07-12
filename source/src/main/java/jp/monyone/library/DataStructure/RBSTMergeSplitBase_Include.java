package jp.monyone.library.DataStructure;

import java.util.Random;

public class RBSTMergeSplitBase_Include {
	//@start
	// TODO; なんかバグってるらしく, 平衡しない. 致命的なのでどうかしたい.
	public static class RBST {
		private static final Random rand = new Random(); // java.util.Random

		public static class Node {
			long data;
			Node left, right;
			int count;

			public Node(long data){
				this.data = data; this.count = 1;
			}

			public void update(){
				this.count = 1;
				if(this.left != null){ this.count += left.count; }
				if(this.right != null){ this.count += right.count; }
			}

			public String toString() {
				return "[ " + this.left + " ] " + this.data + " [ " + this.right + " ]";
			}

			public static int count(Node node){ return node == null ? 0 : node.count; }

			public static Node merge(Node a, Node b){
				if(b == null){ return a; }
				if(a == null){ return b; }
				final int size = count(a) + count(b);

				if(rand.nextInt(size) < count(a)){ // a が根
					a.right = merge(a.right, b);
					a.update(); return a;
				}else{ // b が根
					b.left = merge(a, b.left);
					b.update(); return b;
				}
			}

			public static Node[] split(Node a, int k){ //[0, K), [K, N)
				if(a == null){ return new Node[]{null, null}; }
				if(k <= count(a.left)){
					Node[] split = split(a.left, k);
					a.left = split[1]; a.update(); split[1] = a;
					return split;
				}else{
					Node[] split = split(a.right, k - count(a.left) - 1);
					a.right = split[0]; a.update(); split[0] = a;
					return split;
				}
			}
		}

		Node root = null;

		public void insert(int k, long value){
			Node[] split = Node.split(root, k);
			root = Node.merge(split[0], Node.merge(new Node(value), split[1]));
		}
	}
	//@end

	public static void main(String[] args){
		RBST rbst = new RBST();

		for(int i = 0; i < 10000; i++){
			rbst.insert(RBST.rand.nextInt(i + 1), RBST.rand.nextLong() / (Long.MAX_VALUE / 100));

		}System.out.println( RBST.Node.count(rbst.root.left) + " " + RBST.Node.count(rbst.root.right) /*  + " " + rbst.root */);
	}

}
