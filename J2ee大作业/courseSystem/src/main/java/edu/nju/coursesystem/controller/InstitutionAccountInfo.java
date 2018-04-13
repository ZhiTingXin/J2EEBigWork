package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.Account;
import edu.nju.coursesystem.VO.AccountList;
import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/InstitutionAccountInfo")
public class InstitutionAccountInfo {
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private CourseRepository courseRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model, @RequestParam("Institution")String institution){
        Institution institution1 = institutionRepository.findByOrgid(institution);
        Manager manager = managerRepository.findByUsername("1835427462@qq.com");
        Integer dis = manager.getPercent();
        AccountList accountList = new AccountList();
        List<Course> courses = courseRepository.findByInstitute(institution1.getInstitutionName());
        Integer total = 0;
        for (int s=0;s<courses.size();s++){
            List<Order> orders = orderRepository.findByOrderStateAndCourseName("已预订",courses.get(s).getCourseName());
            String a = "2017-04-01";
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date =simpleDateFormat.parse(a);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (int q = 0;q<orders.size();q++){
                if(orders.get(q).getOrderDate().after(date)){
                    total = total +orders.get(q).getTotal();
                }
            }
        }
        edu.nju.coursesystem.VO.Account account = new edu.nju.coursesystem.VO.Account();
        account.setTotal((int)(total*dis/100.0));
        account.setTime("2018-04");
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        accountList.setAccounts(accounts);
        model.addAttribute("account",accountList);
        return "InstitutionAccountInfo";
    }
}
