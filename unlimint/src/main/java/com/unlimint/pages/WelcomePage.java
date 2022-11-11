package com.unlimint.pages;

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

    public void goTo() {
        driver.get(PropertyReader.get("app.url"));
    }

    public void goToRegistrationPage() {
        this.driver.findElement(btn_register).click();
    }

    public void goToBillPayPage() {
        this.driver.findElement(link_bill_pay).click();
    }

    public boolean isAt() {
        return wait.until(d -> this.driver.getTitle().contains("ParaBank | Welcome | Online Banking"));
    }

}
