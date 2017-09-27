package random;
/**
 * A02 Randomized Queues and Deques 
 * CSIS-2420-004
 * @author Michael Gray, Alan Bischoff
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;



 /** 
 * Referenced code linked in assignment checklist. class LinkedStack.java
 * http://algs4.cs.princeton.edu/13stacks/LinkedStack.java.html
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size = 0;

    private class Node {
        Node prev;
        Node next;
        Item value;

        public Node(Item value) {
            super();
            this.value = value;
        }
    }
    /**
     * 
     * construct an empty deque
     */
    public Deque() {
    	first = null;
        size = 0;
    }

    /**
     * is the deque empty?
     *  @return
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * return the number of items on the deque
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * insert the item at the front
     * @param item
     */
    public void addFirst(Item item) {
        if (null == item) {
            throw new NullPointerException();
        }
        Node a = new Node(item);
        a.prev = first;
        if (null == first) {
            first = last;
            last = a;
        } else {
            first.next = a;
            first = a;
        }
        size++;
    }

    /**
     * insert the item at the end
     * @param item
     */
    public void addLast(Item item) {
        if (null == item) {
            throw new NullPointerException();
        }

        Node a = new Node(item);
        a.next = last;
        if (null == last) {
            last = first;
            first = a;
        } else {
            last.prev = a;
            last = a;
        }
        size++;
    }

    /**
     * delete and return the item at the front
     * @return
     */
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node tmp = first;
        first = first.prev;
        if (null != first) {
            first.next = null;
        } else {
            first = last = null;
        }
        tmp.prev = null;
        size--;
        return tmp.value;
    }

    /**
     * delete and return the item at the end
     * @param Item
     */
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node tmp = last;
        last = last.next;
        if (null != last) {
            last.prev = null;
        } else {
            first = last = null;
        }
        tmp.next = null;
        size--;
        return tmp.value;
    }
    

    /**
     *  return an iterator over items in order from front to end
     *  @return
     */
    public Iterator<Item> iterator() {
       return new DequeIterator();

    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.value;
            current = current.next; 
            return item;
        }
    }
}
