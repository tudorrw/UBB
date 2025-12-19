package com.vvss.FlavorFiesta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index(Model model) {
        // Add logic to fetch and pass data to the index template
        return "index";
    }

    // Define similar methods for other templates as needed
}
