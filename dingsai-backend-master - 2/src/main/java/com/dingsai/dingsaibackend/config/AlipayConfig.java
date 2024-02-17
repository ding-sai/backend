package com.dingsai.dingsaibackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {

    private String gateway;

    private String appid;

    private String pid;

    private String privateKey;

    private String publicKey;

    private String returnUrl;

    private String notifyUrl;

}
