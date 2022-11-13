package com.unlimint.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {

    private String firstName;
    private String lastName;
    private Location location;
    private String phone;
    private String ssn;
    private Login login;

}
