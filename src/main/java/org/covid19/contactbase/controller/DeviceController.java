package org.covid19.contactbase.controller;

import org.covid19.contactbase.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class DeviceController extends AuthenticatedDeviceController {

    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RequestMapping(value = "/device/infected", method = RequestMethod.POST)
    public void markInfected() {
        deviceService.markInfected(getDeviceId());
    }

    @RequestMapping(value = "/device/infected", method = RequestMethod.DELETE)
    public void unMarkInfected() {
        deviceService.unMarkInfected(getDeviceId());
    }

    @RequestMapping(value = "/device/infected", method = RequestMethod.GET)
    public Boolean isInfected() {
        return deviceService.isInfected(getDeviceId());
    }
}
