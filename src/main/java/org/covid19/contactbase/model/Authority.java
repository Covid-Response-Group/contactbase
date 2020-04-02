package org.covid19.contactbase.model;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Authority implements Serializable {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public Authority() {

    }

    public Authority(@NotBlank String email, @NotBlank String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
