package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.ClassList;
import edu.nju.coursesystem.VO.CourseList;
import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;


@Controller
@RequestMapping(value = "/MainPage")
public class MainPageController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ClassModelRepository classModelRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassModelStudentsRepository classModelStudentsRepository;
    @Autowired
    private CourseStudentsRepository courseStudentsRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model){
        CourseList courses = new CourseList();
        List<Course> coursespo = courseRepository.findAll();
        courses.setCourses(coursespo);
        model.addAttribute("courseList",courses);
        return "MainPage";
    }
    @RequestMapping(value = "/on",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CourseList getOnCourse(){
        CourseList courseList = new CourseList();
        List<Course> courses = courseRepository.findByCourseState("正在进行");
        courseList.setCourses(courses);
        return courseList;
    }
    @RequestMapping(value = "/off",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CourseList getOffCourse(){
        CourseList courseList = new CourseList();
        List<Course> courses = courseRepository.findByCourseState("已结束");
        courseList.setCourses(courses);
        return courseList;
    }
    @RequestMapping(value = "/noBegin",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CourseList getNoBeginCourse(){
        CourseList courseList = new CourseList();
        List<Course> courses = courseRepository.findByCourseState("尚未开始");
        courseList.setCourses(courses);
        return courseList;
    }
    @RequestMapping(value = "/getClass",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ClassList getClassList(@RequestParam("courseName")String name){
        //返回班级信息
        ClassList classList = new ClassList();
        Course course = courseRepository.findByCourseName(name);
        List<ClassModel> classModels = classModelRepository.findByCourseId(course.getCourseId());
        classList.setClassModels(classModels);
        return classList;
    }
    @RequestMapping(value = "/getClassInfo",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ClassModel getClassInfo(@RequestParam("className")String className,@RequestParam("courseName")String courseName){
        Course course = courseRepository.findByCourseName(courseName);
        ClassModel classModel = classModelRepository.findByClassNameAndAndCourseId(className,course.getCourseId());
        return classModel;
    }
    @RequestMapping(value = "/generateOrder1",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int generateOrder1(@RequestParam("courseName")String courseName,@RequestParam("className")String className,
                              @RequestParam("num")String num,@RequestParam("userId")String userId,@RequestParam("total")String total){
        Order order = new Order();
        order.setUserid(userId);
        order.setCourseName(courseName);
        order.setOrderClass("选班");
        java.util.Date date = new java.util.Date();
        order.setOrderDate(new Timestamp(date.getTime()));
        order.setClassNum(Integer.parseInt(num));
        order.setTotal(Integer.parseInt(total));
        order.setOrderState("未支付");
        orderRepository.save(order);

        //订单保存之后，修改class中的剩余人数和course中的剩余人数的值
        Course course =courseRepository.findByCourseName(courseName);
        //需要将用户和course和class进行绑定
        Integer lastNum = course.getLastNum();
        course.setLastNum(lastNum-Integer.parseInt(num));
        ClassModel classModel = classModelRepository.findByClassNameAndAndCourseId(className,course.getCourseId());
        order.setClassId(classModel.getClassId());
        Integer last = classModel.getLastNum();
        classModel.setLastNum(last-Integer.parseInt(num));
        classModelRepository.save(classModel);
        List<ClassModelStudents> classModelStudents = classModelStudentsRepository.findByClassmodelId(classModel.getClassId());
        boolean is = false;
        for (int i =0;i<classModelStudents.size();i++){
            if (classModelStudents.get(i).getMail().equals(userId)){
                //该学生已经在班级中
                is=true;
                break;
            }
        }
        if (!is){
            //不在班级中，添加
            ClassModelStudents classModelStudents1 = new ClassModelStudents();
            classModelStudents1.setClassmodelId(classModel.getClassId());
            classModelStudents1.setMail(userId);
            classModelStudentsRepository.save(classModelStudents1);
        }
        List<CourseStudents> courseStudents = courseStudentsRepository.findByCourseId(course.getCourseId());
        boolean ex = false;
        for (int i=0;i<courseStudents.size();i++){
            if (courseStudents.get(i).getMail().equals(userId)){
                ex =true;
                break;
            }
        }
        if(!ex){
            CourseStudents courseStudents1 = new CourseStudents();
            courseStudents1.setCourseId(course.getCourseId());
            courseStudents1.setMail(userId);
            courseStudentsRepository.save(courseStudents1);
        }
        return 0;
    }
    @RequestMapping(value = "/getAvgPrice",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int getAvgPrice(@RequestParam("courseName")String courseName){
        Course course = courseRepository.findByCourseName(courseName);
        List<ClassModel> classModels = classModelRepository.findByCourseId(course.getCourseId());
        int total = 0;
        for (int i=0;i<classModels.size();i++){
            total =total+classModels.get(i).getPrice();
        }
        int result = total/classModels.size();
        return result;
    }

    @RequestMapping(value = "/generateOrder2",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int generateOrder2(@RequestParam("courseName")String courseName,
                              @RequestParam("num")String num,@RequestParam("userId")String userId,@RequestParam("total")String total) {
        Order order = new Order();
        order.setTotal(Integer.parseInt(total));
        order.setUserid(userId);
        order.setCourseName(courseName);
        order.setClassNum(Integer.parseInt(num));
        order.setOrderClass("不选班");
        order.setOrderState("未支付");
        order.setOrderDate(new Timestamp(System.currentTimeMillis()));
        //将订单提交
        //然后确认分班
        //订单保存之后，修改class中的剩余人数和course中的剩余人数的值
        Course course =courseRepository.findByCourseName(courseName);
        //需要将用户和course和class进行绑定
        Integer lastNum = course.getLastNum();
        course.setLastNum(lastNum-Integer.parseInt(num));//课程的剩余名额减少

        List<ClassModel> classModels = classModelRepository.findByCourseId(course.getCourseId());
        Integer maxLastNum = classModels.get(0).getLastNum();
        int a = 0;
        for (int i=1;i<classModels.size();i++){
            if (classModels.get(i).getLastNum()>maxLastNum){
                a=i;
            }
        }
        //分班算法，并没有实现提前两个月进行分班,而是进行最优分配，那个班人少，往哪个班里分
        ClassModel classModel = classModels.get(a);
        order.setClassId(classModel.getClassId());
        orderRepository.save(order);
        //提交order
        Integer last = classModel.getLastNum();
        classModel.setLastNum(last-Integer.parseInt(num));
        classModelRepository.save(classModel);
        List<ClassModelStudents> classModelStudents = classModelStudentsRepository.findByClassmodelId(classModel.getClassId());
        boolean is = false;
        for (int i =0;i<classModelStudents.size();i++){
            if (classModelStudents.get(i).getMail().equals(userId)){
                //该学生已经在班级中
                is=true;
                break;
            }
        }
        if (!is){
            //不在班级中，添加
            ClassModelStudents classModelStudents1 = new ClassModelStudents();
            classModelStudents1.setClassmodelId(classModel.getClassId());
            classModelStudents1.setMail(userId);
            classModelStudentsRepository.save(classModelStudents1);
        }
        List<CourseStudents> courseStudents = courseStudentsRepository.findByCourseId(course.getCourseId());
        boolean ex = false;
        for (int i=0;i<courseStudents.size();i++){
            if (courseStudents.get(i).getMail().equals(userId)){
                ex =true;
                break;
            }
        }
        if(!ex){
            CourseStudents courseStudents1 = new CourseStudents();
            courseStudents1.setCourseId(course.getCourseId());
            courseStudents1.setMail(userId);
            courseStudentsRepository.save(courseStudents1);
        }
        return 0;
    }

}
