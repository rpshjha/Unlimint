package com.unlimint.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.pages.AccountsOverviewPage;
import com.unlimint.pages.BillPayPage;
import com.unlimint.pages.RegistrationPage;
import com.unlimint.pages.WelcomePage;
import com.unlimint.pojo.Result;
import com.unlimint.pojo.Users;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UITest {

    private Result sender;
    private Result recipient;
    private WebDriver driver;

    @Test
    @Order(1)
    void generateUsers() {

        Response response = RestAssured.given().baseUri("https://randomuser.me")
                .queryParam("format", "pretty")
                .queryParam("results", "2")
                .queryParam("nat", "IN")
                .log().all()
                .get("/api");


        System.out.println(response.body().asString());

        ObjectMapper objectMapper = new ObjectMapper();
        Users users = null;
        try {
            users = objectMapper.readValue(response.body().asString(), Users.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        sender = users.getResults().get(0);
        recipient = users.getResults().get(1);

        System.out.println(sender.getName().getFirst());
        System.out.println(recipient.getName().getFirst());

    }

    @BeforeEach
    void setupDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
    }

    @Test
    @Order(2)
    void registerUser() {

        WelcomePage home = new WelcomePage(driver);
        home.goTo();
        assertTrue(home.isAt(), "not able to navigate to home page");
        home.goToRegistrationPage();

        RegistrationPage register = new RegistrationPage(driver);
        assertTrue(register.isAt(), "not able to navigate to registration page");
        register.registerUserAs(sender);

        assertTrue(register.isRegistered(sender.getLogin().getUsername()), "not able to register user");
        home.goToAccountsOverviewPage();
        AccountsOverviewPage accountsOverviewPage = new AccountsOverviewPage(driver);
        assertTrue(accountsOverviewPage.isAt());
        String accountNoSender = accountsOverviewPage.getAccountNo();
        System.out.println("account no for sender is " + accountNoSender);

        home.logoutUser();

        assertTrue(home.isAt(), "not able to navigate to home page");
        home.goToRegistrationPage();

        assertTrue(register.isAt(), "not able to navigate to registration page");
        register.registerUserAs(recipient);

        assertTrue(register.isRegistered(recipient.getLogin().getUsername()), "not able to register user");
        home.goToAccountsOverviewPage();
        assertTrue(accountsOverviewPage.isAt());
        String accountNoRecipient = accountsOverviewPage.getAccountNo();
        System.out.println("account no for recipient is " + accountNoRecipient);

        home.logoutUser();

        assertTrue(home.loginAs(sender), "not able to login user");

        home.goToBillPayPage();

        BillPayPage billPay = new BillPayPage(driver);
        assertTrue(billPay.isAt(), "not able to navigate to bill pay page");

        billPay.payBillTo(recipient, accountNoRecipient, 100);

        assertTrue(billPay.isPaymentSuccessful(), "bill payment could not be done");

    }


}
