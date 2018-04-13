package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.model.Institution;
import edu.nju.coursesystem.model.InstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/InstitutionLogin")
public class InstituteLoginController {
    @Autowired
    private InstitutionRepository institutionRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "InstitutionLogin";
    }

    @RequestMapping(value = "/InstitutionConfirm",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int confirmInstitute(@RequestParam("code")String code){
        //机构登录
       Institution institution =  institutionRepository.findByOrgid(code);
       if (institution==null){
           return 1;
       }else {
           if (institution.getState().equals("审核通过")){
               return 0;
           }else {
               //审核未通过
               return 1;
           }
       }
    }
}
