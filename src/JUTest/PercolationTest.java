package JUTest;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import Percolation.Percolation;

//Tests the expected reactions to several classes within Percolation to determine 
//if they are returning expected values.
public class PercolationTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	
	@Test
	public void isOpenTest(){
		Percolation per = new Percolation(10);
		boolean actual = per.isOpen(2, 9);
		boolean expected = false;
		assertEquals(expected,actual);
	}
	
	@Test
	public void OpenTest(){
		Percolation per = new Percolation(10);
		boolean expected = false;
		per.open(1, 0);
		boolean actual = per.isOpen(0, 0);
		assertEquals(expected, actual);
	}
	
	@Test
	public void isFullTest(){
		Percolation per1 = new Percolation(5);
		for(int i =0;i<5;i++){
			for(int j=0;j<5;j++){
				per1.open(i, j);
			}
		}
		boolean actual = per1.isFull(4,4);
		assertEquals(true, actual);
	}
	
	@Test
	public void percolatesTest(){
		Percolation per = new Percolation(5);
		for(int i =0;i<5;i++){
			for(int j=0;j<5;j++){
				per.open(i, j);
			}
		}
		
		boolean expected = true;
		boolean actual = per.percolates();
		assertEquals(expected, actual);
	}

}
