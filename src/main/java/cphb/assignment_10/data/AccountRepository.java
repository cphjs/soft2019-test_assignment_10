package cphb.assignment_10.data;

import cphb.assignment_10.bank.Account;

public interface AccountRepository {
    
    public Account getByCustomerName(String name);
    public boolean insert(Account account);
}
