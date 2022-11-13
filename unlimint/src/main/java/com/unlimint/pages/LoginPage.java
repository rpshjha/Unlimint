package com.unlimint.pages;

import com.unlimint.constant.Error;
import com.unlimint.exception.InvalidUsernameAndPasswordException;
import com.unlimint.pojo.Result;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@Log4j
public class LoginPage extends Page {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if welcome page is displayed..");
        return wait.until(d -> getPageTitle().contains("ParaBank | Welcome | Online Banking") && getPageHeading().contains("Customer Login"));
    }

    private final By btnRegister = By.linkText("Register");

    private final By inputUsername = By.name("username");
    private final By inputPassword = By.name("password");
    private final By btnLogin = By.cssSelector("input[value='Log In']");

    public RegistrationPage goToRegistrationPage() {
        log.info("navigating to user registration page");
        this.driver.findElement(btnRegister).click();
        return new RegistrationPage(driver);
    }

    public AccountServicesPage loginAs(Result user) {
        log.info("logging in as ..\n" + user.getLogin().getUsername() + "\n" + user.getLogin().getPassword());

        this.driver.findElement(inputUsername).sendKeys(user.getLogin().getUsername());
        this.driver.findElement(inputPassword).sendKeys(user.getLogin().getPassword());
        this.driver.findElement(btnLogin).click();

        if (isError(Error.USERNAME_PASSWORD_NOT_VERIFIED))
            throw new InvalidUsernameAndPasswordException();
        else{
            log.info("login successful!");
        }
        return new AccountServicesPage(this.driver);
    }


}
