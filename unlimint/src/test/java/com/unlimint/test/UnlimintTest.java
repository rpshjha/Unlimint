package com.unlimint.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.pages.BillPayPage;
import com.unlimint.pages.RegistrationPage;
import com.unlimint.pages.WelcomePage;
import com.unlimint.pojo.Result;
import com.unlimint.pojo.Users;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UnlimintTest {

    private Result sender;
    private Result recipient;

    @Test
    void test1() {

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

    @Test
    void test2() {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

        WelcomePage home = new WelcomePage(driver);
        home.goTo();
        assertTrue(home.isAt(), "not able to navigate to home page");
        home.goToRegistrationPage();

        RegistrationPage register = new RegistrationPage(driver);
        assertTrue(register.isAt(), "not able to navigate to registration page");
        register.registerUserAs(sender);

        assertTrue(register.isRegistered(sender.getLogin().getUsername()), "not able to register user");

        home.goToBillPayPage();

        BillPayPage billPay = new BillPayPage(driver);
        assertTrue(billPay.isAt(), "not able to navigate to bill pay page");

        billPay.payBillTo(recipient, 1000);
    }


}
