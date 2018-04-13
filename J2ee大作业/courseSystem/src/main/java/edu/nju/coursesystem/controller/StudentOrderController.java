package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.CancelOrderInfo;
import edu.nju.coursesystem.VO.OrderList;
import edu.nju.coursesystem.VO.OrderVO;
import edu.nju.coursesystem.model.*;
import edu.nju.coursesystem.util.LevelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/MyOrder")
public class StudentOrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ClassModelRepository classModelRepository;
    //展示我的订单界面
    @Autowired
    private UserAccountLogRepository userAccountLogRepository;
    @Autowired
    private ClassModelStudentsRepository classModelStudentsRepository;
    @Autowired
    private CourseStudentsRepository courseStudentsRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model, @RequestParam("mail")String mail){
        OrderList orderList = new OrderList();
        List<Order>  orders = orderRepository.findByUserid(mail);
        List<OrderVO> orderVOS = new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            OrderVO vo = new OrderVO(orders.get(i));
            orderVOS.add(vo);
        }
        orderList.setOrders(orderVOS);
        model.addAttribute("orderList",orderList);
        return "StudentOrder";
    }
    @RequestMapping(value = "/Booked",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public OrderList getBookedOrders(@RequestParam("mail")String mail){
        OrderList orderList = new OrderList();
        List<Order> orders = orderRepository.findByUseridAndOrderState(mail,"已预订");
        List<OrderVO> orderVOS = new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            OrderVO vo = new OrderVO(orders.get(i));
            orderVOS.add(vo);
        }
        orderList.setOrders(orderVOS);
        return orderList;
    }
    @RequestMapping(value = "/Canceled",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public OrderList getCanceledOrders(@RequestParam("mail")String mail){
        OrderList orderList = new OrderList();
        List<Order> orders = orderRepository.findByUseridAndOrderState(mail,"已退订");
        List<OrderVO> orderVOS = new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            OrderVO vo = new OrderVO(orders.get(i));
            orderVOS.add(vo);
        }
        orderList.setOrders(orderVOS);
        return orderList;
    }
    @RequestMapping(value = "/NotPayed",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public OrderList getNotPayedOrders(@RequestParam("mail")String mail){
        OrderList orderList = new OrderList();
        List<Order> orders = orderRepository.findByUseridAndOrderState(mail,"未支付");
        List<OrderVO> orderVOS = new ArrayList<>();
        for (int i=0;i<orders.size();i++){
            OrderVO vo = new OrderVO(orders.get(i));
            orderVOS.add(vo);
        }
        orderList.setOrders(orderVOS);
        return orderList;
    }
    @RequestMapping(value = "/getIntegration",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getIntegration(@RequestParam("mail")String mail){
        Student student = studentRepository.findByMail(mail);
        int integration = student.getIngration();
        return integration;
    }

    @RequestMapping(value = "/checkAccountInfo",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public double checkAccountInfo(@RequestParam("username")String username,@RequestParam("password")String password,@RequestParam("payWord")String payWord){
        Account account =accountRepository.findByUsername(username);
        if (account.getPassword().equals(password)&&account.getPayword().equals(payWord)){
            //证明用户可用
            return account.getAccount();
        }else {
            return -1.0;
        }
    }
    @RequestMapping(value = "/payForTheOrderDis",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int payForTheOrderDis(@RequestParam("total")String total,@RequestParam("courseName")String courseName,@RequestParam("username")String username,@RequestParam("orderId")String orderId
    ,@RequestParam("needPay")String needPay,@RequestParam("mail")String mail){
        //进行支付后，设置订单的状态，设置userlog，改动account的值，修改用户的积分情况
        //支付时优先扣除积分
        if (!(Double.parseDouble(needPay)>0)){
            //只需要扣除积分
            Student student =studentRepository.findByMail(mail);
            student.setIngration(student.getIngration()-Integer.parseInt(total)*10+(int)(Double.parseDouble(needPay)*0.1));
            //只使用积分支付不生成消费信息
            //修改订单的状态
            Order order = orderRepository.findByOrderid(Integer.parseInt(orderId));
            order.setOrderState("已预订");
            orderRepository.save(order);
        }else {
            //使用积分之后,不够支付订单
            Student student = studentRepository.findByMail(mail);
            student.setIngration((int)(Double.parseDouble(needPay)*0.1));//重新获得积分
            Integer cost = student.getCost()+Integer.parseInt(total);
            student.setCost(student.getCost()+Integer.parseInt(total));
            if (student.isVipState()){
                student.setUserLevel(LevelUtil.getLevel(cost));
            }
            studentRepository.save(student);
            //生成消费记录
            UserAccountLog userAccountLog = new UserAccountLog();
            userAccountLog.setLogClass("消费");
            userAccountLog.setOrderId(Integer.parseInt(orderId));
            userAccountLog.setValue(Double.parseDouble(needPay));
            userAccountLog.setCourseName(courseName);
            userAccountLog.setUserId(mail);
            userAccountLogRepository.save(userAccountLog);
            //用户的账户扣除款项
            Account account = accountRepository.findByUsername(username);
            account.setAccount(account.getAccount()-Double.parseDouble(needPay));
            accountRepository.save(account);
            //设置订单的状态
            Order order = orderRepository.findByOrderid(Integer.parseInt(orderId));
            order.setOrderState("已预订");
            orderRepository.save(order);
        }
        return 0;
    }
    @RequestMapping(value = "/payForTheOrder",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int payForTheOrder(@RequestParam("total")String total,@RequestParam("courseName")String courseName,@RequestParam("username")String username,@RequestParam("orderId")String orderId
            ,@RequestParam("mail")String mail){
        //直接使用账户的金额进行支付，修改订单信息，修改账户金额，添加消费信息,为用户增加积分
        //用户的账户扣除款项
        Account account = accountRepository.findByUsername(username);
        account.setAccount(account.getAccount()-Integer.parseInt(total));
        accountRepository.save(account);
        //设置订单状态
        Order order = orderRepository.findByOrderid(Integer.parseInt(orderId));
        order.setOrderState("已预订");
        orderRepository.save(order);
        //为用户增加积分
        Student student =studentRepository.findByMail(mail);
        student.setIngration(student.getIngration()+(int)(Integer.parseInt(total)*0.1));
        Integer cost = student.getCost()+Integer.parseInt(total);
        student.setCost(student.getCost()+Integer.parseInt(total));
        if (student.isVipState()){
            student.setUserLevel(LevelUtil.getLevel(cost));
        }
        studentRepository.save(student);
        //生成用户的记录
        UserAccountLog userAccountLog = new UserAccountLog();
        userAccountLog.setLogClass("消费");
        userAccountLog.setOrderId(Integer.parseInt(orderId));
        userAccountLog.setValue(Integer.parseInt(total));
        userAccountLog.setCourseName(courseName);
        userAccountLog.setUserId(mail);
        userAccountLogRepository.save(userAccountLog);
        return 0;
    }


    @RequestMapping(value = "/CancelOrder",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CancelOrderInfo cancelOrderByUser(@RequestParam("orderId")String orderId, @RequestParam("mail")String mail){
        Order order = orderRepository.findByOrderid(Integer.parseInt(orderId));
        order.setOrderState("已退订");
        orderRepository.save(order);
        List<Order> orders = orderRepository.findByUserid(mail);//所有订单中是否存在该学生的课程和班级
        Student student = studentRepository.findByMail(mail);
        String courseName = order.getCourseName();
        Course course = courseRepository.findByCourseName(courseName);
        //获得课程信息
        Date date = course.getBeginDate();
        Date now = new Date();
        Integer dis = 0;
        if (now.before(date)){
            //用户自己退订，需要返还金额，扣除积分，同时将他从课程和班级中移出,同时生成useraccountLog
            Integer total = order.getTotal();//订单金额
            dis = total;
            //首先进行退款
            Account account = accountRepository.findByUsername(mail);
            account.setAccount(account.getAccount()+total);
            accountRepository.save(account);
        }
        //生成userlog
        UserAccountLog userAccountLog = new UserAccountLog();
        userAccountLog.setUserId(mail);
        userAccountLog.setOrderId(Integer.parseInt(orderId));
        userAccountLog.setLogClass("退款");
        userAccountLog.setValue(dis);
        userAccountLog.setCourseName(order.getCourseName());
        userAccountLogRepository.save(userAccountLog);
        //判断是否存在
        boolean exists = false;
        for (int i=0;i<orders.size();i++){
            if (orders.get(i).getOrderState().equals("已预订")&&orders.get(i).getCourseName().equals(order.getCourseName())){
                exists=true;
                break;
            }
        }
        if (!exists){
            //不存在的话，需要将该学生从课程中移出
            CourseStudents courseStudents = courseStudentsRepository.findByMailAndCourseId(mail,course.getCourseId());
            courseStudentsRepository.delete(courseStudents);
        }
        student.setIngration(student.getIngration()-(int)(dis*0.1));


        boolean claExists = false;
        for (int i=0;i<orders.size();i++){
            if (orders.get(i).getOrderState().equals("已预订")&&orders.get(i).getClassId().equals(order.getClassId())){
                claExists=true;
                break;
            }
        }
        if (!claExists){
            //不存在的话，需要将该学生从课程中移出
            ClassModel classModel = classModelRepository.findByClassId(order.getClassId());
            ClassModelStudents classModelStudents = classModelStudentsRepository.findByMailAndClassmodelId(mail,classModel.getClassId());
            classModelStudentsRepository.delete(classModelStudents);
        }
        studentRepository.save(student);
        CancelOrderInfo cancelOrderInfo = new CancelOrderInfo();
        cancelOrderInfo.setIntegration((int)(dis*0.1));
        cancelOrderInfo.setTotal(dis);
        return cancelOrderInfo;
    }
    @RequestMapping(value = "/Dis",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getDis(@RequestParam("mail")String mail){
        Student student = studentRepository.findByMail(mail);
        return LevelUtil.getDis(student.getUserLevel());
    }
}
