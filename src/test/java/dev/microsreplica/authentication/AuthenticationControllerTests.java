package dev.microsreplica.authentication;

import dev.microsreplica.security.authentication.AuthenticationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc
public class AuthenticationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerUser_WithoutAuthentication_ShouldSucceed() throws Exception {
        String userJson = "{\"username\": \"testUser\", \"password\": \"testPassword\"}";

        mockMvc.perform(post("/auth/register")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"));
    }

    @Test
    public void loginUser_WithoutAuthentication_ShouldSucceed() throws Exception {
        String userJson = "{\"username\": \"testUser\", \"password\": \"testPassword\"}";

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(userJson))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void someAdminOnlyEndpoint_WithAdminAuthentication_ShouldSucceed() throws Exception {
        mockMvc.perform(post("/admin/some-endpoint")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void someAdminOnlyEndpoint_WithUserAuthentication_ShouldFail() throws Exception {
        mockMvc.perform(post("/admin/some-endpoint")
                        .contentType("application/json"))
                .andExpect(status().isForbidden());
    }
}
