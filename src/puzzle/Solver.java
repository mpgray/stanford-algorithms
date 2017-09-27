/**
 * @author Jared Edwards && Michael Gray
 * Date: 3/26/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */
package puzzle;


import java.util.ArrayList;
import java.util.Collections;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
	private MinPQ<Node> toVisit = new MinPQ<Node>();
	private ArrayList<Node> visited = new ArrayList<Node>();
	private Node finalNode;
	
	private class Node implements Comparable<Node> {
		private Board board;
		private int moves;
		private int distance; // Manhattan Distance
		private Node previous;	
		
		public Node(Board board, int moves, Node previous){
			this.board = board;
			this.moves = moves;
			this.previous = previous;
			this.distance = board.manhattan();
		}
		@Override
		public int compareTo(Node other) {
			// Calculates which node is closer to what we want by 
			// adding the moves and distance together, and comparing the values.
			// A smaller value will be closer to the goal.
			int thisValue = this.moves + this.distance;
			int otherValue = other.moves + other.distance;
			return thisValue - otherValue;
		}
		
	}
	
    public Solver(Board initial){
    	// find a solution to the initial board (using the A* algorithm)
    	if(initial == null){
    		throw new java.lang.NullPointerException();
    	}
    	if(!initial.isSolvable()){
    		throw new java.lang.IllegalArgumentException();
    	}
    	
    	Node currentNode = new Node(initial, 0, null);
    	
    	while(!currentNode.board.isGoal()){
        	visited.add(currentNode);
       
	    	Iterable<Board> neighborList = currentNode.board.neighbors();
	    	for(Board b: neighborList){
	    		if(!this.visitCheck(b)){ //If the board has not been visited
	    			Node neighborNode = getFromToVisit(b);
	    			if(neighborNode == null){
	    				neighborNode = new Node(b, currentNode.moves +1, currentNode);
		    			toVisit.insert(neighborNode);
	    			}
	    			else{
	    				if(currentNode.moves+1 < neighborNode.moves){ //Checks if path through current node is shorter for neighbor node
	    					neighborNode.previous = currentNode;	  //Reparents neighbor node
	    				}
	    			}
	    		}
	    	}
	    	// The current best board, based off of moves+manhattanDistance
	    	currentNode = toVisit.delMin();
    	}
    	finalNode = currentNode;
    }
    public int moves(){
    	// min number of moves to solve initial board
    	return finalNode != null ? finalNode.moves : 0;
    }
    public Iterable<Board> solution(){
    	// sequence of boards in a shortest solution
    	if (finalNode == null){
    		return null;
    	}
    	ArrayList<Board> sol = new ArrayList<Board>();
    	Node node = finalNode;
    	while(node != null){
    		sol.add(node.board);
    		node = node.previous;
    	}
    	Collections.reverse(sol);
    	return sol;
    }
    public static void main(String[] args){
    	// solve a slider puzzle (given below) 
    	// create initial board from file
    	String file = args.length > 0 ? args[0]: "ftp://ftp.cs.princeton.edu/pub/cs226/8puzzle/puzzle16.txt";
        In in = new In(file);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // check if puzzle is solvable; if so, solve it and output solution
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

        // if not, report unsolvable
        else {
            StdOut.println("Unsolvable puzzle");
        }
    }
    private boolean visitCheck(Board b){
    	//Checks if the board has already been visited
    	for(Node n: visited){
    		if (n.board.equals(b)){
    			return true;
    		}
    	}
    	return false;
    }
    
    private Node getFromToVisit(Board b){
    	// returns a node from the toVisit list based on the board.
    	for(Node n: toVisit){
    		if (n.board.equals(b)){
    			return n;
    		}
    	}
    	return null;    	
    }
}
