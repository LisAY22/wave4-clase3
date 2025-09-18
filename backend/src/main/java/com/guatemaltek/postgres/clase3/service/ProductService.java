package com.guatemaltek.postgres.clase3.service;


import com.guatemaltek.postgres.clase3.api.dto.ProductRequest;
import com.guatemaltek.postgres.clase3.api.dto.ProductResponse;
import com.guatemaltek.postgres.clase3.api.mapper.ProductMapper;
import com.guatemaltek.postgres.clase3.repo.ProductRepository;
import com.guatemaltek.postgres.clase3.support.NotFoundException;
import com.guatemaltek.postgres.clase3.util.Texts;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

  private final ProductRepository repo;

  public ProductService(ProductRepository repo) {
    this.repo = repo;
  }

  public Page<ProductResponse> search(String name, String currency, Boolean active,
      Pageable pageable) {
    var normalizedName = Texts.blankToNull(name);
    var normalizedCurrency = Texts.blankToNull(currency);
    var page = repo.search(normalizedName, normalizedCurrency, active, pageable);
    return page.map(ProductMapper::toResponse);
  }


  public ProductResponse get(Long id) {
    var entity = repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));
    return ProductMapper.toResponse(entity);
  }

  public ProductResponse create(ProductRequest req) {
    var entity = ProductMapper.toEntity(req);
    // Regla de negocio ejemplo: precios en USD deben ser >= 5.00
    if ("USD".equalsIgnoreCase(entity.getCurrency()) && entity.getPrice().doubleValue() < 5.00) {
      throw new IllegalArgumentException("Price in USD must be >= 5.00");
    }
    var saved = repo.save(entity);
    return ProductMapper.toResponse(saved);
  }

  public ProductResponse update(Long id, ProductRequest req) {
    var entity = repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));
    ProductMapper.updateEntity(entity, req);
    var saved = repo.save(entity);
    return ProductMapper.toResponse(saved);
  }

  public void delete(Long id) {
    var entity = repo.findById(id)
        .orElseThrow(() -> new NotFoundException("Product %d not found".formatted(id)));
    repo.delete(entity);
  }


}
