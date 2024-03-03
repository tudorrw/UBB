import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class NotenTest {
    public Noten noten;
    public int[] notenArr;
    public double expected;
    @BeforeEach public void setup() {
        noten = new Noten();
        notenArr = new int[]{ 45, 39, 67, 84, 100, 19, 93, 32 };
    }
    @Test public void test_insufficient_grades() {
        assertArrayEquals(new int[] { 39, 19, 32 }, noten.insufficient_grades(notenArr));
    }
    @Test public void test_average_value() {
        expected = 59.875;
        assertEquals(expected, noten.average_value(notenArr), 0.01);
    }
    @Test public void test_average_value2() {
        expected = 59;
        assertNotEquals(expected, noten.average_value(notenArr), 0.01);
    }
    @Test public void test_rounded_grades() {
        assertArrayEquals(new int[] { 40, 85, 95 }, noten.rounded_grades(notenArr));
    }
    @Test
    public void test_max_rounded_grade() {
        expected = 95;
        assertEquals(expected, noten.max_rounded_grade(notenArr), 0.01);
    }
    @Test
    public void test_max_rounded_grade2() {
        expected = 100;
        assertNotEquals(expected, noten.max_rounded_grade(notenArr), 0.01);
    }
}