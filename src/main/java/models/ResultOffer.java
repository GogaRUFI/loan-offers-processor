package models;

public class ResultOffer {
    private LoanOffer loanOffer;
    private ResultRepayment resultRepayment;

    public ResultOffer(LoanOffer loanOffer, ResultRepayment resultRepayment) {
        this.loanOffer = loanOffer;
        this.resultRepayment = resultRepayment;
    }

    public LoanOffer getLoanOffer() {
        return loanOffer;
    }

    public ResultRepayment getResultRepayment() {
        return resultRepayment;
    }
}