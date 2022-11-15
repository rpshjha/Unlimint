package com.unlimint.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"uuid", "salt", "md5", "sha1", "sha256"})
public class Login {

    private String username;
    private String password;
}
