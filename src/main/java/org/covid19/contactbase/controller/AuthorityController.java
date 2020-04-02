package org.covid19.contactbase.controller;

import org.covid19.contactbase.request.PasswordChangeRequest;
import org.covid19.contactbase.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
public class AuthorityController extends AuthenticatedAuthorityController {

    private AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @RequestMapping(value = "/authority/password", method = RequestMethod.PUT)
    public void changePassword(@Valid @RequestBody PasswordChangeRequest passwordChangeRequest) {
        authorityService.changePassword(getAuthorityEmail(), passwordChangeRequest.getPassword());
    }
}
