package com.example.service.integration.controller;

import com.example.service.integration.model.EntityModel;
import com.example.service.integration.model.UpsertEntityRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("api/v1/entity")
@Slf4j
public class EntityController {

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList() {

        List<EntityModel> entityModels = IntStream.range(0, 10)
                .mapToObj(i -> EntityModel.createMockModel("Model: " + (i + 1)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name) {
        return ResponseEntity.ok(EntityModel.createMockModel(name));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.createMockModel(request.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest request) {
        return ResponseEntity.ok(new EntityModel(id, request.getName(), Instant.now()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntityById(@PathVariable UUID id) {
        log.info("Delete entity by ID: {}", id);

        return ResponseEntity.noContent().build();
    }
}
