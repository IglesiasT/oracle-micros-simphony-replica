package dev.microsreplica;

import dev.microsreplica.table.Table;
import dev.microsreplica.table.TableRepository;
import dev.microsreplica.table.TableService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TableServiceTests {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    @Test
    public void testGetAllTables() {
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
}
