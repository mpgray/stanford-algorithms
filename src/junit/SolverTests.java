/**
 * @author Jared Edwards && Michael Gray
 * Date: 3/26/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */
package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import puzzle.Board;
import puzzle.Solver;

public class SolverTests {

	@Test
	public void testMoves() {
		//puzzele16.txt
        int[][] blocks = {{0, 1, 2, 3},{5,6,7,4},{9,10,11,8},{13,14,15,12}};

		Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        assertEquals(6,solver.moves());
	}
	
	@Test
	public void testSolution() {
        int[][] blocks = {{0, 1, 2, 3},{5,6,7,4},{9,10,11,8},{13,14,15,12}};

		Board initial = new Board(blocks);
        Solver solver = new Solver(initial);
        for (Board board : solver.solution()){
        		assertNotNull(solver.solution());
        		assertNotNull(board);
        }
	}
}

