package io.example.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class CountriesIntegrationTest {

    @Test
    public void test_wrong_iso_country_code_input() {
        given()
                .when().get("/v1/countries/BEL/cities")
                .then()
                .statusCode(400);
    }

    @Test
    public void test_nullpointer_throws_internal_server_error() {
        given()
          .when().get("/v1/countries/NP/cities")
          .then()
             .statusCode(500);
    }

}