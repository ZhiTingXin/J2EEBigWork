package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.OrderIns;
import edu.nju.coursesystem.VO.OrdersIns;
import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/InstitutionCanceledInfo")
public class InstitutionCanceledInfoController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClassModelRepository classModelRepository;
    @Autowired
    private StudentRepository studentRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model, @RequestParam("Institution")String ins){
        OrdersIns ordersIns = new OrdersIns();
        Institution institution = institutionRepository.findByOrgid(ins);
        List<Order> orders = new ArrayList<>();
        List<Course> courses = courseRepository.findByInstitute(institution.getInstitutionName());
        List<OrderIns> list = new ArrayList<>();
        //检索机构的所有课程
        for(int i=0;i<courses.size();i++){
            List<Order> courseOrders = orderRepository.findByCourseName(courses.get(i).getCourseName());
            for (int i1=0;i1<courseOrders.size();i1++){
                if(courseOrders.get(i1).getOrderState().equals("已退订")){
                    orders.add(courseOrders.get(i1));//将获得的订单信息添加到list中
                }
            }
        }
        //将orderlist转换成ordersins
        for (int m=0;m<orders.size();m++){
            OrderIns orderIns = new OrderIns();
            orderIns.setCourseName(orders.get(m).getCourseName());
            orderIns.setClassName(classModelRepository.findByClassId(orders.get(m).getClassId()).getClassName());
            orderIns.setNum(orders.get(m).getClassNum());
            orderIns.setTotal(orders.get(m).getTotal());
            orderIns.setOrderDate(new Date(orders.get(m).getOrderDate().getTime()));
            orderIns.setUserName(studentRepository.findByMail(orders.get(m).getUserid()).getUserName());
            list.add(orderIns);
        }
        ordersIns.setOrders(list);
        model.addAttribute("orderList",ordersIns);
        return "InstitutionCanceledInfo";
    }
}
