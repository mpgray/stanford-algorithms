package random;
/**
 * A02 Randomized Queues and Deques 
 * CSIS-2420-004
 * @author Michael Gray, Alan Bischoff
 * 
 */

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 
 * Referenced code linked in assignment checklist. class ResizingArrayStack.java
 * http://algs4.cs.princeton.edu/13stacks/ResizingArrayStack.java.html
 * @param <Item>
 *
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
	    private int size = 0;
	    private Item[] q;

	    /**
	     * construct an empty randomized queue
	     */
	    public RandomizedQueue() {
	        q = (Item[]) new Object[2];
	        size = 0;
	    }
	    /**
	     * is the queue empty?
	     * @return
	     */
	    public boolean isEmpty() {
	        return size == 0;
	    }
	    /**
	     * return the number of items on the queue
	     * @return
	     */
	    public int size() {
	        return size;
	    }
	    /**
	     * add the item
	     * @param item
	     */
	    public void enqueue(Item item) {
	        if (null == item) {
	            throw new NullPointerException("Item must not be null!");
	        }
	        if (size == q.length) resize(2*q.length);     
	        q[size++] = item;
	    }
	    /**
	     * used in dequeue and enqueue to resize underlying array holding the elements
	     * @param capacity
	     */
	    private void resize(int capacity) {
	    	assert capacity >= size;

	        Item[] temp = (Item[]) new Object[capacity];
	        for (int i = 0; i < size; i++) {
	            temp[i] = q[i];
	        }
	        q = temp;
	    }
	    /**
	     * delete and return a random item
	     * @return
	     */
	    public Item dequeue() {
	        if (isEmpty()) {
	            throw new NoSuchElementException();
	        }
	        if (size > 0 && size == q.length / 4) {
	            resize(q.length / 2);
	        }

	        size--;
	        randomSwap(q, size);
	        Item item = q[size];
	        q[size] = null;
	        return item;
	    }
	    /**
	     * return (but do not delete) a random item
	     * @return
	     */
	    public Item sample() {
	        if (isEmpty()) {
	            throw new NoSuchElementException();
	        }
	        randomSwap(q, size - 1);
	        Item item = q[size - 1];
	        return item;
	    }
	    
	    private void randomSwap(Item[] queue, int t) {
	        int index = StdRandom.uniform(t + 1);
	        Item tmp = queue[index];
	        queue[index] = queue[t];
	        queue[t] = tmp;
	    }
	    /**
	     * return an independent iterator over items in random order
	     * @return
	     */
	    public Iterator<Item> iterator() {
	        return new RandomIterator();
	    }

	    private class RandomIterator implements Iterator<Item> {
	        Item[] copy = null;
	        int curr = -1;

	        public RandomIterator() {
	            copy = (Item[]) new Object[size];
	            for (int i = 0; i < size; i++) {
	                copy[i] = q[i];
	            }
	            StdRandom.shuffle(copy);

	            curr = 0;
	        }

	        public boolean hasNext() {
	            return curr < copy.length;
	        }

	        public Item next() {
	            if (curr == copy.length) {
	                throw new NoSuchElementException();
	            }
	            Item item = copy[curr++];
	            return item;
	        }

	        public void remove() {
	            throw new UnsupportedOperationException();
	        }
	    }
}
