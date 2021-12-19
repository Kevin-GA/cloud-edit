package com.newtranx.cloud.edit.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "newtranx.account")
public class AccountConfig {
    private String domainname;
    private String client_id;
    private String client_secret;
    private String token_status;
}
