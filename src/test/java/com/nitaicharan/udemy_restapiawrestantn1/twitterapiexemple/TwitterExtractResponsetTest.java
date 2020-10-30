package com.nitaicharan.udemy_restapiawrestantn1.twitterapiexemple;

import static org.junit.Assert.assertNotNull;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class TwitterExtractResponsetTest {

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

    @Test(enabled = false)
    public void extractResponse() {
        var response = RestAssured.given()//
                .auth()//
                .oauth(TWITTER_KEY, TWITTER_SECRET_KEY, TWITTER_TOKEN, TWITTER_SECRET_TOKEN)//
                .queryParam("status", "My First Tweet")//
                .when()//
                .post("/update.json").then()//
                .statusCode(200)//
                .extract().response();

        var jsPath = new JsonPath(response.asInputStream());
        assertNotNull(jsPath.get("user.name"));
    }
}
