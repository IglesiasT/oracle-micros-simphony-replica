package dev.microsreplica.security;

import dev.microsreplica.roles.ApplicationUser;
import dev.microsreplica.roles.Role;
import dev.microsreplica.roles.RoleRepository;
import dev.microsreplica.roles.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {
    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUser registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        Role role = this.roleRepository.findByAuthority("USER");
        Set<Role> authorities = new HashSet<>();

        authorities.add(role);

        return userRepository.save(new ApplicationUser(1L, authorities, username, encodedPassword));
    }
}
