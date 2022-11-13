package com.unlimint.pages;

import com.unlimint.exception.DuplicateUserNameException;
import com.unlimint.pojo.User;
import com.unlimint.utils.Random;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

@Log4j
public class RegistrationPage extends Page {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if registration page is displayed..");
        return wait.until(d ->browserActions.getPageTitle().contains("ParaBank | Register for Free Online Account Access"));
    }

    private final By inputFirstName = By.id("customer.firstName");
    private final By inputLastName = By.id("customer.lastName");
    private final By inputAddress = By.id("customer.address.street");
    private final By inputCity = By.id("customer.address.city");
    private final By inputState = By.id("customer.address.state");
    private final By inputZipcode = By.id("customer.address.zipCode");
    private final By inputPhone = By.id("customer.phoneNumber");
    private final By inputSsn = By.id("customer.ssn");
    private final By inputUsername = By.id("customer.username");
    private final By inputPassword = By.id("customer.password");
    private final By inputConfirmPassword = By.id("repeatedPassword");

    private final By btnRegister = By.cssSelector("input.button[type='submit'][value='Register']");

    private final By errorDuplicateUsername = By.id("customer.username.errors");

    public AccountServicesPage registerUserAs(User user) {
        log.info("registering user as " + user.getFirstName() + " " + user.getLastName());

        this.driver.findElement(inputFirstName).sendKeys(user.getFirstName());
        this.driver.findElement(inputLastName).sendKeys(user.getLastName());
        this.driver.findElement(inputAddress).sendKeys(user.getLocation().getAddress());
        this.driver.findElement(inputCity).sendKeys(user.getLocation().getCity());
        this.driver.findElement(inputState).sendKeys(user.getLocation().getState());
        this.driver.findElement(inputZipcode).sendKeys(String.valueOf(user.getLocation().getZipcode()));
        this.driver.findElement(inputPhone).sendKeys(user.getPhone());
        this.driver.findElement(inputSsn).sendKeys(user.getSsn());

        String username = user.getLogin().getUsername() + Random.getRandomInt(4);
        log.info("setting username for " + user.getFirstName() + " as " + username);
        user.getLogin().setUsername(username);
        this.driver.findElement(inputUsername).sendKeys(username);

        this.driver.findElement(inputPassword).sendKeys(user.getLogin().getPassword());
        this.driver.findElement(inputConfirmPassword).sendKeys(user.getLogin().getPassword());

        this.driver.findElement(btnRegister).click();

        if (isUserNameAlreadyExist()) {
            throw new DuplicateUserNameException(user.getLogin().getUsername());
        } else {
            log.info("registration successful!");
        }
        return new AccountServicesPage(driver);
    }

    public boolean isUserNameAlreadyExist() {
        try {
            log.info("checking for duplicate username message..");
            return this.driver.findElement(errorDuplicateUsername).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
