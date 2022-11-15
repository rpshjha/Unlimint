package com.unlimint.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
@JsonIgnoreProperties({"country", "coordinates", "timezone"})
public class Location {

    @JsonProperty("street")
    private Street address;
    private String city;
    private String state;
    @JsonProperty("postcode")
    private String zipcode;

    public static String getRandomLocation() {
        List<String> location = Arrays.asList("AU", "BR", "CA", "CH", "DE", "DK", "ES", "FI", "FR", "GB", "IE", "IN", "IR", "MX", "NL", "NO", "RS", "TR", "UA", "US");
        return location.get(new Random().nextInt(location.size()));
    }
}
