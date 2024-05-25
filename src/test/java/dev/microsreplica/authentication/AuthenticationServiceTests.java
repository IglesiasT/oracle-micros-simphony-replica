package dev.microsreplica.authentication;

import dev.microsreplica.roles.ApplicationUser;
import dev.microsreplica.roles.Role;
import dev.microsreplica.roles.RoleRepository;
import dev.microsreplica.roles.UserRepository;
import dev.microsreplica.security.TokenService;
import dev.microsreplica.security.authentication.AuthenticationService;
import dev.microsreplica.security.authentication.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    // Use a real instance because of its critical implications
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private TokenService tokenService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(
            this.userRepository,
            this.roleRepository,
            this.passwordEncoder,
            this.authenticationManager,
            this.tokenService
        );
    }

    // Register
    @Test
    void registerUser_WithValidData_ReturnsUserWithCorrectData() {
        // Arrange
        String username = "testUser";
        String password = this.passwordEncoder.encode("password");
        ApplicationUser expectedUser = new ApplicationUser(Collections.emptySet(), username, password);
        when(this.userRepository.save(any(ApplicationUser.class))).thenReturn(expectedUser);

        // Act
        ApplicationUser user = authenticationService.registerUser("testUser", "password");

        // Assert
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertTrue(this.passwordEncoder.matches("password", user.getPassword()));
    }

    @Test
    void registerUser_WithValidData_ReturnsUserWithCorrectRole() {
        // Arrange
        String username = "testUser";
        String password = this.passwordEncoder.encode("password");
        Role expectedRole = new Role("USER");
        ApplicationUser expectedUser = new ApplicationUser(Collections.singleton(expectedRole), username, password);
        when(this.roleRepository.findByAuthority("USER")).thenReturn(expectedRole);
        when(this.userRepository.save(any(ApplicationUser.class))).thenReturn(expectedUser);

        // Act
        ApplicationUser user = authenticationService.registerUser("testUser", "password");

        // Assert
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertTrue(this.passwordEncoder.matches("password", user.getPassword()));
        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().contains(expectedRole));
    }

    // Login
    @Test
    void loginUser_WithValidData_ReturnsLoginResponseDTO() {
        // Arrange
        String username = "testUser";
        String password = this.passwordEncoder.encode("password");
        ApplicationUser expectedUser = new ApplicationUser(Collections.emptySet(), username, password);
        when(this.userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
        when(this.tokenService.generateJwtToken(any())).thenReturn("token");

        // Act
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(username, password);

        // Assert
        assertNotNull(loginResponseDTO);
        assertEquals(expectedUser, loginResponseDTO.getUser());
        assertEquals("token", loginResponseDTO.getToken());
    }

    @Test
    void loginUser_WithInvalidData_ReturnsEmptyLoginResponseDTO() {
        // Arrange
        String username = "testUser";
        String password = this.passwordEncoder.encode("password");
        when(this.userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(username, password);

        // Assert
        assertNotNull(loginResponseDTO);
        assertNull(loginResponseDTO.getUser());
        assertEquals("", loginResponseDTO.getToken());
    }
}
