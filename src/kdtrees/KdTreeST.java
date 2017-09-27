package kdtrees;



import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import static org.junit.Assert.*;
import org.junit.Test;

class Node{
    Point2D point;         
    RectHV rect;       
    Node left;         
    Node right;           
    public Node(Point2D p){
        point=p;
        left=null;
        right=null;
        rect=null;
    }
}


public class KdTreeST {
    
    int treeSize;
    Node rootNode;
    public KdTreeST(){
        treeSize=0;
        rootNode=null;
    }                               
    public boolean isEmpty(){
        return treeSize==0;
    }                      
    public int size(){
        return treeSize;
    }                         
    public void put(Point2D p){
        Node newNode=new Node(p);
        if(rootNode==null){
            RectHV r=new RectHV(0,0,1,1);
            newNode.rect=r;
            rootNode=newNode;
            treeSize++;
            return ;
        }
        if(this.contains(p))
            return ;
        treeSize++;
        int level=0;
        double x0,x1,y0,y1;
        Node n=rootNode;
        boolean inserted=false;
        while(!inserted){
            x0=n.rect.xmin();
            y0=n.rect.ymin();
            x1=n.rect.xmax();
            y1=n.rect.ymax();
            if(level%2==0){                  
                if(p.x()<n.point.x()){
                    if(n.left!=null)
                        n=n.left;
                    else{
                        RectHV r= new RectHV(x0,y0,n.point.x(),y1);                                                     
                        newNode.rect=r;
                        n.left=newNode;
                        inserted=true;
                        break;
                    }
                }    
                else{
                    if(n.right!=null)
                        n=n.right;
                    else{
                        RectHV r=   new RectHV(n.point.x(),y0,x1,y1);                                                   
                        newNode.rect=r;
                        n.right=newNode;
                        inserted=true;
                        break;
                    }
                }    
            }
            else{                            
                if(p.y()<n.point.y()){
                    if(n.left!=null)
                        n=n.left;
                    else{
                        RectHV r= new RectHV(x0,y0,x1,n.point.y());                         
                        newNode.rect=r;
                        n.left=newNode;
                        inserted=true;
                        break;
                    }
                }    
                else{
                   if(n.right!=null)
                        n=n.right;
                    else{
                        RectHV r= new RectHV(x0,n.point.y(),x1,y1);                        
                        newNode.rect=r;
                        n.right=newNode;
                        inserted=true;
                        break;
                    }
                }    
            }
            level++;
        }
    }              
    public boolean contains(Point2D p){
        if(rootNode==null)
            return false;
        int level=0;
        Node n=rootNode;
        while(n!=null){
            if(n.point.x()==p.x() && n.point.y()==p.y())
                return true;
            if(level%2==0){                  
                if(p.x()<n.point.x())
                    n=n.left;
                else
                    n=n.right;
            }
            else{                            
                if(p.y()<n.point.y())
                    n=n.left;
                else
                    n=n.right;
            }
            level++;
        }
        return false;
    }            
    private void treeDraw(Node n,int position,double xmin,double ymin,double xmax,double ymax){
        if(n==null)
            return ;
        if(position%2==0){
            double x=n.point.x();
            double y=n.point.y();
            
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            
            StdDraw.point(x,y);    
 
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();     
            
            StdDraw.line(x,ymin,x,ymax);
            treeDraw(n.left,position+1,xmin,ymin,x,ymax);
            treeDraw(n.right,position+1,x,ymin,xmax,ymax);
        }
        else{
            double x=n.point.x();
            double y=n.point.y();
            
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            
            StdDraw.point(x,y);    
 
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            
            StdDraw.line(xmin,y,xmax,y);
            treeDraw(n.left,position+1,xmin,ymin,xmax,y);
            treeDraw(n.right,position+1,xmin,y,xmax,ymax);
        }
    }
    
    public void draw(){
        StdDraw.rectangle(1,1,1,1);
        treeDraw(rootNode,0,0,0,1,1);
    }   
    
    public Iterable<Point2D> levelOrder() {
        Queue<Point2D> points = new Queue<Point2D>();
        Queue<Node> q = new Queue<Node>();
        q.enqueue(rootNode);
        while (!q.isEmpty()) {
            Node x = q.dequeue();
            if (x != null) {
            points.enqueue(x.point);
            q.enqueue(x.left);
            q.enqueue(x.right);
            }
        }
        return points;
    }
    private Iterable<Point2D> nodeRangeSearch(Node n,RectHV r){
        if(n==null)
            return null;
        Queue<Point2D> q=new Queue<Point2D>();
        if(r.contains(n.point))
            q.enqueue(n.point);
        if(n.left!=null && n.left.rect.intersects(r)){
            for(Point2D p :nodeRangeSearch(n.left,r)){
                q.enqueue(p);
            }
        }
        if(n.right!=null && n.right.rect.intersects(r)){
            for(Point2D p :nodeRangeSearch(n.right,r)){
                q.enqueue(p);
            }
        }
        return q;
    }
    
