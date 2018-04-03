package models;

/**
 * LoanOffer represents a lender's offer:
 * - doesn't carry any logic
 * - is immutable
 */

public class LoanOffer {
    private String lender;
    private double rate;
    private int amount;

    public LoanOffer(String lender,
                     double rate,
                     int amount) {
        this.lender = lender;
        this.rate = rate;
        this.amount = amount;
    }

    public String getLender() {
        return lender;
    }

    public double getRate() {
        return rate;
    }

    public int getAmount() {
        return amount;
    }
}
