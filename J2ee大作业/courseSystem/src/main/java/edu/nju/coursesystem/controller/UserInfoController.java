package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.model.Student;
import edu.nju.coursesystem.model.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Name;

@Controller
@RequestMapping(value = "/UserInfo")
public class UserInfoController {
    @Autowired
    private StudentRepository studentRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model,@RequestParam("user")String user){
        Student student = studentRepository.findByMail(user);
        model.addAttribute("student",student);
        return "UserInfo";
    }
    @RequestMapping(value = "/saveUser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int saveStudent(@RequestParam("code")String code,@RequestParam("name")String name, @RequestParam("phone")String phone){
        //用户修改自己的信息
        Student student = studentRepository.findByMail(code);
        student.setUserName(name);
        student.setPhone(phone);
        studentRepository.save(student);
        return 0;
    }
    @RequestMapping(value = "/getInfo",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Student getInfo(@RequestParam("mail")String mail){
        Student student = studentRepository.findByMail(mail);
        return student;
    }

    @RequestMapping(value = "/changeUserVipState",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int change(@RequestParam("mail")String mail){
        Student student = studentRepository.findByMail(mail);
        if (student.isVipState()) {
            student.setVipState(false);
        }else {
            student.setVipState(true);
        }
        studentRepository.save(student);
        return 0;
    }

}
