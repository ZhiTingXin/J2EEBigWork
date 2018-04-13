package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.CourseList;
import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/InstitutionMainPage")
public class InstitutionMainPageController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ClassModelRepository classModelRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model,@RequestParam("code")String code){
        CourseList courses = new CourseList();
        Institution institution = institutionRepository.findByOrgid(code);
        List<Course> coursespo = courseRepository.findByInstitute(institution.getInstitutionName());
        courses.setCourses(coursespo);
        model.addAttribute("courseList",courses);
        return "InstitutionMainPage";
    }
    @RequestMapping(value = "/on",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CourseList getOnCourse(@RequestParam("code")String code){
        Institution institution = institutionRepository.findByOrgid(code);
        CourseList courseList = new CourseList();
        List<Course> courses = courseRepository.findByInstituteAndCourseState(institution.getInstitutionName(),"正在进行");
        System.out.println(courses.size());
        courseList.setCourses(courses);

        return courseList;
    }
    @RequestMapping(value = "/off",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CourseList getOffCourse(@RequestParam("code")String code){
        Institution institution = institutionRepository.findByOrgid(code);
        CourseList courseList = new CourseList();
        List<Course> courses = courseRepository.findByInstituteAndCourseState(institution.getInstitutionName(),"已结束");
        courseList.setCourses(courses);
        return courseList;
    }
    @RequestMapping(value = "/noBegin",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public CourseList getNoBeginCourse(@RequestParam("code")String code){
        Institution institution = institutionRepository.findByOrgid(code);
        System.out.println(code);
        CourseList courseList = new CourseList();
        List<Course> courses = courseRepository.findByInstituteAndCourseState(institution.getInstitutionName(),"尚未开始");
        courseList.setCourses(courses);
        System.out.println(courses.size());
        return courseList;
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
                              @RequestParam("num")String num){

        //订单保存之后，修改class中的剩余人数和course中的剩余人数的值
        Course course =courseRepository.findByCourseName(courseName);
        //需要将用户和course和class进行绑定
        Integer lastNum = course.getLastNum();
        course.setLastNum(lastNum-Integer.parseInt(num));
        ClassModel classModel = classModelRepository.findByClassNameAndAndCourseId(className,course.getCourseId());
        Integer last = classModel.getLastNum();
        classModel.setLastNum(last-Integer.parseInt(num));
        classModelRepository.save(classModel);
        return 0;
    }


}
