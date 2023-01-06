package com.inventory.repository;

import com.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    public Optional<Inventory> findBySkuCode(String skuCode);
    //public Optional<Inventory> findBySkuCode();
}
