package com.nitaicharan.udemy_restapiawrestantn1.assertexamples;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class TwitterSoftAssertExampleTest {

    private String tweetId1 = "";
    private String tweetId2 = "";
    private String TWITTER_KEY = System.getenv("TWITTER_KEY");
    private String TWITTER_SECRET_KEY = System.getenv("TWITTER_SECRET_KEY");
    private String TWITTER_TOKEN = System.getenv("TWITTER_TOKEN");
    private String TWITTER_SECRET_TOKEN = System.getenv("TWITTER_SECRET_TOKEN");
    private String TWITTER_USER_ID = System.getenv("TWITTER_USER_ID");
    private String TWITTER_USER_NAME = System.getenv("TWITTER_USER_NAME");

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
        assertNotNull(TWITTER_USER_NAME);
    }

    @Test
    public void tweet1() {
        var response = RestAssured.given()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .queryParam("status", "My First Tweet #first")//
                .when()//
                .post("/update.json").then()//
                .statusCode(200).extract().response();

        this.tweetId1 = response.path("id_str");
    }

    @Test
    public void tweet2() {
        var response = RestAssured.given()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .queryParam("status", "My Secound Tweet #first #second")//
                .when()//
                .post("/update.json").then()//
                .statusCode(200).extract().response();

        this.tweetId2 = response.path("id_str");
    }

    @Test(dependsOnMethods = { "tweet1", "tweet2" })
    public void readTweet() {
        RestAssured.given()//
                .auth().oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .log().all()//
                .queryParam("user_id", TWITTER_USER_ID)//
                .when()//
                .get("/user_timeline.json")//
                .then()//
                .statusCode(200)//
                .body(//
                        "user.name", hasItem(TWITTER_USER_NAME), //
                        "entities.hashtags[1].size()", equalTo(1), //
                        "entities.hashtags[0].size()", equalTo(2)//
                )//
                .log().all();//
    }

    @Test(dependsOnMethods = { "readTweet" })
    public void deleteTweet1() {
        RestAssured.given()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .pathParam("id", this.tweetId1)//
                .when()//
                .post("/destroy/{id}.json")//
                .then()//
                .statusCode(200);
    }

    @Test(dependsOnMethods = { "readTweet" })
    public void deleteTweet2() {
        RestAssured.given()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .pathParam("id", this.tweetId2)//
                .when()//
                .post("/destroy/{id}.json")//
                .then()//
                .statusCode(200);
    }
}
