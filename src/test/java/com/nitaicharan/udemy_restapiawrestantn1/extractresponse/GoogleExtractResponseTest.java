package com.nitaicharan.udemy_restapiawrestantn1.extractresponse;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class GoogleExtractResponseTest {
    private String KEY = System.getenv("GOOGLEMAP_KEY");

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://maps.googleapis.com";
        RestAssured.basePath = "/maps/api";
    }

    @Test
    public void keyNotNull() {
        assertNotNull(KEY);
    }

    @Test
    public void extractResponse() {
        var response = RestAssured.given()//
                .param("units", "imperial")//
                .param("origins", "Washington,DC")//
                .param("destinations", "New+York+City,NY")//
                .param("key", KEY)//
                .when()//
                .get("/distancematrix/xml")//
                .then()//
                .statusCode(200).extract().response();
        assertTrue(response.contentType().contains("application/xml"));
    }
}
