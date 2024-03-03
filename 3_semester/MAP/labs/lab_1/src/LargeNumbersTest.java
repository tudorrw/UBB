import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LargeNumbersTest {
    public LargeNumbers largeNumber;
    public int[] n1, n2, expected;
    public int digit;
    @BeforeEach public void setup() {
        largeNumber = new LargeNumbers();
    }

    @Test public void test_add() {
        n1 = new int[] { 1, 3, 0, 0, 0, 0, 0, 0, 0 };
        n2 = new int[] { 8, 7, 0, 0, 0, 0, 0, 0, 0 };
        expected = new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        assertArrayEquals(expected, largeNumber.add(n1, n2));
    }
    @Test public void test_subtract1() {
        n1 = new int[] { 8, 3, 0, 0, 0, 0, 0, 0, 0 };
        n2 = new int[] { 5, 4, 0, 0, 0, 0, 0, 0, 0 };
        expected = new int[] { 2, 9, 0, 0, 0, 0, 0, 0, 0 };
        assertArrayEquals(expected, largeNumber.subtract(n1, n2));
    }

    @Test public void test_subtract2() {
        n1 =new int[] { 4, 3, 2, 8, 1 };
        n2 = new int[] { 3, 7, 9, 1, 3 };
        expected = new int[] { 5, 3, 6, 8 };
        assertArrayEquals(expected, largeNumber.subtract(n1, n2));
    }
    @Test public void test_multiply1() {
        n1 = new int[] { 2, 3, 6, 0, 0, 0, 0, 0, 0 };
        digit = 2;
        expected = new int[] { 4, 7, 2, 0, 0, 0, 0, 0, 0 };
        assertArrayEquals(expected, largeNumber.multiply(n1, digit));
    }
    @Test public void test_multiply2() {
        n1 = new int[] { 5, 6, 0, 0 };
        digit = 5;
        expected = new int[] { 2, 8, 0, 0, 0 };
        assertArrayEquals(expected, largeNumber.multiply(n1, digit));
    }

    @Test public void test_divide1() {
        n1 = new int[] { 2, 3, 6, 0, 0, 0, 0, 0, 0 };
        digit = 2;
        expected = new int[] { 1, 1, 8, 0, 0, 0, 0, 0, 0 };
        assertArrayEquals(expected, largeNumber.divide(n1, digit));
    }
    @Test public void test_divide2() {
        n1 = new int[] { 3, 6, 7, 0, 0, 0, 0, 0 };
        digit = 0;
        expected = new int[] {-1};
        assertArrayEquals(expected, largeNumber.divide(n1, digit));
    }

}
