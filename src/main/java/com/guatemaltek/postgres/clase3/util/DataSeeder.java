package com.guatemaltek.postgres.clase3.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Configuration
public class DataSeeder {

  private static final String API_URL = "http://localhost:8090/api/products";
  private static final List<String> CURRENCIES = List.of("USD", "GTQ", "MXN", "EUR");

  @Bean
  CommandLineRunner seedProducts() {
    return args -> {
      RestTemplate rest = new RestTemplate();
      ObjectMapper mapper = new ObjectMapper();
      Random random = new Random();

      for (int i = 1; i <= 10_000; i++) {
        var product = new ProductRequest(
            "Product-" + UUID.randomUUID().toString().substring(0, 8),
            BigDecimal.valueOf(1 + (10_000 - 1) * random.nextDouble()).setScale(2, BigDecimal.ROUND_HALF_UP),
            CURRENCIES.get(random.nextInt(CURRENCIES.size())),
            random.nextBoolean()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(product), headers);

        try {
          rest.postForEntity(API_URL, request, String.class);
        } catch (Exception ex) {
          System.err.println("Error on record " + i + ": " + ex.getMessage());
        }

        if (i % 1000 == 0) {
          System.out.println(i + " products seeded...");
        }
      }

      System.out.println("✅ Finished seeding 10,000 products.");
    };
  }

  // DTO interno solo para la request
  record ProductRequest(String name, BigDecimal price, String currency, Boolean active) {}
}
