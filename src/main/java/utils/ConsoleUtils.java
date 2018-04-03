package utils;

import models.ResultOffer;
import models.ResultRepayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * ConsoleUtils contains commonly used functionality related to printing and reading data to the console
 */
public class ConsoleUtils {

    public static int parseLoanAmount(String loanAmount) {
        if (loanAmount.trim().length() > 0) {
            try {
                int value = Integer.parseInt(loanAmount.trim());
                double v = value / 100D;
                if (value >= 1000 && value <= 15000 && v == Math.floor(v)) {
                    return value;
                } else {
                    String msg = "Loan amount can't be equal to " + loanAmount + ". The value is expected to be an increment of 100 from 1000 to 15000.";
                    System.err.println(msg);
                    throw new RuntimeException(msg);
                }
            } catch (NumberFormatException e) {
                System.err.println("Loan amount can't be equal to " + loanAmount + ". The value is expected to be an integer.");
                throw e;
            }
        } else {
            String msg = "Loan amount can't be empty";
            System.err.println(msg);
            throw new RuntimeException(msg);
        }
    }

    public static void print(int amount, Optional<ResultOffer> resultOffer) {
        if (resultOffer.isPresent()) {
            if (!isRepaymentTooLarge(resultOffer.get().getResultRepayment())) {
                print(amount, resultOffer.get());
            } else {
                System.out.println("Sorry, result numbers are too large.");
            }
        } else {
            System.out.println("Sorry, no offers were found.");
        }
    }

    static boolean isRepaymentTooLarge(ResultRepayment resultRepayment) {
        return resultRepayment.getRegularRepayment() == Double.POSITIVE_INFINITY ||
                resultRepayment.getTotalRepayment() == Double.POSITIVE_INFINITY;
    }

    static String rateToPrint(double rate) {
        return new BigDecimal(rate).multiply(BigDecimal.valueOf(100)).setScale(1, RoundingMode.HALF_UP).toString();
    }

    static String repaymentToPrint(double repayment) {
        return new BigDecimal(repayment).setScale(2, RoundingMode.HALF_UP).toString();
    }

    private static void print(int amount, ResultOffer resultOffer) {
        System.out.println("Requested amount: \u00A3" + amount);
        System.out.println("Rate: \u00A3" + rateToPrint(resultOffer.getLoanOffer().getRate()));
        System.out.println("Monthly repayment: \u00A3" + repaymentToPrint(resultOffer.getResultRepayment().getRegularRepayment()));
        System.out.println("Total repayment: \u00A3" + repaymentToPrint(resultOffer.getResultRepayment().getTotalRepayment()));
    }
}
