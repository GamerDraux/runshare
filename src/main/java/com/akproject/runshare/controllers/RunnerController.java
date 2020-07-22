package com.akproject.runshare.controllers;


import com.akproject.runshare.models.DTO.NewRunnerRegistrationDTO;
import com.akproject.runshare.models.DTO.RunnerLoginDTO;
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
import java.util.Optional;

@Controller
@RequestMapping("/runners")
public class RunnerController extends MainController {

    public static void setUserInSession(HttpSession session, Runner runner) {
        session.setAttribute(runnerSessionKey, runner.getId());
    }

    public static String callsignSort = "csasc";
    public static String nameSort = "fnasc";
    public static String runnerLevelSort = "rlasc";
    public static String ageSort = "aasc";
    public void setSortVariablesInModel (Model model){
        model.addAttribute("callsignSort", callsignSort);
        model.addAttribute("nameSort", nameSort);
        model.addAttribute("runnerLevelSort", runnerLevelSort);
        model.addAttribute("ageSort", ageSort);
    }

    @GetMapping
    public String displayRunnersIndex(HttpServletRequest request, Model model){
        model.addAttribute("title", "Runners");
        setSortVariablesInModel(model);
        setRunnerInModel(request, model);
        model.addAttribute("runners", runnerRepository.findAll());
        return "runners/index";
    }

    @GetMapping("/{sort}")
    public String displayRunnerIndexSorted(@PathVariable String sort, HttpServletRequest request, Model model){
        model.addAttribute("title", "Runners");
        setRunnerInModel(request, model);
        if (sort.equals("csasc")){
            callsignSort="csdesc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "ascending Callsign");
            model.addAttribute("runners", runnerRepository.findAllByOrderByCallsignAsc());
        } else if (sort.equals("csdesc")) {
            callsignSort="csasc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "descending Callsign");
            model.addAttribute("runners",runnerRepository.findAllByOrderByCallsignDesc());
        } else if (sort.equals("fnasc")){
            nameSort="fndesc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "ascending first name");
            model.addAttribute("runners", runnerRepository.findAllByOrderByFirstNameAsc());
        } else if (sort.equals("fndesc")){
            nameSort="lnasc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "descending first name");
            model.addAttribute("runners", runnerRepository.findAllByOrderByFirstNameDesc());
        } else if (sort.equals("lnasc")){
            nameSort="lndesc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "ascending last name");
            model.addAttribute("runners", runnerRepository.findAllByOrderByLastNameAsc());
        } else if (sort.equals("lndesc")){
            nameSort="fnasc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "descending last name");
            model.addAttribute("runners", runnerRepository.findAllByOrderByLastNameDesc());
        } else if (sort.equals("rlasc")){
            runnerLevelSort = "rldesc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "ascending running level");
            model.addAttribute("runners", runnerRepository.findAllByOrderByRunnerLevelAsc());
        } else if (sort.equals("rldesc")){
            runnerLevelSort="rlasc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "descending running level");
            model.addAttribute("runners", runnerRepository.findAllByOrderByRunnerLevelDesc());
        } else if (sort.equals("aasc")){
            ageSort="adesc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "ascending age");
            model.addAttribute("runners", runnerRepository.findAllByOrderByAgeAsc());
        } else if (sort.equals("adesc")){
            ageSort="aasc";
            setSortVariablesInModel(model);
            model.addAttribute("sortType", "descending age");
            model.addAttribute("runners", runnerRepository.findAllByOrderByAgeDesc());
        } else {
            setSortVariablesInModel(model);
            model.addAttribute("sortType", null);
            model.addAttribute("runners", runnerRepository.findAll());
        }
        return "runners/index";
    }

    @GetMapping("/addRunner")
    public String displayAddRunnerForm(HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute("runnerLevels", RunnerLevel.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute(new NewRunnerRegistrationDTO());
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
//todo-change input on age to make it start at 16

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
//todo-refactor model.addAttribute lines into new static class
        Runner newRunner = new Runner(newRunnerRegistrationDTO.getCallsign(),newRunnerRegistrationDTO.getFirstName(),newRunnerRegistrationDTO.getLastName(),newRunnerRegistrationDTO.isCallsignOnly(),newRunnerRegistrationDTO.getPassword(),newRunnerRegistrationDTO.getAge(),newRunnerRegistrationDTO.getWeight(),newRunnerRegistrationDTO.getGender(),newRunnerRegistrationDTO.getRunnerLevel(), newRunnerRegistrationDTO.getZip());
        runnerRepository.save(newRunner);
        setUserInSession(request.getSession(), newRunner);


        return "redirect:";
    }

    @GetMapping("/login")
    public String displayLoginForm(Model model, HttpServletRequest request){
            setRunnerInModel(request, model);
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

    //todo-add listing of runsessions for runner in runnerDetails
}
