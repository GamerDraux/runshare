package com.akproject.runshare.controllers;


import com.akproject.runshare.models.DTO.NewRunnerRegistrationDTO;
import com.akproject.runshare.models.DTO.RunnerLoginDTO;
import com.akproject.runshare.models.Runner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/runners")
public class RunnerController extends MainController {

    public static void setUserInSession(HttpSession session, Runner runner) {
        session.setAttribute(runnerSessionKey, runner.getId());
    }

    @GetMapping
    public String displayRunnersIndex(HttpServletRequest request, Model model){
        model.addAttribute("title", "Runners");
        setRunnerInModel(request, model);
        model.addAttribute("runners", runnerRepository.findAll());
        return "runners/index";
    }

    @GetMapping("/addRunner")
    public String displayAddRunnerForm(HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute(new NewRunnerRegistrationDTO());
        model.addAttribute("title", "Add Runner");
        return "runners/addrunner";
    }

    @PostMapping("/addRunner")
    public String processAddRunnerForm(@ModelAttribute @Valid NewRunnerRegistrationDTO newRunnerRegistrationDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
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

        Runner newRunner = new Runner(newRunnerRegistrationDTO.getCallsign(),newRunnerRegistrationDTO.getFirstName(),newRunnerRegistrationDTO.getLastName(),newRunnerRegistrationDTO.isCallsignOnly(),newRunnerRegistrationDTO.getPassword(),newRunnerRegistrationDTO.getAge());
        runnerRepository.save(newRunner);
        setUserInSession(request.getSession(), newRunner);


        return "redirect:";
    }

    @GetMapping("/login")
    private String displayLoginForm(Model model, HttpServletRequest request){
            setRunnerInModel(request, model);
            model.addAttribute(new RunnerLoginDTO());
            model.addAttribute("title", "Login");
            return "/runners/login";
    }

    @GetMapping("/login/{id}")
    private String displayLoginFormWithId(@PathVariable Integer id, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        RunnerLoginDTO listedRunner = new RunnerLoginDTO();
        listedRunner.setCallsign(runnerRepository.findById(id).get().getCallsign());
        model.addAttribute("runnerLoginDTO", listedRunner);
        model.addAttribute("title", "Login "+listedRunner.getCallsign());
        return "/runners/login";
    }

    @PostMapping("/login")
//    todo-add a different loginform for if the Runner logs in from the runner list
    private String processLoginForm (@ModelAttribute @Valid RunnerLoginDTO runnerLoginDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Login");
        if (errors.hasErrors()){
            return "runners/login";
        }

        Runner loginRunner = runnerRepository.findByCallsign(runnerLoginDTO.getCallsign());

        if (loginRunner == null){
            errors.rejectValue("callsign", "callsign.notFound", "That Callsign has not been registered!");
            return "runners/login";
        }

        if (!loginRunner.isMatchingPassword(runnerLoginDTO.getPassword())){
            errors.rejectValue("password", "password.incorrectPassword", "Password is not correct for this Callsign");
            return "runners/login";
        }

        setUserInSession(request.getSession(), loginRunner);
        setRunnerInModel(request, model);
        model.addAttribute("runners", runnerRepository.findAll());
        return "runners/index";
    }

    @PostMapping("/login/{id}")
//    todo-add a different loginform for if the Runner logs in from the runner list
    private String processLoginForm (@PathVariable Integer id, @ModelAttribute @Valid RunnerLoginDTO runnerLoginDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Login");
        if (errors.hasErrors()){
            return "runners/login";
        }

        Runner loginRunner = runnerRepository.findByCallsign(runnerLoginDTO.getCallsign());

        if (loginRunner == null){
            errors.rejectValue("callsign", "callsign.notFound", "That Callsign has not been registered!");
            return "runners/login";
        }

        if (!loginRunner.isMatchingPassword(runnerLoginDTO.getPassword())){
            errors.rejectValue("password", "password.incorrectPassword", "Password is not correct for this Callsign");
            return "runners/login";
        }

        setUserInSession(request.getSession(), loginRunner);
        model.addAttribute("runners", runnerRepository.findAll());
        setRunnerInModel(request, model);
        return "runners/index";
    }

    @GetMapping("/logout")
    private String logoutRunner (Model model, HttpSession session, HttpServletRequest request){
        session.removeAttribute(runnerSessionKey);
        request.getSession().invalidate();
        return "/index";
    }

    @GetMapping("/runnerDetails/{id}")
    private String displayRunnerDetailsView (@PathVariable Integer id, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);

        Optional<Runner> testRunner = runnerRepository.findById(id);

        if (testRunner.isEmpty()){
            return "/runners/index";
        }

        Runner detailedRunner = testRunner.get();
        model.addAttribute("title", "Details "+detailedRunner.getCallsign());
        model.addAttribute("detailedRunner",  detailedRunner);
        return "/runners/runnerDetails";
    }

    @GetMapping("/runnerDetails")
    private String displayRunnerDetailsBlank (Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Blank Details");
        model.addAttribute("detailedRunner", new Runner());
        return "/runners/runnerDetails";
    }

}
