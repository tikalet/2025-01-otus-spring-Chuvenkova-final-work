package ru.otus.laboratory.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public interface JwtProperty {

    RSAPublicKey getPublicKey();

    RSAPrivateKey getPrivateKey();
}