    public Iterable<Point2D> range(RectHV r){
        return nodeRangeSearch(rootNode,r);    
    }
    
    private double xDistance(Point2D p, Point2D d) {
    	return p.x() - d.x();
    }
    private double yDistance(Point2D p, Point2D d) {
    	return p.y() - d.y();
    }
    public Point2D nearest(Point2D p){
        if(treeSize==0)
            return null;
        Queue<Node> q=new Queue<Node>();
        Node n=rootNode;
        Point2D bestPoint=n.point;
        double best=p.distanceTo(n.point);
        q.enqueue(n);
        while(!q.isEmpty()){
            Node newNode=q.dequeue();
            if(newNode != null && p.distanceTo(newNode.point)<=best){
            	best=p.distanceTo(newNode.point);
                bestPoint=newNode.point;
            }
            if (newNode.left!= null)
            	q.enqueue(newNode.left);
            if (newNode.right!=null) 
            	q.enqueue(newNode.right);
        }
        return bestPoint;
    }   
    public int height() { 
    	return height(rootNode); 
    	}
    
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    public static void main(String[] args){
        In in = new In("src/project5/input100k.txt");
        //StdDraw.show(0);
        PointST<Integer> brute = new PointST<Integer>();
        KdTreeST kdtree = new KdTreeST();
        //Testing that both classes initial a 0 sized tree
        assertTrue(kdtree.size() == 0);
        assertTrue(brute.size() == 0);
        int counter = 0;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            //System.out.println("x "+x+" y "+y );
            Point2D p = new Point2D(x, y);
            kdtree.put(p);
            brute.put(p, counter);
            counter++;
        }
        //Tests the contains method fo the kdtree
        assertTrue(kdtree.contains(new Point2D(0.771410, 0.386939)));
        assertTrue(!kdtree.contains(new Point2D(0.0, 0.0)));
        assertTrue(brute.contains(new Point2D(0.771410, 0.386939)));
        assertTrue(!brute.contains(new Point2D(0.0, 0.0)));
        //Testing that the size is what we expect
        assertTrue(kdtree.size() == 100000);
        assertTrue(brute.size() == 100000);
        //The same values are contained in each data structure
        for (Point2D p: kdtree.levelOrder()) {
        brute.contains(p);	
        }
        //Testing the nearest method
        StdOut.print(kdtree.nearest(new Point2D(0.327990, 0.733888)).x());
       // assertTrue(kdtree.nearest(new Point2D(0.327990, 0.733888)).x() == 0.32799);
        assertTrue(brute.nearest(new Point2D(0.327990, 0.733888)).x() == 0.32799);
      
        
       // kdtree.draw();
        int counterKD = 0;
        Stopwatch s = new Stopwatch();
        while(s.elapsedTime() < 1) {
        	kdtree.nearest(new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0)));
        	counterKD++;
        }
        System.out.printf("2dtree with 100k: %d\n", counterKD);
        counterKD = 0;
        Stopwatch s1 = new Stopwatch();
        while(s1.elapsedTime() < 1) {
        	brute.nearest(new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0)));
        	counterKD++;
        }
        System.out.printf("Bruteforce with 100k: %d\n", counterKD);
        in = new In("src/project5/input1M.txt");
        KdTreeST kd2 = new KdTreeST();
        PointST<Integer> brute2 = new PointST<Integer>();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            //System.out.println("x "+x+" y "+y );
            Point2D p = new Point2D(x, y);
            kd2.put(p);
            brute2.put(p, counter);
            counter++;
        }
        counterKD = 0;
        Stopwatch s2 = new Stopwatch();
        while(s2.elapsedTime() < 1) {
        	kd2.nearest(new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0)));
        	counterKD++;
        }
        System.out.printf("2dtree with 1m: %d\n", counterKD);
        counterKD = 0;
        Stopwatch s3 = new Stopwatch();
        while(s3.elapsedTime() < 1) {
        	brute2.nearest(new Point2D(StdRandom.uniform(0.0, 1.0), StdRandom.uniform(0.0, 1.0)));
        	counterKD++;
        }
        System.out.printf("brute with 1m: %d\n", counterKD);
        
    }        
}
