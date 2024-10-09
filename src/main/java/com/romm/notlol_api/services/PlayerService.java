package com.romm.notlol_api.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    
    String riotUrl = "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/";
    @Value("${api_key}")
    String key;

    public String getInfo(String gameName, String tagLine) {
        String url = riotUrl+gameName.replaceAll("\\s","")+"/"+tagLine+"?api_key="+key;
        HttpRequest request = HttpRequest.newBuilder().
            uri(
                URI.create(url)
                ).
            timeout(Duration.ofSeconds(10)).
            GET().
            build();
    
        HttpClient client = HttpClient.newBuilder().build();

        try {
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "deu merda";
        }
    }
}
