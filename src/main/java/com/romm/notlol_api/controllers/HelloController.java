package com.romm.notlol_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.romm.notlol_api.services.HelloService;

@RestController @RequestMapping(value = "/")

public class HelloController {

    @Autowired HelloService hs;

    @GetMapping
    public String hello() {
        return hs.hello();
    }
}
