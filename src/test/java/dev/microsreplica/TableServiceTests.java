package dev.microsreplica;

import dev.microsreplica.table.Table;
import dev.microsreplica.table.TableRepository;
import dev.microsreplica.table.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TableServiceTests {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

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
}
