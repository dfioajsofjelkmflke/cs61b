package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /**
     * find : find the parent of the item;
     * union : connect two item;
     * connected : return if two items are connected;
     * count : return the number of union sets;
     * */
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private int openSites = 0;
    private final int N;
    private final int top;
    private final int bottom;
    public Percolation(int N){
        // create N-by-N grid, with all sites initially blocked
        if(N <=0){
            throw new IllegalArgumentException();
        }
        this.N = N;
        int sitesNumber = N*N;
        top = sitesNumber +1;
        bottom = sitesNumber +2;
        uf = new WeightedQuickUnionUF(sitesNumber);
        grid = new boolean[N][N];
        for(int i=0;i<N;i+=1){
            uf.union(top, i);
            uf.union(bottom, sitesNumber - i);
        }
    }
    public void open(int row, int col){
        // open the site (row, col) if it is not open already
        if(row<0 || row>=N || col<0 || col>=N){
            throw new IllegalArgumentException();
        }
        grid[row][col] = true;
        openSites += 1;
        int n = row*N + col;
        // if there is A open site around , connect it
        int leftCol = (col -1 >=0 ) ? col-1 : col;
        int rightCol = (col+1<N) ? col+1 : col;
        int upRow = (row-1 >=0) ? row-1 : row;
        int downRow = (row+1<N) ? row+1 : row;
        if(isOpen(row, leftCol)){
            uf.union(n, row*N + leftCol);
        }
        if(isOpen(row, rightCol)){
            uf.union(n, row*N + rightCol);
        }
        if(isOpen(upRow, col)){
            uf.union(n, (row-1)*N + col);
        }
        if(isOpen(downRow, col)){
            uf.union(n, (row+1)*N + col);
        }
    }
    public boolean isOpen(int row, int col){
        if(row<0 || row>=N || col<0 || col>=N){
            throw new IllegalArgumentException();
        }
        // is the site (row, col) open?
        return grid[row][col];
    }
    public boolean isFull(int row, int col){
        // is the site (row, col) full?
        // return true if the site connected to any site on the top row
        if(row<0 || row>= N || col<0 || col>= N ){
            throw new IllegalArgumentException();
        }
        if(row == N-1){
        }
        return uf.connected(top, row*N + col);
    }
    public int numberOfOpenSites(){
        //number of open sites
        return openSites;
    }
    public boolean percolates(){
        // does the system percolate?
        return uf.connected(top, bottom);
    }
    public static void main(String[] args){
        // use for unit testing (not required)
        Percolation p = new Percolation(5);
    }
}
