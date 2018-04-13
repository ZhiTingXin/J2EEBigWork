package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.model.Student;
import edu.nju.coursesystem.model.StudentRepository;
import edu.nju.coursesystem.util.MailUtil;
import edu.nju.coursesystem.util.RandomConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/Register")
public class RegisterController {
    @Autowired
    private StudentRepository studentRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getRegister(){
        return "Register";
    }

    @RequestMapping(value="/AddUser",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int RegisterStudent(@RequestParam("mail")String mail,@RequestParam("password")String password){
        Student student = new Student();
        //获取验证码
        String code = RandomConfirm.getConfirm();
        //邮件信息
        String mailContent = "Mail Confirm: Click the following URL to confirm your mail: http://localhost:8080/Login/Yanzheng?code="+code;
        try {
            MailUtil.sendMail(mail,mailContent);
        } catch (Exception e) {
            System.out.println("邮件发送失败");
            e.printStackTrace();
        }
        student.setMail(mail);
        student.setConfirm(code);
        student.setPassword(password);
        student.setIngration(0);
        student.setUserLevel(0);
        student.setCost(0);
        //尚未激活
        student.setUserState(false);
        studentRepository.save(student);
        return 0;
    }

}
