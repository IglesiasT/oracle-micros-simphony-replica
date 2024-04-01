package dev.microsreplica.security;

import dev.microsreplica.roles.ApplicationUser;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO user) {
        return this.authenticationService.registerUser(user.getUsername(), user.getPassword());
    }
}
