package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.Account;
import edu.nju.coursesystem.VO.AccountList;
import edu.nju.coursesystem.VO.InsList;
import edu.nju.coursesystem.VO.NumList;
import edu.nju.coursesystem.VO.Nums;
import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/DataAnalysis")
public class DataAnalysisController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private OrderRepository orderRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "DataAnalysis";
    }
    @RequestMapping(value = "/getNum",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Nums getNums(){
        Nums nums = new Nums();
        List<Student> students = studentRepository.findAll();
        Integer stuNum = students.size();
        Integer vipNum = 0;
        for (int i=0;i<students.size();i++){
            if (students.get(i).isVipState()){
                vipNum++;
            }
        }
        List<Institution> institutions = institutionRepository.findAll();
        Integer insNum = institutions.size();
        System.out.println(insNum);
        nums.setInsNum(insNum);
        nums.setStuNum(stuNum);
        nums.setVipNum(vipNum);
        return  nums;
    }
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public NumList getUserNums (){
        NumList numList = new NumList();
        List<Student> students = studentRepository.findAll();
        Integer noVip = 0;
        Integer vip0 = 0;
        Integer vip1 = 0;
        Integer vip2 = 0;
        Integer vip3 = 0;
        Integer vip4 = 0;
        for (int i=0;i<students.size();i++){
            if (!students.get(i).isVipState()){
                noVip++;
            }else {
                if (students.get(i).getUserLevel()==0){
                    vip0++;
                }else if(students.get(i).getUserLevel()==1){
                    vip1++;
                }else if (students.get(i).getUserLevel()==2){
                    vip2++;
                }else if (students.get(i).getUserLevel()==3){
                    vip3++;
                }else{
                    vip4++;
                }
            }
        }
        numList.setNoVip(noVip);
        numList.setVip0(vip0);
        numList.setVip1(vip1);
        numList.setVip2(vip2);
        numList.setVip3(vip3);
        numList.setVip4(vip4);
        return numList;
    }
    @RequestMapping(value = "/getInstitutionInfo",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public InsList getInsInfo (){
        InsList insList = new InsList();
        //返回机构的信息
        List<Course> courses = courseRepository.findAll();
        insList.setCourseNum(courses.size());
        List<Institution> institutions = institutionRepository.findAll();
        insList.setInsNum(institutions.size());
        Integer firstClass = 0;
        Integer secondClass = 0;
        Integer thirdClass = 0;
        for (int i =0;i<institutions.size();i++){
            List<Course> courses1 = courseRepository.findByInstitute(institutions.get(i).getInstitutionName());
            if (courses1.size()<5) {
                firstClass++;
            }else if (courses1.size()>10){
                thirdClass++;
            }else {
                secondClass++;
            }
        }
        insList.setFirstClass(firstClass);
        insList.setSecondClass(secondClass);
        insList.setThirdClass(thirdClass);
        return insList;
    }
    @RequestMapping(value = "/getAccount")
    @ResponseBody
    public AccountList getAccount(){
        Manager manager = managerRepository.findByUsername("1835427462@qq.com");
        Integer dis = manager.getPercent();
        AccountList accountList = new AccountList();
        Integer total = 0;
        List<Order> orders = orderRepository.findByOrderState("已预订");
        String a = "2017-04-01";
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
           date =simpleDateFormat.parse(a);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=0;i<orders.size();i++){
            if(orders.get(i).getOrderDate().after(date)){
                total = total +orders.get(i).getTotal();
            }
        }
        Account account = new Account();
        account.setTotal((int)(total*(100-dis)/100.0));
        account.setTime("2018-04");
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        accountList.setAccounts(accounts);
        return accountList;
    }
}
