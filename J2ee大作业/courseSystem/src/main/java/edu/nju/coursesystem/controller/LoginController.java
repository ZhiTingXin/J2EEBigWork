package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.model.Student;
import edu.nju.coursesystem.model.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/Login")
public class LoginController {
    @Autowired
    private StudentRepository studentRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getLogin(){
        return "Login";
    }
    @RequestMapping(value="/Confirm",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int Login(@RequestParam("mail")String email,@RequestParam("password")String password){

       Student student =  studentRepository.findByMail(email);
       //如果用户激活
        if (student.isUserState()){
            if(password.equals(student.getPassword())){
                return 1;
            }else {
                return 0;
                //密码错误
            }
        }else {
            return 2;
            //用户尚未激活
        }
    }
    @RequestMapping(value = "/Yanzheng",method = RequestMethod.GET)
    public String confirmUser(@RequestParam("code")String code){
        Student student = studentRepository.findByConfirm(code);
        student.setUserState(true);
        studentRepository.save(student);
        return "Login";
    }

}
