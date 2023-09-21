package com.test.sito;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

class TestAPISitoRestAssured {

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://cv-matteo.onrender.com";
    }

    /**
     * CONTROLLIAMO SE LA HOME PAGE E' ACCESSIBILE
     */
    @Test
    void testHomePage() {
        given()
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    /**
     * CONTROLLIAMO SE LA PAGINA INGLESE EFFETTIVAMENTE HA LA LINGUA CORRETTA
     */
    @Test
    void testEnglishHomePage() {
        given()
                .when()
                .get("/index_en")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .contentType("text/html")
                .body(containsString("Personal Blog by Matteo"));
    }

    /**
     * CONTROLLIAMO SE IL DOWNLOAD DEL CV FUNZIONA CORRETTAMENTE
     */
    @Test
    void testDownloadCV() {
        given()
                .when()
                .get("/download-cv")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .header("Content-Disposition", containsString("attachment")) // Verifica l'header Content-Disposition
                .header("Content-Type", equalTo("application/pdf")); // Verifica il tipo di contenuto
    }

}