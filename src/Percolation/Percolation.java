/*	Assignment: A01 - Percolation - CSIS-2420-004
 * 	Authors: Ning Zhang and Michael Gray
 *  Instructor: Gene Riggs
 *  Date: January, 2017
 */

package Percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;;

//this percolation class creates an N-by-N grid with no open blocks at first then is able to 
//check for open and full blocks as well as test for the percolation of those open blocks
// with this we can find percolation threshholds 
public class Percolation {
	private int [][]site;
	private boolean [][]openSite;
	private WeightedQuickUnionUF uf;

	// create N-by-N grid, with all sites blocked;
	public Percolation(int N){
		if(N<=0){
				throw new IllegalArgumentException("N should greater than 0");
				}
		uf = new WeightedQuickUnionUF((N*N)+3);
		openSite = new boolean[N][N];
		site = new int[N][N];
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				site[i][j] = i*N+j;
				openSite[i][j] = false;
			}	
		}
	}
	
	// open site(row i, column j) if it is not open already	
	public void open(int i,int j) throws IndexOutOfBoundsException{
		openSite[i][j] = true;
		unionUpAndDown(i, j);
		unionLeftAndRight(i,j);
			
	}
	
	// is site(row i, column j) open?	
	public boolean isOpen (int i,int j)throws IndexOutOfBoundsException{
		return openSite[i][j];
	}
	
	// is site(row i, column j) full?	
	public boolean isFull(int i,int j)throws IndexOutOfBoundsException{
		boolean check = true;
		int a= (site.length-1);
		while(a-->0){
			if(!isOpen(a,j))
				check = false;
		}
		a = (site.length-1);
		while(a-->0){
			if(!isOpen(i,a))
				check = false;
		}
		return check;
	}
	
	// does the system percolate?	
	public boolean percolates(){
		return uf.connected(site.length*site.length+1, site.length*site.length+2);
	}
	
	//these use WeightedQuickUnionUF class to determine open sites for percolation
	private void unionUpAndDown(int i, int j){
		if(i>0&&i<site.length-1){
			if(isOpen(i-1,j)&&!uf.connected(site[i-1][j], site[i][j])){
				uf.union(site[i-1][j], site[i][j]);}
			if(isOpen(i+1,j)&&!uf.connected(site[i+1][j], site[i][j])){
				uf.union(site[i+1][j],site[i][j]);
			}
		}else if(i==0){
				uf.union(site[i][j], site.length*site.length+1);
			if(isOpen(1,j)&&!uf.connected(site[1][j], site[0][j])){
				uf.union(site[1][j], site[0][j]);
			}
		}else if(i==site.length-1){
			uf.union(site[i][j], site.length*site.length+2);
			if(isOpen(i-1,j)&&!uf.connected(site[i-1][j], site[i][j])){
				uf.union(site[i-1][j], site[i][j]);
			}
		}
	}
	private void unionLeftAndRight(int i,int j){
		if(j>0&&j<site.length-1){
			if(isOpen(i,j-1)&&!uf.connected(site[i][j-1], site[i][j])){
				uf.union(site[i][j-1], site[i][j]);}
			if(isOpen(i,j+1)&&!uf.connected(site[i][j+1], site[i][j])){
				uf.union(site[i][j+1],site[i][j]);
			}
		}else if(j==0){
			if(isOpen(i,1)&&!uf.connected(site[i][1], site[i][j])){
				uf.union(site[i][1], site[i][j]);
			}
		}else if(j==site.length-1){
			if(isOpen(i,j-1)&&!uf.connected(site[i][j-1], site[i][j])){
				uf.union(site[i][j-1], site[i][j]);
			}
		}
	}
}
