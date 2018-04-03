package services;

import models.LoanOffer;
import models.dao.LoanOfferCsvDAO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class LoanOfferServiceTest {

    private static RepaymentCalculator repaymentCalculator = new RepaymentCalculator();

    private static LoanOfferCsvDAO loanOfferCsvDAO = mock(LoanOfferCsvDAO.class);

    private static LoanOfferService loanOfferService = new LoanOfferService(repaymentCalculator, loanOfferCsvDAO);

    private static LoanOffer getOffer(String lender, double rate, int amount) {
        return new LoanOffer(
                lender,
                rate,
                amount
        );
    }

    private static LoanOffer getOffer(double rate, int amount) {
        return getOffer("Bob", rate, amount);
    }

    @Test
    public void findBestOffer() {

        List<LoanOffer> input = new ArrayList<>();

        input.add(getOffer(0.02D, 100));
        input.add(getOffer(0.07D, 1100));
        input.add(getOffer(0.09D, 1800));

        Optional<LoanOffer> result = loanOfferService.findBestOffer(1000, input);

        LoanOffer expected = getOffer(0.07D, 1100);

        assertOffers(result.get(), expected);
    }

    private void assertOffers(LoanOffer result, LoanOffer expected) {
        assertEquals(result.getLender(), expected.getLender());
        assertEquals(result.getRate(), expected.getRate(), 0);
        assertEquals(result.getAmount(), expected.getAmount());
    }

}
