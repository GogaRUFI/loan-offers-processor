package services;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RepaymentCalculatorTest {

    private RepaymentCalculator repaymentCalculator = new RepaymentCalculator();

    @Test
    public void calculatePeriodicRate() {
        double result1 = repaymentCalculator.calculatePeriodicRate(0.048D);
        double result2 = repaymentCalculator.calculatePeriodicRate(0.07D);
        double result3 = repaymentCalculator.calculatePeriodicRate(0.05D);

        assertEquals(0.003914D, result1, 0.0000009D);
        assertEquals(0.005654D, result2, 0.0000009D);
        assertEquals(0.004074D, result3, 0.0000009D);
    }

    @Test
    public void calculateRegularPayment() {
        double result1 = repaymentCalculator.calculateRegularRepayment(1000, 0.048D);
        double result2 = repaymentCalculator.calculateRegularRepayment(15000, 0.07D);
        double result3 = repaymentCalculator.calculateRegularRepayment(1400, 0.05D);

        assertEquals(29.835283D, result1, 0.0000009D);
        assertEquals(461.683182D, result2, 0.0000009D);
        assertEquals(41.889488D, result3, 0.0000009D);
    }

    @Test
    public void calculateTotalPayment() {
        double result1 = repaymentCalculator.calculateTotalRepayment(29.835283D);
        double result2 = repaymentCalculator.calculateTotalRepayment(461.683182D);
        double result3 = repaymentCalculator.calculateTotalRepayment(41.889488D);

        assertEquals(1074.070188D, result1, 0.0000009D);
        assertEquals(16620.594552D, result2, 0.0000009D);
        assertEquals(1508.021568D, result3, 0.0000009D);
    }
}
