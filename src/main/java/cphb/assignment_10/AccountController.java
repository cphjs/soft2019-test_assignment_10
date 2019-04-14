/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cphb.assignment_10;

import cphb.assignment_10.bank.Account;
import cphb.assignment_10.data.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    private AccountRepository accountRepo;
    
    @Autowired
    public void setAccountRepo(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @GetMapping("/account/open")
    public String index() {
        return "account/open";
    }
    
    @PostMapping("/account/open")
    public String store(HttpServletRequest request, HttpSession session, RedirectAttributes redirectAtr) {
        String name = request.getParameter("name");

        if (name == null || name.isEmpty() || name.length() < 3) {
            redirectAtr.addFlashAttribute("error", "Name must be at least 3 characters");
            return "redirect:/account/open";
        }
        
        Account account = new Account(name);
        if (!accountRepo.insert(account)) {
            redirectAtr.addFlashAttribute("error", "Account could not be created");
            return "redirect:/account/open";
        }
        
        logger.debug("Account created for " + account.getCustomerName());
        session.setAttribute("account", account);
        return "redirect:/atm";
    }


    @PostMapping("/account/login")
    public String login(@RequestParam("name")String name, HttpSession session, RedirectAttributes redirectAtr) {
        
        if (name == null || name.isEmpty() || name.length() < 3) {
            redirectAtr.addFlashAttribute("error", "Name must be at least 3 characters");
            return "redirect:/";
        }
        
        Account account = accountRepo.getByCustomerName(name);
        if (account == null) {
            redirectAtr.addFlashAttribute("error", "Account not found");
            return "redirect:/";
        }
        
        session.setAttribute("account", account);
        return "redirect:/atm";
    }
}