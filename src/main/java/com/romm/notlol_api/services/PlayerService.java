package com.romm.notlol_api.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.romm.notlol_api.DTOs.MatchDTO;
import com.romm.notlol_api.DTOs.PlayerDTO;

@Service
public class PlayerService {
    
    String accountsApiUrl = "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/";
    String matchesApiUrl1 = "https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/";
    String matchesApiUrl2 = "https://americas.api.riotgames.com/lol/match/v5/matches/";

    @Value("${api_key}")
    String key;

    public String getPlayerPuid(String gameName, String tagLine) {
        
        String url = accountsApiUrl+gameName.replaceAll("\\s","")+"/"+tagLine+"?api_key="+key;

        try {
            HttpResponse<String> response = (HttpResponse<String>) makeRequest(url);
            ObjectMapper objectMapper = new ObjectMapper();
            var playerObj = objectMapper.readValue(response.body(), PlayerDTO.class);

            return playerObj.puuid();
        } catch (Exception e) {
            return "deu merda";
        }
    }

    public List getPlayerMatches(String puuid) {
        String url = matchesApiUrl1+puuid+"/ids"+"?api_key="+key;

        try {
            HttpResponse<String> response = makeRequest(url);
            ObjectMapper objectMapper = new ObjectMapper();
            var matchIds = objectMapper.readValue(response.body(), List.class);
            return matchIds;
        } catch (Exception e) {
            return new ArrayList<PlayerDTO>();
        }

    }

    public String getPlayerLastMatch(List matchIds) {
        String url = matchesApiUrl2+matchIds.get(0)+"?api_key="+key;

        try {
            HttpResponse<String> response = makeRequest(url);
            //ObjectMapper objectMapper = new ObjectMapper();
            //var playerObj = objectMapper.readValue(response.body(), PlayerDTO.class);
            return response.body();
        } catch (Exception e) {
            return "deu merda";
        }
    }

    public String getMatchCreation(String matchString) {
        // https://www.baeldung.com/jackson-object-mapper-tutorial
        // https://www.baeldung.com/jackson-ignore-properties-on-serialization
        // https://www.baeldung.com/jackson-deserialize-json-unknown-properties
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            var matchObj = objectMapper.readValue(matchString, MatchDTO.class);
            return String.format("%d", matchObj.info.gameCreation);
        } catch (Exception e) {
            return "deu bostaaaaa" + e.getMessage();
        }
    }

    public String getDaysSinceLastMatch(String matchString) {
        Long unixTimestamp = Long.parseLong(getMatchCreation(matchString));
        Date date = new Date(unixTimestamp); // depois tentar transformar isso em DateTime pra ser mais preciso?

        Instant ld = date.toInstant();

        var duration = Duration.between(ld, Instant.now());

        var daysWithoutPlaying = duration.toDays();

        return String.format("%d", daysWithoutPlaying);
    }

    public String getMatchCreationFormatted(String matchString) {
        Long unixTimestamp = Long.parseLong(getMatchCreation(matchString));
        Date date = new Date(unixTimestamp); // depois tentar transformar isso em DateTime pra ser mais preciso?

        Instant ld = date.toInstant();

        var duration = Duration.between(ld, Instant.now());

        var daysWithoutPlaying = duration.toDays();
        String output;

        output = "Esse mano jogou há menos de 24 horas!";
        if (daysWithoutPlaying > 0)
            output = String.format("Esse mano está a %d dias sem jogar. É um guerreiro inabalável.", duration.toDays());
        
        return output;

    }

    private HttpResponse<String> makeRequest(String url) throws Exception {
        final int TIMEOUT_SECONDS = 10;

        HttpRequest request = HttpRequest.newBuilder().
        uri(
            URI.create(url)
            ).
        timeout(Duration.ofSeconds(TIMEOUT_SECONDS)).
        GET().
        build();

        HttpClient client = HttpClient.newBuilder().build();

        return client.send(request, BodyHandlers.ofString());        
    }
}
