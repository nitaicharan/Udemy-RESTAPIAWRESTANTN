package com.nitaicharan.udemy_restapiawrestantn1.specs;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class SpecificationWhithTimeTest {

    private String tweetId1 = "";
    private String tweetId2 = "";
    private RequestSpecBuilder requestSpecBuilder;
    private ResponseSpecBuilder responseSpecBuilder;
    private String TWITTER_KEY = System.getenv("TWITTER_KEY");
    private String TWITTER_SECRET_KEY = System.getenv("TWITTER_SECRET_KEY");
    private String TWITTER_TOKEN = System.getenv("TWITTER_TOKEN");
    private String TWITTER_SECRET_TOKEN = System.getenv("TWITTER_SECRET_TOKEN");
    private String TWITTER_USER_ID = System.getenv("TWITTER_USER_ID");
    private String TWITTER_USER_NAME = System.getenv("TWITTER_USER_NAME");

    @BeforeClass
    public void setup() {
        var authenticationScheme = RestAssured.oauth(//
                TWITTER_KEY//
                , TWITTER_SECRET_KEY//
                , TWITTER_TOKEN//
                , TWITTER_SECRET_TOKEN//
        );

        this.requestSpecBuilder = new RequestSpecBuilder()//
                .setBaseUri("https://api.twitter.com")//
                .setBasePath("/1.1/statuses")//
                .addQueryParam("user_id", TWITTER_USER_ID)//
                .setAuth(authenticationScheme);

        this.responseSpecBuilder = new ResponseSpecBuilder()//
                .expectStatusCode(200)//
                .rootPath("user")//
                .expectBody("name", hasItem(TWITTER_USER_NAME))//
                .expectResponseTime(greaterThan(0L), TimeUnit.MILLISECONDS)
                .expectBody("screen_name", hasItem(TWITTER_USER_ID));

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
                .post("/update.json")//
                .then()//
                .statusCode(200)//
                .extract().response();

        this.tweetId2 = response.path("id_str");
    }

    @Test(dependsOnMethods = { "tweet1", "tweet2" })
    public void readTweet() {
        RestAssured.given()//
                .spec(requestSpecBuilder.build())//
                .when()//
                .get("/user_timeline.json")//
                .then()//
                .spec(responseSpecBuilder.build());//
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
