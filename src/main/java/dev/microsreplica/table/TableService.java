package dev.microsreplica.table;

import dev.microsreplica.payment.PaymentMethod;
import dev.microsreplica.payment.Priceable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
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

    public Table getById(Integer id){
        return this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));
    }

    public Table saveTable(Table table) {
        if (table.getId() < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Table ID can not be negative or zero");
        }
        return this.tableRepository.save(table);
    }

    public Table updateTable(Integer id, Table table){
        Table existingTable = this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));

        existingTable.setId(table.getId());
        existingTable.setProducts(table.getAllPriceableItems());

        return this.tableRepository.save(existingTable);
    }

    public void deleteTable(Integer id) {
        this.tableRepository.deleteById(id);
    }

    public Table chargeTable(Integer id, PaymentMethod paymentMethod) {
        Table tableToCharge = this.tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));
        double finalCost = 0.0;
        List<Priceable> itemsToCharge = this.tableRepository.getAllPriceableItems();

        for (Priceable item : itemsToCharge){
            finalCost += item.getFinalPrice();
        }
        paymentMethod.pay(finalCost);
        tableToCharge.setProducts(Collections.emptyList());

        return this.tableRepository.save(tableToCharge);
    }
}
