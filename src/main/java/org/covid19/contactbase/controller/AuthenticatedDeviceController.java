package org.covid19.contactbase.controller;

import org.covid19.contactbase.auth.ThreadLocalWrapper;
import org.covid19.contactbase.model.Device;

public class AuthenticatedDeviceController implements RequiresDeviceAuthentication {

    public Device getDevice() {
        return ThreadLocalWrapper.getDevice();
    }

    public String getDeviceId() {
        return getDevice().getDeviceId();
    }
}
