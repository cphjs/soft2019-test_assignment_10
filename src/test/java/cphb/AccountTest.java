package cphb;

import cphb.assignment_10.bank.Account;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * AccountTest
 */
public class AccountTest {

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account("Mr. Test customer");
    }

    @Test
    public void testDeposit_with_positive_amount() {
        double balance = account.getBalance();
        account.deposit(10);
        assertThat(account.getBalance(), is(balance + 10));
    }

    @Test
    public void testDeposit_with_zero_amount() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0));
    }

    @Test
    public void testDeposit_with_zero_negative_amount() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-10));
    }

    @Test
    public void testWithdraw_with_sufficient_amount() {
        account.setBalance(10);
        account.withdraw(5);
        assertThat(account.getBalance(), is(5.0));
    }

    @Test
    public void testWithdraw_with_insufficient_amount() {
        account.setBalance(10);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(11));
    }

    @Test
    public void testWithdraw_with_invalid_amount() {
        account.setBalance(10);
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-1));
    }

    /*
        Boundaries and equivalance partitioning
     */

    @ParameterizedTest
    @CsvSource({
        "0,0", 
        "99,2.97",
        "100,5",
        "999,49.95",
        "1000,70"
    })
    public void testGetInterest_boundaries_with_valid_values(int balance, double interest) {
        account.setBalance(balance);
        assertThat(account.getInterest(), closeTo(interest, 0.1));
    }

    @Test
    public void testGetInterest_boundaries_with_invalid_value() {
        account.setBalance(-1);
        assertThrows(IllegalArgumentException.class, () -> account.getInterest());
    }
}