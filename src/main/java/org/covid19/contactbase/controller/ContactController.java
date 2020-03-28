package org.covid19.contactbase.controller;

import org.covid19.contactbase.model.Contact;
import org.covid19.contactbase.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1")
public class ContactController extends AuthenticatedDeviceController {

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.POST)
    public void store(@Valid @RequestBody List<Contact> contacts) {
        contactService.store(getDeviceId(), contacts);
    }

    @RequestMapping(value = "/contacts", method = RequestMethod.GET)
    public List<Contact> list(@RequestParam String dateStamp,
                              @RequestParam String geohash) {
        return contactService.list(getDeviceId(), geohash, dateStamp);
    }
}
