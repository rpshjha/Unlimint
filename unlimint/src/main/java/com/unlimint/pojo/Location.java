package com.unlimint.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
@ToString
public class Location {

    private String address;
    private String city;
    private String state;
    private String zipcode;

    public static String getRandomLocation() {
        List<String> location = Arrays.asList("AU", "BR", "CA", "CH", "DE", "DK", "ES", "FI", "FR", "GB", "IE", "IN", "IR", "MX", "NL", "NO", "RS", "TR", "UA", "US");
        return location.get(new Random().nextInt(location.size()));
    }
}
