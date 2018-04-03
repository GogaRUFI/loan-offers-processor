package services;

import models.ResultRepayment;

/**
 * RepaymentCalculator is responsible for calculation of a loan's repayments based on its interest rate:
 * - regular and total repayments on monthly basis
 * - for 3 years loan in total
 * - using effective interest rate which has to be calculated on the same basis (monthly)
 * - for calculations double type is used which applies some limitations to precision of the calculations for large numbers (for advanced calculations third-party libraries utilising BigDecimal can be used instead)
 */

public class RepaymentCalculator {

    private static int PERIODS_ANNUALLY = 12;
    private static int PERIODS_TOTAL = PERIODS_ANNUALLY * 3;

    ResultRepayment calculate(int amount, double effectiveMonthlyRate) {
        double regularRepayment = calculateRegularRepayment(amount, effectiveMonthlyRate);
        double totalRepayment = calculateTotalRepayment(regularRepayment);
        return new ResultRepayment(
                regularRepayment,
                totalRepayment
        );
    }

    double calculateRegularRepayment(int amount, double effectiveMonthlyRate) {
        double periodicRate = calculatePeriodicRate(effectiveMonthlyRate);
        return (periodicRate * amount) / (1D - Math.pow(1D + periodicRate, -PERIODS_TOTAL));
    }

    double calculatePeriodicRate(double effectiveMonthlyRate) {
        return Math.pow(effectiveMonthlyRate + 1D, 1D / PERIODS_ANNUALLY) - 1D;
    }

    double calculateTotalRepayment(double regularRepayment) {
        return regularRepayment * PERIODS_TOTAL;
    }
}
