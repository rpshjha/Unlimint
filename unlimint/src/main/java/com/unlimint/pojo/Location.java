package com.unlimint.pojo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Location {

    private String address;
    private String city;
    private String state;
    private String zipcode;
}
