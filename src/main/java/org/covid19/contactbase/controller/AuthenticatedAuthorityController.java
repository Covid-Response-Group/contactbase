package org.covid19.contactbase.controller;

import org.covid19.contactbase.auth.ThreadLocalWrapper;
import org.covid19.contactbase.model.Authority;

public class AuthenticatedAuthorityController implements RequiresAuthorityAuthentication {

    public Authority getAuthority() {
        return ThreadLocalWrapper.getAuthority();
    }

    public String getAuthorityEmail() {
        return getAuthority().getEmail();
    }
}
