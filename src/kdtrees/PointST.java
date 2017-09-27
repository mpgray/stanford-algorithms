package kdtrees;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RedBlackBST;

public class PointST<Value> {
	RedBlackBST<Point2D, Value> allItems;

	
	public PointST() {
		allItems = new RedBlackBST<Point2D, Value>();
		
	}
	
	public boolean isEmpty() {
		return allItems.isEmpty();
	}
	public int size() {
		return allItems.size();
	}
	public void put(Point2D p, Value v) {
	if (!allItems.contains(p)) {
		allItems.put(p, v);
	}
	}
	
	public Value get(Point2D p) {
		return allItems.get(p);
	}
	public boolean contains(Point2D p) {
		return allItems.contains(p);
	}
	public Iterable<Point2D> points() {
		return allItems.keys();
	}
	public Iterable<Point2D> range(RectHV rect) {
		Point2D low = new Point2D(rect.xmin(), rect.ymin());
		Point2D hi = new Point2D(rect.xmax(), rect.ymax());
		return allItems.keys(low, hi);
	}
	public Point2D nearest(Point2D p) {
		Point2D higher = allItems.ceiling(p);
		Point2D lower = allItems.floor(p);
		if (p.distanceTo(higher) > p.distanceTo(lower))
			return lower;
		else if (p.distanceTo(higher) < p.distanceTo(lower))
			return higher;
		else
			return null;
	}
	
}
