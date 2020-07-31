package com.akproject.runshare.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class HomeController extends MainController {
    //todo-condense items in navbar with dropdowns for runner, runsessions, and trails
    @RequestMapping
    private String displayIndexPage(Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Home");
        return "index.html";
    }
}
