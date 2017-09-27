package junit;

import autocomplete.BinarySearchDeluxe;
import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Author: Alfredo Rodriguez && Michael Gray
 * Date: 3/5/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */
public class BinarySearchDeluxeTest extends BinarySearchDeluxe
{
	private String[] a = {"a", "a", "c", "c", "c", "c", "c", "h"};
	
	@Test
	public void testFirstIndexOf_BeginningOfArray()
	{
		int expectedValue = 0;
		int actualValue = BinarySearchDeluxe.firstIndexOf(a, "a", String.CASE_INSENSITIVE_ORDER);
		assertEquals(expectedValue, actualValue);
		
	}
	
	@Test
	public void testFirstIndex_c()
	{
		int expectedValue = 2;
		int actualValue = BinarySearchDeluxe.firstIndexOf(a, "c", String.CASE_INSENSITIVE_ORDER);
		assertEquals(expectedValue, actualValue);
		
	}
	
	@Test
	public void testFirstIndex_singleElement()
	{
		int expectedValue = 7;
		int actualValue = BinarySearchDeluxe.firstIndexOf(a, "h", String.CASE_INSENSITIVE_ORDER);
		assertEquals(expectedValue, actualValue);
		
	}
	
	@Test
	public void testFirstIndex_lastElement()
	{
		int expectedValue = 7;
		int actualValue = BinarySearchDeluxe.firstIndexOf(a, "h", String.CASE_INSENSITIVE_ORDER);
		assertEquals(expectedValue, actualValue);
		
	}
	
	
	@Test
	public void testLastIndexOf()
	{
		int expectedValue = 7;
		int actualValue = BinarySearchDeluxe.lastIndexOf(a, "h", String.CASE_INSENSITIVE_ORDER);
		assertEquals(expectedValue, actualValue);
		
	}
	
	@Test
	public void testLastIndexOf_LastOfArray()
	{
		int expectedValue = 6;
		int actualValue = BinarySearchDeluxe.lastIndexOf(a, "c", String.CASE_INSENSITIVE_ORDER);
		assertEquals(expectedValue, actualValue);
	}
	
}
