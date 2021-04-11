package com.khushal.Assignment.models;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("app.default")
@Data
@Component
public class Properties {

    private String username;
    private String password;
}
