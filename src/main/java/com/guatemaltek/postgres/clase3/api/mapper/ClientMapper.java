package com.guatemaltek.postgres.clase3.api.mapper;

import com.guatemaltek.postgres.clase3.api.dto.ClientRequest;
import com.guatemaltek.postgres.clase3.api.dto.ClientResponse;
import com.guatemaltek.postgres.clase3.domain.Client;

public class ClientMapper {

    private ClientMapper(){}

    public static Client toEntity(ClientRequest dto) {
        return Client.builder()
                .name(dto.name().trim())
                .age(dto.age())
                .address(dto.address())
                .phone(dto.phone())
                .active(dto.active())
                .build();
    }

    public static void updateEntity(Client entity, ClientRequest dto) {
        entity.setName(dto.name().trim());
        entity.setAge(dto.age());
        entity.setAddress(dto.address());
        entity.setPhone(dto.phone());
        entity.setActive(dto.active());
    }

    public static ClientResponse toResponse(Client p) {
        return new ClientResponse(p.getId(), p.getName(), p.getAge(), p.getAddress(), p.getPhone(),
                p.getActive(), p.getCreatedAt(), p.getUpdatedAt());
    }

}
