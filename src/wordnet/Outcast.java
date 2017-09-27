/*
 * Authors: Michael Gray & Chad Seale
 * 
 * 
 * 
 */

package wordnet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
   private final WordNet immutableNet;
	
   // constructor takes a WordNet object
   public Outcast(WordNet wordnet){
	   immutableNet = wordnet;
   }
   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns) {
        String outcast = null;
        int max = 0;

        for (String nounA : nouns) {
            int dist = 0;
            for (String nounB : nouns) {
                if (!nounA.equals(nounB)) {
                    dist += immutableNet.distance(nounA, nounB);
                }
            }
            if (dist > max) {
                max = dist;
                outcast = nounA;
            }
        }
	return outcast;	   
	   
   }
   // see test client below
   public static void main(String[] args) {
       WordNet wordnet = new WordNet(args[0], args[1]);
       Outcast outcast = new Outcast(wordnet);
       for (int t = 2; t < args.length; t++) {
        In in = new In(args[t]);
        String[] nouns = in.readAllStrings();
        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
       } 
    }

   
}


