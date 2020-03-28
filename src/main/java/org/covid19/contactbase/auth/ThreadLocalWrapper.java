package org.covid19.contactbase.auth;

import org.covid19.contactbase.model.Authority;
import org.covid19.contactbase.model.Device;

public class ThreadLocalWrapper {

    private static final ThreadLocal<Device> deviceContext;
    private static final ThreadLocal<Authority> authorityContext;

    static {
        deviceContext = new ThreadLocal<>();
        authorityContext = new ThreadLocal<>();
    }

    public static Device getDevice() {
        return deviceContext.get();
    }

    public static void setDevice(Device device) {
        deviceContext.set(device);
    }

    public static Authority getAuthority() {
        return authorityContext.get();
    }

    public static void setAuthority(Authority authority) {
        authorityContext.set(authority);
    }
}
