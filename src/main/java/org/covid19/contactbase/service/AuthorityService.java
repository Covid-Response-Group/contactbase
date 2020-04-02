package org.covid19.contactbase.service;

import org.covid19.contactbase.model.Authority;
import org.covid19.contactbase.util.Jwt;
import org.covid19.contactbase.util.Password;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorityService {

    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.secret.key}")
    private String jwtSecretKey;

    private static final String FIELD_PASSWORD = "password";

    @Autowired
    public AuthorityService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void create(Authority authority) {
        String authorityKey = getAuthorityKey(authority.getEmail());

        if (stringRedisTemplate.hasKey(authorityKey)) {
            throw new RuntimeException("Authority " + authority.getEmail() + " already exists");
        }

        Map<String, String> authorityDetails = new HashMap<>();
        authorityDetails.put(FIELD_PASSWORD, Password.encrypt(authority.getPassword()));

        stringRedisTemplate.opsForHash().putAll(authorityKey, authorityDetails);
    }

    public String authenticate(Authority authority) {
        Object hashedPassword = stringRedisTemplate.opsForHash().get(getAuthorityKey(authority.getEmail()), FIELD_PASSWORD);

        try {
            if (!Password.verify(authority.getPassword(), hashedPassword.toString())) {
                throw new RuntimeException("Invalid login credentials");
            }
        } catch (Exception e) {
            throw new RuntimeException("Invalid login credentials");
        }

        authority.setPassword(null);
        return Jwt.getAuthorityToken(authority, jwtSecretKey);
    }

    private String getAuthorityKey(String email) {
        return "AUTHORITY/" + email;
    }
}
