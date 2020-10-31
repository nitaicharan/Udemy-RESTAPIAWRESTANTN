package com.nitaicharan.udemy_restapiawrestantn1.extractresponse;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;

public class GoogleExtractResponseTest {
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
    public void extractResponse() {
        var response = RestAssured.given()//
                .param("units", "imperial")//
                .param("origins", "Washington,DC")//
                .param("destinations", "New+York+City,NY")//
                .param("key", KEY)//
                .when()//
                .get("/distancematrix/xml")//
                .then()//
                .statusCode(200).extract().response();
        assertTrue(response.contentType().contains("application/xml"));

        var xmlPaht = new XmlPath(response.asInputStream());
        var value = xmlPaht.get("distancematrixresponce.row.element.duration.value");
        assertEquals("13770", value);
    }
}
