package com.akproject.runshare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("")
    private String displayIndexPage(Model model){
        return "index";
    }
}
