package dev.microsreplica.table;

import dev.microsreplica.product.Product;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

@Service
public class TableService {
    @Autowired
    private TableRepository tableRepository;

    public List<Table> getAllTables(){
        return this.tableRepository.findAll();
    }

    public Table getById(@NotBlank Integer id){
        return this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));
    }

    public Table saveTable(Table table) {
        return this.tableRepository.save(table);
    }

    public Table updateTable(@NotBlank Integer id, Table table){
        Table existingTable = this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));

        existingTable.setId(table.getId());
        existingTable.setProducts(table.getProducts());

        return this.tableRepository.save(existingTable);
    }

    public Table updateTable(@NotBlank Integer id, Collection<Product> products){
        Table existingTable = this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));

        existingTable.setProducts(products);

        return this.tableRepository.save(existingTable);
    }

    public void deleteTable(Integer id) {
        this.tableRepository.deleteById(id);
    }
}
