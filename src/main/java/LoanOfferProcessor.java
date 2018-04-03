import csv.CsvAccessorConfig;
import models.ResultOffer;
import models.dao.LoanOfferCsvDAO;
import services.LoanOfferService;
import services.RepaymentCalculator;
import utils.ConsoleUtils;

import java.util.Optional;

/**
 * LoanOfferProcessor is a main class:
 * - has a section which defines all dependencies used by the service.
 * It has to be replaced by a DI framework (Google Guice ...) for more complex projects.
 * - most of the dependencies are singletons (their instances can be reused in the code),
 * generally a simple static class would be used for such a small project.
 * Assuming that this is a part of a bigger project. In that case we would need a better way to manage dependencies.
 */

public class LoanOfferProcessor {

    private static RepaymentCalculator repaymentCalculator = new RepaymentCalculator();
    private static CsvAccessorConfig csvAccessorConfig = new CsvAccessorConfig();
    private static LoanOfferCsvDAO loanOfferCsvDAO = new LoanOfferCsvDAO(csvAccessorConfig);
    private static LoanOfferService loanOfferService = new LoanOfferService(repaymentCalculator, loanOfferCsvDAO);

    public static void main(String args[]) {

        if (args.length < 2) {
            System.out.printf("Two arguments are required to run the application: [market_file] [loan_amount]");
            return;
        }

        csvAccessorConfig.setCsvFilePath(args[0]);

        int loanAmount;
        try {
            loanAmount = ConsoleUtils.parseLoanAmount(args[1]);
        } catch (Exception e) {
            return;
        }

        Optional<ResultOffer> resultOffer = loanOfferService.find(loanAmount);
        ConsoleUtils.print(loanAmount, resultOffer);
    }
}
