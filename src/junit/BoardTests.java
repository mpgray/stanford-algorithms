/**
 * @author Jared Edwards && Michael Gray
 * Date: 3/26/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */
package junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import puzzle.Board;

public class BoardTests {


	    @Test
	    public void testisSolvable1() {
	        int[][] a1 = {{4, 0, 7},{1,2,5},{3,6,8}};
	        Board testBoard = new Board(a1);
	        assertTrue(testBoard.isSolvable());
	    }

	    @Test
	    public void testisSolvable2() {
	    	//puzzle3x3-unsolvable.txt
	        int[][] a2 = {{1, 2, 3},{4,6,5},{7,8,0}};
	        Board testBoard = new Board(a2);
	        assertFalse(testBoard.isSolvable());
	    }
	    
	    @Test
	    public void testisSolvable3() {
	    	//puzzle50.txt
	        int[][] a3 = {{2, 9, 3, 5},{8,11,12,7},{15,4,0,13},{6,1,10,14}};
	        Board testBoard = new Board(a3);
	        assertTrue(testBoard.isSolvable());
	    }
	    
	    @Test
	    public void testisGoal1() {
	    	//completed board - is the goal
	        int[][] a1 = {{1, 2, 3},{4,5,6},{7,8,0}};
	        Board testBoard = new Board(a1);
	        assertTrue(testBoard.isGoal());
	    }
	    
	    @Test
	    public void testisGoal2() {
	    	//puzzle50.txt - not the goal
	        int[][] a2 = {{2, 9, 3, 5},{8,11,12,7},{15,4,0,13},{6,1,10,14}};
	        Board testBoard = new Board(a2);
	        assertFalse(testBoard.isGoal());
	    }
	    
	    @Test
	    public void testHamming() {
	    	//puzzle50.txt 
	        int[][] a1 = {{2, 9, 3, 5},{8,11,12,7},{15,4,0,13},{6,1,10,14}};
	        Board testBoard = new Board(a1);

	        assertEquals(14,testBoard.hamming());
	    }
	    
	    @Test
	    public void testManhattan() {
	    	//
	    	//puzzle50.txt 
	        int[][] a1 = {{2, 9, 3, 5},{8,11,12,7},{15,4,0,13},{6,1,10,14}};
	        Board testBoard = new Board(a1);
	        assertEquals(38,testBoard.manhattan());
	    }
	    
	    @Test
	    public void testEquals1() {
	    	//puzzle50.txt 
	        int[][] a1 = {{2, 9, 3, 5},{8,11,12,7},{15,4,0,13},{6,1,10,14}};
	        Board testBoard = new Board(a1);
	        assertTrue(testBoard.equals(testBoard));
	    }
	    
	    @Test
	    public void testEquals2() {
	    	//
	    	//puzzle50.txt 
	        int[][] a1 = {{2, 9, 3, 5},{8,11,12,7},{15,4,0,13},{6,1,10,14}};
	        Board testBoard = new Board(a1);
	    	ArrayList<Board> neighbors = (ArrayList<Board>) testBoard.neighbors();
	    	for(Board b: neighbors){
	    		assertFalse(b.equals(testBoard));
	    	}
	    }	    
	
}
