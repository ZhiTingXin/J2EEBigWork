package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.InstitutionList;
import edu.nju.coursesystem.model.Institution;
import edu.nju.coursesystem.model.InstitutionRepository;
import edu.nju.coursesystem.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/ManagerMainPage")
public class ManagerMainPageController
{
    @Autowired
    private InstitutionRepository institutionRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model){
        InstitutionList institutionList = new InstitutionList();
        List<Institution> institutions = institutionRepository.findAll();
        institutionList.setInstitutions(institutions);
        model.addAttribute("institutionList",institutionList);
        return "ManagerMainPage";
    }

    @RequestMapping(value = "/PassInstitution",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int passInstitution(@RequestParam("institutionName")String institutionName){
        Institution institution = institutionRepository.findByInstitutionName(institutionName);
        institution.setState("审核通过");
        institutionRepository.save(institution);
        String code = institution.getOrgid();
        String mailContent = "The institution you register has passed,you can use the following code to login: "+code;
        String mail = institution.getMail();
        try {
            MailUtil.sendMail(mail,mailContent);
        } catch (Exception e) {
            System.out.println("发送邮件失败");
            e.printStackTrace();
        }
        return 0;
    }
    @RequestMapping(value = "/Pass",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public InstitutionList getPass(){
        InstitutionList list = new InstitutionList();
        List<Institution> institutions = institutionRepository.findByState("审核通过");
        list.setInstitutions(institutions);
        return list;
    }
    @RequestMapping(value = "/noPass",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public InstitutionList getNoPass(){
        InstitutionList list = new InstitutionList();
        List<Institution> institutions = institutionRepository.findByState("未审核");
        list.setInstitutions(institutions);
        return list;
    }
}
