package io.example.service.city.service.model;

import lombok.Data;

@Data
public class CityModel {
  private String idn;
  private String name;
  private String zipCode;
  private String country;
  private String countryCode;
}
