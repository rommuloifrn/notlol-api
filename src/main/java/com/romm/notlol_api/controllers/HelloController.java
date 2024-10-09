package com.romm.notlol_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romm.notlol_api.DTOs.PlayerDTO;
import com.romm.notlol_api.services.HelloService;
import com.romm.notlol_api.services.PlayerService;


@RestController @RequestMapping(value = "/")

public class HelloController {

    @Autowired HelloService hs;
    @Autowired PlayerService ps;

    @GetMapping
    public String hello() {
        return hs.hello();
    }

    @GetMapping("/account")
    public String account(@RequestBody PlayerDTO data) {
        return ps.getInfo(data.gameName(), data.tagLine());
    }
    
}
