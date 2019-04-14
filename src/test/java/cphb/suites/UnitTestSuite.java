package cphb.suites;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cphb.AccountTest;
import cphb.CreditCardTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
//@SelectClasses({AccountTest.class, CreditCardTest.class})
@Suite.SuiteClasses({AccountTest.class, CreditCardTest.class})
public class UnitTestSuite {
    
}
