package com.guatemaltek.postgres.clase3.api.mapper;

import com.guatemaltek.postgres.clase3.api.dto.ProductRequest;
import com.guatemaltek.postgres.clase3.api.dto.ProductResponse;
import com.guatemaltek.postgres.clase3.domain.Product;

public final class ProductMapper {
  private ProductMapper() {
  }

  public static Product toEntity(ProductRequest dto) {
    return Product.builder()
            .name(dto.name().trim())
            .price(dto.price())
            .currency(dto.currency().toUpperCase())
            .active(dto.active())
            .build();
  }

  public static void updateEntity(Product entity, ProductRequest dto) {
    entity.setName(dto.name().trim());
    entity.setPrice(dto.price());
    entity.setCurrency(dto.currency().toUpperCase());
    entity.setActive(dto.active());
  }

  public static ProductResponse toResponse(Product p) {
    return new ProductResponse(p.getId(), p.getName(), p.getPrice(), p.getCurrency(),
            p.getActive(), p.getCreatedAt(), p.getUpdatedAt());
  }
}
