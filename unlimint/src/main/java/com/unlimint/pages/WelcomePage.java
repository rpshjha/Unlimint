package com.unlimint.pages;

import com.unlimint.pojo.Result;
import com.unlimint.utils.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WelcomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By btn_register = By.linkText("Register");
    private By link_bill_pay = By.linkText("Bill Pay");
    private By link_accnt_overview = By.linkText("Accounts Overview");
    private By link_logout = By.linkText("Log Out");

    private By input_username = By.name("username");
    private By input_password = By.name("password");
    private By btn_login = By.cssSelector("input[value='Log In']");

    public void goTo(String url) {
        driver.get(PropertyReader.get("app.url"));
        driver.get(url);
    }

    public void goToRegistrationPage() {
        this.driver.findElement(btn_register).click();
    }

    public void goToBillPayPage() {
        this.driver.findElement(link_bill_pay).click();
    }

    public void goToAccountsOverviewPage() {
        this.driver.findElement(link_accnt_overview).click();
    }

    public void logoutUser() {
        this.driver.findElement(link_logout).click();
    }

    public boolean loginAs(Result user) {
        this.driver.findElement(input_username).sendKeys(user.getLogin().getUsername());
        this.driver.findElement(input_password).sendKeys(user.getLogin().getPassword());
        this.driver.findElement(btn_login).click();

        return new AccountsOverviewPage(this.driver).isAt();
    }

    public boolean isAt() {
        return wait.until(d -> this.driver.getTitle().contains("ParaBank | Welcome | Online Banking"));
    }

}
