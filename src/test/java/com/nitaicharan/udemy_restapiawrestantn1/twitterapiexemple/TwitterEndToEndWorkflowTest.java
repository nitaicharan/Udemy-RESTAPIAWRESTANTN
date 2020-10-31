package com.nitaicharan.udemy_restapiawrestantn1.twitterapiexemple;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class TwitterEndToEndWorkflowTest {

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
    public void postTweet() {
        var response = RestAssured.given()//
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

    @Test(dependsOnMethods = { "postTweet" })
    public void readTweet() {
        var response = RestAssured.given()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .queryParam("id", this.tweetId)//
                .when()//
                .get("/show.json")//
                .then()//
                .extract().response();

        assertEquals("My First Tweet", response.path("text"));
    }

    @Test(dependsOnMethods = { "readTweet" })
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
