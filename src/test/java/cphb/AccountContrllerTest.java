package cphb;

import cphb.assignment_10.AccountController;
import cphb.assignment_10.bank.Account;
import cphb.assignment_10.data.AccountRepository;
import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class AccountContrllerTest {
    
    public AccountContrllerTest() {
    }
    
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ctr.setAccountRepo(repo);    
    }
    

    private AccountController ctr = new AccountController();
    
    @Mock
    private AccountRepository repo;
    
    @Mock
    private HttpSession session;
    
    @Mock
    private RedirectAttributes redirectAttr;
    
    
    @Test
    public void test_login_addsAccountToSession() {
        String name = "A customer";
        Account account = new Account(name);
        when(repo.getByCustomerName(name)).thenReturn(account);
        
        ctr.login(name, session, redirectAttr);
        
        verify(session).setAttribute("account", account);
    }
}
