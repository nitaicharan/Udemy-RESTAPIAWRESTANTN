package com.nitaicharan.udemy_restapiawrestantn1.specs;

import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

public class RequestSpecificationDemoTest {
    private String TWITTER_KEY = System.getenv("TWITTER_KEY");
    private String TWITTER_SECRET_KEY = System.getenv("TWITTER_SECRET_KEY");
    private String TWITTER_TOKEN = System.getenv("TWITTER_TOKEN");
    private String TWITTER_SECRET_TOKEN = System.getenv("TWITTER_SECRET_TOKEN");
    private String TWITTER_USER_ID = System.getenv("TWITTER_USER_ID");
    private String TWITTER_USER_NAME = System.getenv("TWITTER_USER_NAME");
    private RequestSpecBuilder specBuilder;

    @BeforeClass
    public void setup() {
        var authenticationScheme = RestAssured.oauth(//
                TWITTER_KEY//
                , TWITTER_SECRET_KEY//
                , TWITTER_TOKEN//
                , TWITTER_SECRET_TOKEN//
        );

        this.specBuilder = new RequestSpecBuilder()//
                .setBaseUri("https://api.twitter.com")//
                .setBasePath("/1.1/statuses")//
                .addQueryParam("user_id", TWITTER_USER_ID)//
                .setAuth(authenticationScheme);//
    }

    @Test
    public void keyNotNull() {
        assertNotNull(TWITTER_KEY);
        assertNotNull(TWITTER_SECRET_KEY);
        assertNotNull(TWITTER_TOKEN);
        assertNotNull(TWITTER_SECRET_TOKEN);
        assertNotNull(TWITTER_USER_ID);
        assertNotNull(TWITTER_USER_NAME);
    }

    @Test
    public void readTweet() {
        RestAssured.given()//
                .spec(specBuilder.build())//
                .when()//
                .get("/user_timeline.json")//
                .then()//
                .statusCode(200);
    }
}