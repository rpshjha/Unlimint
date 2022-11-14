package com.unlimint.pages;

import com.unlimint.constant.Error;
import com.unlimint.exception.CustomerInfoNotProvided;
import com.unlimint.pojo.User;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

@Log4j
public class CustomerLookupPage extends Page {

    public CustomerLookupPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if forgot login page is displayed..");
        return wait.until(d -> this.browser.getPageTitle().contains("ParaBank | Customer Lookup"));
    }

    private final By inputFirstName = By.cssSelector("input#firstName");
    private final By inputLastName = By.cssSelector("input#lastName");
    private final By inputAddress = By.id("address.street");
    private final By inputCity = By.name("address.city");
    private final By inputState = By.name("address.state");
    private final By inputZipcode = By.name("address.zipCode");
    private final By inputSSN = By.id("ssn");
    private final By btnFindMyLoginInfo = By.cssSelector("input[value='Find My Login Info']");

    private final By txtUsername = By.xpath("//h1[@class='title']/following::p[2]/b[1]");
    private final By txtPassword = By.xpath("//h1[@class='title']/following::p[2]/b[2]");

    /**
     * @param user
     * @return
     */
    public AccountServicesPage findMyLoginInfo(User user) {
        log.info("recovering password ..");

        this.element.enterText(inputFirstName, user.getFirstName());
        this.element.enterText(inputLastName, user.getLastName());
        this.element.enterText(inputAddress, user.getLocation().getAddress());
        this.element.enterText(inputCity, user.getLocation().getCity());
        this.element.enterText(inputState, user.getLocation().getState());
        this.element.enterText(inputZipcode, user.getLocation().getZipcode());
        this.element.enterText(inputSSN, user.getSsn());
        this.element.click(btnFindMyLoginInfo);

        try {
            if (isError(Error.CUSTOMER_INFO_NOT_PROVIDED)) {
                throw new CustomerInfoNotProvided();
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        log.info("credentials : \n" + getUsername() + "\n" + getPassword());
        return new AccountServicesPage(driver);
    }

    public String getUsername() {
        return this.element.getText(txtUsername);
    }

    public String getPassword() {
        return this.element.getText(txtPassword);
    }
}
