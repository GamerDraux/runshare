package com.akproject.runshare.controllers;

import com.akproject.runshare.models.Comment;
import com.akproject.runshare.models.RunSession;
import com.akproject.runshare.models.Runner;
import com.akproject.runshare.models.Trail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("restRequest")
public class RestController extends MainController {


    @GetMapping(value = "/runner/{id}", produces = "application/json")
    public Runner getRunner(@PathVariable int id){
        if (runnerRepository.findById(id) != null) {
            Runner runner=runnerRepository.findById(id);
            return new Runner(runner.getCallsign(),runner.getFirstName(),runner.getLastName(),runner.isCallsignOnly(),"",runner.getAge(),runner.getWeight(),runner.getGender(),runner.getRunnerLevel(),runner.getZip());
        } else {return new Runner();}
    }

    @GetMapping(value = "/trail/{id}", produces = "application/json")
    public Trail getTrail(@PathVariable int id){
        return trailRepository.findById(id);
    }

    @GetMapping(value="/runSession/{id}", produces = "application/json")
    public RunSession getRunSession(@PathVariable int id){
        return runSessionRepository.findById(id);
    }

    @GetMapping(value="/comment/{id}", produces = "application/json")
    public Comment getComment(@PathVariable int id){
        return commentRepository.findById(id);
    }
}
