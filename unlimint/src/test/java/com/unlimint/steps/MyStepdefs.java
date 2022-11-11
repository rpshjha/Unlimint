package com.unlimint.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.cucumber.Context;
import com.unlimint.cucumber.TestContext;
import com.unlimint.pages.AccountsOverviewPage;
import com.unlimint.pages.BillPayPage;
import com.unlimint.pages.RegistrationPage;
import com.unlimint.pages.WelcomePage;
import com.unlimint.pojo.Result;
import com.unlimint.pojo.Users;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.unlimint.core.DriverInstance.getDriver;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyStepdefs {

    private static final Logger logger = LoggerFactory.getLogger(MyStepdefs.class);
    private TestContext testContext;

    public MyStepdefs(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("I generate {int} users")
    public void iGenerateUsers(int noOfUser) {

        logger.info("generating users .. ");
        Response response = RestAssured.given().baseUri("https://randomuser.me")
                .queryParam("format", "pretty")
                .queryParam("results", noOfUser)
                .queryParam("nat", "IN")
                .log().all()
                .get("/api");

        ObjectMapper objectMapper = new ObjectMapper();
        Users users = null;
        try {
            users = objectMapper.readValue(response.body().asString(), Users.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        testContext.getScenarioContext().setContext(Context.SENDER, users.getResults().get(0));
        testContext.getScenarioContext().setContext(Context.RECIPIENT, users.getResults().get(1));
    }


    @Given("I register user as")
    public void iRegisterUserAs(List<String> users) {

        users.forEach(user -> {

            WelcomePage home = new WelcomePage(getDriver());
            home.goTo();
            assertTrue(home.isAt(), "not able to navigate to home page");
            home.goToRegistrationPage();

            RegistrationPage register = new RegistrationPage(getDriver());
            assertTrue(register.isAt(), "not able to navigate to registration page");

            Result userType = null;
            if (user.equals("SENDER"))
                userType = (Result) testContext.getScenarioContext().getContext(Context.SENDER);
            else if (user.equals("RECIPIENT"))
                userType = (Result) testContext.getScenarioContext().getContext(Context.SENDER);

            register.registerUserAs(userType);
            assertTrue(register.isRegistered(userType.getLogin().getUsername()), "not able to register user");

            home.goToAccountsOverviewPage();
            AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(getDriver());
            assertTrue(accountsOverviewPage.isAt());

            String accountNo = accountsOverviewPage.getAccountNo();

            if (user.equals("SENDER"))
                testContext.getScenarioContext().setContext(Context.SENDER_ACCOUNT_NO, accountNo);
            else if (user.equals("RECIPIENT"))
                testContext.getScenarioContext().setContext(Context.RECIPIENT_ACCOUNT_NO, accountNo);

            home.logoutUser();
            assertTrue(home.isAt(), "not able to navigate to home page");

        });

    }

    @When("I login as a SENDER")
    public void iLoginAsASENDER() {

        WelcomePage home = new WelcomePage(getDriver());
        home.goTo();
        assertTrue(home.isAt(), "not able to navigate to home page");

        Result user = (Result) testContext.getScenarioContext().getContext(Context.SENDER);
        assertTrue(home.loginAs(user), "not able to login user");
    }

    @Then("I can transfer amount {string} to RECIPIENT")
    public void iCanTransferAmountToRECIPIENT(int amount) {

        WelcomePage home = new WelcomePage(getDriver());
        home.goToBillPayPage();

        BillPayPage billPay = new BillPayPage(getDriver());
        assertTrue(billPay.isAt(), "not able to navigate to bill pay page");

        Result user = (Result) testContext.getScenarioContext().getContext(Context.RECIPIENT);
        String accountNoRecipient = (String) testContext.getScenarioContext().getContext(Context.RECIPIENT_ACCOUNT_NO);

        billPay.payBillTo(user, accountNoRecipient, amount);

        assertTrue(billPay.isPaymentSuccessful(), "bill payment could not be done");
    }

}
