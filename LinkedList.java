/**
 * Represents a list of Nodes. 
 */
public class LinkedList {
	
	private Node first; // pointer to the first element of this list
	private Node last;  // pointer to the last element of this list
	private int size;   // number of elements in this list
	
	/**
	 * Constructs a new list.
	 */ 
	public LinkedList () {
		first = null;
		last = first;
		size = 0;
	}
	
	/**
	 * Gets the first node of the list
	 * @return The first node of the list.
	 */		
	public Node getFirst() {
		return this.first;
	}

	/**
	 * Gets the last node of the list
	 * @return The last node of the list.
	 */		
	public Node getLast() {
		return this.last;
	}
	
	/**
	 * Gets the current size of the list
	 * @return The size of the list.
	 */		
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Gets the node located at the given index in this list. 
	 * 
	 * @param index
	 *        the index of the node to retrieve, between 0 and size
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 * @return the node at the given index
	 */		
	public Node getNode(int index) {
		if (index < 0 || index > size) {
			throw new IllegalArgumentException(
					"index must be between 0 and size");
		}
		//// Replace the following statement with your code
		Node current = first;
    	for (int i = 0; i < index; i++) {
        	current = current.next;
    	}
    	return current;
	}
	
	/**
	 * Creates a new Node object that points to the given memory block, 
	 * and inserts the node at the given index in this list.
	 * <p>
	 * If the given index is 0, the new node becomes the first node in this list.
	 * <p>
	 * If the given index equals the list's size, the new node becomes the last 
	 * node in this list.
     * <p>
	 * The method implementation is optimized, as follows: if the given 
	 * index is either 0 or the list's size, the addition time is O(1). 
	 * 
	 * @param block
	 *        the memory block to be inserted into the list
	 * @param index
	 *        the index before which the memory block should be inserted
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than the list's size
	 */
	public void add(int index, MemoryBlock block) {
		//// Write your code here
		if (index > size || index < 0) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		}

		if (index == 0) {
			addFirst(block);
			return;
		}
		
		if (index == size) {
			addLast(block);
			return;
		}

		Node addNode = new Node(block);
		Node current = first;
		for (int i = 0; i < index - 1; i++) {
			current = current.next;
		}
		addNode.next = current.next;
		current.next = addNode;

		this.size++;		
	}
	

	/**
	 * Creates a new node that points to the given memory block, and adds it
	 * to the end of this list (the node will become the list's last element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addLast(MemoryBlock block) {
		//// Write your code here
		Node addNode = new Node(block);
		if (last == null) {
			first = addNode;
			last = addNode;
		}
		else {
			last.next = addNode;
			last = addNode;
		}
		this.size++;	
	}
	
	/**
	 * Creates a new node that points to the given memory block, and adds it 
	 * to the beginning of this list (the node will become the list's first element).
	 * 
	 * @param block
	 *        the given memory block
	 */
	public void addFirst(MemoryBlock block) {
		//// Write your code here
		Node addNode = new Node(block);
		if (last == null) {
			first = addNode;
			last = addNode;
		}
		else {
			addNode.next = first;
			first = addNode;
		}
		this.size++;
	}

	/**
	 * Gets the memory block located at the given index in this list.
	 * 
	 * @param index
	 *        the index of the retrieved memory block
	 * @return the memory block at the given index
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public MemoryBlock getBlock(int index) {
		//// Replace the following statement with your code
		if (index >= size || index < 0) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		}
		Node current = first;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current.block;
	}	

	/**
	 * Gets the index of the node pointing to the given memory block.
	 * 
	 * @param block
	 *        the given memory block
	 * @return the index of the block, or -1 if the block is not in this list
	 */
	public int indexOf(MemoryBlock block) {
		//// Replace the following statement with your code
		if (block == null) {
			throw new IllegalArgumentException("MemoryBlock cannot be null");
		}

		Node current = first;
		for (int i = 0; i < size; i++) {
			if (current.block.equals(block)) {
				return i;
			}
			current = current.next;
		}
		return -1;
	}

	/**
	 * Removes the given node from this list.	
	 * 
	 * @param node
	 *        the node that will be removed from this list
	 */
	public void remove(Node node) {
		//// Write your code here
		if (node == null) {
			throw new IllegalArgumentException("Node cannot be null");
		}
		if (first == null) {
			return;
		}

		if (first.equals(node)) {
			first = first.next;
			if (first == null) {
				last = null;
			}
			size--;
			return;
		}

		Node current = first;
		while (current.next != null) {
			if (current.next.equals(node)) {
				if (current.next.next == null) {
					last = current;
				}
				current.next = current.next.next;
				size--;
				return;
			}
			current = current.next;
		}
		throw new IllegalArgumentException("Node not found in the list");
	}

	/**
	 * Removes from this list the node which is located at the given index.
	 * 
	 * @param index the location of the node that has to be removed.
	 * @throws IllegalArgumentException
	 *         if index is negative or greater than or equal to size
	 */
	public void remove(int index) {
		//// Write your code here
		if (index >= size || index < 0) {
			throw new IllegalArgumentException("Index out of bounds: " + index);
		}

		if (index == 0) {
			first = first.next;
			if (first == null) {
				last = null;
			}
			size--;
			return;
		}

		Node current = first;
		for (int i=0; i<index-1; i++) {
			current = current.next;
		}

		
		if (current.next.next == null) {
			last = current;
		}
		
		current.next = current.next.next;
		size--;
	}

	/**
	 * Removes from this list the node pointing to the given memory block.
	 * 
	 * @param block the memory block that should be removed from the list
	 * @throws IllegalArgumentException
	 *         if the given memory block is not in this list
	 */
	public void remove(MemoryBlock block) {
		//// Write your code here
		if (block == null) {
			throw new IllegalArgumentException("MemoryBlock cannot be null");
		}

		if (first != null && first.block.equals(block)) {
			first = first.next;
			if (first == null) {
				last = null;
			}
			size--;
			return;
		}

		Node current = first;
		while (current != null && current.next != null) {
			if (current.next.block.equals(block)) {
				if (current.next.next == null) {
					last = current;
				}
				current.next = current.next.next;
				size--;	
				return;
			}
			current = current.next;
		}

		throw new IllegalArgumentException("MemoryBlock not found in the list.");

	}	

	/**
	 * Returns an iterator over this list, starting with the first element.
	 */
	public ListIterator iterator(){
		return new ListIterator(first);
	}
	
	/**
	 * A textual representation of this list, for debugging.
	 */
	public String toString() {
		//// Replace the following statement with your code
		if (first == null) {
			return "[]";
		}

		String result = "[";
		Node current = first;
		while (current != null) {
			result += current.block.toString();
			if (current.next != null) {
				result += ", ";
			}

			current = current.next;
		}

		result += "]";
		return result;
	}
}