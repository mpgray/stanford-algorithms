package JUTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Percolation.PercolationStats;

//Test PercolationStats output data within a certain threshold of expected
//output to make sure the generated data is around what is expected.
// these test use N= 200, T = 100 and N = 2, T = 100000 because the expected output is known within the threshold
public class PercolationStatsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Test
	public void meanTest() {
		PercolationStats per1 = new PercolationStats(200,100);
		double expected = 0.592993;
		double actual = per1.mean();
		assertEquals(expected, actual, 0.1);
		
	}
	
	@Test
	public void stddevTest(){
		PercolationStats per2 = new PercolationStats(200,100);
		double expected  =  0.00876990421552567;
		double actual = per2.stddev();
		assertEquals(expected,actual,0.005);
	}
	
	@Test
	public void confidenceLowTest(){
		PercolationStats per3 = new PercolationStats(200,100);
		double expected = 0.5912745987737576;
		double actual = per3.confidenceLow();
		assertEquals(expected,actual,0.01);
	}
	
	@Test
	public void confidenceHighTest(){
		PercolationStats per4 = new PercolationStats(200,100);
		double expected = 0.5947124012262428;
		double actual = per4.confidenceHigh();
		assertEquals(expected, actual,0.01);
	}
	
	@Test
	public void meanTestBigT() {
		PercolationStats per1 = new PercolationStats(2,100000);
		double expected = 0.6669475;
		double actual = per1.mean();
		assertEquals(expected, actual, 0.1);
		
	}
	
	@Test
	public void stddevTestBigT(){
		PercolationStats per2 = new PercolationStats(2,100000);
		double expected  =  0.11775205263262094;
		double actual = per2.stddev();
		assertEquals(expected,actual,0.005);
	}
	
	@Test
	public void confidenceLowTestBigT(){
		PercolationStats per3 = new PercolationStats(2,100000);
		double expected = 0.666217665216461;
		double actual = per3.confidenceLow();
		assertEquals(expected,actual,0.01);
	}
	
	@Test
	public void confidenceHighTestBigT(){
		PercolationStats per4 = new PercolationStats(2,100000);
		double expected = 0.6676773347835391;
		double actual = per4.confidenceHigh();
		assertEquals(expected, actual,0.01);
	}
}
