package dev.microsreplica.table;

import dev.microsreplica.payment.PaymentMethod;
import dev.microsreplica.product.Product;
import dev.microsreplica.product.ProductsCollection;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("api/tables")
public class TableController {
    @Autowired
    TableService tableService;

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

    @PutMapping("/{id}/payment")
    public Table chargeTable(@PathVariable Integer id, PaymentMethod paymentMethod){
        return this.tableService.chargeTable(id, paymentMethod);
    }

    @PatchMapping("/{id}/products")
    public Table updateTable(@PathVariable Integer id, @RequestBody ProductsCollection products){
        return this.tableService.updateTable(id, products);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable Integer id){
        this.tableService.deleteTable(id);
    }
}
