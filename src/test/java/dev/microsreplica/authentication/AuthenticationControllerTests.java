package dev.microsreplica.authentication;

import dev.microsreplica.roles.ApplicationUser;
import dev.microsreplica.security.authentication.AuthenticationController;
import dev.microsreplica.security.authentication.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void registerUser_WithoutAuthentication_ShouldSucceed() throws Exception {
        // Arrange
        String userJson = "{\"username\": \"testUser\", \"password\": \"testPassword\", \"roles\": []}";
        when(this.authenticationService.registerUser(anyString(), anyString()))
                .thenReturn(mock(ApplicationUser.class));

        // Act & Assert
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUser_WithoutAuthentication_ShouldSucceed() throws Exception {
        String userJson = "{\"username\": \"testUser\", \"password\": \"testPassword\"}";

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void someAdminOnlyEndpoint_WithAdminAuthentication_ShouldSucceed() throws Exception {
        mockMvc.perform(post("/admin/some-endpoint")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void someAdminOnlyEndpoint_WithUserAuthentication_ShouldBeForbidden() throws Exception {
        mockMvc.perform(post("/admin/some-endpoint")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }
}
