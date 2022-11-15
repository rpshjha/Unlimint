package com.unlimint.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Name {

    @JsonProperty("title")
    private String title;
    @JsonProperty("first")
    private String first;
    @JsonProperty("last")
    private String last;

}