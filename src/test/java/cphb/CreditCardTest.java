package cphb;

import cphb.assignment_10.bank.CreditCard;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.hamcrest.core.Is.is;

/**
 * CreditCardTest
 */
public class CreditCardTest {

    @ParameterizedTest
    @CsvSource({
        "true,false,true, 20",
        "true, false, false, 15",
        "false, true, true, 30",
        "false, true, false, 10",
        "false, false, true, 20",
        "false, false, false, 0",
    })
    public void testDiscountsWithValidInputs(boolean newCustomer, boolean loyaltyCard, boolean coupon, Integer discount) {
        assertThat(CreditCard.getDiscount(newCustomer, loyaltyCard, coupon), is(discount));
    }

    /**
     * This is a wrong use case for a dynamic test
     * But an assignment is an assignment...
     */
    @TestFactory
    public DynamicTest[] testDiscountsWithInvalidInputs() {
        final boolean[][] invalidData = {
            { true, true, true },
            { true, true ,false },
        };
        return (DynamicTest[])Arrays.asList(invalidData)
            .stream()
            .map(item -> dynamicTest(
                String.format("test discounts with "), 
                () -> assertThrows(IllegalArgumentException.class, () -> CreditCard.getDiscount(item[0], item[1], item[2]))
            ))
            .toArray(len -> new DynamicTest[len]);
    }
}