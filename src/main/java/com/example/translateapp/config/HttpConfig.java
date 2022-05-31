package com.example.translateapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Configuration
public class HttpConfig {

    private static final String host = "deep-translate1.p.rapidapi.com";
    private static final String apiKey = "";

    public static HttpEntity<String> getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Host", host);
        headers.set("X-RapidAPI-Key", apiKey);
        return new HttpEntity<>(null, headers);
    }

    public static HttpEntity<String> postHeaders(String quote, String source, String target) {
        String postBody = String.format("{\r\"q\": \"%s\",\r\"source\": \"%s\",\r\"target\": \"%s\"\r}", quote, source, target);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RapidAPI-Host", host);
        headers.set("X-RapidAPI-Key", apiKey);
        return new HttpEntity<>(postBody, headers);
    }
}
