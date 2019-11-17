package io.example.service;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeCountriesIntegrationIT extends CountriesIntegrationTest {

    // Execute the same tests but in native mode.
}