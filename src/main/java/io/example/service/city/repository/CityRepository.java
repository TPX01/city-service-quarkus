package io.example.service.city.repository;

import io.example.service.city.repository.model.CityEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Stream;

@ApplicationScoped
public class CityRepository implements PanacheRepository<CityEntity> {

    public Stream<CityEntity> getCitiesByCountryIdn(String countryCode) {
        Parameters params = new Parameters();
        params.and(CityEntity.PARAM_COUNTRY_CODE, countryCode);
        return stream(CityEntity.QUERY_BY_COUNTRY, params);
    }
}