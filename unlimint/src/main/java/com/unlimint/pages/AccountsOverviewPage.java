package com.unlimint.pages;

import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j
public class AccountsOverviewPage extends Page {

    public AccountsOverviewPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if accounts overview page is displayed..");
        return wait.until(d -> browser.getPageTitle().contains("ParaBank | Accounts Overview"));
    }

    private final By txtAccountNo = By.xpath("//table[@id='accountTable']/tbody/tr[@ng-repeat='account in accounts']/td/a");

    public String getAccountNo() {
        log.info("fetching account no");
        return this.element.getText(txtAccountNo);
    }

}
