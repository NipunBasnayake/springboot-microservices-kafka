package com.inventory.inventory.service.impl;

import com.inventory.inventory.dto.InventoryDTO;
import com.inventory.inventory.model.Inventory;
import com.inventory.inventory.repo.InventoryRepo;
import com.inventory.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepo inventoryRepo;
    private final ModelMapper modelMapper;

    private static final String ITEM_NOT_FOUND_MESSAGE = "Inventory item not found with id: ";

    @Override
    public List<InventoryDTO> getAllItems() {
        List<Inventory> inventoryItems = inventoryRepo.findAll();
        return inventoryItems.stream()
                .map(item -> modelMapper.map(item, InventoryDTO.class))
                .toList();
    }

    @Override
    public InventoryDTO saveItem(InventoryDTO inventoryDTO) {
        Inventory inventoryItem = modelMapper.map(inventoryDTO, Inventory.class);
        Inventory savedItem = inventoryRepo.save(inventoryItem);
        return modelMapper.map(savedItem, InventoryDTO.class);
    }

    @Override
    public InventoryDTO updateItem(InventoryDTO inventoryDTO) {
        Optional<Inventory> existingItem = inventoryRepo.findById(inventoryDTO.getId());
        if (existingItem.isEmpty()) {
            throw new NoSuchElementException(ITEM_NOT_FOUND_MESSAGE + inventoryDTO.getId());
        }

        Inventory itemToUpdate = modelMapper.map(inventoryDTO, Inventory.class);
        Inventory updatedItem = inventoryRepo.save(itemToUpdate);
        return modelMapper.map(updatedItem, InventoryDTO.class);
    }

    @Override
    public String deleteItem(Integer itemId) {
        if (!inventoryRepo.existsById(itemId)) {
            throw new NoSuchElementException(ITEM_NOT_FOUND_MESSAGE + itemId);
        }

        inventoryRepo.deleteById(itemId);
        return "Inventory item deleted successfully";
    }

    @Override
    public InventoryDTO getItemById(Integer itemId) {
        Optional<Inventory> inventoryItem = inventoryRepo.findById(itemId);
        return inventoryItem.map(item -> modelMapper.map(item, InventoryDTO.class))
                .orElseThrow(() -> new NoSuchElementException(ITEM_NOT_FOUND_MESSAGE + itemId));
    }
}