package jp.monyone.library.DataStructure;

public class StackTPersystentDoubling_Include {
	//@start
	public static class Stack<T> {
		public static class Node<T> {
			Node[] prev;
			int size;
			T element;
			public Node(T element){ this(element, 0, 1); }
			public Node(T element, int prev_size, int node_size){
				prev = new Node[prev_size];
				this.element = element;
				this.size = node_size;
			}
			public Node(Node<T> n){
				prev = new Node[n.prev.length];
				System.arraycopy(n.prev, 0, this.prev, 0, n.prev.length);
				this.element = n.element; this.size = n.size;
			}

			public Node<T> prevNth(int n){
				if(n == 0){ return this; }
				final int highest = Integer.highestOneBit(n);
				final int log = Integer.numberOfTrailingZeros(highest);
				if(prev.length <= log){ return null; }
				return prev[log].prevNth(n - highest);
			}

			public static <T> Node<T> push(Node<T> cur, T pushed){
				if(cur == null){ return new Node(pushed); }

				int length = 0;
				for(Node<T> node = cur; node.prev.length > 0; ){
					length += (1 << (node.prev.length - 1));
					node = node.prev[node.prev.length - 1];
				}
				length++;
				final int highest = Integer.highestOneBit(length);
				final int log = (Integer.numberOfTrailingZeros(highest) + 1);

				Node<T> node = new Node<T>(pushed, log, length + 1); //length に自分を足す
				node.prev[0] = cur;
				for(int i = 1; i < log; i++){
					node.prev[i] = cur.prevNth((1 << i) - 1);
				}
				return node;
			}
		}

		private Node<T> top_node;

		private Stack(Node n){ this.top_node = new Node<T>(n); }
		public Stack(){ this.top_node = null; }
		public Stack(Stack stack){ this.top_node = new Node<T>(stack.top_node); }

		//基本操作
		public Stack<T> push(T elem){ return new Stack(Node.push(this.top_node, elem)); }
		public T top(){
			return this.top_node == null ? null : this.top_node.element;
		}
		public T topN(int n) {
			return this.top_node == null ? null : this.top_node.prevNth(n).element;
		}
		public Stack<T> pop(){
			return new Stack(this.top_node == null ? null : this.top_node.prev[0]);
		}
		public Stack<T> popN(int n){
			return new Stack(this.top_node == null ? null : this.top_node.prevNth(n));
		}
		public boolean isEmpty(){ return this.top_node == null; }
		public int size(){ return isEmpty() ? 0 : this.top_node.size; }
	}
	//@end
}

