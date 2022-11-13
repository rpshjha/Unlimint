package com.unlimint.pages;

import com.unlimint.pojo.User;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        this.element.enterText(inputPayeeName,recipient.getFirstName() + " " + recipient.getLastName());
        this.element.enterText(inputAddress , recipient.getLocation().getAddress());
        this.element.enterText(inputCity,recipient.getLocation().getCity());
        this.element.enterText(inputState, recipient.getLocation().getState());
        this.element.enterText(inputZipcode, String.valueOf(recipient.getLocation().getZipcode()));
        this.element.enterText(inputPhone, recipient.getPhone());
        this.element.enterText(inputAccount, accountNoToBeTransferredTo);
        this.element.enterText(inputVerifyAccount, accountNoToBeTransferredTo);
        this.element.enterText(inputAmount, String.valueOf(amountToBeTransferred));

        Select select = new Select(this.driver.findElement(inputFromAccount));
        select.getOptions().stream().findFirst().ifPresent(WebElement::click);

        this.element.click(btnSendPayment);
    }

    public boolean isPaymentSuccessful() {
        log.info("verifying payment success message");

        String h1 = this.element.getText(By.cssSelector("div[ng-show='showResult'] h1.title"));
        String p = this.element.getText(By.cssSelector("div[ng-show='showResult'] h1.title + p + p"));

        log.info("***  bill payment details ***\n" +
                "payee name :" + getPayeeName() + "\n" +
                "amount :" + getTransferredAmount() + "\n" +
                "transferred from account no :" + getFromAccountNo());
        return h1.contains("Bill Payment Complete") && p.contains("See Account Activity for more details.");
    }

    public String getPayeeName() {
        return element.getText(payeeName);
    }

    public int getTransferredAmount() {
        String text = this.element.getText(transferredAmount);
        text = text.split("\\.")[0].replaceAll("\\W+", "");
        return Integer.parseInt(text);
    }

    public String getFromAccountNo() {
        return this.element.getText(fromAccount);
    }

}
