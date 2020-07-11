package com.akproject.runshare.controllers;


import com.akproject.runshare.models.DTO.NewRunnerRegistrationDTO;
import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.data.RunnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/runners")
public class RunnerController {

    @Autowired
    RunnerRepository runnerRepository;

    private static final String runnerSessionKey = "user";

    public Runner getUserFromSession(HttpSession session) {
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

    private static void setUserInSession(HttpSession session, Runner runner) {
        session.setAttribute(runnerSessionKey, runner.getId());
    }

    @GetMapping
    public String displayRunnersIndex(Model model){
        model.addAttribute("title", "Runners");
        return "runners/index";
    }

    @GetMapping("/addRunner")
    public String displayAddRunnerForm(Model model){
        model.addAttribute(new NewRunnerRegistrationDTO());
        model.addAttribute("title", "Add Runner");
        return "runners/addrunner";
    }

    @PostMapping("/addRunner")
    public String processAddRunnerForm(@ModelAttribute @Valid NewRunnerRegistrationDTO newRunnerRegistrationDTO, Errors errors, Model model, HttpServletRequest request){
        if (errors.hasErrors()){
            model.addAttribute("title", "Add Runner");
            model.addAttribute("newRunnerRegistrationDTO", newRunnerRegistrationDTO);
            return "runners/addrunner";
        }

        Runner checkedRunner = runnerRepository.findByCallsign(newRunnerRegistrationDTO.getCallsign());

        if (checkedRunner != null){
            errors.rejectValue("callsign", "callsign.alreadyExists", "A Runner with this callsign already exists!");
            model.addAttribute("title", "Add Runner");
            return "runners/addrunner";
        }

        String password = newRunnerRegistrationDTO.getPassword();
        String verifyPassword = newRunnerRegistrationDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)){
            errors.rejectValue("password","password.doesNotMatch","The passwords do not match");
            model.addAttribute("title","Add Runner");
            return "runners/addrunner";
        }
//todo age is coming up as 0 on all runners.  find and correct
        Runner newRunner = new Runner(newRunnerRegistrationDTO.getCallsign(),newRunnerRegistrationDTO.getFirstName(),newRunnerRegistrationDTO.getLastName(),newRunnerRegistrationDTO.isCallsignOnly(),newRunnerRegistrationDTO.getPassword(),newRunnerRegistrationDTO.getAge());
        runnerRepository.save(newRunner);
        setUserInSession(request.getSession(), newRunner);


        return "redirect:";
    }
//TODO-Add a runner index page with stats on the current runner
}
