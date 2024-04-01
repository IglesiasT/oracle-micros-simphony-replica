package dev.microsreplica.security.authentication;

import dev.microsreplica.roles.ApplicationUser;

public class LoginResponseDTO {
    private ApplicationUser user;
    private String token;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(ApplicationUser user, String token) {
        this.user = user;
        this.token = token;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
