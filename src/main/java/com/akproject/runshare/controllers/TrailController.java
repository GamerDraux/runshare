package com.akproject.runshare.controllers;

import com.akproject.runshare.models.DTO.NewTrailDTO;
import com.akproject.runshare.models.Trail;
import com.akproject.runshare.models.data.TrailRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/trails")
public class TrailController extends MainController{

    @GetMapping
    public String displayTrailIndex (Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Trail List");
        model.addAttribute("trails", trailRepository.findAll());
        return "trails/index";
    }

    @GetMapping("/addTrail")
    private String displayAddTrailView (Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Add Trail");
        model.addAttribute(new NewTrailDTO());
        return "trails/addTrail";
    }

    @PostMapping("/addTrail")
    private String processAddTrailForm (@ModelAttribute @Valid NewTrailDTO newTrailDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Add Trail");
        if (errors.hasErrors()){
            model.addAttribute("title", "Add Trail");
            return "trails/addTrail";
        }

        Trail checkedTrail = trailRepository.findByName(newTrailDTO.getName());

        if (checkedTrail!=null){
            errors.rejectValue("name", "name.alreadyExists", "A Trail of that name already exists!");
            model.addAttribute("title", "Add Trail");
            return "trails/addTrail";
        }

        Trail newTrail = new Trail(newTrailDTO.getName(), newTrailDTO.getMiles(), newTrailDTO.getAddress(), newTrailDTO.getZipCode());
        trailRepository.save(newTrail);
        model.addAttribute("title", "Trail List");
        model.addAttribute("trails", trailRepository.findAll());
        return "trails/index";
    }



    @GetMapping("/trailDetails/{id}")
    public String displayTrailDetails (@PathVariable Integer id, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);

        Optional<Trail> testTrail = trailRepository.findById(id);
        if (testTrail.isEmpty()){
            return "trails/";
        }

        Trail detailedTrail = testTrail.get();
        model.addAttribute("title", "Details");
        model.addAttribute("detailedTrail",detailedTrail);
        return "trails/trailDetails";
    }

    @GetMapping("/trailDetails")
    public String displayTrailDetailsBlank (Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Blank");
        model.addAttribute("detailedTrail", new Trail("Blank", 0, "Blank", "Blank"));
        return "trails/trailDetails";
    }

}
