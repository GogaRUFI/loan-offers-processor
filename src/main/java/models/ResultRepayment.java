package models;

public class ResultRepayment {
    private double regularRepayment;
    private double totalRepayment;

    public ResultRepayment(double regularRepayment, double totalRepayment) {
        this.regularRepayment = regularRepayment;
        this.totalRepayment = totalRepayment;
    }

    public double getRegularRepayment() {
        return regularRepayment;
    }

    public double getTotalRepayment() {
        return totalRepayment;
    }
}
