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
@RequestMapping(value = "/InstitutionInfo")
public class InstitutionInfoController {
    @Autowired
    private InstitutionRepository institutionRepository;
    @RequestMapping(method = RequestMethod.GET)
    public  String getPage(){
        return "InstitutionInfo";
    }
    @RequestMapping(value = "/getInfo",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Institution getInfo(@RequestParam("id")String institution){
        Institution institution1 = institutionRepository.findByOrgid(institution);
        return institution1;
    }
    @RequestMapping(value = "/saveIns",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int saveIns (@RequestParam("institution")String ins,@RequestParam("name")String name,
                        @RequestParam("address")String address,@RequestParam("description")String des,
                        @RequestParam("teachpower")String teachpower){
        Institution institution = institutionRepository.findByOrgid(ins);
        institution.setInstitutionName(name);
        institution.setAddress(address);
        institution.setDescription(des);
        institution.setTeachpower(teachpower);
        institutionRepository.save(institution);
        return 0;
    }

}
