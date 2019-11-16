package io.example.service.city.service.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CountryModel {
  private String name;
  private String code;
  private List<CityModel> cities;
}
