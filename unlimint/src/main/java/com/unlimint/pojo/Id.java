package com.unlimint.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Id {

    @JsonProperty("name")
    private String name;
    @JsonProperty("value")
    private String value;

}