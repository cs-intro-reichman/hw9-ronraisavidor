/**
 * Represents a managed memory space. The memory space manages a list of allocated 
 * memory blocks, and a list free memory blocks. The methods "malloc" and "free" are 
 * used, respectively, for creating new blocks and recycling existing blocks.
 */
public class MemorySpace {
	
	// A list of the memory blocks that are presently allocated
	private LinkedList allocatedList;

	// A list of memory blocks that are presently free
	private LinkedList freeList;

	/**
	 * Constructs a new managed memory space of a given maximal size.
	 * 
	 * @param maxSize
	 *            the size of the memory space to be managed
	 */
	public MemorySpace(int maxSize) {
		// initiallizes an empty list of allocated blocks.
		allocatedList = new LinkedList();
	    // Initializes a free list containing a single block which represents
	    // the entire memory. The base address of this single initial block is
	    // zero, and its length is the given memory size.
		freeList = new LinkedList();
		freeList.addLast(new MemoryBlock(0, maxSize));
	}

	/**
	 * Allocates a memory block of a requested length (in words). Returns the
	 * base address of the allocated block, or -1 if unable to allocate.
	 * 
	 * This implementation scans the freeList, looking for the first free memory block 
	 * whose length equals at least the given length. If such a block is found, the method 
	 * performs the following operations:
	 * 
	 * (1) A new memory block is constructed. The base address of the new block is set to
	 * the base address of the found free block. The length of the new block is set to the value 
	 * of the method's length parameter.
	 * 
	 * (2) The new memory block is appended to the end of the allocatedList.
	 * 
	 * (3) The base address and the length of the found free block are updated, to reflect the allocation.
	 * For example, suppose that the requested block length is 17, and suppose that the base
	 * address and length of the the found free block are 250 and 20, respectively.
	 * In such a case, the base address and length of of the allocated block
	 * are set to 250 and 17, respectively, and the base address and length
	 * of the found free block are set to 267 and 3, respectively.
	 * 
	 * (4) The new memory block is returned.
	 * 
	 * If the length of the found block is exactly the same as the requested length, 
	 * then the found block is removed from the freeList and appended to the allocatedList.
	 * 
	 * @param length
	 *        the length (in words) of the memory block that has to be allocated
	 * @return the base address of the allocated block, or -1 if unable to allocate
	 */
	public int malloc(int length) {
		// Edge case: if length is 0, return -1
		if (length <= 0) {
			return -1;
		}
	
		// Traverse through the free list to find the first suitable block
		Node currentNode = freeList.getFirst(); // Start from the first node in the free list
		while (currentNode != null) {
			MemoryBlock freeBlock = currentNode.block;
	
			// Check if the free block has enough space
			if (freeBlock.length >= length) {
				// If the free block's length is exactly the requested size
				if (freeBlock.length == length) {
					// Remove this block from the free list
					freeList.remove(currentNode.block);
	
					// Add it to the allocated list
					allocatedList.addLast(new MemoryBlock(freeBlock.baseAddress, freeBlock.length));
	
					// Return the base address of the allocated block
					return freeBlock.baseAddress;
				} else {
					// If the free block's length is larger than the requested size
					// Create a new memory block of the requested size
					MemoryBlock allocatedBlock = new MemoryBlock(freeBlock.baseAddress, length);
					allocatedList.addLast(allocatedBlock); // Add it to the allocated list
	
					// Update the free block; reduce its size and change its base address
					freeBlock.baseAddress += length;  // Move the base address forward
					freeBlock.length -= length;       // Reduce the size of the free block
	
					// Return the base address of the allocated block
					return allocatedBlock.baseAddress;
				}
			}
	
			// Move to the next free block in the list
			currentNode = currentNode.next;
		}
	
		// If no suitable block is found, return -1 (allocation failed)
		return -1;
	}

	/**
	 * Frees the memory block whose base address equals the given address.
	 * This implementation deletes the block whose base address equals the given 
	 * address from the allocatedList, and adds it at the end of the free list. 
	 * 
	 * @param baseAddress
	 *            the starting address of the block to freeList
	 */
	public void free(int address) {

		if (allocatedList.getSize() == 0) {
			throw new IllegalArgumentException("index must be between 0 and size");
		}
	
		// Find the block in the allocatedList with the given base address
		Node currentNode = allocatedList.getFirst();
		Node blockToFree = null;
	
		// Traverse the allocated list to find the block
		while (currentNode != null) {
			if (currentNode.block.baseAddress == address) {
				blockToFree = currentNode;  // Found the block
				break;
			}
			currentNode = currentNode.next;  // Move to the next node in the list
		}
	
		// If no block with the given address was found in allocatedList, just return (no-op)
		if (blockToFree == null) {
			return;  // Ignore the invalid free request, no exception
		}
	
		// Remove the block from the allocatedList
		allocatedList.remove(blockToFree.block);
	
		// Add the block to the freeList
		freeList.addLast(blockToFree.block);
	}
	
	/**
	 * A textual representation of the free list and the allocated list of this memory space, 
	 * for debugging purposes.
	 */
	public String toString() 
	{
		return freeList.toString() + "\n" + allocatedList.toString();		
	}
	
	/**
	 * Performs defragmantation of this memory space.
	 * Normally, called by malloc, when it fails to find a memory block of the requested size.
	 * In this implementation Malloc does not call defrag.
	 */
	public void defrag() {
		// Start from the first node in the free list
		Node currentNode = freeList.getFirst();
		boolean flag = true;
		boolean flag2 = true;
	
		while (currentNode != null) {
			Node runner = freeList.getFirst();
			while (runner != null) {
	
				// Check if the current block and the next block are adjacent
				if (currentNode.block.baseAddress + currentNode.block.length == runner.block.baseAddress) {
					// Merge the blocks
					currentNode.block.length += runner.block.length;
	
					// Remove the next node from the free list (since it has been merged)
					Node newRemove = runner;
					runner = runner.next;
					freeList.remove(newRemove.block);
					flag = false;
					flag2 = false;
				}
	
				if (flag2) {
					runner = runner.next;
				}
				flag2 = true;
			}
	
			if (flag) {
				currentNode = currentNode.next;
			}
			flag = true;
		}
		
	}
}
