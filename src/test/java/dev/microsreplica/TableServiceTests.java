package dev.microsreplica;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import dev.microsreplica.payment.Cash;
import dev.microsreplica.product.Product;
import dev.microsreplica.table.TableRepository;
import dev.microsreplica.table.TableService;
import dev.microsreplica.table.Table;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@WebMvcTest(TableService.class)
@WithMockUser
public class TableServiceTests {

    @MockBean
    private TableRepository tableRepositoryMock;

    @InjectMocks    // Injects repositoryMock into service
    private TableService underTest;

    @Test
    public void chargeTableResetsItCost(){

        // Arrange
        Table table = new Table(1);
        Cash cashMock = mock(Cash.class);
        when(cashMock.pay(8.7)).thenReturn(true);

        // Act
        table.add(new Product(1, "Coffee", 5.3));
        table.add(new Product(2, "Cake", 3.4));
        this.underTest.chargeTable(1, cashMock);

        // Assert
        verify(cashMock, times(1)).pay(8.7);
    }
}
