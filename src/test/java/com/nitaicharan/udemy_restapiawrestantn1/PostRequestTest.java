package com.nitaicharan.udemy_restapiawrestantn1;

import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class PostRequestTest {

    private String KEY = System.getenv("API_KEY");

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
    public void statusCodeVerification() {
        var response = RestAssured.given()//
                .queryParam("key", KEY)//
                .body(""//
                        + "{"//
                        + "\"location\": {"//
                        + "\"lat\" : -33.8669710,"//
                        + "\"lang\" : 151.1958750"//
                        + "},"//
                        + "\"accuracy\" : 50,"//
                        + "\"name\": \"Google Shoes!\","//
                        + "\"phone_number\": \"(02) 9374 4000\","//
                        + "\"address\": \"48 Pirrama Road, Pyrmont, NSW @))(, Australia\","//
                        + "\"types\": [\"shoes_store\"],"//
                        + "\"website\": \"http://www.google.com.au/\","//
                        + "\"language\": \"en-AU\""//
                        + "}"//
                )//
                .when()//
                .post("/distancematrix/json");
        // .then()//
        // .statusCode(200).and()//
        // .contentType(ContentType.JSON).and()//
        // .body("scope", equalTo("APP")).and()//
        // .body("status", equalTo("OK"));

        System.out.println(response.body().asString());
    }
}