package com.unlimint.steps;

import com.unlimint.cucumber.Context;
import com.unlimint.cucumber.TestContext;
import com.unlimint.pages.*;
import com.unlimint.pojo.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;

import java.util.List;

import static com.unlimint.core.DriverInstance.getDriver;
import static com.unlimint.steps.TestHelper.setUserData;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j
public class MyStepdefs {

    private final TestContext testContext;

    public MyStepdefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I generate {int} users of {string} nationality")
    public void iGenerateUsers(int noOfUser, String nationality) {
        log.info("generating users .. ");
        Response response = RestAssured.given()
                .baseUri("https://randomuser.me")
                .queryParam("format", "pretty")
                .queryParam("results", noOfUser)
                .queryParam("nat", nationality)
                .log().all()
                .get("/api");

        User sender = setUserData(response, 0);
        User recipient = setUserData(response, 1);

        log.info("sender user data is generated \n" + sender);
        log.info("recipient user data is generated \n" + recipient);

        testContext.getScenarioContext().setContext(Context.SENDER, sender);
        testContext.getScenarioContext().setContext(Context.RECIPIENT, recipient);
    }

    @And("I register user as")
    public void iRegisterUserAs(List<String> users) {
        users.forEach(user -> {

            LoginPage loginPage = new LoginPage(getDriver());
            RegistrationPage register = loginPage.goToRegistrationPage();

            assertTrue(register.isAt(), "not able to navigate to registration page");

            User userType = null;

            if (user.equals("SENDER")) {
                userType = (User) testContext.getScenarioContext().getContext(Context.SENDER);
            } else if (user.equals("RECIPIENT"))
                userType = (User) testContext.getScenarioContext().getContext(Context.RECIPIENT);

            assert userType != null;
            AccountServicesPage accountServicesPage = register.registerUserAs(userType);
            assertTrue(accountServicesPage.isRegistered(userType.getLogin().getUsername()), "not able to register user");

            AccountsOverviewPage accountsOverviewPage = accountServicesPage.goToAccountsOverviewPage();
            assertTrue(accountsOverviewPage.isAt());

            String accountNo = accountsOverviewPage.getAccountNo();

            if (user.equals("SENDER"))
                testContext.getScenarioContext().setContext(Context.SENDER_ACCOUNT_NO, accountNo);
            else testContext.getScenarioContext().setContext(Context.RECIPIENT_ACCOUNT_NO, accountNo);

            loginPage = accountServicesPage.logout();
            assertTrue(loginPage.isAt(), "not able to navigate to home page");
        });
    }

    @When("I login as a SENDER")
    public void iLoginAsASENDER() {
        LoginPage loginPage = new LoginPage(getDriver());

        User user = (User) testContext.getScenarioContext().getContext(Context.SENDER);

        AccountServicesPage overviewPage = loginPage.loginAs(user);
        assertTrue(overviewPage.isAt(), "not able to login user");
    }

    @Then("I can transfer amount {int} to RECIPIENT")
    public void iCanTransferAmountToRECIPIENT(int amount) {
        AccountServicesPage accountServicesPage = new AccountServicesPage(getDriver());

        BillPayPage billPay = accountServicesPage.goToBillPayPage();
        assertTrue(billPay.isAt(), "not able to navigate to bill pay page");

        User recipient = (User) testContext.getScenarioContext().getContext(Context.RECIPIENT);
        String accountNoRecipient = (String) testContext.getScenarioContext().getContext(Context.RECIPIENT_ACCOUNT_NO);
        String accountNoSender = (String) testContext.getScenarioContext().getContext(Context.SENDER_ACCOUNT_NO);

        billPay.payBillTo(recipient, accountNoRecipient, amount);
        assertTrue(billPay.isPaymentSuccessful(), "bill payment could not be done");
        assertEquals(billPay.getPayeeName(), recipient.getFirstName() + " " + recipient.getLastName(), "payee name is not correct");
        assertEquals(billPay.getTransferredAmount(), amount, "amount transferred is not correct");
        assertEquals(billPay.getFromAccountNo(), accountNoSender, "account no of sender is not correct");
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        Page page = new LoginPage(getDriver());
        page.navigateTo(url);

        assertTrue(page.isAt(), "not able to navigate to home page");
    }

}
