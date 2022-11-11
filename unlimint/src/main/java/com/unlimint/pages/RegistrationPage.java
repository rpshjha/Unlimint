package com.unlimint.pages;

import com.unlimint.pojo.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By input_first_name = By.id("customer.firstName");
    private By input_last_name = By.id("customer.lastName");
    private By input_address = By.id("customer.address.street");
    private By input_city = By.id("customer.address.city");
    private By input_state = By.id("customer.address.state");
    private By input_zipcode = By.id("customer.address.zipCode");
    private By input_phone = By.id("customer.phoneNumber");
    private By input_ssn = By.id("customer.ssn");
    private By input_username = By.id("customer.username");
    private By input_password = By.id("customer.password");
    private By input_confirm_password = By.id("repeatedPassword");

    private By btn_register = By.cssSelector("input.button[type='submit'][value='Register']");

    private By error_duplicate_username = By.id("customer.username.errors");

    public void registerUserAs(Result user) {
        this.driver.findElement(input_first_name).sendKeys(user.getName().getFirst());
        this.driver.findElement(input_last_name).sendKeys(user.getName().getLast());
        this.driver.findElement(input_address).sendKeys(user.getLocation().getStreet().getName());
        this.driver.findElement(input_city).sendKeys(user.getLocation().getCity());
        this.driver.findElement(input_state).sendKeys(user.getLocation().getState());
        this.driver.findElement(input_zipcode).sendKeys(String.valueOf(user.getLocation().getPostcode()));
        this.driver.findElement(input_phone).sendKeys(user.getPhone());
        this.driver.findElement(input_ssn).sendKeys(user.getId().getValue());
        this.driver.findElement(input_username).sendKeys(user.getLogin().getUsername());

        if (isUserNameAlreadyExist()) {
            System.out.println("username already exists , going ahead with new username");
            String username = user.getLogin().getUsername() + System.currentTimeMillis();
            user.getLogin().setUsername(username);
            this.driver.findElement(input_username).sendKeys(username);
        }
        this.driver.findElement(input_password).sendKeys(user.getLogin().getPassword());
        this.driver.findElement(input_confirm_password).sendKeys(user.getLogin().getPassword());

        this.driver.findElement(btn_register).click();
    }

    public boolean isRegistered(String username) {
        WebElement h1 = this.driver.findElement(By.cssSelector("h1.title"));
        WebElement p = this.driver.findElement(By.cssSelector("h1.title + p"));

        if (h1.getText().contains("Welcome " + username) && p.getText().contains("Your account was created successfully. You are now logged in."))
            return true;
        return false;
    }

    public boolean isUserNameAlreadyExist() {
        try {
            return this.driver.findElement(error_duplicate_username).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAt() {
        return wait.until(d -> this.driver.getTitle().contains("ParaBank | Register for Free Online Account Access"));
    }

}
