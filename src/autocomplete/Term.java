/**
 * Author: Alfredo Rodriguez && Michael Gray
 * Date: 3/5/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */

package autocomplete;

import java.util.Comparator;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;

/**
 * The Class Term.
 *
 */
public class Term implements Comparable<Term>
{

	private String query;
	private double weight;

	/**
	 * Initialize a term with the given query string and weight.
	 * @param query the string queried
	 * @param weight the weight given by key
	 *
	 */
	public Term(String query, double weight)
	{
		if (query == null)
		{
			throw new NullPointerException("Your query cannot be null");
		}
		if (weight < 0)
		{
			throw new IllegalArgumentException("Weight cannot be negative");
		}
		this.query = query;
		this.weight = weight;
	}

	/**
	 *
	 * Compare the terms in descending order by weight.
	 * @return the comparator
	 */
	public static Comparator<Term> byReverseWeightOrder()
	{
		return new Comparator<Term>()
		{
			@Override public int compare(Term term1, Term term2)
			{
				//compares weights and returns value based on weights
				if (term1.weight > term2.weight)
				{
					return -1;
				}
				if (term1.weight == term2.weight)
				{
					return 0;
				}

				else
				{//term1.weight < term2.weight
					return 1;
				}
			}

		};

	}

	/**
	 * Compare the terms in lexicographic order but using only the first r characters of each query.
	 *
	 * @param r
	 * @return the comparator
	 */
	public static Comparator<Term> byPrefixOrder(final int r)
	{
		return new Comparator<Term>()
		{
			@Override public int compare(Term term1, Term term2)
			{
				//the length of term1 and term2 must be less then r or be made length of r
				if (term1.query.length() < r && term2.query.length() < r)
					return term1.query.compareTo(term2.query);
				else if (term1.query.length() < r)
					return term1.query.compareTo(term2.query.substring(0, r));
				else if (term2.query.length() < r)
					return term1.query.substring(0, r).compareTo(term2.query);
				else
					return term1.query.substring(0, r).compareTo(term2.query.substring(0, r));
			}
		};

	}

	/**
	 * Compare the terms in lexicographic order by query.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override public int compareTo(Term that)
	{
		String currQuery = this.query;
		String prevQuery = that.query;
		return currQuery.compareTo(prevQuery);
	}

	/**
	 * Return a string representation of the term in the following format:
	 * the weight, followed by a tab, followed by the query.
	 * @see java.lang.Object#toString()
	 */
	@Override public String toString()
	{
		return String.format("%.2f\t%s", this.weight, this.query);
	}
}