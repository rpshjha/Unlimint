package com.unlimint.steps;

import com.unlimint.pojo.Location;
import com.unlimint.pojo.Login;
import com.unlimint.pojo.User;

public class TestHelper {

    /**
     * @param response
     * @param index
     * @return User
     */
    static User setUserData(io.restassured.response.Response response, int index) {
        User user = new User();
        user.setFirstName(response.jsonPath().getString("results[" + index + "].name.first"));
        user.setLastName(response.jsonPath().getString("results[" + index + "].name.last"));
        Location senderLoc = new Location();
        user.setLocation(senderLoc);
        user.getLocation().setAddress(response.jsonPath().getString("results[" + index + "].location.street.name"));
        user.getLocation().setCity(response.jsonPath().getString("results[" + index + "].location.city"));
        user.getLocation().setState(response.jsonPath().getString("results[" + index + "].location.state"));
        user.getLocation().setZipcode(response.jsonPath().getString("results[" + index + "].location.postcode"));
        user.setPhone(response.jsonPath().getString("results[" + index + "].cell"));
        user.setSsn(response.jsonPath().getString("results[" + index + "].id.value"));
        Login senderLogin = new Login();
        user.setLogin(senderLogin);
        user.getLogin().setUsername(response.jsonPath().getString("results[" + index + "].login.username"));
        user.getLogin().setPassword(response.jsonPath().getString("results[" + index + "].login.password"));
        return user;
    }
}
