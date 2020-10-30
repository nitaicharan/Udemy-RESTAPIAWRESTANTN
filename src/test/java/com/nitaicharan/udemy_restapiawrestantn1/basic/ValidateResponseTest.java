package com.nitaicharan.udemy_restapiawrestantn1.basic;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ValidateResponseTest {

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
    public void statusCodeVerification() {
        RestAssured.given()//
                .param("units", "imperial")//
                .param("origins", "Washington,DC")//
                .param("destinations", "New+York+City,NY")//
                .param("key", KEY)//
                .when()//
                .get("/distancematrix/json")//
                .then()//
                .statusCode(200)//
                .and()//
                .body("rows[0].elements[0].distance.text", equalTo("232 mi"))//
                .contentType(ContentType.JSON);
    }
}
