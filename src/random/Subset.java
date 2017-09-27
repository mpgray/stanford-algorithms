package random;
/**
 * A02 Randomized Queues and Deques 
 * CSIS-2420-004
 * @author Michael Gray, Alan Bischoff
 * 
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Subset {

	public static void main(String[] args) {
		
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        while (StdIn.hasNextLine() == true && StdIn.isEmpty() == false) {
            q.enqueue(StdIn.readString());
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(q.iterator().next());
        }
    

	}

}
