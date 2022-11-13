package com.unlimint.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j
public class AccountServicesPage extends Page {

    public AccountServicesPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if accounts services page is displayed..");
        return wait.until(d -> getPageHeading().contains("Account Services"));
    }

    private final By linkBillPay = By.linkText("Bill Pay");
    private final By linkAccountOverview = By.linkText("Accounts Overview");
    private final By linkLogout = By.linkText("Log Out");

    public boolean isRegistered(String username) {
        log.info("checking for registration success message");
        String titleHeading = this.element.getText(By.cssSelector("h1.title"));
        String titleDesc = this.element.getText(By.cssSelector("h1.title + p"));

        return titleHeading.contains("Welcome " + username) && titleDesc.contains("Your account was created successfully. You are now logged in.");
    }

    public BillPayPage goToBillPayPage() {
        log.info("navigating to bill pay page");
        this.element.click(linkBillPay);
        return new BillPayPage(driver);
    }

    public AccountsOverviewPage goToAccountsOverviewPage() {
        log.info("navigating to accounts overview page");
        this.element.click(linkAccountOverview);
        return new AccountsOverviewPage(driver);
    }

    public LoginPage logout() {
        log.info("logging out user");
        this.element.click(linkLogout);
        return new LoginPage(driver);
    }
}
