package com.poc.orchestration.inventory.controller;

import com.poc.orchestration.dto.InventoryRequestDTO;
import com.poc.orchestration.dto.InventoryResponseDTO;
import com.poc.orchestration.inventory.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("inventory")
@AllArgsConstructor
public class InventoryController {

    private final InventoryService service;

    @PostMapping("/deduct")
    public InventoryResponseDTO deduct(@RequestBody final InventoryRequestDTO requestDTO) {
        return this.service.deductInventory(requestDTO);
    }

    @PostMapping("/add")
    public void add(@RequestBody final InventoryRequestDTO requestDTO) {
        this.service.addInventory(requestDTO);
    }

}
