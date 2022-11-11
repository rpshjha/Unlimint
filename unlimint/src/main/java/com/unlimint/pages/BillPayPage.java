package com.unlimint.pages;

import com.unlimint.pojo.Result;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BillPayPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public BillPayPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By input_payee_name = By.name("payee.name");
    private By input_address = By.name("payee.address.street");
    private By input_city = By.name("payee.address.city");
    private By input_state = By.name("payee.address.state");
    private By input_zipcode = By.name("payee.address.zipCode");
    private By input_phone = By.name("payee.phoneNumber");
    private By input_account = By.name("payee.accountNumber");
    private By input_verify_account = By.name("verifyAccount");
    private By input_amount = By.name("amount");
    private By input_from_account = By.name("fromAccountId");

    private By btn_send_payment = By.cssSelector("input.button[value='Send Payment'][type='submit']");

    public void payBillTo(Result user, String accountNo, int amount) {

        this.driver.findElement(input_payee_name).sendKeys(user.getName().getFirst());
        this.driver.findElement(input_address).sendKeys(user.getLocation().getStreet().getName());
        this.driver.findElement(input_city).sendKeys(user.getLocation().getCity());
        this.driver.findElement(input_state).sendKeys(user.getLocation().getState());
        this.driver.findElement(input_zipcode).sendKeys(String.valueOf(user.getLocation().getPostcode()));
        this.driver.findElement(input_phone).sendKeys(user.getPhone());
        this.driver.findElement(input_account).sendKeys(accountNo);
        this.driver.findElement(input_verify_account).sendKeys(accountNo);

        this.driver.findElement(input_amount).sendKeys(String.valueOf(amount));

        Select select = new Select(this.driver.findElement(input_from_account));
        select.getOptions().stream().findFirst().get().click();

        this.driver.findElement(btn_send_payment).click();
    }

    public boolean isPaymentSuccessful() {
        WebElement h1 = this.driver.findElement(By.cssSelector("div[ng-show='showResult'] h1.title"));
        WebElement p = this.driver.findElement(By.cssSelector("div[ng-show='showResult'] h1.title + p + p"));

        if (h1.getText().contains("Bill Payment Complete") && p.getText().contains("See Account Activity for more details."))
            return true;
        return false;
    }

    public boolean isAt() {
        return wait.until(d -> this.driver.getTitle().contains("ParaBank | Bill Pay"));
    }

}
