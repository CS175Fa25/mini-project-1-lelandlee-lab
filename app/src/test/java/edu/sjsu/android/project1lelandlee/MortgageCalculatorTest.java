package edu.sjsu.android.project1lelandlee;

import org.junit.Test;
import static org.junit.Assert.*;

public class MortgageCalculatorTest {

    @Test
    public void testCalculateMonthlyPayment_5_5Percent_15Years_NoTax() {
        double result = MortgageCalculator.calculateMonthlyPayment(10000, 5.5, 15, false);
        assertEquals(81.71, result, 0.01);
    }

    @Test
    public void testCalculateMonthlyPayment_0Percent_20Years_NoTax() {
        double result = MortgageCalculator.calculateMonthlyPayment(20000, 0, 20, false);
        assertEquals(83.33, result, 0.01);
    }

    @Test
    public void testCalculateMonthlyPayment_5_5Percent_15Years_WithTax() {
        double result = MortgageCalculator.calculateMonthlyPayment(10000, 5.5, 15, true);
        assertEquals(91.71, result, 0.01);
    }

    @Test
    public void testCalculateMonthlyPayment_10Percent_20Years_WithTax() {
        double result = MortgageCalculator.calculateMonthlyPayment(20000, 10.0, 20, true);
        assertEquals(213.00, result, 0.01);
    }

    @Test
    public void testCalculateMonthlyPayment_10Percent_30Years_WithTax() {
        double result = MortgageCalculator.calculateMonthlyPayment(10000, 10.0, 30, true);
        assertEquals(97.76, result, 0.01);
    }
}