package services;

import models.LoanOffer;
import models.ResultOffer;
import models.ResultRepayment;
import models.dao.LoanOfferCsvDAO;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/* LoanOfferService is responsible for finding the best loan offer:
    - uses other services for calculations
    - uses an injected data access object to read loan offers
*/

public class LoanOfferService {

    private RepaymentCalculator repaymentCalculator;
    private LoanOfferCsvDAO loanOfferCsvDAO;

    public LoanOfferService(RepaymentCalculator repaymentCalculator,
                            LoanOfferCsvDAO loanOfferCsvDAO) {
        this.repaymentCalculator = repaymentCalculator;
        this.loanOfferCsvDAO = loanOfferCsvDAO;
    }

    public Optional<ResultOffer> find(int amount) {
        List<LoanOffer> loanOffers = loanOfferCsvDAO.getAll();
        return findBestOffer(amount, loanOffers).map(o -> calculateResultOffer(amount, o));
    }

    Optional<LoanOffer> findBestOffer(int amount, List<LoanOffer> loanOffers) {
        return loanOffers.stream()
                .filter(o -> o.getAmount() >= amount)
                .sorted(Comparator.comparing(LoanOffer::getAmount))
                .findFirst();
    }

    private ResultOffer calculateResultOffer(int amount, LoanOffer loanOffer) {
        ResultRepayment resultRepayment = repaymentCalculator.calculate(amount, loanOffer.getRate());
        return new ResultOffer(loanOffer, resultRepayment);
    }
}
