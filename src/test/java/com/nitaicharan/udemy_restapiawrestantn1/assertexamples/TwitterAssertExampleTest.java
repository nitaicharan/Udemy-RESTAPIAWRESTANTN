package com.nitaicharan.udemy_restapiawrestantn1.assertexamples;

import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class TwitterAssertExampleTest {

    private String TWITTER_KEY = System.getenv("TWITTER_KEY");
    private String TWITTER_SECRET_KEY = System.getenv("TWITTER_SECRET_KEY");
    private String TWITTER_TOKEN = System.getenv("TWITTER_TOKEN");
    private String TWITTER_SECRET_TOKEN = System.getenv("TWITTER_SECRET_TOKEN");
    private String TWITTER_USER_ID = System.getenv("TWITTER_USER_ID");

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
        assertNotNull(TWITTER_USER_ID);
    }

    @Test
    public void readTweet() {
        RestAssured.given()//
                .auth().oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .log().all()//
                // .queryParam("user_id", "apiautomation")//
                .queryParam("user_id", TWITTER_USER_ID)//
                .when()//
                .get("/user_timeline.json")//
                .then()//
                .log().all()//
                .extract().response();
    }
}
