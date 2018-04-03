package models.dao;

import csv.CsvAccessor;
import csv.CsvAccessorConfig;
import models.LoanOffer;
import org.apache.commons.csv.CSVRecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static models.dao.LoanOfferCsvFields.*;

/**
 * LoanOfferCsvDAO is a data access class for loan offers:
 * - extends CsvAccessor and implements its interface for parsing loan offers
 * - sets CsvAccessorConfig for CsvAccessor
 */

public class LoanOfferCsvDAO extends CsvAccessor<LoanOffer> {

    public LoanOfferCsvDAO(CsvAccessorConfig CsvAccessorConfig) {
        this.CsvAccessorConfig = CsvAccessorConfig;
    }

    public List<LoanOffer> getAll() {
        return read();
    }

    protected Optional<LoanOffer> parse(CSVRecord csvRecord) {
        try {
            return Optional.of(LoanOfferCsvParser.parse(csvRecord.toMap()));
        } catch (Throwable e) {
            System.err.println("Cant' parse row: " + csvRecord);
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
}


/**
 * LoanOfferCsvFields describes fields of a CSV file record for an offer
 */

class LoanOfferCsvFields {
    static String Lender = "Lender";
    static String Rate = "Rate";
    static String AvailableAmount = "Available";
}


/**
 * LoanOfferCsvParser is responsible for parsing a CSV record into a loan offer instance
 * - contains logic responsible for data validation
 */

class LoanOfferCsvParser {

    static LoanOffer parse(Map<String, String> record) {
        return new LoanOffer(
                parseLender(record.get(Lender)),
                parseRate(record.get(Rate)),
                parseAmount(record.get(AvailableAmount))
        );
    }

    private static String parseLender(String value) {
        if (value.trim().isEmpty())
            throw new RuntimeException(generateEmptyValueParsingErrorMessage(Lender));
        else
            return value.trim();
    }

    private static double parseRate(String value) {
        double result = parseDouble(Rate, value);
        if (result <= 0 || result == Double.POSITIVE_INFINITY) {
            throw new RuntimeException(generateParsingError(Rate, value, "The value has to be a positive number less or equal than: " + Double.MAX_VALUE));
        }
        return result;
    }

    private static double parseDouble(String field, String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException(generateParsingError(field, value, "The value is expected to be a decimal."));
        }
    }

    private static int parseAmount(String value) {
        int result = parseInteger(AvailableAmount, value);
        if (result < 0) {
            throw new RuntimeException(generateParsingError(AvailableAmount, value, "The value can't be a negative number."));
        }
        return result;
    }

    private static int parseInteger(String field, String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new RuntimeException(generateParsingError(field, value, "The value is expected to be an integer less or equal than: " + Integer.MAX_VALUE));
        }
    }

    private static String generateParsingError(String filed, String value, String description) {
        return "Offer's " + filed + " can't be equal to: '" + value + "'. " + description;
    }

    private static String generateEmptyValueParsingErrorMessage(String filed) {
        return "Offer's " + filed + " can't be an empty value.";
    }
}


