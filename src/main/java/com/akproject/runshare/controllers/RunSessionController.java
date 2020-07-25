package com.akproject.runshare.controllers;

import com.akproject.runshare.models.DTO.NewRunSessionDTO;
import com.akproject.runshare.models.RunSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/runSessions")
public class RunSessionController extends MainController {

    @GetMapping(value={"", "/{sortType}"})
    public String displayRunSessionsList(@PathVariable(required=false) String sortType, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Run Sessions");
        if (sortType!=null) {
            switch (sortType) {
                case "nameAsc":
                    model.addAttribute("runSessions", runSessionRepository.findAllByOrderByNameAsc());
                    model.addAttribute("sortType", "ascending name");
                    return "runSessions/index";
                case "nameDesc":
                    model.addAttribute("runSessions", runSessionRepository.findAllByOrderByNameDesc());
                    model.addAttribute("sortType", "descending name");
                    return "runSessions/index";
            }
        }
        model.addAttribute("runSessions", runSessionRepository.findAll());
        return "runSessions/index";
    }

    @GetMapping("/addRunSession")
    public String displayAddRunSessionsForm(Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Add Run Session");
        model.addAttribute(new NewRunSessionDTO());
        model.addAttribute("runners", runnerRepository.findAll());
        model.addAttribute("trails", trailRepository.findAll());
        return "runSessions/addRunSession";
    }

    @PostMapping("/addRunSession")
    public String processAddRunSessionForm (@ModelAttribute @Valid NewRunSessionDTO newRunSessionDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        if (errors.hasErrors()){
            model.addAttribute("title", "Add Run Session");
            model.addAttribute("runners", runnerRepository.findAll());
            model.addAttribute("trails", trailRepository.findAll());
            return "runSessions/addRunSession";
        }

        RunSession checkedRunSession = runSessionRepository.findByName(newRunSessionDTO.getName());

        if (checkedRunSession!=null){
            errors.rejectValue("name", "runSession.alreadyExists", "That Run Session name has already been used");
            model.addAttribute("title", "Add Run Session");
            return "runSessions/addRunSession";
        }

        RunSession newRunSession = new RunSession(newRunSessionDTO.getName(), newRunSessionDTO.getDate(), newRunSessionDTO.getRunner(), newRunSessionDTO.getTrail());
        runSessionRepository.save(newRunSession);
        model.addAttribute("title", "Run Sessions");
        model.addAttribute("runSessions", runSessionRepository.findAll());


        return "runSessions/index";
    }

    @GetMapping("/runSessionDetails/{id}")
    public String displayRunSessionDetails (@PathVariable Integer id, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);

        Optional<RunSession> testRunSession = runSessionRepository.findById(id);

        if (testRunSession.isEmpty()){
            return "runSessions/index";
        }

        RunSession detailedRunSession = testRunSession.get();
        model.addAttribute("title", "Details " + detailedRunSession.getName());
        model.addAttribute("detailedRunSession", detailedRunSession);
        return "/runSessions/runSessionDetails";
    }

    @GetMapping("/runSessionDetails")
    public String displayBlankRunSessionDetails (Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Blank Details");
        model.addAttribute("detailedRunSession", new RunSession());
        return "/runSessions/runSessionDetails";
    }

    //todo-create time functions to be able to store length of run.
    //todo- change date field to Date type for sorting
    //todo- sort runSessions by date
}
