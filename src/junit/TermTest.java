package junit;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import autocomplete.Term;

/**
 * Author: Alfredo Rodriguez && Michael Gray
 * Date: 3/5/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */

public class TermTest {
	
	private Term[] terms;
	private final Term[] originalTerms = {new Term("their", 2820265.0),
			new Term("they", 3340398.0), new Term("there", 1961200.0),
			new Term("them", 2509917.0), new Term("thereby", 2446500.0)};
	
	@Before
	public void setUp() throws Exception {
		terms = Arrays.copyOf(originalTerms, originalTerms.length);
	}
	
	@Test(expected = NullPointerException.class)
	public void testTerm_nullQuery() {
		new Term(null, 1);
	}
	
	@Test(expected = IllegalArgumentException .class)
	public void testTerm_weightNegative() {
		new Term("negative", -1);
	}
	
	@Test
	public void testTerm() {
		Term automobile = new Term("automobile", 6197.0);
		assertEquals("6197.0\tautomobile", automobile.toString());
	}
	
	@Test
	public void testByReverseOrder() {
		String termsReverseWeightOrder = "[3340398.0\tthey, 2820265.0\ttheir, " +
				"2509917.0\tthem, 2446500.0\tthereby, 1961200.0\tthere]";
		Arrays.sort(terms, Term.byReverseWeightOrder());
		
		assertEquals(termsReverseWeightOrder, Arrays.toString(terms));
	}
	
	@Test(expected = IllegalArgumentException .class)
	public void testByPrefixOrder_negativeR() {
		Term.byPrefixOrder(-1);
	}
	
	@Test
	public void testByPrefixOrder() {
		String termsPrefixOrder5 = "[2820265.0\ttheir, 2509917.0\tthem, " +
				"1961200.0\tthere, 2446500.0\tthereby, 3340398.0\tthey]";
		Arrays.sort(terms, Term.byPrefixOrder(5));
		
		assertEquals(termsPrefixOrder5, Arrays.toString(terms));
	}
	
	@Test
	public void testCompareTo() {
		String termsNaturalOrder = "[2820265.0\ttheir, " +
				"2509917.0\tthem, 1961200.0\tthere, 2446500.0\tthereby, 3340398.0\tthey]";
		Arrays.sort(terms);
		
		assertEquals(termsNaturalOrder, Arrays.toString(terms));
	}
	
	@Test
	public void testToString() {
		assertEquals("3340398.0\tthey", terms[1].toString());
	}
}
