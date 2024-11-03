package com.romm.notlol_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romm.notlol_api.DTOs.PlayerDTO;
import com.romm.notlol_api.services.PlayerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController @RequestMapping(value = "/")

public class NotLolController {

    @Autowired PlayerService ps;

    @GetMapping @Operation(summary = "Greets you.")
    public String getMethodName() {
        return new String("Hi. My name is roms and this is an API that returns a players not-playing-lol streak. I hope you have some use to this.");
    }
    
    @Operation(summary = "Returns the number of days since the player's last match.")
    @GetMapping("/main")
    public String main(@RequestBody PlayerDTO data) {
        String puuid = ps.getPlayerPuid(data.gameName(), data.tagLine());

        return ps.getDaysSinceLastMatch(ps.getPlayerLastMatch(ps.getPlayerMatches(puuid)));
    }
    
}
