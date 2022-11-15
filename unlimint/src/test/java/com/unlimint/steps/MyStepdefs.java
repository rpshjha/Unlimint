package com.unlimint.steps;

import com.unlimint.api.GenerateUser;
import com.unlimint.cucumber.Context;
import com.unlimint.cucumber.TestContext;
import com.unlimint.pages.*;
import com.unlimint.pojo.User;
import com.unlimint.pojo.Users;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;

import java.util.List;

import static com.unlimint.core.DriverInstance.getDriver;
import static org.junit.jupiter.api.Assertions.*;

@Log4j
public class MyStepdefs {

    private final TestContext testContext;

    public MyStepdefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I generate {int} users of {string} nationality")
    public void iGenerateUsers(int noOfUser, String nationality) {
        Response response = GenerateUser.generate(noOfUser, nationality);
        Users users = GenerateUser.parseResponse(response);

        assert users != null;

        log.info("sender user data is generated \n" + users.getUserList().get(0));
        testContext.getScenarioContext().setContext(Context.SENDER, users.getUserList().get(0));

        log.info("recipient user data is generated \n" + users.getUserList().get(1));
        testContext.getScenarioContext().setContext(Context.RECIPIENT, users.getUserList().get(1));
    }

    @Given("I generate {int} users")
    public void iGenerateUsers(int noOfUser) {
        Response response = GenerateUser.generate(noOfUser);
        Users users = GenerateUser.parseResponse(response);

        assert users != null;

        log.info("sender user data is generated \n" + users.getUserList().get(0));
        testContext.getScenarioContext().setContext(Context.SENDER, users.getUserList().get(0));

        log.info("recipient user data is generated \n" + users.getUserList().get(1));
        testContext.getScenarioContext().setContext(Context.RECIPIENT, users.getUserList().get(1));
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
            assertFalse(accountServicesPage.isError(com.unlimint.constant.Error.INTERNAL_ERROR), "internal error occurred..");
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

        AccountServicesPage accountServicesPage = loginPage.loginAs(user);
        assertFalse(accountServicesPage.isError(com.unlimint.constant.Error.INTERNAL_ERROR), "internal error occurred..");
        assertTrue(accountServicesPage.isAt(), "not able to login user");
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
        assertEquals(billPay.getPayeeName(), recipient.getName().getFirst() + " " + recipient.getName().getLast(), "payee name is not correct");
        assertEquals(billPay.getTransferredAmount(), amount, "amount transferred is not correct");
        assertEquals(billPay.getFromAccountNo(), accountNoSender, "account no of sender is not correct");

        accountServicesPage.logout();
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        Page page = new LoginPage(getDriver());
        page.navigateTo(url);

        assertTrue(page.isAt(), "not able to navigate to home page");
    }

    @And("I want to find my login info")
    public void iWantToFindMyLoginInfo() {
        LoginPage loginPage = new LoginPage(getDriver());
        CustomerLookupPage customerLookupPage = loginPage.goToForgotLoginInfoPage();

        User recipient = (User) testContext.getScenarioContext().getContext(Context.RECIPIENT);
        AccountServicesPage accountServicesPage = customerLookupPage.findMyLoginInfo(recipient);

        accountServicesPage.logout();
    }
}
