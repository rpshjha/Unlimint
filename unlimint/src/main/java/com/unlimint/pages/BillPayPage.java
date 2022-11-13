package com.unlimint.pages;

import com.unlimint.pojo.User;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

@Log4j
public class BillPayPage extends Page {

    public BillPayPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        log.info("verifying if bill pay page is displayed..");
        return wait.until(d -> this.driver.getTitle().contains("ParaBank | Bill Pay"));
    }

    private final By inputPayeeName = By.name("payee.name");
    private final By inputAddress = By.name("payee.address.street");
    private final By inputCity = By.name("payee.address.city");
    private final By inputState = By.name("payee.address.state");
    private final By inputZipcode = By.name("payee.address.zipCode");
    private final By inputPhone = By.name("payee.phoneNumber");
    private final By inputAccount = By.name("payee.accountNumber");
    private final By inputVerifyAccount = By.name("verifyAccount");
    private final By inputAmount = By.name("amount");
    private final By inputFromAccount = By.name("fromAccountId");

    private final By btnSendPayment = By.cssSelector("input.button[value='Send Payment'][type='submit']");

    public BillPayPage payBillTo(User user, String accountNo, int amount) {
        log.info("paying bill to recipient");

        this.driver.findElement(inputPayeeName).sendKeys(user.getFirstName() + " " + user.getLastName());
        this.driver.findElement(inputAddress).sendKeys(user.getLocation().getAddress());
        this.driver.findElement(inputCity).sendKeys(user.getLocation().getCity());
        this.driver.findElement(inputState).sendKeys(user.getLocation().getState());
        this.driver.findElement(inputZipcode).sendKeys(String.valueOf(user.getLocation().getZipcode()));
        this.driver.findElement(inputPhone).sendKeys(user.getPhone());
        this.driver.findElement(inputAccount).sendKeys(accountNo);
        this.driver.findElement(inputVerifyAccount).sendKeys(accountNo);

        this.driver.findElement(inputAmount).sendKeys(String.valueOf(amount));

        Select select = new Select(this.driver.findElement(inputFromAccount));
        select.getOptions().stream().findFirst().ifPresent(WebElement::click);

        this.driver.findElement(btnSendPayment).click();
        return this;
    }

    public boolean isPaymentSuccessful() {
        log.info("verifying payment success message");

        WebElement h1 = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[ng-show='showResult'] h1.title")));
        WebElement p = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[ng-show='showResult'] h1.title + p + p")));

        return h1.getText().contains("Bill Payment Complete") && p.getText().contains("See Account Activity for more details.");
    }

}
