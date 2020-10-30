package com.nitaicharan.udemy_restapiawrestantn1.basic;

import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class GetRequestTest {

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
                .statusCode(200);
    }

    @Test(enabled = false)
    public void getRespondeBody() {
        var response = RestAssured.given()//
                .param("units", "imperial")//
                .param("origins", "Washington,DC")//
                .param("destinations", "New+York+City,NY")//
                .param("key", KEY)//
                .when()//
                .get("/distancematrix/json");
        System.out.println(response.getBody().prettyPrint());
    }
}
