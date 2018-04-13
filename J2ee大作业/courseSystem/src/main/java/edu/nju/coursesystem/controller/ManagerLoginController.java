package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.model.Manager;
import edu.nju.coursesystem.model.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ManagerLogin")
public class ManagerLoginController {
    @Autowired
    private ManagerRepository managerRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "ManagerLogin";
    }
    @RequestMapping(value = "/Confirm",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int confirm(@RequestParam("mail")String username,@RequestParam("password")String password){
        Manager manager = managerRepository.findByUsername(username);
        if (manager.getPassword().equals(password)){
            return 0;
        }else {
            return 1;
        }
    }
}
