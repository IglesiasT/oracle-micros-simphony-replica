package dev.microsreplica.table;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.microsreplica.order.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(TableController.class)
@WithMockUser(username = "admin", roles = {"ADMIN"})
@AutoConfigureMockMvc(addFilters = false)   // Disable all filters from SecurityFilterChain
public class TableControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TableService tableService;
    private static final String rootUri = "/tables";


    // GET
    @Test
    public void getTable_ByValidId_ReturnsOkStatus() throws Exception {

        // Arrange
        String tableUri = rootUri + "/{id}";
        when(this.tableService.getById(1L)).thenReturn(new Table(1L, mock(Order.class)));

        // Act
        RequestBuilder request = get(tableUri, 1L).accept(MediaType.APPLICATION_JSON);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void getRootUriReturnsOkStatus() throws Exception {

        // Arrange
        List<Table> tables = Arrays.asList(
                new Table(1L, mock(Order.class)),
                new Table(2L, mock(Order.class))
        );
        when(this.tableService.getAllTables()).thenReturn(tables);

        // Act
        RequestBuilder request = get(rootUri).accept(MediaType.APPLICATION_JSON);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    public void getTable_ByWrongId_ReturnsNotFound() throws Exception {

        // Arrange
        Long wrongId = 91218L;
        String tableUri = rootUri + "/{id}";
        when(this.tableService.getById(wrongId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));

        // Act
        RequestBuilder request = get(tableUri, wrongId).accept(MediaType.APPLICATION_JSON);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    // POST
    @Test
    public void postValidTable_InRootUri_ReturnsCreatedStatus() throws Exception {

        // Arrange
        Table table = new Table(1L, mock(Order.class));
        when(this.tableService.saveTable(table)).thenReturn(table);
        String json = """
                {
                    "id":1,
                    "products":[]
                }
                """;

        // Act
        RequestBuilder request = post(rootUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isCreated());
    }

    @Test
    public void postInvalidJsonInRootUriReturnsBadRequest() throws Exception {

        // Arrange
        String invalidJson = """
                {
                    I can't be parsed to Table
                }
                """;

        // Act
        RequestBuilder request = post(rootUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    // PUT
    @Test
    public void putValidTableInRootUriReturnsOkStatus() throws Exception {

        // Arrange
        Table updated = new Table(5L, mock(Order.class));
        when(this.tableService.updateTable(5L, updated)).thenReturn(updated);
        String tableUri = rootUri + "/{id}";
        String json = """
                {
                    "id":5,
                    "products":[]
                }
                """;

        // Act
        RequestBuilder request = put(tableUri, 5)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isOk());

    }

    // DELETE
    @Test
    public void deleteValidTableInRootUriDeletesTable() throws Exception {

        // Arrange
        doNothing().when(this.tableService).deleteTable(100L);
        String tableUri = rootUri + "/{id}";

        // Act
        RequestBuilder request = delete(tableUri, 100)
                .contentType(MediaType.APPLICATION_JSON);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isNoContent());
        verify(this.tableService, times(1)).deleteTable(100L);
    }

}
