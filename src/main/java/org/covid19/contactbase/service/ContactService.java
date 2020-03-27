package org.covid19.contactbase.service;

import org.covid19.contactbase.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public ContactService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void store(String deviceId, List<Contact> contacts) {
        for (Contact contact : contacts) {
            stringRedisTemplate.opsForSet().add(getContactKey(deviceId, contact.getDeviceId()),
                    contact.getSpatialTemporalStamps());
        }
    }

    private String getContactKey(String sourceDeviceId, String targetDeviceId) {
        Integer i = sourceDeviceId.compareTo(targetDeviceId);

        if (i < 0) {
            return "CONTACT_" + sourceDeviceId + "_" + targetDeviceId;
        } else {
            return "CONTACT_" + targetDeviceId + "_" + sourceDeviceId;
        }
    }
}
