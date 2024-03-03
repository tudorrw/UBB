import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayOperationsTest {
    public ArrayOperations op;
    int[] intArr;
    @BeforeEach public void setup() {
        op = new ArrayOperations();
        intArr = new int[] { 4, 8, 3, 10, 17 };
    }
    @Test public void correct_test_max_number() throws IllegalAccessException {
        int expected = 17;
        assertEquals(expected, op.max_number(intArr));
    }

    @Test public void correct_test_sum_min() {
        int expected = 25;
        assertEquals(expected, op.sum_min(intArr));
    }

    @Test public void correct_test_min_number() throws IllegalAccessException {
        int expected = 3;
        assertEquals(expected, op.min_number(intArr));
    }

    @Test public void correct_test_sum_max() {
        int expected = 39;
        assertEquals(expected, op.sum_max(intArr));
    }
    @Test public void failed_test_max_number() throws IllegalAccessException {
        int expected = 3;
        assertNotEquals(expected, op.max_number(intArr));
    }

    @Test public void failed_test_sum_min() {
        int expected = 39;
        assertNotEquals(expected, op.sum_min(intArr));
    }

    @Test public void failed_test_min_number() throws IllegalAccessException {
        int expected = 17;
        assertNotEquals(expected, op.min_number(intArr));
    }

    @Test public void failed_test_sum_max() {
        int expected = 25;
        assertNotEquals(expected, op.sum_max(intArr));
    }
}