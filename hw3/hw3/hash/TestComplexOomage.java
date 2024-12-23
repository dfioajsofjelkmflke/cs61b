package hw3.hash;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;
public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    /* TODO: Create a list of Complex Oomages called deadlyList
     * that shows the flaw in the hashCode function.
     */
    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();

        // Your code here.
        List<Integer> param1= new ArrayList<>();
        List<Integer> param2= new ArrayList<>();
        List<Integer> param3= new ArrayList<>();
        List<Integer> param4= new ArrayList<>();
        List<Integer> param5= new ArrayList<>();
        List<Integer> param6= new ArrayList<>();
        List<Integer> param7= new ArrayList<>();
        List<Integer> param8= new ArrayList<>();
        List<Integer> param9= new ArrayList<>();
        for(int i=0;i<100;i+=1){
            param1.add(1);
            param2.add(1);
            param3.add(1);
            param4.add(1);
            param5.add(1);
            param6.add(1);
            param7.add(1);
            param8.add(1);
            param9.add(1);
        }
        deadlyList.add(new ComplexOomage(param1));
        deadlyList.add(new ComplexOomage(param3));
        deadlyList.add(new ComplexOomage(param4));
        deadlyList.add(new ComplexOomage(param5));
        deadlyList.add(new ComplexOomage(param6));
        deadlyList.add(new ComplexOomage(param7));
        deadlyList.add(new ComplexOomage(param8));
        deadlyList.add(new ComplexOomage(param9));
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 5));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
