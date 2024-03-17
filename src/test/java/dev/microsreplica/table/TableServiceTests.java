package dev.microsreplica.table;

import dev.microsreplica.payment.Cash;
import dev.microsreplica.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TableServiceTests {
    @Mock
    private TableRepository tableRepository;

    private TableService tableService;

    @BeforeEach
    public void setUp(){
        this.tableService = new TableService(this.tableRepository);
    }

    @Test
    public void testGetAllTables_ReturnsAllTables() {
        // Arrange
        List<Table> expectedTables = new ArrayList<>();
        expectedTables.add(new Table(1));
        expectedTables.add(new Table(2));
        when(this.tableRepository.findAll()).thenReturn(expectedTables);

        // Act
        List<Table> actualTables = this.tableService.getAllTables();

        // Assert
        assertEquals(expectedTables.size(), actualTables.size());
        assertEquals(expectedTables.get(0).getId(), actualTables.get(0).getId());
        assertEquals(expectedTables.get(1).getId(), actualTables.get(1).getId());
    }

    @Test
    public void testGetById_WithValidId_ReturnsTable() {
        // Arrange
        Integer id = 1;
        Table expectedTable = new Table(id);
        when(this.tableRepository.findById(id)).thenReturn(Optional.of(expectedTable));

        // Act
        Table actualTable = tableService.getById(id);

        // Assert
        assertEquals(expectedTable, actualTable);
    }

    @Test
    public void testGetById_WithNonexistentId_ThrowsNotFoundException() {
        // Arrange
        Integer nonExistentId = -1;
        String expectedMessage = "Table not found";
        when(tableRepository.findById(nonExistentId)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tableService.getById(nonExistentId));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "Expected NOT_FOUND status");
        assertTrue(exception.getMessage().contains(expectedMessage), "Exception message does not contain expected text");
    }

    @Test
    public void testSaveTable_WithNegativeId_ThrowsBadRequest(){
        // Arrange
        Table table = new Table(-1);
        String expectedMessage = "Table ID can not be negative or zero";

        // Act & Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tableService.saveTable(table));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "Expected BAD_REQUEST status");
        assertTrue(exception.getMessage().contains(expectedMessage), "Exception message does not contain expected text");
    }

    @Test
    public void testChargeTable_WithCorrectIdAndEnoughCash_ReturnsTableWithoutItems(){
        // Arrange
        Table tableWithItems = new Table(1);
        Cash cash = new Cash(3000);
        List<Product> products = List.of(
                new Product("Product A", 10),
                new Product("Product B", 16),
                new Product("Product C", 4)
        );
        when(this.tableRepository.findById(1)).thenReturn(Optional.of(tableWithItems));

        // Act
        tableWithItems.setProducts(products);
        this.tableService.chargeTable(1, cash);

        // Assert
        verify(this.tableRepository, times(1)).findById(1);
        assertTrue(tableWithItems.getProducts().isEmpty());
    }
}
