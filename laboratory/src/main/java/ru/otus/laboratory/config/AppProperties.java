package ru.otus.laboratory.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Setter
@ConfigurationProperties(prefix = "custom-security")
public class AppProperties implements JwtProperty {

    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    @Override
    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }
}
