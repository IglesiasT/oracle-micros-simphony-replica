package dev.microsreplica.table;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TableService {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository){
        this.tableRepository = tableRepository;
    }

    public List<Table> getAllTables(){
        return this.tableRepository.findAll();
    }

    public Table getById(Long id){
        return this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));
    }

    public Table saveTable(Table table) {
        if (table.getId() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table ID can not be negative or zero");
        }
        return this.tableRepository.save(table);
    }

    public Table updateTable(Long id, Table table){
        Table existingTable = this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));

        existingTable.setId(table.getId());

        return this.tableRepository.save(existingTable);
    }

    public void deleteTable(Long id) {
        this.tableRepository.deleteById(id);
    }

}
