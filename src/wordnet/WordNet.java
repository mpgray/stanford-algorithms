/*
 * Authors: Michael Gray & Chad Seale
 * 
 * 
 * 
 */
package wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class WordNet {
	SeparateChainingHashST<String, Queue<Integer>> wordToIntegerMap;
	SeparateChainingHashST<Integer, String> integerToWordMap;
	Digraph graph;
	SAP sap;
	
	/**
	 * Constructor takes the name of the two input files, synsets and hypernyms
	 * @param synsets String path
	 * @param hypernyms String path
	 */
	public WordNet(String synsets, String hypernyms) {
		if (synsets == null || hypernyms == null) throw new NullPointerException("Arguments can't be null");
		wordToIntegerMap = new SeparateChainingHashST<>();
		integerToWordMap = new SeparateChainingHashST<>();
		int vertices = 0;
		In in = new In(synsets);
		while (in.hasNextLine()) {
			vertices++;
			String[] line = in.readLine().split(",");
			String[] words = line[1].split(" ");
			Integer number = Integer.valueOf(line[0]);
			integerToWordMap.put(Integer.valueOf(line[0]), line[1]);
			for (int i = 0; i < words.length; i++) {
				Queue<Integer> wordToIntegerMapQueue = wordToIntegerMap.get(words[i]);
				if (wordToIntegerMapQueue == null) {
					wordToIntegerMapQueue = new Queue<>();
					wordToIntegerMapQueue.enqueue(number);
					wordToIntegerMap.put(words[i], wordToIntegerMapQueue);
				}
				else {
					if (!contains(wordToIntegerMapQueue, number)) {
						wordToIntegerMapQueue.enqueue(number);
					}
				}
			}
		}
		graph = new Digraph(vertices);
		in = new In(hypernyms);
		while (in.hasNextLine()) {
			String[] line = in.readLine().split(",");
			for (int i = 1; i < line.length; i++)
				graph.addEdge(Integer.parseInt(line[0]), Integer.parseInt(line[i]));
		}
		sap = new SAP(graph);
		if (!sap.isRootedDAG()) throw new IllegalArgumentException("hypernyms must be a rooted DAG");
	}
	
	private <Item> boolean contains (Iterable<Item> iterable, Item item) {
		for (Item query : iterable)
			if (query == item) return true;
		return false;
	}

	/**
	 * Return all the WordNet nouns
	 * @return Iterable of the nouns
	 */
	public Iterable<String> nouns() {
		return wordToIntegerMap.keys(); 
	}

	/**
	 * Is the word a WordNet noun?
	 * @param word String word to check if it is a WordNet noun
	 * @return boolean true if it is a WordNet noun, false otherwise
	 */
	public boolean isNoun(String word) {
		if (word == null || "".equals(word)){
			return false;
		}
		return wordToIntegerMap.contains(word);
	}

	/**
	 * Distance between nounA and nounB
	 * @param nounA String the first noun to check
	 * @param nounB String the second noun to check
	 * @return int the distance between the two nouns
	 */
	public int distance(String nounA, String nounB) {
		if (nounA == null || nounB == null) throw new NullPointerException("Arguments can't be null");
		if (wordToIntegerMap.get(nounA) == null || wordToIntegerMap.get(nounB) == null)
			throw new IllegalArgumentException("Nouns must be contained in WordNet");
		Iterable<Integer> integerA = wordToIntegerMap.get(nounA);
		Iterable<Integer> integerB = wordToIntegerMap.get(nounB);
		return sap.length(integerA, integerB);
	}

	/**
	 * A synset that is the common ancestor of nounA and nounB in a shortest ancestral path
	 * @param nounA first noun to check
	 * @param nounB second noun to check
	 * @return String synset of the common anestor of nounA and nounB
	 */
	public String sap(String nounA, String nounB) {
		if (nounA == null || nounB == null) throw new NullPointerException("Arguments can't be null");
		if (wordToIntegerMap.get(nounA) == null || wordToIntegerMap.get(nounB) == null)
			throw new IllegalArgumentException("Nouns must be contained in WordNet");
		Iterable<Integer> integerA = wordToIntegerMap.get(nounA);
		Iterable<Integer> integerB = wordToIntegerMap.get(nounB);
		return integerToWordMap.get(sap.ancestor(integerA, integerB));
	}
	
    /**
     * for unit testing of this class
     * 
     * @param args
     */
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        while (!StdIn.isEmpty()) {
            String v = StdIn.readString();
            String w = StdIn.readString();
            if (!wordNet.isNoun(v)) {
                StdOut.println(v + " not in the word net");
                continue;
            }
            if (!wordNet.isNoun(w)) {
                StdOut.println(w + " not in the word net");
                continue;
            }
            int distance = wordNet.distance(v, w);
            String ancestor = wordNet.sap(v, w);
            StdOut.printf("distance = %d, ancestor = %s\n", distance, ancestor);
        }
    }
	
}
