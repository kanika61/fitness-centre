package com.garima.fitnesscentre.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController 
{  
    @GetMapping("/srp")
    public String home()
    {
        return "home";
    }
    
}
