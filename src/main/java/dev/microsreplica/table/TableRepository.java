package dev.microsreplica.table;

import dev.microsreplica.payment.Priceable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Integer> {
    List<Priceable> getAllPriceableItems();
}
