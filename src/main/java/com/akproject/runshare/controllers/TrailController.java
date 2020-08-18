package com.akproject.runshare.controllers;

import com.akproject.runshare.models.DTO.NewTrailDTO;
import com.akproject.runshare.models.DTO.NewTrailDifficultyDTO;
import com.akproject.runshare.models.Trail;
import com.akproject.runshare.models.TrailDifficultyRating;
import com.akproject.runshare.models.data.TrailDifficultyRatingRepository;
import com.akproject.runshare.models.enums.TrailDifficulty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/trails")
public class TrailController extends MainController{
    //todo-create an edit view for Trails
    @GetMapping(value={"", "/{sortType}"})
    public String displayTrailIndex (@PathVariable (required=false) String sortType, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Trail List");
        if (sortType!=null) {
            switch (sortType) {
                case "nameAsc":
                    model.addAttribute("sortType", "ascending name");
                    model.addAttribute("trails", trailRepository.findAllByOrderByNameAsc());
                    return "trails/index";
                case "nameDesc":
                    model.addAttribute("sortType", "descending name");
                    model.addAttribute("trails", trailRepository.findAllByOrderByNameDesc());
                    return "trails/index";
                case "addressAsc":
                    model.addAttribute("sortType", "ascending location");
                    model.addAttribute("trails", trailRepository.findAllByOrderByAddressAsc());
                    return "trails/index";
                case "addressDesc":
                    model.addAttribute("sortType", "descending location");
                    model.addAttribute("trails", trailRepository.findAllByOrderByAddressDesc());
                    return "trails/index";
                case "milesAsc":
                    model.addAttribute("sortType", "ascending length");
                    model.addAttribute("trails", trailRepository.findAllByOrderByMilesAsc());
                    return "trails/index";
                case "milesDesc":
                    model.addAttribute("sortType", "descending length");
                    model.addAttribute("trails", trailRepository.findAllByOrderByMilesDesc());
                    return "trails/index";
            }
        }
        model.addAttribute("trails", trailRepository.findAll());
        return "trails/index";
    }

    @GetMapping("/addTrail")
    private String displayAddTrailView (Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Add Trail");
        model.addAttribute("difficulties", TrailDifficulty.values());
        model.addAttribute(new NewTrailDTO());
        model.addAttribute(new NewTrailDifficultyDTO());
        return "trails/addTrail";
    }

    @PostMapping("/addTrail")
    private String processAddTrailForm (@ModelAttribute @Valid NewTrailDTO newTrailDTO, @ModelAttribute @Valid NewTrailDifficultyDTO newTrailDifficultyDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Add Trail");
        model.addAttribute("difficulties", TrailDifficulty.values());
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

        HttpSession session = request.getSession();
        if (newTrailDifficultyDTO.getTrailDifficulty()!=null){
            TrailDifficultyRating newTrailDifficultyRating = new TrailDifficultyRating(newTrailDifficultyDTO.getTrailDifficulty(), getRunnerFromSession(session), newTrail);
            trailDifficultyRatingRepository.save(newTrailDifficultyRating);
        }
        model.addAttribute("title", "Trail List");
        model.addAttribute("trails", trailRepository.findAll());
        return "redirect:/trails";
    }



    @GetMapping("/trailDetails/{id}")
    public String displayTrailDetails (@PathVariable Integer id, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);

        Optional<Trail> testTrail = trailRepository.findById(id);
        if (testTrail.isEmpty()){
            return "trails/";
        }

        Trail detailedTrail = testTrail.get();
        model.addAttribute("comments", commentRepository.findByTrail_IdOrderByDateCreatedDescTimeCreatedDesc(id));
        model.addAttribute("title", "Trail Details");
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
