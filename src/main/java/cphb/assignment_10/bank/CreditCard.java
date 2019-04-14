package cphb.assignment_10.bank;

/**
 * CreditCard
 */
public class CreditCard {

    private Account account;

    public CreditCard(Account account) {
        setAccount(account);
    }

    /**
     * @return the account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    public static int getDiscount(boolean newCustomer, boolean loyaltyCard, boolean coupon) {
        if (newCustomer && loyaltyCard && coupon) 
            throw new IllegalArgumentException("You cannot be a customer and not be a customer at the same time");
        if (newCustomer && loyaltyCard)
            throw new IllegalArgumentException("New customers cannot have a existing loyalty cards");

        if (coupon && loyaltyCard) return 30;
        else if (coupon) return 20;
        else if (newCustomer) return 15;
        else if (loyaltyCard) return 10;
        else return 0;
    }
}