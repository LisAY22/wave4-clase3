package com.guatemaltek.postgres.clase3.service;

import com.guatemaltek.postgres.clase3.api.dto.ClientRequest;
import com.guatemaltek.postgres.clase3.api.dto.ClientResponse;
import com.guatemaltek.postgres.clase3.api.mapper.ClientMapper;
import com.guatemaltek.postgres.clase3.repo.ClientRepository;
import com.guatemaltek.postgres.clase3.support.NotFoundException;
import com.guatemaltek.postgres.clase3.util.Texts;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ClientService {

    private final ClientRepository repo;

    public ClientService(ClientRepository repo) {
        this.repo = repo;
    }

    public Page<ClientResponse> search(String name, String address, String phone,
                                       Boolean active, Pageable pageable) {
        var normalizedName = Texts.blankToNull(name);
        var normalizedAddress = Texts.blankToNull(address);
        var normalizedPhone = Texts.blankToNull(phone);
        var page = repo.search(normalizedName, normalizedAddress, normalizedPhone, active, pageable);
        return page.map(ClientMapper::toResponse);
    }


    public ClientResponse get(Long id) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Client %d not found".formatted(id)));
        return ClientMapper.toResponse(entity);
    }

    public ClientResponse create(ClientRequest req) {
        var entity = ClientMapper.toEntity(req);
        var saved = repo.save(entity);
        return ClientMapper.toResponse(saved);
    }

    public ClientResponse update(Long id, ClientRequest req) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Client %d not found".formatted(id)));
        ClientMapper.updateEntity(entity, req);
        var saved = repo.save(entity);
        return ClientMapper.toResponse(saved);
    }

    public void delete(Long id) {
        var entity = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Client %d not found".formatted(id)));
        repo.delete(entity);
    }

}
