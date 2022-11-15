package com.unlimint.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unlimint.pojo.Error;
import com.unlimint.pojo.Location;
import com.unlimint.pojo.Users;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;

@Log4j
public class GenerateUser {

    public static Response generate(int noOfUser) {
        log.info("generating users .. ");
        return RestAssured.given()
                .baseUri("https://randomuser.me")
                .queryParam("format", "pretty")
                .queryParam("results", noOfUser)
                .queryParam("nat", Location.getRandomLocation())
                .log().all()
                .get("/api");
    }

    public static Response generate(int noOfUser, String nationality) {
        log.info("generating users .. ");
        return RestAssured.given()
                .baseUri("https://randomuser.me")
                .queryParam("format", "pretty")
                .queryParam("results", noOfUser)
                .queryParam("password", "special,upper,lower,number,8-16")
                .queryParam("nat", nationality)
                .log().all()
                .get("/api");
    }

    public static Users parseResponse(Response response) {
        Users users = null;
        ObjectMapper objectMapper = null;
        try {
            objectMapper = new ObjectMapper();
            users = objectMapper.readValue(response.body().asString(), Users.class);
        } catch (JsonProcessingException e) {
            try {
                log.error(e);
                Error error = objectMapper.readValue(response.body().asString(), Error.class);
                throw new RuntimeException(error.getError());
            } catch (JsonProcessingException ex) {
                log.error(e);
            }
        }
        return users;
    }
}
