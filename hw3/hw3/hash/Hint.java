package hw3.hash;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
public class Hint {
    public static void main(String[] args) {
        System.out.println("The powers of 256 in Java are:");
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i=0;i<10;i+=1){
            numbers.add(StdRandom.uniform(1,10));
        }
        int total = 2;
        int x = StdRandom.uniform(1,10);
        for (int i : numbers) {
            System.out.println(i + "th power: " + total);
            total = total * 256 ;
        }
    }
} 
