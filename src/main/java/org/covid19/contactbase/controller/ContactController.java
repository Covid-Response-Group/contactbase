package org.covid19.contactbase.controller;

import org.covid19.contactbase.model.Contact;
import org.covid19.contactbase.request.QueryRequest;
import org.covid19.contactbase.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/contacts/query", method = RequestMethod.POST)
    public List<Contact> list(@Valid @RequestBody QueryRequest queryRequest) {
        return contactService.query(getDeviceId(),
                queryRequest.getGeoHashes(),
                queryRequest.getFromDateStamp(),
                queryRequest.getToDateStamp());
    }
}
