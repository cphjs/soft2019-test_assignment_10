package cphb.assignment_10.data;

import cphb.assignment_10.bank.Account;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class InMemoryAccountRepository implements AccountRepository {

    private Map<String, Account> accounts = new HashMap<>();
            
    
    @Override
    public Account getByCustomerName(String name) {
        return accounts.get(name);
    }

    @Override
    public boolean insert(Account account) {
        if (accounts.containsKey(account.getCustomerName())) {
            return false;
        }
        accounts.put(account.getCustomerName(), account);
        return true;
    }
    
}
