package com.unlimint.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties({"gender", "description", "email", "dob", "registered", "phone", "picture", "nat"})
public class User {

    @JsonProperty("name")
    private Name name;
    private Location location;
    @JsonProperty("cell")
    private String phone;
    @JsonProperty("id")
    private Id ssn;
    private Login login;

}
