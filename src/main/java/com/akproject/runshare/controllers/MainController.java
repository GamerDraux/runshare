package com.akproject.runshare.controllers;

import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.data.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

//todo-clean up bootstrap navbar

@Controller
public class MainController {

    @Autowired
    RunnerRepository runnerRepository;

    static final String runnerSessionKey = "user";

    Runner getRunnerFromSession(HttpSession session) {
        Integer runnerId = (Integer) session.getAttribute(runnerSessionKey);
        if (runnerId == null) {
            return null;
        }

        Optional<Runner> runner = runnerRepository.findById(runnerId);

        if (runner.isEmpty()) {
            return null;
        }

        return runner.get();
    }

    void setRunnerInModel(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        if (getRunnerFromSession(session)!=null){
            model.addAttribute("currentRunner", getRunnerFromSession(session));
        } else {
            model.addAttribute("currentRunner", false);
        }
    }
}
