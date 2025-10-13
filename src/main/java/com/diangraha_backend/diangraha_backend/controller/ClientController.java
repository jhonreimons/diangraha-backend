package com.diangraha_backend.diangraha_backend.controller;

import com.diangraha_backend.diangraha_backend.dto.BrandRequest;
import com.diangraha_backend.diangraha_backend.dto.BrandResponse;
import com.diangraha_backend.diangraha_backend.dto.ClientRequest;
import com.diangraha_backend.diangraha_backend.dto.ClientResponse;
import com.diangraha_backend.diangraha_backend.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clients")
@Slf4j

public class ClientController {

    private final ClientService service;

    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        List<ClientResponse> clients = service.findAll();
        return ResponseEntity.ok(clients);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ClientResponse> create(
            @RequestParam("name") String name,
            @RequestParam("imageUrl") MultipartFile imageUrl
    ) throws IOException {

        ClientRequest request = new ClientRequest();
        request.setName(name);

        ClientResponse createdClient = service.create(request, imageUrl);
        return ResponseEntity.ok(createdClient);
    }
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ClientResponse> update(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "imageUrl", required = false) MultipartFile imageUrl
    ) throws IOException {
        ClientRequest request = new ClientRequest();
        request.setName(name);
        return ResponseEntity.ok(service.update(id, request, imageUrl));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}


