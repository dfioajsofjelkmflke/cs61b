import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    @Test
    public void testnotequalChars(){
        boolean actual = offByOne.equalChars('%','-');
        assertFalse(actual);
    }
    @Test
    public void testequalChars(){
        boolean actual = offByOne.equalChars('a','b');
        assertTrue(actual);
    }
    @Test
    public void testeualChars(){
        boolean actual = offByOne.equalChars('a','B');
        assertFalse(actual);
    }


    // Your tests go here.
//    Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
}
