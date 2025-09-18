package com.guatemaltek.postgres.clase3.api;

import com.guatemaltek.postgres.clase3.api.dto.ClientRequest;
import com.guatemaltek.postgres.clase3.api.dto.ClientResponse;
import com.guatemaltek.postgres.clase3.api.dto.ProductRequest;
import com.guatemaltek.postgres.clase3.api.dto.ProductResponse;
import com.guatemaltek.postgres.clase3.service.ClientService;
import com.guatemaltek.postgres.clase3.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping
    public Page<ClientResponse> search(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) String address,
                                       @RequestParam(required = false) String phone,
                                       @RequestParam(required = false) Boolean active,
                                       @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return service.search(name, address, phone, active, pageable);
    }

    @GetMapping("/{id}")
    public ClientResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest req) {
        var created = service.create(req);
        return ResponseEntity.created(URI.create("/api/clients/" + created.id())).body(created);
    }

    @PutMapping("/{id}")
    public ClientResponse update(@PathVariable Long id, @Valid @RequestBody ClientRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
