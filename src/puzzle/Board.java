/**
 * @author Jared Edwards && Michael Gray
 * Date: 3/26/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */
package puzzle;

import java.util.ArrayList;

public class Board {
	private int[][] tiles;
	private int N;
	
    public Board(int[][] blocks){     
    	// construct a board from an N-by-N array of blocks
    	// (where blocks[i][j] = block in row i, column j)
    	N = blocks[0].length;
    	tiles = new int[N][N];
    	for(int row = 0; row < N; row++){
    		for (int col=0; col < N; col++){
    			tiles[row][col] = blocks[row][col];
    		}
    	}
    }
    
    public int size(){                      
    	// board size N
    	return N;
    }
    
    public int hamming(){
    	// number of blocks out of place
    	int hammingCount = 0;
    	int value = 1;
    	for(int row = 0; row< N; row++){
    		for(int col = 0; col < N; col++){
    			if (tiles[row][col] != value++ && tiles[row][col] != 0){
    				hammingCount++;
    			}
    		}
    	}
    	return hammingCount;
    }
    
    public int manhattan(){
    	// sum of Manhattan distances between blocks and goal
    	int manhattanCount = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
            	int value = tiles[row][col];
                if (value != 0) {
                    int rowsAway = Math.abs(((value - 1) / N) - row);
                    int colsAway = Math.abs(((value - 1) % N) - col);
                    manhattanCount += rowsAway + colsAway;
                }
            }
        }
        return manhattanCount;
    }

    public boolean isGoal(){
    	// is this board the goal board?
    	int value = 1;
    	int endValue = N*N;
    	for (int row = 0; row < N; row++) {
    		for (int col = 0; col < N; col++) {
               	if(tiles[row][col] != value && value != endValue){
               		return false;
               	}
               	value++;
       	 	}	
    	}
    	return true;
    }

    public boolean isSolvable(){
    	// is this board solvable?
    	// create 1D array from 2D to find if it is solvable
    	int[] solveCheck = this.toOneD(tiles);
    	int zeroIndex = 0;
    	for(int i = 0; i< solveCheck.length; i++){
    		if(solveCheck[i] == 0)
    			zeroIndex = i;
    	}
    	int inversionCount = countInversions(solveCheck); 
        if (N%2 == 0){
        	//if board size is even
        	return inversionCount%2 + zeroIndex%N !=0;
        }
        else{
        	//if board size is odd
        	return inversionCount %2 == 0;
        }
    }
    private static int countInversions(int[] a) {
        return mergeSort(a, 0, a.length);
    }
    
    private static int mergeSort (int[] a, int low, int high) {
        if (low == high - 1) return 0;
        //faster then (low+high)/2 and probably won't overflow -- it is basically (low+high)/2^1
        int mid = (low + high)/2;

        return mergeSort (a, low, mid) + mergeSort (a, mid, high) + merge (a, low, mid, high);
    }

    private static int merge (int[] a, int low, int mid, int high) {
        int count = 0;
        int[] temp = new int[a.length];

       for (int i = low, lb = low, hb = mid; i < high; i++) {
            if (hb >= high || lb < mid && a[lb] <= a[hb]) {
                temp[i]  = a[lb++];
            } else {
                count = count + (mid - lb); 
                temp[i]  = a[hb++];
            } 
       }
       System.arraycopy(temp, low, a, low, high - low);
       return count;
    }

    
    public boolean equals(Object y){
    	// does this board equal y?
    	if(y.getClass() != this.getClass()){
    		return false;
    	}
    	Board yBoard = (Board) y;
    	if(yBoard.N != this.N){
    		return false;
    	}
    	for(int row = 0; row<N; row++){
    		for(int col = 0; col<N; col++){
    			int yT = yBoard.tiles[row][col];
    			int cT = this.tiles[row][col];
    			if(yT != cT){
    				return false;
    			}
    		}
    	}
    	return true;
    }

    public Iterable<Board> neighbors(){
    	// all neighboring boards
    	ArrayList<Board> neighbors = new ArrayList<Board>();
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                if (tiles[row][col] == 0) { //if a given point is the "blank" or zero tile
                    if (col > 0) { //if zero isn't on the far left
                        neighbors.add(getNeighbor(row, col, row, col - 1)); //exchanges the blank with the value on the left
                    }
                    if (col < N - 1) {  //if zero ins't on the far right
                    	neighbors.add(getNeighbor(row, col, row, col + 1)); //exchanges the blank with the value on the right
                    }
                    if (row > 0) { //if zero isn't the top row
                    	neighbors.add(getNeighbor(row, col, row - 1, col)); //exchanges the blank with the value above
                    }
                    if (row < N - 1) { //if zero isn't the bottom row
                    	neighbors.add(getNeighbor(row, col, row + 1, col)); //exchanges the blank with the value below
                    }
                    return neighbors;
                }
            }
        }
        return null;
    }

  
    public String toString() {
        // string representation of this board (in the output format specified below)
    	//This doens't look as good with non-single digit numbers
    	StringBuilder sb = new StringBuilder();
    	sb.append(N + "\n");
    	for(int row = 0; row<N; row++){
    		sb.append(" ");
    		for(int col = 0; col<N; col++){
    			sb.append(tiles[row][col] + "  ");
    		}
    		sb.append("\n");
    	}
    	return sb.toString();
    }
    
    private int[] toOneD(int[][] arr){
    	// create a 1d ray for easy comparison in select methods.
    	int[] oneD = new int[N*N];
    	int arrayCounter = 0;
    	for (int row = 0; row < tiles.length; row++) {
			for(int col = 0; col < tiles.length; col++){
         		oneD[arrayCounter] = tiles[row][col];
         		arrayCounter++;
			}
         }
    	return oneD;
    }
    
    private Board getNeighbor(int row, int col, int rowN, int colN){
    	//Creating private method because it looks cleaner in the neighbors method
    	//row and col are the position of the zero value
    	//rowN and colN are the position that we are exchanging the zero value with
    	Board neighbor = new Board(this.tiles);
    	int temp = tiles[row][col];  //Store the value we are exchanging
    	neighbor.tiles[row][col] = tiles[rowN][colN];
    	neighbor.tiles[rowN][colN] = temp;
    	return neighbor;
    }
    
    public static void main(String[] args){
    	// unit tests (not graded)
    	int n = 3;
    	int[][] arr = new int[n][n];
    	for(int row = 0; row<n; row++){
    		for(int col = 0; col<n; col++){
    			arr[row][col] = 1 + row * n + col;
    		}
    	}
    	arr[n-1][n-1] = 0;
    }
}