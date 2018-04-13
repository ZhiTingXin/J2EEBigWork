package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.model.Institution;
import edu.nju.coursesystem.model.InstitutionRepository;
import edu.nju.coursesystem.util.RandomCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/InstitutionRegister")
public class InstitutionRegisterController {
    @Autowired
    private InstitutionRepository institutionRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "InstitutionRegister";
    }

    @RequestMapping(value ="/AddInstitution")
    @ResponseBody
    public int addInstitution(@RequestParam("institutionName")String name,
                              @RequestParam("mail")String mail,@RequestParam("teachpower")String teachpower,@RequestParam("address")String address,@RequestParam("description")String des){
        String code = RandomCode.getCode();
        Institution institution1= institutionRepository.findByOrgid(code);
        while (true){
            if (institution1==null){
                System.out.println("no find");
                Institution institution = new Institution();
                institution.setAddress(address);
                institution.setDescription(des);
                institution.setOrgid(code);
                institution.setState("未审核");
                institution.setTeachpower(teachpower);
                institution.setMail(mail);
                institution.setInstitutionName(name);
                institutionRepository.save(institution);
                break;
            }else {
                System.out.println("find one");
                code = RandomCode.getCode();//重新生成code一直进行
            }
        }
        return 0;
    }
}
