package models.dao;

import models.LoanOffer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static models.dao.LoanOfferCsvFields.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LoanOfferCsvParserTest {

    private static Map<String, String> validOfferRecord = getValidOfferRecord();

    private static Map<String, String> getValidOfferRecord() {
        Map<String, String> record = new HashMap<>();
        record.put(Lender, "Bob");
        record.put(Rate, "0.045");
        record.put(AvailableAmount, "1560");

        return record;
    }

    private static Map<String, String> getOfferRecord(String key, String value) {
        Map<String, String> record = getValidOfferRecord();
        record.replace(key, value);
        return record;
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseValidOffer() {
        LoanOffer expected = new LoanOffer(
                "Bob",
                0.045D,
                1560
        );

        LoanOffer result = LoanOfferCsvParser.parse(validOfferRecord);

        assertOffers(result, expected);
    }

    @Test
    public void parseWithInvalidLender() {
        List<Map<String, String>> inputs = new ArrayList<>();
        inputs.add(getOfferRecord(Lender, ""));
        inputs.add(getOfferRecord(Lender, " "));

        inputs.forEach(input -> {
            try {
                LoanOfferCsvParser.parse(input);
                fail("Exception should have been thrown at: " + input);
            } catch (RuntimeException e) {
                //CONTINUE
            }
        });
    }

    @Test
    public void parseWithInvalidRate() {
        List<Map<String, String>> inputs = new ArrayList<>();
        inputs.add(getOfferRecord(Rate, "-1"));
        inputs.add(getOfferRecord(Rate, "1,0"));
        inputs.add(getOfferRecord(Rate, "0"));
        inputs.add(getOfferRecord(Rate, "0.0"));
        inputs.add(getOfferRecord(Rate, ""));
        inputs.add(getOfferRecord(Rate, "text"));
        inputs.add(getOfferRecord(Rate, "10.7976931348623157E308"));

        inputs.forEach(input -> {
            try {
                LoanOfferCsvParser.parse(input);
                fail("Exception should have been thrown at: " + input);
            } catch (RuntimeException e) {
                //CONTINUE
            }
        });
    }

    @Test
    public void parseWithInvalidAmount() {
        List<Map<String, String>> inputs = new ArrayList<>();
        inputs.add(getOfferRecord(AvailableAmount, "-1"));
        inputs.add(getOfferRecord(AvailableAmount, "0.0"));
        inputs.add(getOfferRecord(AvailableAmount, "100.4"));
        inputs.add(getOfferRecord(AvailableAmount, "100,0"));
        inputs.add(getOfferRecord(AvailableAmount, "text"));
        inputs.add(getOfferRecord(AvailableAmount, "2147483648"));

        inputs.forEach(input -> {
            try {
                LoanOfferCsvParser.parse(input);
                fail("Exception should have been thrown at: " + input);
            } catch (RuntimeException e) {
                //CONTINUE
            }
        });
    }

    private void assertOffers(LoanOffer result, LoanOffer expected) {
        assertEquals(result.getLender(), expected.getLender());
        assertEquals(result.getRate(), expected.getRate(), 0);
        assertEquals(result.getAmount(), expected.getAmount());
    }
}
