package structs;

public class CircularLinkedList<T> {
	
	/**
	 * Definition of a node within this class.
	 */
	class Node {
		private T data;
		private Node next;
		
		public Node() {
			this.data = null;
			this.next = null;
		}
		public Node(T data) {
			this.data = data;
			this.next = null;
		}
		public Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data; 
		}
		public Node getNext() {
			return next;
		}
		public void setNext(Node next) {
			this.next = next;
		}
		public String toString() {
			return data.toString();
		}
	}
	/**
	 * Basic constructor.
	 */
	public CircularLinkedList() {
		cursor = null;
		size = 0;
	}
	
	private Node cursor;
	private int size;
	
	/**
	 * Adds an element to the circular linked list.
	 * @param info data to add to the list.
	 */
	public void add(T info) {
		
		Node newNode = new Node(info); 
		Node handle = cursor;
		
		if (cursor == null) {
			cursor = newNode;
			newNode.setNext(cursor);
		} else {
			while (cursor.getNext() != handle) {
				cursor = cursor.getNext();
			}
			cursor.setNext(newNode);
			newNode.setNext(handle);
			cursor = handle;
		}
		size++;
	}
	/**
	 * Removes the element the cursor is currently pointing towards.
	 * @return Element that has been removed.
	 */
	public T remove() {
		if (cursor == null || cursor.getData() == cursor.getNext().getData()) {
			cursor = null;
			return null;
		}
		Node handle = cursor;
		while (cursor.getNext() != handle) {
			cursor = cursor.getNext();
		}
		Node temp = cursor.getNext();
		cursor.setNext(cursor.getNext().getNext());
		size--;
		return temp.getData();
	}
	/**
	 * Removes all references maintained within the list.
	 */
	public void removeAll() {
		cursor = null;
		size = 0;
	}
	/**
	 * scrolls through the list until the given integer has reached zero
	 * from right to left of the list starting from the cursor.
	 * @param shifts the amount of shifts to switch the cursor.
	 * @param lock if true, sets the cursor to the last element shifted to.
	 * @return the element at the last node shifted to.
	 */
	public T scroll(int shifts, boolean lock) {
		Node handle = cursor;
		while (shifts-- > 0) {
			cursor = cursor.getNext();
		}
		if (lock) {
			Node temp = cursor;
			cursor = handle;
			return temp.getData();
		} else {
			return cursor.getData();
		}
	}
	/**
	 * Shifts the current cursor to point at the next element in the list.
	 * @return the next element in the list.
	 */
	public T shift() {
		cursor = cursor.getNext();
		return cursor.getData();
	}
	/**
	 * Returns the element the cursor is currently pointing towards.
	 * @return the current element the cursor is pointing towards.
	 */
	public T atCursor() {
		return cursor.getData();
	}
	/**
	 * Returns true if the current list contains a reference to the given data.
	 * @param info element to check.
	 * @return true if the element is located within the list.
	 */
	public boolean contains(T info) {
		Node handle = cursor;
		while (cursor.getNext() != handle) {
			if (cursor.getData() == info) {
				cursor = handle;
				return true;
			}
			cursor = cursor.getNext();
		}
		cursor = handle;
		return false;
	}
	/**
	 * Returns true if the list contains no elements
	 * @return true if the list contains no elements.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * The size of the linked list.
	 * @return the size of the linked list.
	 */
	public int length() {
		return size;
	}
	@Override
	public String toString() {
		if (cursor == null) {
			return "[]";
		}
		StringBuilder sb = new StringBuilder();
		Node handle = cursor;
		sb.append("[");
		while (cursor.getNext() != handle) {
			sb.append(cursor.toString() + ", ");
			cursor = cursor.getNext();
		}
		sb.append(cursor.toString() + "]");
		
		cursor = handle;
		return sb.toString();
	}
}
