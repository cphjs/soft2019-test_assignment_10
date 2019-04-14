package cphb;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OpenAccountTest {
    
    private final Logger logger = LoggerFactory.getLogger(OpenAccountTest.class);

    private String appUrl;
    private WebDriver driver;
    
    @BeforeEach
    public void setUp() {
        appUrl = System.getProperty("APP_URL");
        if (appUrl == null || appUrl.isEmpty()) {
            appUrl = "http://localhost:8080";
        }
        System.out.println("app url is " + appUrl);
        // Create a new instance of the Firefox driver
        // Notice that the remainder of the code relies on the interface, 
        // not the implementation.
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(true);
        driver = new FirefoxDriver(options);
    }
    
    @AfterEach 
    public void tearDown() {
        driver.quit();
    }
    
    @Test
    public void testIndexPageLoads() throws Exception {
        // And now use this to visit NetBeans
        driver.get(appUrl);

 
        (new WebDriverWait(driver, 1))
            .until((ExpectedCondition<Boolean>) (WebDriver d) -> d.findElement(By.id("name")) != null);
    }
    
    @Test
    public void testOpeningAnAccountWorks() {
        driver.get(appUrl + "/account/open");
        String customerName = "A customer";
        driver.findElement(By.id("name")).sendKeys(customerName);
        driver.findElement(By.className("btn")).click();
        (new WebDriverWait(driver, 2))
                .until(d -> d.getCurrentUrl().contains("/atm"));
    }
            
    
}
