package dev.microsreplica;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import dev.microsreplica.payment.CreditCard;
import dev.microsreplica.table.Table;
import dev.microsreplica.table.TableController;
import dev.microsreplica.table.TableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
@WithMockUser
public class TableControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TableService tableService;
    private static final String rootUri = "/api/tables";


    // GET
    @Test
    public void getTableByValidIdReturnsTable() throws Exception {

        // Arrange
        Integer id = 1;
        Table table = new Table(id);
        String tableUri = rootUri + "/{id}";
        when(this.tableService.getById(id)).thenReturn(table);

        // Act
        RequestBuilder request = get(tableUri, id).accept(MediaType.APPLICATION_JSON);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id)));
    }

    @Test
    public void getRootUriGetsAllTables() throws Exception {

        // Arrange
        List<Table> tables = Arrays.asList(
                new Table(1),
                new Table(2)
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
    public void getTableByWrongIdReturnsNotFound() throws Exception {

        // Arrange
        Integer wrongId = 91218;
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
    public void postValidTableInRootUriSavesTable() throws Exception {

        // Arrange
        Table table = new Table(1);
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

    @Test
    public void postTableWithInvalidIdReturnsBadRequest() throws Exception {

        // Arrange
        when(this.tableService.saveTable(new Table(-1)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Table"));
        String invalidTableJson = """
                {
                    "id":I'm an invalid format,
                    "products":[]
                }
                """;

        // Act
        RequestBuilder request = post(rootUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidTableJson);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    // PUT
    @Test
    public void putValidTableInRootUriUpdatesTable() throws Exception {

        // Arrange
        Table updated = new Table(5);
        when(this.tableService.updateTable(5, updated)).thenReturn(updated);
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

    // PATCH
    @Test
    public void chargeValidTableReturnsOkStatus() throws Exception {

        // Arrange
        Integer id = 200;
        CreditCard card = new CreditCard();
        Table table = new Table(id);
        String tableUri = rootUri + "/{id}";
        when(this.tableService.chargeTable(id, card)).thenReturn(table);
        String json = """
                {
                  "paymentMethod": {
                    "type": "creditCard",
                    "cardNumber": "1234-5678-9012-3456",
                    "expirationDate": "12/24",
                    "cvv": "123"
                  }
                }
                                
                """;

        // Act
        RequestBuilder request = patch(tableUri, id, card)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(id)));
    }

    // DELETE
    @Test
    public void deleteValidTableInRootUriDeletesTable() throws Exception {

        // Arrange
        doNothing().when(this.tableService).deleteTable(100);
        String tableUri = rootUri + "/{id}";

        // Act
        RequestBuilder request = delete(tableUri, 100)
                .contentType(MediaType.APPLICATION_JSON);

        // Assert
        this.mockMvc.perform(request)
                .andExpect(status().isNoContent());
        verify(this.tableService, times(1)).deleteTable(100);
    }

}
