package com.romm.notlol_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.romm.notlol_api.services.PlayerService;

import io.swagger.v3.oas.annotations.Operation;

@RestController @RequestMapping(value = "/") @CrossOrigin
public class NotLolController {

    @Autowired PlayerService ps;

    @GetMapping @Operation(summary = "Greets you.")
    public String getMethodName() {
        return new String("Hi. My name is roms and this is an API that returns a players not-playing-lol streak. Try /docs or /docs-json.");
    }
    
    @Operation(summary = "Returns the number of days since the player's last match.")
    @RequestMapping(value = "/main", method = { RequestMethod.GET, RequestMethod.POST })
    public String main(@RequestParam String gameName, @RequestParam String tagLine) {
        String puuid = ps.getPlayerPuid(gameName, tagLine);

        return ps.getDaysSinceLastMatch(ps.getPlayerLastMatch(ps.getPlayerMatches(puuid)));
    }
    
}
