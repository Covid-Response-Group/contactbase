package org.covid19.contactbase.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

public class Device implements Serializable {

    @NotBlank
    private String deviceId;

    @NotNull
    private Map<String, Object> user;

    private Long registrationTimeStamp;

    public Device() {

    }

    public Device(String deviceId, Map<String, Object> user, Long registrationTimeStamp) {
        this.deviceId = deviceId;
        this.user = user;
        this.registrationTimeStamp = registrationTimeStamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, Object> getUser() {
        return user;
    }

    public void setUser(Map<String, Object> user) {
        this.user = user;
    }

    public Long getRegistrationTimeStamp() {
        return registrationTimeStamp;
    }

    public void setRegistrationTimeStamp(Long registrationTimeStamp) {
        this.registrationTimeStamp = registrationTimeStamp;
    }
}
