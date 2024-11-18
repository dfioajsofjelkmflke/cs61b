package hw2;

import org.junit.experimental.theories.internal.ParameterizedAssertionError;

public class PercolationStats {
    private Percolation per;
    private int[] items;
    public PercolationStats(int N, int T, PercolationFactory pf){
        /** perform T independent experiments on an N-by-N grid
         * N : size of the gird
         * T : repeat numbers
         */
        items = new int[T];
        for(int i=0;i<T;i+=1){
            per = pf.make(N);
            items[i]= per.numberOfOpenSites();
        }
    }
    public double mean(){
        //sample mean of percolation threshold
        double average = 0.0;
        for(int i=0;i<items.length;i+=1){
            average += items[i];
        }
        average = average/items.length;
        return average;
    }
    public double stddev(){
        //sample standard deviation of percolation threshold
        double result = 0.0;
        double average = mean();
        for(int i=0;i<items.length;i+=1){
            result += Math.pow(items[i]-average, 2);
        }
        result = result/(items.length-1);
        return result;
    }
    public double confidenceLow(){
        //low endpoint of 95% confidence interval
        double average = mean();
        return average - 1.96*stddev()/Math.sqrt(items.length);
    }
    public double confidenceHigh(){
        //high endpoint of 95% confidence interval
        double average = mean();
        return average + 1.96*stddev()/Math.sqrt(items.length);
    }
}
