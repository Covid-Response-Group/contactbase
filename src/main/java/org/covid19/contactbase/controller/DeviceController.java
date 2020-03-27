package org.covid19.contactbase.controller;

import org.covid19.contactbase.model.Device;
import org.covid19.contactbase.response.TokenResponse;
import org.covid19.contactbase.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class DeviceController {

    private DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RequestMapping(value = "/devices", method = RequestMethod.POST)
    public TokenResponse register(@Valid @RequestBody Device device) {
        return new TokenResponse(deviceService.register(device));
    }
}
