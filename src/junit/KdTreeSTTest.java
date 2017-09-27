package junit;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdOut;
import kdtrees.KdTreeST;

public class KdTreeSTTest {

	@Test
	public void testKdTreeST() {
		KdTreeST kdt = new KdTreeST();
		assertNotNull(kdt);
	}

	@Test
	public void testIsEmpty() {
		KdTreeST kd = new KdTreeST();
		assertTrue(kd.isEmpty());	
	}
	@Test
	public void testIsEmpty1() {
		KdTreeST kd1 = new KdTreeST();
		Point2D testPoint = new Point2D(0.1, 0.1);
		kd1.put(testPoint);
		assertFalse(kd1.isEmpty());
		
	}

	@Test
	public void testSize() {
		KdTreeST kd2 = new KdTreeST();
		Point2D testPoint = new Point2D(0.1, 0.1);
		kd2.put(testPoint);
		assertEquals(1,kd2.size());
	}
	
	@Test
	public void testSize1() {
		KdTreeST kd3 = new KdTreeST();
		assertEquals(0,kd3.size());
	}
	
	@Test
	public void testSize2() {
		KdTreeST kd4 = new KdTreeST();
		In readIn = new In("src/project5/input100k.txt");
		String[] readStrings = readIn.readAllStrings();
		for(int i=0;i<readStrings.length;i+=2){
			Point2D testPoint = new Point2D(Double.parseDouble(readStrings[i]), Double.parseDouble(readStrings[i+1]));
			kd4.put(testPoint);
		}
		assertEquals(100000,kd4.size());

	}

	@Test
	public void testContains() {
		KdTreeST kd5 = new KdTreeST();
		Point2D testPoint = new Point2D(0.1, 0.1);
		kd5.put(testPoint);
		assertTrue(kd5.contains(testPoint));
	}
	
	@Test
	public void testContains2() {
		KdTreeST kd6 = new KdTreeST();
		Point2D existsPoint = new Point2D(0.095420, 0.786050);
		In readIn = new In("src/project5/input100k.txt");
		String[] readStrings = readIn.readAllStrings();
		for(int i=0;i<readStrings.length;i+=2){
			Point2D testPoint = new Point2D(Double.parseDouble(readStrings[i]), Double.parseDouble(readStrings[i+1]));
			kd6.put(testPoint);
		}
		assertTrue(kd6.contains(existsPoint));
	}

	

	@Test
	public void testPut() {
		KdTreeST kd8 = new KdTreeST();
		Point2D testPoint = new Point2D(0.1, 0.1);
		kd8.put(testPoint);
		assertNotNull(testPoint);
		
	}


}
