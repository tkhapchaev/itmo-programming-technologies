package ru.tkhapchaev.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "certificates")
public record RsaProperties(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
}