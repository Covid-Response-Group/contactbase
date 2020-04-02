package org.covid19.contactbase.request;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class PasswordChangeRequest implements Serializable {

    @NotBlank
    private String password;

    public PasswordChangeRequest() {

    }

    public PasswordChangeRequest(@NotBlank String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
