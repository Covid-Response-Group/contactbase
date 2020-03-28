package org.covid19.contactbase.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.covid19.contactbase.model.Authority;
import org.covid19.contactbase.model.Device;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

public class Jwt {

    public Jwt() {

    }

    public static Device getDevice(String token, String secretKey) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
            Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

            return (Device) Json.decode(
                    Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject(),
                    Device.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static Authority getAuthority(String token, String secretKey) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
            Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

            return (Authority) Json.decode(
                    Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject(),
                    Authority.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDeviceToken(Device device, String secretKey) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder().setSubject(Json.encode(device))
                .signWith(signatureAlgorithm, signingKey).compact();
    }

    public static String getAuthorityToken(Authority authority, String secretKey) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder().setSubject(Json.encode(authority))
                .signWith(signatureAlgorithm, signingKey).compact();
    }
}
