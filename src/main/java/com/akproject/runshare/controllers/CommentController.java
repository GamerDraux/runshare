package com.akproject.runshare.controllers;

import com.akproject.runshare.models.Comment;
import com.akproject.runshare.models.DTO.NewCommentDTO;
import com.akproject.runshare.models.Runner;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/comments")
public class CommentController extends MainController{

    @GetMapping
    public String displayCommentIndex(HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Comments");
        model.addAttribute("comments", commentRepository.findAll());
        return "/comments/index";
    }

    @GetMapping("/createComment")
    public String displayCreateComment(HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Create Comment");
        model.addAttribute("nullTrail", null);
        model.addAttribute("runners", runnerRepository.findAll());
        model.addAttribute("trails", trailRepository.findAll());
        model.addAttribute("runSessions", runSessionRepository.findAll());
        model.addAttribute(new NewCommentDTO());
        return "/comments/createComment";
    }

    @PostMapping("/createComment")
    public String processCreateComment(@ModelAttribute @Valid NewCommentDTO newCommentDTO, Errors errors, Model model, HttpServletRequest request){
        setRunnerInModel(request, model);
        if (errors.hasErrors()){
            model.addAttribute("trails", trailRepository.findAll());
            model.addAttribute("title", "Create Comment");
            return "/comments/createComment";
        }

        HttpSession commentSession = request.getSession();
        Runner commentCreator = getRunnerFromSession(commentSession);
        Comment savedComment = new Comment(newCommentDTO.getMessageTitle(), newCommentDTO.getMessage(), commentCreator, LocalDate.now(), LocalTime.now(), newCommentDTO.getTrail() );
        commentRepository.save(savedComment);
        return "redirect:/comments";


    }
}
