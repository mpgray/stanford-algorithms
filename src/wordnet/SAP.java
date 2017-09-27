/*
 * Authors: Michael Gray & Chad Seale
 * 
 * 
 * 
 */
package wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.Stack;

public class SAP {
	private Digraph graph;

	
    public SAP(Digraph G){
    	if (G == null) throw new NullPointerException("Digraph G can't be null");
	    graph = new Digraph(G);
    }

    /**
     * Is the digraph a directed acyclic graph?
     * boolean true if it is a directed acyclic graph
     */
    public boolean isDAG(){
	    return !new DirectedCycle(graph).hasCycle();
    }

    /**
     * Is the digraph a rooted DAG?
     * boolean true if yes
     */
    public boolean isRootedDAG(){
	    if(!isDAG()) return false;
	   
	    // Find the potential 'root' 
	    DepthFirstOrder dfo = new DepthFirstOrder(this.graph);
	    Integer rootVertex = dfo.post().iterator().next();
	   
	    // Check if all vertices have a path to the root
	    DepthFirstDirectedPaths dfdp = new DepthFirstDirectedPaths(graph.reverse(), rootVertex);
	    for(int i=0; i<graph.V(); i++){
		    if(!dfdp.hasPathTo(i)) return false;
	    }
	    return true;
    }

    /**
     * length of the shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w){
    	Stack<Integer> vStack = new Stack<>();
    	vStack.push(v);
    	Stack<Integer> wStack = new Stack<>();
    	wStack.push(w);
	    return ancestorAndLength(vStack, wStack)[1];
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral path -1 if no such path
     */
    public int ancestor(int v, int w){
    	Stack<Integer> vStack = new Stack<>();
    	vStack.push(v);
    	Stack<Integer> wStack = new Stack<>();
    	wStack.push(w);
	    return ancestorAndLength(vStack, wStack)[0];
    }
   
    /**
     * length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w){
	    return ancestorAndLength(v, w)[1];
    }

    /**
     * length of the shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
	    return ancestorAndLength(v, w)[0];
    }

    private int[] ancestorAndLength(Iterable<Integer> v, Iterable<Integer> w){
	    // Data Structures to find the paths
	    BreadthFirstDirectedPaths vPaths = new BreadthFirstDirectedPaths(graph, v);
	    BreadthFirstDirectedPaths wPaths = new BreadthFirstDirectedPaths(graph, w);
	   
	    DepthFirstOrder DFO = new DepthFirstOrder(graph);
	        
	    int ancestor = -1;
	    int length = -1;

	    for(int i: DFO.reversePost()){
		    if(vPaths.hasPathTo(i) && wPaths.hasPathTo(i)){
			    int currentLength = vPaths.distTo(i) + wPaths.distTo(i);
			    if(currentLength < length || ancestor == -1){
				    ancestor = i;
				    length = currentLength;
			    }else break;
		    }
	    }
	   
	    int[] ancestorAndLength = {ancestor, length};
	    return ancestorAndLength;
    }
}
