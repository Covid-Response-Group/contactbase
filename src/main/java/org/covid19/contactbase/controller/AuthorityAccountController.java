package org.covid19.contactbase.controller;

import org.covid19.contactbase.model.Authority;
import org.covid19.contactbase.response.TokenResponse;
import org.covid19.contactbase.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthorityAccountController {

    private AuthorityService authorityService;

    @Autowired
    public AuthorityAccountController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/authorities/authenticate", method = RequestMethod.POST)
    public TokenResponse authenticate(@Valid @RequestBody Authority authority) {
        return new TokenResponse(authorityService.authenticate(authority));
    }
}
