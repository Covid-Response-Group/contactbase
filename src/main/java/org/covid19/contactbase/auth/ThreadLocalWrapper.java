package org.covid19.contactbase.auth;

import org.covid19.contactbase.model.Device;

public class ThreadLocalWrapper {

    private static final ThreadLocal<Device> deviceContext;

    static {
        deviceContext = new ThreadLocal<>();
    }

    public static Device getDevice() {
        return deviceContext.get();
    }

    public static void setDevice(Device device) {
        deviceContext.set(device);
    }
}
