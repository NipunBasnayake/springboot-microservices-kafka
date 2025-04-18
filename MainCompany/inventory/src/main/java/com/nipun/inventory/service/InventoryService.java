package com.nipun.inventory.service;

import com.nipun.inventory.dto.Inventory;

import java.util.List;

public interface InventoryService {
    Inventory addInventory(Inventory inventory);
    List<Inventory> getAllInventories();
    Inventory getInventoryById(Long id);
    Inventory updateInventory(Long id, Inventory updatedInventory);
    void deleteInventory(Long id);
}
