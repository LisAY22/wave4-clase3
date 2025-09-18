package com.guatemaltek.postgres.clase3.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.guatemaltek.postgres.clase3.api.dto.ClientRequest;
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
  private static final String API_URL_2 = "http://localhost:8090/api/clients";
  private static final List<String> CURRENCIES = List.of("USD", "GTQ", "MXN", "EUR");
  private static final List<String> NAMES = List.of("Elisa", "Yanira", "Angel", "Emmanuel", "Claudio", "Martha",
          "Patricio", "Obispo", "Helen", "Carolina", "Karol", "Lili");
  private static final List<String> ADDRESS = List.of("Toto", "Xela", "Guate", "Solola", "Tecpan");

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

  @Bean
  CommandLineRunner seedClients(){
    return args -> {
      RestTemplate rest = new RestTemplate();
      ObjectMapper mapper = new ObjectMapper();
      Random random = new Random();

      for (int i = 1; i <= 10_000; i++) {
        var client = new ClientRequest(
                NAMES.get(random.nextInt(NAMES.size())),
                (int) (Math.random() * 101),
                ADDRESS.get(random.nextInt(ADDRESS.size())),
                (random.nextInt(9000) + 1000) + "-" + (random.nextInt(9000) + 1000),
                random.nextBoolean()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(client), headers);

        try {
          rest.postForEntity(API_URL, request, String.class);
        } catch (Exception ex) {
          System.err.println("Error on record " + i + ": " + ex.getMessage());
        }

        if (i % 1000 == 0) {
          System.out.println(i + " clients seeded...");
        }
      }

      System.out.println("✅ Finished seeding 10,000 clients.");
    };
  }

  // DTO interno solo para la request
  record ProductRequest(String name, BigDecimal price, String currency, Boolean active) {}

  record ClientRequest(String name, Integer age, String address, String phone, Boolean active){}
}
