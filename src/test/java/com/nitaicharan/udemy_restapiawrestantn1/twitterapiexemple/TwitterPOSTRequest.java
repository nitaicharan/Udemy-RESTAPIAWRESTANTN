package com.nitaicharan.udemy_restapiawrestantn1.twitterapiexemple;

import static org.junit.Assert.assertNotNull;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class TwitterPOSTRequest {
    private String TWITTER_KEY = System.getenv("TWITTER_KEY");
    private String TWITTER_SECRET_KEY = System.getenv("TWITTER_SECRET_KEY");
    private String TWITTER_TOKEN = System.getenv("TWITTER_TOKEN");
    private String TWITTER_SECRET_TOKEN = System.getenv("TWITTER_SECRET_TOKEN");

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/2/tweets/1275828087666679809";
    }

    @Test
    public void keyNotNull() {
        assertNotNull(TWITTER_KEY);
        assertNotNull(TWITTER_SECRET_KEY);
        assertNotNull(TWITTER_TOKEN);
        assertNotNull(TWITTER_SECRET_TOKEN);
    }

    @Test
    public void statusCodeVerification() {
        RestAssured.given()//
                .queryParam("")//
                .when()//
                .post("/distancematrix/json").then()//
                .statusCode(200).and()//
                .contentType(ContentType.JSON).and()//
                .body("scope", Matchers.equalTo("APP")).and()//
                .body("status", Matchers.equalTo("OK"));
    }
}
