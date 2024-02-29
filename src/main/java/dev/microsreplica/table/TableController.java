package dev.microsreplica.table;

import dev.microsreplica.payment.PaymentMethod;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    public Table getById(@PathVariable Integer id){
        return this.tableService.getById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Table addTable(@RequestBody @Valid Table table){
        return this.tableService.saveTable(table);
    }

    @PutMapping("/{id}")
    public Table updateTable(@PathVariable Integer id, @RequestBody Table table){
        return this.tableService.updateTable(id, table);
    }

    @PatchMapping("/{id}")
    public Table chargeTable(@PathVariable Integer id, @RequestBody PaymentMethod paymentMethod){
        return this.tableService.chargeTable(id, paymentMethod);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{id}")
    public void deleteTable(@PathVariable Integer id){
        this.tableService.deleteTable(id);
    }
}
