package com.signature;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

  private Info info() {
    return new Info().title("Signature API").description("Signature REST APIs").version("1.0.0");
  }

  private License license() {
    return new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0.html");
  }

  private Contact contact() {
    return new Contact().name("Signature Inc.").email("about@signature.com").url("https://signature.com/about");
  }

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI().info(info().contact(contact()).license(license()).termsOfService("https://signature.com/terms"));
  }
}