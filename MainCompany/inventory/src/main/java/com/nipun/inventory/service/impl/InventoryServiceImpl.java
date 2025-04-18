package com.nipun.inventory.service.impl;

import com.nipun.inventory.dto.Inventory;
import com.nipun.inventory.entity.InventoryEntity;
import com.nipun.inventory.repository.InventoryRepository;
import com.nipun.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public Inventory addInventory(Inventory inventory) {
        InventoryEntity entity = modelMapper.map(inventory, InventoryEntity.class);
        return modelMapper.map(inventoryRepository.save(entity), Inventory.class);
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll()
                .stream()
                .map(entity -> modelMapper.map(entity, Inventory.class))
                .collect(Collectors.toList());
    }

    @Override
    public Inventory getInventoryById(Long id) {
        InventoryEntity entity = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));
        return modelMapper.map(entity, Inventory.class);
    }

    @Override
    public Inventory updateInventory(Long id, Inventory updatedInventory) {
        InventoryEntity entity = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory not found with id: " + id));

        entity.setProductCode(updatedInventory.getProductCode());
        entity.setLocation(updatedInventory.getLocation());
        entity.setQuantity(updatedInventory.getQuantity());

        return modelMapper.map(inventoryRepository.save(entity), Inventory.class);
    }

    @Override
    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}