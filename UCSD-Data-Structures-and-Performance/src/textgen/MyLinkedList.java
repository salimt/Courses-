package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author salimt
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	int size;
    LLNode<E> head, tail;

    /** Create a new empty LinkedList */
	public MyLinkedList() {
        head = new LLNode <>(null);
        tail = new LLNode <>(null);
        head.setNext(tail);
        tail.setPrev(head);
    }

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element)
	{
        if (element == null)
            throw new NullPointerException("Element cannot be null!");

        new LLNode<>(element, tail);
        size++;
        return true;
    }

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index)
	{
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index is out of bounds");

        return getNode(index).data;
    }

    public LLNode<E> getNode(int index){
        LLNode<E> curr = head;
        for(int i=-1; i<index; i++){
            //System.out.println(curr.data + " <- currdata ||" +  i + " <- number ||" + curr.next.data + " <- currNextdata");
            curr = curr.next;
        }
        return curr;
    }

	/**
	 * Add an element to the list at the specified index
	 * @param index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) {
        if (element == null)
            throw new NullPointerException("element cannot be passed as null");
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("index is out of bounds");

        LLNode<E> prevNode = getNode(index);
        if(prevNode!=null){ size++; }

        new LLNode<>(element, prevNode);
    }

	/** Return the size of the list */
	public int size() { return size; }

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index is out of bounds");
        }
        LLNode <E> delVal = getNode(index);

        delVal.prev.setNext(delVal.next);
        delVal.next.setPrev(delVal.prev);
        delVal.setNext(null);
        delVal.setPrev(null);

        size--;
        return delVal.data;

    }

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("index is out of bounds");

        if (element == null)
            throw new NullPointerException("Element cannot be null!");

        LLNode<E> oldVal = getNode(index);
        oldVal.prev.setNext(new LLNode <E>(element));
        oldVal.next.setPrev(new LLNode <E>(element));
        return oldVal.data;
	}   
}

class LLNode<E>
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E e)
	{
		this.data = e;
		//this.prev = null;
		//this.next = null;
	}

	public LLNode(E e, LLNode<E> next){
	    this(e);
        this.setPrev(next.prev);
        this.setNext(next);
        next.prev.setNext(this);
        next.setPrev(this);

    }

    public void setNext(LLNode <E> next) {
        this.next = next;
    }

    public void setPrev(LLNode <E> prev) {
        this.prev = prev;
    }
}
