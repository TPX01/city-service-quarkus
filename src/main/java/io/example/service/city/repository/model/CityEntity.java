package io.example.service.city.repository.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "CITY")
public class CityEntity extends PanacheEntity implements Serializable {
  public static final String QUERY_BY_COUNTRY =
          "SELECT c " +
          "FROM CityEntity c " +
          "WHERE c.countryCode = :" + CityEntity.PARAM_COUNTRY_CODE + " " +
          "ORDER BY c.zipCode ASC";
  public static final String PARAM_COUNTRY_CODE = "countryCode";

  @Id
  @Column(name = "CITY_IDN", nullable = false)
  private String cityIdn;

  @Column(name = "ZIP_COD")
  private String zipCode;

  @Column(name = "COUNTRY_IDN")
  private String countryCode;
}

