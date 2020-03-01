package edu.wpi.cs3733.c20.teamS.utilities;

import edu.wpi.cs3733.c20.teamS.utilities.numerics.Vector2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2Tests {
    Vector2 TestVector2A = new Vector2(2.5,3.5);
    Vector2 TestVector2B = new Vector2(7.6,9.7);
    Vector2 TestVector2Zero = new Vector2(0,0);

    @Test
    public void TestVector2length(){
        double lengthA = TestVector2A.length();
        double lengthB = TestVector2B.length();
        double lengthC = TestVector2Zero.length();
        double True_lengthA = Math.sqrt(Math.pow(2.5,2) + Math.pow(3.5,2));
        double True_lengthB = Math.sqrt(Math.pow(7.6,2) + Math.pow(9.7,2));
        assertEquals(lengthA, True_lengthA, 0.0001);
        assertEquals(lengthB, True_lengthB,0.0001);
        assertEquals(lengthC, 0);
    }

    @Test
    public void TestVector2add(){
        Vector2 SumAB = TestVector2A.add(TestVector2B);
        double SumAB_X = SumAB.x();
        double SumAB_Y = SumAB.y();
        assertEquals(SumAB_X, (2.5+7.6), 0.0001);
        assertEquals(SumAB_Y, (3.5+9.7), 0.0001);
    }

    @Test
    public void TestVector2subtract(){
        Vector2 DiffAB = TestVector2A.subtract(TestVector2B);
        double DiffAB_X = DiffAB.x();
        double DiffAB_Y = DiffAB.y();
        assertEquals(DiffAB_X, (2.5-7.6),0.0001);
        assertEquals(DiffAB_Y, (3.5-9.7),0.0001);
    }

    @Test
    public void TestVector2multiply(){
        double TestScalar = 3.0;
        Vector2 A_multiplied = TestVector2A.multiply(TestScalar);
        double A_multiplied_X = A_multiplied.x();
        double A_multiplied_Y = A_multiplied.y();
        assertEquals(A_multiplied_X, (2.5 * TestScalar), 0.0001);
        assertEquals(A_multiplied_Y, (3.5 * TestScalar), 0.0001);
    }

    @Test
    public void TestVector2divide(){
        double TestScalar = 3.0;
        Vector2 A_divided = TestVector2A.divide(TestScalar);
        double A_divided_X = A_divided.x();
        double A_divided_Y = A_divided.y();
        assertEquals(A_divided_X, (2.5 / TestScalar), 0.0001);
        assertEquals(A_divided_Y, (3.5 / TestScalar), 0.0001);
    }

    @Test
    public void TestVector2dot(){
        double DotProduct_AB = TestVector2A.dot(TestVector2B);
        assertEquals(DotProduct_AB, (2.5*7.6+3.5*9.7),0.0001);
    }

    @Test
    public void equality_usesValueSemantics() {
        final double x = 3214.898;
        final double y = -4.21342e5;
        Vector2 first = new Vector2(x, y);
        Vector2 second = new Vector2(x, y);

        assertTrue(first.equals(second));
    }

    @Test
    public void anyNonNullVector_isNotEqualToNull() {
        Vector2 first = new Vector2(4, -2.3);

        assertFalse(first.equals(null));
    }

    @Test
    public void equalVectors_haveEqualHashCodes() {
        final double x = -1.2345e7;
        final double y = -4.0001e-6;
        Vector2 first = new Vector2(x, y);
        Vector2 second = new Vector2(x, y);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    public void unequalVectors_compareAsUnequal() {
        Vector2 first = new Vector2(69, 420);
        Vector2 second = new Vector2(420, 69);

        assertFalse(first.equals(second));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void anyVector_isNotEqualToNonVector() {
        Vector2 vector = new Vector2(92, 45.234);
        String nonVector = "I'm not a vector!";

        assertFalse(vector.equals(nonVector));
    }

    @Test
    public void equals_bothNull_true() {
        assertTrue(Vector2.equals(null, null));
    }

    @Test
    public void equals_oneNull_false() {
        assertFalse(Vector2.equals(null, new Vector2(3, 4)));
        assertFalse(Vector2.equals(new Vector2(3, 4), null));
    }
}
