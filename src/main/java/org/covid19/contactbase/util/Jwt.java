package org.covid19.contactbase.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.covid19.contactbase.model.Device;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

public class Jwt {

    private static final String SECRET_KEY = "covid!2019";

    public Jwt() {

    }

    public static Device getDevice(String token) {
        try {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
            Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

            return (Device) Json.decode(
                    Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject(),
                    Device.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getDeviceToken(Device device) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgorithm.getJcaName());

        return Jwts.builder().setSubject(Json.encode(device))
                .signWith(signatureAlgorithm, signingKey).compact();
    }
}
