package junit;

import static org.junit.Assert.*;


import org.junit.Test;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import wordnet.SAP;

public class SapTest {


	@Test
	public void testIsDag() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph1.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(true, sap.isDAG());
	}

	@Test
	public void testIsDagFalse() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraphNoDAG.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(false, sap.isDAG());
	}
	
	@Test
	public void testIsRootedDagTrue() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph1.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(true, sap.isRootedDAG());
	}
	
	@Test
	public void testIsRootedDagFalseNotaDAG() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraphNoDAG.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(false, sap.isRootedDAG());
	}

	
	@Test
	public void testAncestorwDigraph1() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph1.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(1, sap.ancestor(6, 8));
	}
	
	@Test
	public void testAncestorwDigraph2() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph2.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(0, sap.ancestor(1, 5));
	}
	
	@Test
	public void testAncestorwDigraphNoDAG() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraphNoDAG.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(9, sap.ancestor(0, 9));
	}
	
	@Test
	public void testLengthwDigraph1() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph1.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(4, sap.length(6, 8));
	}
	
	@Test
	public void testLengthwDigraph2() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph2.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(2, sap.length(1, 5));
	}
	
	@Test
	public void testLengthwDigraphNoDAG() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraphNoDAG.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       assertEquals(1, sap.length(0, 9));
	}
	
	@Test
	public void testAncestorIterablewDigraph1() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph1.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       Queue<Integer> q1 = new Queue<>();
	       q1.enqueue(2);
	       q1.enqueue(7);
	       Queue<Integer> q2 = new Queue<>();
	       q2.enqueue(8);
	       q2.enqueue(11);
	       assertEquals(1, sap.ancestor(q1, q2));
	       assertEquals(4, sap.length(q1, q2));
	}
	
	@Test
	public void testAncestorIterablewDigraph2() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph2.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       Queue<Integer> q1 = new Queue<>();
	       q1.enqueue(1);
	       q1.enqueue(3);
	       Queue<Integer> q2 = new Queue<>();
	       q2.enqueue(5);
	       q2.enqueue(0);
	       assertEquals(0, sap.ancestor(q1, q2));
	       assertEquals(1, sap.length(q1, q2));
	}
	
	@Test
	public void testAncestorIterablewDigraphNoDAG() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraphNoDAG.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       Queue<Integer> q1 = new Queue<>();
	       q1.enqueue(2);
	       q1.enqueue(0);
	       Queue<Integer> q2 = new Queue<>();
	       q2.enqueue(8);
	       q2.enqueue(11);
	       assertEquals(9, sap.ancestor(q1, q2));
	       assertEquals(2, sap.length(q1, q2));
	}
	
	@Test
	public void testLengthIterablewDigraph1() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph1.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       Queue<Integer> q1 = new Queue<>();
	       q1.enqueue(0);
	       q1.enqueue(9);
	       Queue<Integer> q2 = new Queue<>();
	       q2.enqueue(8);
	       q2.enqueue(3);     
	       assertEquals(2, sap.length(q1, q2));
	       assertEquals(5, sap.ancestor(q1, q2));
	}
	
	@Test
	public void testLengthIterablewDigraph2() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraph2.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       Queue<Integer> q1 = new Queue<>();
	       q1.enqueue(2);
	       Queue<Integer> q2 = new Queue<>();
	       q2.enqueue(5);
	       q2.enqueue(0);
	       assertEquals(3, sap.length(q1, q2));
	       assertEquals(5, sap.ancestor(q1, q2));
	}
	
	@Test
	public void testLengthwIterableDigraphNoDAG() {
		 // Create graph from file
		   In in = new In("src/wordNet/digraphNoDAG.txt");
	       Digraph G = new Digraph(in);
	       SAP sap = new SAP(G);
	       Queue<Integer> q1 = new Queue<>();
	       q1.enqueue(8);
	       Queue<Integer> q2 = new Queue<>();
	       q2.enqueue(0);
	       assertEquals(3, sap.length(q1, q2));
	       assertEquals(0, sap.ancestor(q1, q2));
	}
}
