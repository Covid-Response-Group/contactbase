package org.covid19.contactbase.service;

import org.covid19.contactbase.model.Device;
import org.covid19.contactbase.util.Json;
import org.covid19.contactbase.util.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public DeviceService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public String register(Device device) {
        String token = Jwt.getDeviceToken(device);

        if (stringRedisTemplate.hasKey(getDeviceKey(device))) {
            return token;
        }

        device.setRegistrationTimeStamp(System.currentTimeMillis());

        String deviceString = Json.encode(device);

        if (deviceString != null) {
            stringRedisTemplate.opsForValue().set(getDeviceKey(device), deviceString);
            return token;
        }

        throw new RuntimeException("Error while registering device");
    }

    public void markInfected(String deviceId) {
        stringRedisTemplate.opsForValue().set(getInfectedKey(deviceId), "1");
    }

    public void unMarkInfected(String deviceId) {
        stringRedisTemplate.delete(getInfectedKey(deviceId));
    }

    public Boolean isInfected(String deviceId) {
        return stringRedisTemplate.hasKey(getInfectedKey(deviceId));
    }

    private String getDeviceKey(Device device) {
        return "DEVICE/" + device.getDeviceId();
    }

    private String getInfectedKey(String deviceId) {
        return "INFECTED/" + deviceId;
    }
}
