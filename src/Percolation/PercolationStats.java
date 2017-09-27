/*	Assignment: A01 - Percolation - CSIS-2420-004
 * 	Authors: Ning Zhang and Michael Gray
 *  Instructor: Gene Riggs
 *  Date: January, 2017
 */

package Percolation;
import edu.princeton.cs.algs4.*;

//PercolationStats call performs a series of experiments on the Percolation class 
//in order to find mean, standard deviation and the lower and upper bonds where we are confident
//the grid will percolate
public class PercolationStats {
	public Percolation myPer;
	//counts the number of open sites
	private int openCount;
	//retains the value of number of independent experiments or 'T' in PercolationsStats
	private double testTime;
	//stores data of experiments so that it can be used to find standard deviation and mean
	private double[] dataSet;

	//perform T independent experiments on and N-by-N grid 
	public PercolationStats(int N,int T){
		if(N<=0||T<=0){
			throw new IllegalArgumentException("Only non negative integers maybe passed for N and T");
		}else{
		
			//retain the value of how many independent experiments are performed
			testTime = T;
			//creates a T sized array to store all data from experiments to use in results. 
			dataSet = new double[T];			
			int dataSetIndex = 0;
			
			//iterates T times on the N by N grid using Percolation() to find the percolation probability
			while (T-->0){
								
				openCount = 0;				
				myPer = new Percolation(N);
				
				while(!myPer.percolates()){
					int a = StdRandom.uniform(0, N);
					int b = StdRandom.uniform(0, N);
				
					if(!myPer.isOpen(a,b)){
						myPer.open(a,b );
						openCount +=1;
					}
				}
				//Stores (# of open sites) / (Grid size ^2)  into the array on each trial to find computational experiment data 
				dataSet[dataSetIndex] = (double)openCount/Math.pow(N, 2);
				dataSetIndex++;
				
			}
			
		}
	}
	
	//sample mean of percolation threshold
	public double mean(){ 
		return StdStats.mean(dataSet);
	}
	
	//sample standard deviation of percolation threshold
	public double stddev(){
		return StdStats.stddev(dataSet);
	}
	
	//low end-point of 95% confidence interval
	public double confidenceLow(){
		return mean()-((1.96*stddev())/Math.sqrt(testTime));
	}
	
	//high end-point of 95% confidence interval
	public double confidenceHigh(){		
		return mean()+((1.96*stddev())/Math.sqrt(testTime));
	}
	
	//This main method can be used to input test data for an N by N grid and run a certain number of experiments
	//that can be viewed in our system out for clarity of the user. It is only used to test PercolationStats and
	//not necessary for its operation
	public static void main(String[] args) {

		int Ngrid = 200;
		int numExperiments = 100;
		PercolationStats myPer = new PercolationStats(Ngrid, numExperiments);
		System.out.println("Example values after creating PercolationStats(" + Ngrid + "," + numExperiments + ")");
		System.out.println("mean()    = " + myPer.mean());
		System.out.println("stddev()  = " + myPer.stddev());
		System.out.println("confidenceLow()= " + myPer.confidenceLow());
		System.out.println("confidenceHigh()=" + myPer.confidenceHigh());
	}	

}
