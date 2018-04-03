package utils;

import models.ResultRepayment;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ConsoleUtilsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void rateToPrint() {
        String result1 = ConsoleUtils.rateToPrint(0.0489D);
        String result2 = ConsoleUtils.rateToPrint(0.07D);
        String result3 = ConsoleUtils.rateToPrint(0.50D);

        assertEquals(result1, "4.9");
        assertEquals(result2, "7.0");
        assertEquals(result3, "50.0");
    }

    @Test
    public void repaymentToPrint() {
        String result1 = ConsoleUtils.repaymentToPrint(29.835283D);
        String result2 = ConsoleUtils.repaymentToPrint(461.683182D);
        String result3 = ConsoleUtils.repaymentToPrint(41.889488D);

        assertEquals(result1, "29.84");
        assertEquals(result2, "461.68");
        assertEquals(result3, "41.89");
    }

    @Test
    public void parseLoanAmount() {
        int result1 = ConsoleUtils.parseLoanAmount("1000");
        int result2 = ConsoleUtils.parseLoanAmount("15000");
        int result3 = ConsoleUtils.parseLoanAmount("1400");

        assertEquals(result1, 1000);
        assertEquals(result2, 15000);
        assertEquals(result3, 1400);
    }

    @Test
    public void parseInvalidLoanAmount() {
        List<String> inputs = new ArrayList<>();
        inputs.add("900");
        inputs.add("999");
        inputs.add("16000");
        inputs.add("15999");
        inputs.add(" ");
        inputs.add("");
        inputs.add("text");
        inputs.add("1450");
        inputs.add("1500.0");
        inputs.add("1500.01");
        inputs.add("2147483648");

        inputs.forEach(input -> {
            try {
                ConsoleUtils.parseLoanAmount(input);
                fail("Exception should have been thrown at: " + input);
            } catch (RuntimeException e) {
                //CONTINUE
            }
        });
    }

    @Test
    public void isRepaymentAmountTooLarge() {
        ResultRepayment input1 = new ResultRepayment(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        ResultRepayment input2 = new ResultRepayment(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        ResultRepayment input3 = new ResultRepayment(Double.POSITIVE_INFINITY, 0D);
        ResultRepayment input4 = new ResultRepayment(0D, Double.POSITIVE_INFINITY);
        ResultRepayment input5 = new ResultRepayment(1D, 36D);

        boolean result1 = ConsoleUtils.isRepaymentTooLarge(input1);
        boolean result2 = ConsoleUtils.isRepaymentTooLarge(input2);
        boolean result3 = ConsoleUtils.isRepaymentTooLarge(input3);
        boolean result4 = ConsoleUtils.isRepaymentTooLarge(input4);
        boolean result5 = ConsoleUtils.isRepaymentTooLarge(input5);

        assertEquals(true, result1);
        assertEquals(true, result2);
        assertEquals(true, result3);
        assertEquals(true, result4);
        assertEquals(false, result5);
    }
}
