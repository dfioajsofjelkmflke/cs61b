import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy2 = new OffByN(2);
    @Test
    public void testoffn1() {
        boolean actual = offBy2.equalChars('a','c');
        assertTrue(actual);
    }
    @Test
    public void testoffn2(){
        boolean actual = offBy2.equalChars('b','e');
        assertFalse(actual);
    }
}
