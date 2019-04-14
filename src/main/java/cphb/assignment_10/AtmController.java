/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cphb.assignment_10;

import cphb.assignment_10.bank.Account;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AtmController {
    
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);
     
    
    @GetMapping("/atm")
    public ModelAndView index(HttpSession session, ModelAndView mv) {
        Account account = (Account)session.getAttribute("account");
        
        mv.addObject("account", account);
        mv.setViewName("atm/index");
        return mv;
    }
    
    @PostMapping("/atm/deposit")
    public String deposit(HttpSession session, @RequestParam("amount") String amount, RedirectAttributes redirectAtr) {
        Account account = (Account)session.getAttribute("account");
        
        double amountParsed = 0.0;
        try {
            amountParsed = Double.parseDouble(amount);
        } catch (NumberFormatException e) {}
        
        if (amountParsed > 0) {
            account.deposit(amountParsed);
        } else {
            redirectAtr.addFlashAttribute("error", "Amount must be greater than zero");
        }
        
        return "redirect:/atm";
    }
    
    @PostMapping("/atm/withdraw")
    public String withdraw(HttpSession session, @RequestParam("amount") String amount, RedirectAttributes redirectAtr) {
        Account account = (Account)session.getAttribute("account");
        
        double amountParsed = 0.0;
        try {
            amountParsed = Double.parseDouble(amount);
        } catch (NumberFormatException e) {}
        
        if (amountParsed > 0 && amountParsed < account.getBalance()) {
            account.withdraw(amountParsed);
        } else {
            redirectAtr.addFlashAttribute("error", "Amount must be greater than zero and not greater than your balance");
        }
        
        return "redirect:/atm";
    }
    
    @GetMapping("/atm/exit")
    public String exit(HttpSession session) {
        session.removeAttribute("account");
        return "redirect:/";
    }
}
