package com.unlimint.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.cucumber.Context;
import com.unlimint.cucumber.TestContext;
import com.unlimint.pages.*;
import com.unlimint.pojo.Result;
import com.unlimint.pojo.Users;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;

import java.util.List;

import static com.unlimint.core.DriverInstance.getDriver;
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

        ObjectMapper objectMapper = new ObjectMapper();
        Users users = null;
        try {
            users = objectMapper.readValue(response.body().asString(), Users.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        assert users != null;
        testContext.getScenarioContext().setContext(Context.SENDER, users.getResults().get(0));
        testContext.getScenarioContext().setContext(Context.RECIPIENT, users.getResults().get(1));
    }


    @And("I register user as")
    public void iRegisterUserAs(List<String> users) {
        users.forEach(user -> {

            LoginPage loginPage = new LoginPage(getDriver());
            RegistrationPage register = loginPage.goToRegistrationPage();

            assertTrue(register.isAt(), "not able to navigate to registration page");

            Result userType = null;
            if (user.equals("SENDER"))
                userType = (Result) testContext.getScenarioContext().getContext(Context.SENDER);
            else if (user.equals("RECIPIENT"))
                userType = (Result) testContext.getScenarioContext().getContext(Context.RECIPIENT);

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

        Result user = (Result) testContext.getScenarioContext().getContext(Context.SENDER);

        AccountServicesPage overviewPage = loginPage.loginAs(user);
        assertTrue(overviewPage.isAt(), "not able to login user");
    }

    @Then("I can transfer amount {int} to RECIPIENT")
    public void iCanTransferAmountToRECIPIENT(int amount) {
        AccountServicesPage accountServicesPage = new AccountServicesPage(getDriver());

        BillPayPage billPay = accountServicesPage.goToBillPayPage();
        assertTrue(billPay.isAt(), "not able to navigate to bill pay page");

        Result user = (Result) testContext.getScenarioContext().getContext(Context.RECIPIENT);
        String accountNoRecipient = (String) testContext.getScenarioContext().getContext(Context.RECIPIENT_ACCOUNT_NO);

        billPay.payBillTo(user, accountNoRecipient, amount);

        assertTrue(billPay.isPaymentSuccessful(), "bill payment could not be done");
    }

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        Page page = new LoginPage(getDriver());

        page.navigateTo(url);
        assertTrue(page.isAt(), "not able to navigate to home page");
    }

}
