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
        return wait.until(d -> browser.getPageTitle().contains("ParaBank | Register for Free Online Account Access"));
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

    /**
     *
     * @param user
     * @return
     */
    public AccountServicesPage registerUserAs(User user) {
        log.info("registering user as " + user.getFirstName() + " " + user.getLastName());

        this.element.enterText(inputFirstName, user.getFirstName());
        this.element.enterText(inputLastName, user.getLastName());
        this.element.enterText(inputAddress, user.getLocation().getAddress());
        this.element.enterText(inputCity, user.getLocation().getCity());
        this.element.enterText(inputState, user.getLocation().getState());
        this.element.enterText(inputZipcode, String.valueOf(user.getLocation().getZipcode()));
        this.element.enterText(inputPhone, user.getPhone());
        this.element.enterText(inputSsn, user.getSsn());

        String username = user.getLogin().getUsername() + Random.getRandomInt(4);
        log.info("setting username for " + user.getFirstName() + " as " + username);
        user.getLogin().setUsername(username);

        this.element.enterText(inputUsername, username);
        this.element.enterText(inputPassword, user.getLogin().getPassword());
        this.element.enterText(inputConfirmPassword, user.getLogin().getPassword());
        this.element.click(btnRegister);

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
            return this.element.isDisplayed(errorDuplicateUsername);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
