package com.unlimint.pages;

import com.unlimint.exception.InvalidUsernameAndPasswordException;
import com.unlimint.pojo.User;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.unlimint.constant.Error.USERNAME_PASSWORD_NOT_VERIFIED;

@Log4j
public class LoginPage extends Page {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if welcome page is displayed..");
        return wait.until(d -> browser.getPageTitle().contains("ParaBank | Welcome | Online Banking") && getPageHeading().contains("Customer Login"));
    }

    private final By btnRegister = By.linkText("Register");
    private final By btnForgotLogin = By.xpath("//a[text()='Forgot login info?']");
    private final By inputUsername = By.name("username");
    private final By inputPassword = By.name("password");
    private final By btnLogin = By.cssSelector("input[value='Log In']");

    public RegistrationPage goToRegistrationPage() {
        log.info("navigating to user registration page");
        this.element.click(btnRegister);
        return new RegistrationPage(driver);
    }

    public CustomerLookupPage goToForgotLoginInfoPage() {
        log.info("navigating to forgot login info page");
        this.element.click(btnForgotLogin);
        return new CustomerLookupPage(driver);
    }

    public AccountServicesPage loginAs(User user) {
        log.info("logging in as ..\nusername : " + user.getLogin().getUsername() + "\npassword : " + user.getLogin().getPassword());

        this.element.enterText(inputUsername, user.getLogin().getUsername());
        this.element.enterText(inputPassword, user.getLogin().getPassword());
        this.element.click(btnLogin);

        if (isError(USERNAME_PASSWORD_NOT_VERIFIED))
            throw new InvalidUsernameAndPasswordException();
        else{
            log.info("login successful !!");
        }
        return new AccountServicesPage(this.driver);
    }


}
