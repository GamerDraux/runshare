package com.akproject.runshare.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/runners")
public class RunnerController {

    @GetMapping("/addRunner")
    public String displayAddRunnerForm(Model model){
        return "/addrunner";
    }
//TODO-Get addRunnerPage working
}
