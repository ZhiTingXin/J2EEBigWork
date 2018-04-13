package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.Grade;
import edu.nju.coursesystem.VO.GradeList;
import edu.nju.coursesystem.model.CourseLog;
import edu.nju.coursesystem.model.CourseLogRepository;
import edu.nju.coursesystem.model.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "UserGradeInfo")
public class UserGradeInfoController {
    @Autowired
    private CourseLogRepository courseLogRepository;
    @Autowired
    private CourseRepository courseRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(Model model, @RequestParam("user")String user){
        //学生的成绩信息
        List<CourseLog> courseLogs = courseLogRepository.findByStudentId(user);
        System.out.println(courseLogs.size());
        GradeList gradeList = new GradeList();
        List<Grade> grades = new ArrayList<>();
        for(int i=0;i<courseLogs.size();i++){
            Grade grade = new Grade(courseRepository.findByCourseId(courseLogs.get(i).getCourseId()).getCourseName(),courseLogs.get(i).getTime(),courseLogs.get(i).getGrade());
            grades.add(grade);
        }
        gradeList.setGrades(grades);
        model.addAttribute("gradeList",gradeList);
        return "UserGradeInfo";
    }
}
