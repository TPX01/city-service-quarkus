package io.example.service.city.adapter.zipcode;

import io.example.service.city.adapter.zipcode.mapper.ZipCodeMapper;
import io.example.service.city.service.model.CityModel;
import io.example.service.common.logging.Logged;
import io.opentracing.contrib.concurrent.TracedExecutorService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import zipcode.api.ZipCodesApi;
import zipcode.api.model.City;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;

@Logged
@ApplicationScoped
public class ZipCodeAdapter {

    @Inject
    TracedExecutorService tracedExecutorService;

    @Inject
    transient Logger log;

    @Inject
    @RestClient
    ZipCodesApi zipCodesApi;

    @Inject
    ZipCodeMapper zipCodeMapper;

    public CompletableFuture<CityModel> getZipCodeInfo(String zipCode, String countryCode) {
        log.info("START searching for cities");
        return CompletableFuture.supplyAsync(() -> {
            Response response = zipCodesApi.getZipCodeInfo(countryCode, zipCode);
            if (Response.Status.OK.getStatusCode() != response.getStatus() ) {
                return null;
            }
            final CityModel cityModel = zipCodeMapper.map(response.readEntity(City.class));
            log.info("END searching for cities");
            return cityModel;
        }, tracedExecutorService);
    }
}
