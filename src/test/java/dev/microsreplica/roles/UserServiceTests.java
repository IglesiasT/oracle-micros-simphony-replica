package dev.microsreplica.roles;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private UserRepository userRepository;

    // Use a real instance because of its critical implications
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService(this.passwordEncoder, this.userRepository);
    }

    @Test
    void loadUserByUsername_WithExistentUser_ReturnsUserDetails() {
        // Arrange
        String username = "testUser";
        String password = this.passwordEncoder.encode("password");
        ApplicationUser user = new ApplicationUser(Collections.emptySet(), username, password);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Assert
        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertTrue(this.passwordEncoder.matches("password", userDetails.getPassword()));
        assertTrue(userDetails.getAuthorities().isEmpty()); // Assuming no authorities are set for this test user
    }

    @Test
    void loadUserByUsername_WithNonExistentUser_ThrowsUsernameNotFoundException() {
        // Arrange
        String nonExistingUsername = "nonExistingUser";
        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(nonExistingUsername));
    }
}
