package com.guatemaltek.postgres.clase3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class Clase3Application {

  public static void main(String[] args) {
    SpringApplication.run(Clase3Application.class, args);
  }

}
