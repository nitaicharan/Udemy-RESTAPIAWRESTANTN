package com.nitaicharan.udemy_restapiawrestantn1.basic;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.nitaicharan.udemy_restapiawrestantn1.model.PlacesAdd;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class PostRequestTest {

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
        Map<String, Double> location = new HashMap<>();
        location.put("lat", -33.8669710);
        location.put("lang", 151.1958750);

        var placesAdd = PlacesAdd.builder()//
                .location(location)//
                .accuracy(50)//
                .name("Google Shoes!")//
                .phoneNumber("(02) 9374 4000")//
                .address("48 Pirrama Road, Pyrmont, NSW @))(, Australia")//
                .types(Arrays.asList("shoes_store"))//
                .website("http://www.google.com.au")//
                .language("en-AU")//
                .build();

        RestAssured.given()//
                .queryParam("key", KEY)//
                .body(placesAdd)//
                .when()//
                .post("/distancematrix/json").then()//
                .statusCode(200).and()//
                .contentType(ContentType.JSON).and()//
                .body("scope", Matchers.equalTo("APP")).and()//
                .body("status", Matchers.equalTo("OK"));
    }
}