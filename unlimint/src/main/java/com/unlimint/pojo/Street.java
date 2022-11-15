package com.unlimint.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties({"number"})
public class Street {

    @JsonProperty("name")
    private String name;
}
