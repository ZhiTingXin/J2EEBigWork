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
@RequestMapping(value = "/PayInstitution")
public class PayInstitutionController {
    @Autowired
    private ManagerRepository managerRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "PayInstitution";
    }
    @RequestMapping(value = "/Save",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int save(@RequestParam("pay")String pay){
        Manager manager = managerRepository.findByUsername("1835427462@qq.com");
        manager.setPercent(Integer.parseInt(pay));
        managerRepository.save(manager);
        return 0;
    }
}
