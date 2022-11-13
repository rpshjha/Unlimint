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

    private final By payeeName = By.cssSelector("span#payeeName");
    private final By transferredAmount = By.cssSelector("span#amount");
    private final By fromAccount = By.cssSelector("span#fromAccountId");

    /**
     * @param recipient
     * @param accountNoToBeTransferredTo
     * @param amountToBeTransferred
     */
    public void payBillTo(User recipient, String accountNoToBeTransferredTo, int amountToBeTransferred) {
        log.info("paying bill to recipient");

        this.driver.findElement(inputPayeeName).sendKeys(recipient.getFirstName() + " " + recipient.getLastName());
        this.driver.findElement(inputAddress).sendKeys(recipient.getLocation().getAddress());
        this.driver.findElement(inputCity).sendKeys(recipient.getLocation().getCity());
        this.driver.findElement(inputState).sendKeys(recipient.getLocation().getState());
        this.driver.findElement(inputZipcode).sendKeys(String.valueOf(recipient.getLocation().getZipcode()));
        this.driver.findElement(inputPhone).sendKeys(recipient.getPhone());
        this.driver.findElement(inputAccount).sendKeys(accountNoToBeTransferredTo);
        this.driver.findElement(inputVerifyAccount).sendKeys(accountNoToBeTransferredTo);

        this.driver.findElement(inputAmount).sendKeys(String.valueOf(amountToBeTransferred));

        Select select = new Select(this.driver.findElement(inputFromAccount));
        select.getOptions().stream().findFirst().ifPresent(WebElement::click);

        this.driver.findElement(btnSendPayment).click();
    }

    public boolean isPaymentSuccessful() {
        log.info("verifying payment success message");

        WebElement h1 = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[ng-show='showResult'] h1.title")));
        WebElement p = this.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[ng-show='showResult'] h1.title + p + p")));

        log.info("***  bill payment details ***\n" +
                "payee name :" + getPayeeName() + "\n" +
                "amount :" + getTransferredAmount() + "\n" +
                "transferred from account no :" + getFromAccountNo());
        return h1.getText().contains("Bill Payment Complete") && p.getText().contains("See Account Activity for more details.");
    }

    public String getPayeeName() {
        return driver.findElement(payeeName).getText();
    }

    public int getTransferredAmount() {
        String text = driver.findElement(transferredAmount).getText();
        text = text.split("\\.")[0].replaceAll("\\W+", "");
        return Integer.parseInt(text);
    }

    public String getFromAccountNo() {
        return driver.findElement(fromAccount).getText();
    }

}
