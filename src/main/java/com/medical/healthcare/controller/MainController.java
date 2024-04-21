package com.medical.healthcare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class MainController {
    
    @GetMapping("/index")
    public String root(){
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        return "login";
    }

}