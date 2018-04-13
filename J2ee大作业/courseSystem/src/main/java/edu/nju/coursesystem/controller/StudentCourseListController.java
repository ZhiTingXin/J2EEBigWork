package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.CourseList;
import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/UserCourseInfo")
public class StudentCourseListController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseStudentsRepository courseStudentsRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model,@RequestParam("mail")String mail){
        List<CourseStudents> courseStudents = courseStudentsRepository.findByMail(mail);
        List<Course> courses1 = new ArrayList<>();
        for (int i=0;i<courseStudents.size();i++){
            Course course = courseRepository.findByCourseId(courseStudents.get(i).getCourseId());
            courses1.add(course);
        }
        CourseList courseList = new CourseList();
        courseList.setCourses(courses1);
        model.addAttribute("courseList",courseList);
        return "StudentCourseList";
    }
}
