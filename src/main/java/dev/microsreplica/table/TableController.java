package dev.microsreplica.table;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {
    private final TableService tableService;


    public TableController(TableService tableService){
        this.tableService = tableService;
    }

    @GetMapping("")
    public List<Table> getAllTables(){
        return this.tableService.getAllTables();
    }

    @GetMapping("/{id}")
    public Table getById(@PathVariable Long id){
        return this.tableService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Table addTable(@RequestBody @Valid Table table){
        return this.tableService.saveTable(table);
    }

    @PutMapping("/{id}")
    public Table updateTable(@PathVariable Long id, @RequestBody Table table){
        return this.tableService.updateTable(id, table);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteTable(@PathVariable Long id){
        this.tableService.deleteTable(id);
    }
}
