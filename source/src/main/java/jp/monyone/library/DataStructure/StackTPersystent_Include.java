package jp.monyone.library.DataStructure;

public class StackTPersystent_Include {
	//@start
	public static class Stack<T> {
		public static class Node<T> {
			Node prev;
			T element;
			public Node(T element){
				this.element = element;
			}
			public Node(Node<T> n){
				this.prev = n.prev;
				this.element = n.element;
			}

			public static <T> Node<T> push(Node<T> cur, T pushed){
				Node<T> node = new Node<T>(pushed);
				node.prev = cur;
				return node;
			}
		}

		private Node<T> top_node = null;
		private Stack(Node n){ this.top_node = new Node<T>(n); }
		public Stack(Stack stack){ this.top_node = new Node<T>(stack.top_node); }

		//基本操作
		public Stack<T> push(T elem){ return new Stack(Node.push(this.top_node, elem)); }
		public T top(){
			return this.top_node == null ? null : this.top_node.element;
		}
		public Stack<T> pop(){
			return new Stack(this.top_node == null ? null : this.top_node.prev);
		}
		public boolean isEmpty(){ return this.top_node == null; }
	}
	//@end
}

