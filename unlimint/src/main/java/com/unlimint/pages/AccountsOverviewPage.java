package com.unlimint.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class AccountsOverviewPage {

    private static final Logger logger = LoggerFactory.getLogger(AccountsOverviewPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    public AccountsOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By txt_Account_no = By.xpath("//table[@id='accountTable']/tbody/tr[@ng-repeat='account in accounts']/td/a");


    public String getAccountNo() {
        return this.wait.until(ExpectedConditions.visibilityOfElementLocated(txt_Account_no)).getText();
    }

    public boolean isAt() {
        return wait.until(d -> this.driver.getTitle().contains("ParaBank | Accounts Overview"));
    }

}
