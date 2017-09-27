/******************************************************************************
 *  Name:  Michael Gray
 *
 *  Partner Name: Bryce Turner  
 *
 ******************************************************************************/



/******************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 *****************************************************************************/
The Node data structure has 4 main components: the 2D point, the rectangle
it encompasses, and the left and right node. The left and right node are set 
to null by default and set later after the insertion of another node. The 
rectangle is set to null when instantiated but set during the insertion 
process based on the previous nodes inserted. 

/******************************************************************************
 *  Describe your method for range search in a kd-tree.
 *****************************************************************************/
 The range search uses a queue and separate private method to recursively search
 the KD tree and find nodes which are contained in the rectangle. If the node
 is contained, a recursive search is performed on the found node and child nodes.
 Any found within the rectangle are enqueued and returned. This is important
 since a parent node contained in the rectangle does not mean that a child
 node is contained within the rectangle. 

/******************************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 *****************************************************************************/
The range search is performed by a slight modification of the brute force 
method used in the PointST class. Instead of iterating through the entire list,
a queue is used which adds the left and right node of the point discovered with
the "best" current distance from the desired point. 

/******************************************************************************
 *  Using the 64-bit memory cost model from the textbook and lecture,
 *  give the total memory usage in bytes of your 2d-tree data structure
 *  as a function of the number of points N. Use tilde notation to
 *  simplify your answer (i.e., keep the leading coefficient and discard
 *  lower-order terms).
 *
 *  Include the memory for all referenced objects (including
 *  Node, Point2D, and RectHV objects) except for Value objects
 *  (because the type is unknown). Also, include the memory for
 *  all referenced objects.
 *
 *  Justify your answer below.
 *
 *****************************************************************************/

bytes per Point2D: 2 doubles, object overhead
Point2D: 8+8+8 = 24 bytes

bytes per RectHV: 4 double, object overhead
RectHV = 8+8+8+8+8 = 40 bytes

bytes per KdTree of N points: int+object overhead + N*node = N*(RectHV, Point2D), object, int
KdTree = 8 + 4 + N(40 + 24) = 12 + 64N, round up to 8m gives 16 + 64N


/******************************************************************************
 *  How many nearest neighbor calculations can your brute-force
 *  implementation perform per second for input100K.txt (100,000 points)
 *  and input1M.txt (1 million points), where the query points are
 *  random points in the unit square? Show the math how you used to determine
 *  the operations per second. (Do not count the time to read in the points
 *  or to build the 2d-tree.)
 *
 *  Repeat the question but with the 2d-tree implementation.
 *****************************************************************************/

                       calls to nearest() per second
                     brute force               2d-tree
                     ---------------------------------
input100K.txt        148                     130

input1M.txt          9                        13

This was determined using the stopwatch and a counter. For this reason, we can 
reasonably expect the numbers to be slightly lower (since we are incrementing
are an int every time in the loop) but the comparison between the two is 
reasonably meaningful. We see that larger datasets benefit from the KD tree methods 
while a smaller dataset can be parsed in approximately the same amount of time.


/******************************************************************************
 *  Known bugs / limitations.
 *****************************************************************************/
The nearest method in the KD tree doesn't quite parse correctly, due to 

/******************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 *****************************************************************************/
 Contributions were equal for this project. Michael contributed the early portion 
 (PointST and some testing) while Bryce focused on the KD Tree. Discussions, 
 debugging, and planning was done equally.


/******************************************************************************
 *  List any other comments here. Feel free to provide any feedback   
 *  on  how helpful the class meeting was and on how much you learned 
 * from doing the assignment, and whether you enjoyed doing it.
 *****************************************************************************/