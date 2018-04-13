package edu.nju.coursesystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/UserDataAnalysis")
public class UserDataAnalysisController {
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "UserDataAnalysis";
    }
}
