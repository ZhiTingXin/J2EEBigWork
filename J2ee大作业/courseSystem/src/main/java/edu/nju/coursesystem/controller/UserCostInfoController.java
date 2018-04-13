package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.CostList;
import edu.nju.coursesystem.model.UserAccountLog;
import edu.nju.coursesystem.model.UserAccountLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value = "/UserCostInfo")
public class UserCostInfoController {
    @Autowired
    private UserAccountLogRepository userAccountLogRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model, @RequestParam("mail")String mail){
        //获取用户的消费信息
        CostList costList =new CostList();
        List<UserAccountLog> logs = userAccountLogRepository.findByUserId(mail);
        costList.setCosts(logs);
        model.addAttribute("costs",costList);
        return "UserCostInfo";
    }
}
