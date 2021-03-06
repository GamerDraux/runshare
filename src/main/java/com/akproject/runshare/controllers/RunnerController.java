package com.akproject.runshare.controllers;


import com.akproject.runshare.models.Comment;
import com.akproject.runshare.models.DTO.NewRunnerRegistrationDTO;
import com.akproject.runshare.models.DTO.RunnerLoginDTO;
import com.akproject.runshare.models.RunSession;
import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.enums.Gender;
import com.akproject.runshare.models.enums.RunnerLevel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/runners")
public class RunnerController extends MainController {

    public static void setUserInSession(HttpSession session, Runner runner) {
        session.setAttribute(runnerSessionKey, runner.getId());
    }

    @GetMapping(value={"", "/{sortType}"})
    public String displayRunnersIndex(@PathVariable(required = false) String sortType, HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Runners");
       if (sortType!=null){
           switch (sortType) {
               case "callsignAsc":
                   model.addAttribute("sortType", "ascending Callsign");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByCallsignAsc());
                   return "runners/index";
               case "callsignDesc":
                   model.addAttribute("sortType", "descending Callsign");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByCallsignDesc());
                   return "runners/index";
               case "runnerLevelAsc":
                   model.addAttribute("sortType", "ascending runner level");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByRunnerLevelAsc());
                   return "runners/index";
               case "runnerLevelDesc":
                   model.addAttribute("sortType", "descending runner level");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByRunnerLevelDesc());
                   return "runners/index";
               case "firstNameAsc":
                   model.addAttribute("sortType", "ascending first name");
                   model.addAttribute("runners",runnerRepository.findAllByOrderByFirstNameAsc());
                   return "runners/index";
               case "firstNameDesc":
                   model.addAttribute("sortType", "descending first name");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByFirstNameDesc());
                   return "runners/index";
               case "lastNameAsc":
                   model.addAttribute("sortType", "ascending last name");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByLastNameAsc());
                   return "runners/index";
               case "lastNameDesc":
                   model.addAttribute("sortType", "descending last name");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByLastNameDesc());
                   return "runners/index";
               case "ageAsc":
                   model.addAttribute("sortType", "ascending age");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByAgeAsc());
                   return "runners/index";
               case "ageDesc":
                   model.addAttribute("sortType", "descending age");
                   model.addAttribute("runners", runnerRepository.findAllByOrderByAgeDesc());
                   return "runners/index";
           }
       }
        model.addAttribute("runners", runnerRepository.findAll());
        return "runners/index";
    }

    @GetMapping("/addRunner")
    public String displayAddRunnerForm(HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute("runnerLevels", RunnerLevel.values());
        model.addAttribute("genders", Gender.values());
        NewRunnerRegistrationDTO newRunnerRegistrationDTO= new NewRunnerRegistrationDTO();
        newRunnerRegistrationDTO.setAge(16);
        model.addAttribute(newRunnerRegistrationDTO);
        model.addAttribute("title", "Add Runner");
        return "runners/addrunner";
    }

    @PostMapping("/addRunner")
    public String processAddRunnerForm(@ModelAttribute @Valid NewRunnerRegistrationDTO newRunnerRegistrationDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        if (errors.hasErrors()){
            model.addAttribute("runnerLevels", RunnerLevel.values());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("title", "Add Runner");
            model.addAttribute("newRunnerRegistrationDTO", newRunnerRegistrationDTO);
            return "runners/addrunner";
        }

        Runner checkedRunner = runnerRepository.findByCallsign(newRunnerRegistrationDTO.getCallsign());

        if (checkedRunner != null){
            errors.rejectValue("callsign", "callsign.alreadyExists", "A Runner with this callsign already exists!");
            model.addAttribute("runnerLevels", RunnerLevel.values());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("title", "Add Runner");
            return "runners/addrunner";
        }

        String password = newRunnerRegistrationDTO.getPassword();
        String verifyPassword = newRunnerRegistrationDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)){
            errors.rejectValue("password","password.doesNotMatch","The passwords do not match");
            model.addAttribute("runnerLevels", RunnerLevel.values());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("title","Add Runner");
            return "runners/addrunner";
        }
        Runner newRunner = new Runner(newRunnerRegistrationDTO.getCallsign(),newRunnerRegistrationDTO.getFirstName(),newRunnerRegistrationDTO.getLastName(),newRunnerRegistrationDTO.isCallsignOnly(),newRunnerRegistrationDTO.getPassword(),newRunnerRegistrationDTO.getAge(),newRunnerRegistrationDTO.getWeight(),newRunnerRegistrationDTO.getGender(),newRunnerRegistrationDTO.getRunnerLevel(), newRunnerRegistrationDTO.getZip());
        runnerRepository.save(newRunner);
        setUserInSession(request.getSession(), newRunner);


        return "redirect:/runners";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model, HttpServletRequest request){
            setRunnerInModel(request, model);
            model.addAttribute("runners", runnerRepository.findAll());
            model.addAttribute(new RunnerLoginDTO());
            model.addAttribute("title", "Login");
            return "/runners/login";
    }

    @GetMapping("/login/{id}")
    public String displayLoginFormWithId(@PathVariable Integer id, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("selectedRunner", runnerRepository.findById(id).get());
        RunnerLoginDTO listedRunner = new RunnerLoginDTO();
        listedRunner.setCallsign(runnerRepository.findById(id).get().getCallsign());
        model.addAttribute("runnerLoginDTO", listedRunner);
        model.addAttribute("title", "Login "+listedRunner.getCallsign());
        return "/runners/login";
    }

    @PostMapping("/login")
    public String processLoginForm (@ModelAttribute @Valid RunnerLoginDTO runnerLoginDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Login");
        if (errors.hasErrors()){
            model.addAttribute("runners", runnerRepository.findAll());
            return "runners/login";
        }

        Runner loginRunner = runnerRepository.findByCallsign(runnerLoginDTO.getCallsign());

        if (loginRunner == null){
            errors.rejectValue("callsign", "callsign.notFound", "That Callsign has not been registered!");
            return "runners/login";
        }

        if (!loginRunner.isMatchingPassword(runnerLoginDTO.getPassword())){
            errors.rejectValue("password", "password.incorrectPassword", "Password is not correct for this Callsign");
            model.addAttribute("runners", runnerRepository.findAll());
            return "runners/login";
        }

        setUserInSession(request.getSession(), loginRunner);
        setRunnerInModel(request, model);
        model.addAttribute("runners", runnerRepository.findAll());
        return "runners/index";
    }

    @PostMapping("/login/{id}")
    public String processLoginForm (@PathVariable Integer id, @ModelAttribute @Valid RunnerLoginDTO runnerLoginDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("selectedRunner", runnerRepository.findById(id).get());
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
    private String displayRunnerDetailsView (@PathVariable Integer id, Model model, HttpServletRequest request, HttpSession session){
        setRunnerInModel(request, model);
        if (getRunnerFromSession(session)==null){
            model.addAttribute("detailedRunner", runnerRepository.findById(id).get());
            return"/runners/runnerDetailsNoAccess";
        }
        if (getRunnerFromSession(session).getId()!=id){
            model.addAttribute("detailedRunner", runnerRepository.findById(id).get());
            return "/runners/runnerDetailsNoAccess";
        }
        Optional<Runner> testRunner = runnerRepository.findById(id);

        if (testRunner.isEmpty()){
            return "/runners/index";
        }

        Runner detailedRunner = testRunner.get();
        model.addAttribute("title", "Details "+detailedRunner.getCallsign());
        model.addAttribute("detailedRunner",  detailedRunner);

        List<Comment> comments = commentRepository.findByRunners_IdOrderByDateCreatedDescTimeCreatedDesc(id);
        if (!comments.isEmpty()){
            model.addAttribute("comments", comments);
        }

        List<RunSession> runSessions = runSessionRepository.findAllByCreator_Id(id);
        if (!runSessions.isEmpty()) {
            model.addAttribute("runSessions", runSessions);
        }

        return "/runners/runnerDetails";
    }

    @GetMapping("/runnerDetails")
    private String displayRunnerDetailsBlank (Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Blank Details");
        model.addAttribute("detailedRunner", new Runner());
        return "/runners/runnerDetails";
    }

    @GetMapping("/editRunner/{id}")
    private String displayEditRunnerView (@PathVariable Integer id, Model model, HttpServletRequest request, HttpSession session){
        setRunnerInModel(request, model);
        Optional<Runner> runnerTest = runnerRepository.findById(id);
        if (runnerTest.isEmpty()){
            return "/runners/index";
        }

        Runner runnerToEdit = runnerTest.get();
        NewRunnerRegistrationDTO newRunnerRegistrationDTO = new NewRunnerRegistrationDTO();
        newRunnerRegistrationDTO.setCallsign(runnerToEdit.getCallsign());
        newRunnerRegistrationDTO.setFirstName(runnerToEdit.getFirstName());
        newRunnerRegistrationDTO.setLastName(runnerToEdit.getLastName());
        newRunnerRegistrationDTO.setCallsignOnly(runnerToEdit.isCallsignOnly());
        newRunnerRegistrationDTO.setPassword("111111111");
        newRunnerRegistrationDTO.setVerifyPassword("111111111");
        newRunnerRegistrationDTO.setAge(runnerToEdit.getAge());
        newRunnerRegistrationDTO.setWeight(runnerToEdit.getWeight());
        newRunnerRegistrationDTO.setGender(runnerToEdit.getGender());
        newRunnerRegistrationDTO.setRunnerLevel(runnerToEdit.getRunnerLevel());
        newRunnerRegistrationDTO.setZip(runnerToEdit.getZip());
        model.addAttribute(newRunnerRegistrationDTO);
        model.addAttribute("genders", Gender.values());
        model.addAttribute("runnerLevels", RunnerLevel.values());
        model.addAttribute("title", "Editing Runner"+newRunnerRegistrationDTO.getCallsign());
        return "/runners/editRunner";

    }

    @PostMapping("/editRunner/{id}")
    private String processEditRunnerForm (@ModelAttribute @Valid NewRunnerRegistrationDTO newRunnerRegistrationDTO, Errors errors, Model model, HttpServletRequest request, @PathVariable Integer id){
        setRunnerInModel(request, model);
        if (errors.hasErrors()){
            model.addAttribute("newRunnerRegistrationDTO", newRunnerRegistrationDTO);
            model.addAttribute("runnerLevels", RunnerLevel.values());
            model.addAttribute("genders", Gender.values());
            model.addAttribute("title", "Editing Runner"+newRunnerRegistrationDTO.getCallsign());
            return "runners/editRunner";
        }
        Runner updatedRunner = runnerRepository.findById(id).get();
        updatedRunner.setCallsign(newRunnerRegistrationDTO.getCallsign());
        updatedRunner.setFirstName(newRunnerRegistrationDTO.getFirstName());
        updatedRunner.setLastName(newRunnerRegistrationDTO.getLastName());
        updatedRunner.setCallsignOnly(newRunnerRegistrationDTO.isCallsignOnly());
        updatedRunner.setAge(newRunnerRegistrationDTO.getAge());
        updatedRunner.setWeight(newRunnerRegistrationDTO.getWeight());
        updatedRunner.setGender(newRunnerRegistrationDTO.getGender());
        updatedRunner.setRunningLevel(newRunnerRegistrationDTO.getRunnerLevel());
        updatedRunner.setZip(newRunnerRegistrationDTO.getZip());
        updatedRunner.setNumberZipCode(Integer.parseInt(newRunnerRegistrationDTO.getZip()));
        runnerRepository.save(updatedRunner);
        return "redirect:/runners";
    }
}
