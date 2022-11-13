package com.unlimint.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Log4j
public class AccountServicesPage extends Page {

    public AccountServicesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if accounts services page is displayed..");
        return wait.until(d -> getPageTitle().contains("ParaBank | Customer Created") && getPageHeading().contains("Account Services"));
    }

    private final By linkBillPay = By.linkText("Bill Pay");
    private final By linkAccountOverview = By.linkText("Accounts Overview");
    private final By linkLogout = By.linkText("Log Out");

    public boolean isRegistered(String username) {
        log.info("checking for registration success message");
        WebElement h1 = this.driver.findElement(By.cssSelector("h1.title"));
        WebElement p = this.driver.findElement(By.cssSelector("h1.title + p"));

        return h1.getText().contains("Welcome " + username) && p.getText().contains("Your account was created successfully. You are now logged in.");
    }

    public BillPayPage goToBillPayPage() {
        log.info("navigating to bill pay page");
        this.driver.findElement(linkBillPay).click();
        return new BillPayPage(driver);
    }

    public AccountsOverviewPage goToAccountsOverviewPage() {
        log.info("navigating to accounts overview page");
        this.driver.findElement(linkAccountOverview).click();
        return new AccountsOverviewPage(driver);
    }

    public LoginPage logout() {
        log.info("logging out user");
        this.driver.findElement(linkLogout).click();
        return new LoginPage(driver);
    }
}
