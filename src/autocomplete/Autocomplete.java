/**
 * Author: Alfredo Rodriguez && Michael Gray
 * Date: 3/5/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */

package autocomplete;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import javax.swing.*;
import java.lang.NullPointerException;
import java.util.Arrays;

/**
 * The Class Autocomplete.
 */
public class Autocomplete
{

	private Term[] arrayTerms;

	/**
	 * Instantiates a new autocomplete.
	 * Initialize the data structure from the given array of terms.
	 * @param terms the terms
	 */

	public Autocomplete(Term[] terms)
	{
		if (terms == null)
		{
			JOptionPane.showMessageDialog(null, "There are no terms", "No Terms", JOptionPane.ERROR_MESSAGE);
			throw new NullPointerException("There are no terms");
		}
		this.arrayTerms = terms;
		Arrays.sort(arrayTerms); //orders the array of terms
	}

	/**
	 * All matches.
	 * Return all terms that start with the given prefix, in descending order of weight.
	 * @param prefix the prefix
	 * @return the term[]
	 */
	public Term[] allMatches(String prefix)
	{
		if (prefix == null)
		{
			throw new NullPointerException();
		}

		Term temp = new Term(prefix, 0);

		int begin = BinarySearchDeluxe.firstIndexOf(arrayTerms, temp, Term.byPrefixOrder(prefix.length()));
		int end = BinarySearchDeluxe.lastIndexOf(arrayTerms, temp, Term.byPrefixOrder(prefix.length()));
		if (begin == -1 || end == -1)
		{
			JOptionPane.showMessageDialog(null, "Nothing matches that term", "No Matches", JOptionPane.ERROR_MESSAGE);
			throw new NullPointerException("Nothing matches that term");
		}

		Term[] matches = new Term[end - begin + 1];
		matches = Arrays.copyOfRange(arrayTerms, begin, end);
		Arrays.sort(matches, Term.byReverseWeightOrder());
		return matches;

	}

	/**
	 * Number of matches.
	 * Return the number of terms that start with the given prefix.
	 * @param prefix the prefix
	 * @return the int
	 */
	public int numberOfMatches(String prefix)
	{
		if (prefix == null)
		{
			JOptionPane.showMessageDialog(null, "Nothing with that prefix", "No Matches", JOptionPane.ERROR_MESSAGE);
			throw new NullPointerException("Nothing with that prefix");
		}

		Term temp = new Term(prefix, 0);
		int begin = BinarySearchDeluxe.firstIndexOf(arrayTerms, temp, Term.byPrefixOrder(prefix.length()));
		int end = BinarySearchDeluxe.lastIndexOf(arrayTerms, temp, Term.byPrefixOrder(prefix.length()));
		return end - begin + 1;

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
		// read in the terms from a file
		String filename = args[0];
		In in = new In(filename);
		int N = in.readInt();
		Term[] terms = new Term[N];
		for ( int i = 0; i < N; i++ )
		{
			double weight = in.readDouble();       // read the next weight
			in.readChar();                         // scan past the tab
			String query = in.readLine();          // read the next query
			terms[i] = new Term(query, weight);    // construct the term
		}
		
		// read in queries from standard input and print out the top k matching terms
		int k = Integer.parseInt(args[1]);
		Autocomplete autocomplete = new Autocomplete(terms);
		while ( StdIn.hasNextLine() )
		{
			String prefix = StdIn.readLine();
			Term[] results = autocomplete.allMatches(prefix);
			for ( int i = 0; i < Math.min(k, results.length); i++ )
				StdOut.println(results[i]);
		}
	}

}
