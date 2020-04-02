package org.covid19.contactbase.controller;

import org.covid19.contactbase.model.Authority;
import org.covid19.contactbase.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class AdminController extends AuthenticatedAdminController {

    private AuthorityService authorityService;

    @Autowired
    public AdminController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/authorities", method = RequestMethod.POST)
    public void addAuthority(@Valid @RequestBody Authority authority) {
        authorityService.create(authority);
    }
}
