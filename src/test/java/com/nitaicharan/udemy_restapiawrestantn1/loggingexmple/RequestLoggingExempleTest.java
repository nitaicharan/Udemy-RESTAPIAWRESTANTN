package com.nitaicharan.udemy_restapiawrestantn1.loggingexmple;

import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class RequestLoggingExempleTest {

    private String tweetId;
    private String TWITTER_KEY = System.getenv("TWITTER_KEY");
    private String TWITTER_SECRET_KEY = System.getenv("TWITTER_SECRET_KEY");
    private String TWITTER_TOKEN = System.getenv("TWITTER_TOKEN");
    private String TWITTER_SECRET_TOKEN = System.getenv("TWITTER_SECRET_TOKEN");

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/statuses";
    }

    @Test
    public void keyNotNull() {
        assertNotNull(TWITTER_KEY);
        assertNotNull(TWITTER_SECRET_KEY);
        assertNotNull(TWITTER_TOKEN);
        assertNotNull(TWITTER_SECRET_TOKEN);
    }

    @Test
    public void testMethod() {
        var response = RestAssured.given()//
                .log().all()//
                // .log().ifValidationFails()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .queryParam("status", "My First Tweet")//
                .when()//
                .post("/update.json")//
                .then()//
                .statusCode(200)//
                .extract().response();

        this.tweetId = response.path("id_str");
    }

    @Test(dependsOnMethods = { "testMethod" })
    public void deleteTweet() {
        RestAssured.given()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .pathParam("id", this.tweetId)//
                .when()//
                .post("/destroy/{id}.json")//
                .then()//
                .statusCode(200);
    }
}
