package dev.microsreplica.security.authentication;

import dev.microsreplica.roles.ApplicationUser;
import dev.microsreplica.security.RegistrationDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO user) {
        return this.authenticationService.registerUser(user.getUsername(), user.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO user) {
        return this.authenticationService.loginUser(user.getUsername(), user.getPassword());
    }
}
