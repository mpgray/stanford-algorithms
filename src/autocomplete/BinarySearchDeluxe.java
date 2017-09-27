/**
 * Author: Alfredo Rodriguez && Michael Gray
 * Date: 3/5/2017
 * Class: CSIS 2420
 * Teacher: Gene Riggs
 */

package autocomplete;

import java.util.Comparator;
import java.lang.NullPointerException;


/**
 * The Class BinarySearchDeluxe.
 */
public class BinarySearchDeluxe
{

	/**
	 * First index of.
	 * Return the index of the first key in a[] that equals the search key, or -1 if no such key.
	 * @param <Key> the generic type
	 * @param a
	 * @param key
	 * @param comparator
	 * @return int
	 */
	public static <Key> int firstIndexOf(final Key[] a, final Key key, final Comparator<Key> comparator)
	{
		if (a == null || key == null || comparator == null)
		{
			throw new NullPointerException("Cannot find first key index");
		}
		//if not found
		if (a.length == 0)
		{
			return -1;
		}
		//Binary Search Algorithm
		int left = 0;
		int right = a.length - 1;
		while (left + 1 < right)
		{
			int mid = left + (right - left) / 2;
			if (comparator.compare(key, a[mid]) <= 0)
			{
				right = mid;
			} else
			{
				left = mid;
			}
		}
		if (comparator.compare(key, a[left]) == 0)
		{
			return left;
		}
		if (comparator.compare(key, a[right]) == 0)
		{
			return right;
		}
		return -1;

	}

	/**
	 * Last index of.
	 * Return the index of the last key in a[] that equals the search key, or -1 if no such key.
	 * @param <Key> the generic type
	 * @param a
	 * @param key the key
	 * @param comparator
	 * @return int
	 */
	public static <Key> int lastIndexOf(final Key[] a, final Key key, final Comparator<Key> comparator)
	{
		if (a == null || key == null || comparator == null)
		{
			throw new NullPointerException("Cannot find Last key index");
		}
		//if not found
		if (a.length == 0)
		{
			return -1;
		}
		//Binary Search Algorithm
		int left = 0;
		int right = a.length - 1;
		while (left + 1 < right)
		{
			int mid = left + (right - left) / 2;
			if (comparator.compare(key, a[mid]) < 0)
			{
				right = mid;
			} else
			{
				left = mid;
			}
		}
		if (comparator.compare(key, a[right]) == 0)
		{
			return right;
		}
		if (comparator.compare(key, a[left]) == 0)
		{
			return left;
		}
		return -1;
	}
}
