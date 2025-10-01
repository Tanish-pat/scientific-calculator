package com.calc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;

public class AppTest {

    // Factorial tests
    @Test
    void testFactorialPositive() {
        assertEquals(BigInteger.valueOf(120), App.factorial(5));
        assertEquals(BigInteger.valueOf(3628800), App.factorial(10));
    }

    @Test
    void testFactorialZero() {
        assertEquals(BigInteger.ONE, App.factorial(0));
    }

    @Test
    void testFactorialNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> App.factorial(-1));
        assertEquals("Negative numbers not allowed", exception.getMessage());
    }

    // Square root tests
    @Test
    void testSqrtPositive() {
        assertEquals(5.0, Math.sqrt(25), 0.0001);
        assertEquals(3.0, Math.sqrt(9), 0.0001);
    }

    @Test
    void testSqrtZero() {
        assertEquals(0.0, Math.sqrt(0), 0.0001);
    }

    @Test
    void testSqrtNegative() {
        assertTrue(Double.isNaN(Math.sqrt(-4)));
    }

    // Natural logarithm tests
    @Test
    void testLogPositive() {
        assertEquals(0.0, Math.log(1), 0.0001);
        assertEquals(Math.log(10), Math.log(10), 0.0001);
    }

    @Test
    void testLogZero() {
        assertEquals(Double.NEGATIVE_INFINITY, Math.log(0));
    }

    @Test
    void testLogNegative() {
        assertTrue(Double.isNaN(Math.log(-5)));
    }

    // Power function tests
    @Test
    void testPowerPositive() {
        assertEquals(8.0, Math.pow(2, 3), 0.0001);
        assertEquals(16.0, Math.pow(4, 2), 0.0001);
    }

    @Test
    void testPowerZeroExponent() {
        assertEquals(1.0, Math.pow(5, 0), 0.0001);
    }

    @Test
    void testPowerZeroBase() {
        assertEquals(0.0, Math.pow(0, 5), 0.0001);
    }

    @Test
    void testPowerZeroBaseZeroExponent() {
        assertEquals(1.0, Math.pow(0, 0), 0.0001);
    }

    @Test
    void testPowerNegativeExponent() {
        assertEquals(0.25, Math.pow(2, -2), 0.0001);
    }

    // Combined edge case tests (optional)
    @Test
    void testFactorialLargeNumber() {
        BigInteger result = App.factorial(20);
        assertTrue(result.compareTo(BigInteger.ZERO) > 0);
    }
}