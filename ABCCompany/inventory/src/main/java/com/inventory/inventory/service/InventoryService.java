package com.inventory.inventory.service;

import com.inventory.inventory.dto.InventoryDTO;

import java.util.List;

public interface InventoryService {

    List<InventoryDTO> getAllItems();

    InventoryDTO saveItem(InventoryDTO inventory);

    InventoryDTO updateItem(InventoryDTO inventory);

    String deleteItem(Integer itemId);

    InventoryDTO getItemById(Integer itemId);
}
