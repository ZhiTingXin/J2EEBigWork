package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.VO.StudentList;
import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/ClassLog")
public class ClassLogController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseLogRepository courseLogRepository;
    @Autowired
    private CourseStudentsRepository courseStudentsRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "ClassLog";
    }
    @RequestMapping(value = "/GetStudents",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public StudentList getStudents(@RequestParam("course")String courseName){
        Course course = courseRepository.findByCourseName(courseName);
        ;List<Student> students = new ArrayList<>();
        List<CourseStudents> courseStudents = courseStudentsRepository.findByCourseId(course.getCourseId());
        for (int i=0;i<courseStudents.size();i++){
            Student student = studentRepository.findByMail(courseStudents.get(i).getMail());
            students.add(student);
        }
        StudentList studentList = new StudentList();
        studentList.setStus(students);
        return studentList;
    }

    @RequestMapping(value = "/GenerateLog",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int generateLog(@RequestParam("courseName")String courseName,@RequestParam("time")String time,@RequestParam("stu")String students,
                           @RequestParam("state")String state,@RequestParam("grade")String grade){
        String[] stus = students.split("-");
        String[] grades = grade.split("-");
        String[] states = state.split("-");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i=0;i<stus.length;i++){
            CourseLog courseLog = new CourseLog();
            courseLog.setCourseId(courseRepository.findByCourseName(courseName).getCourseId());
            courseLog.setGrade(Integer.parseInt(grades[i]));
            try {
                courseLog.setTime(new Date(simpleDateFormat.parse(time).getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(states[i].equals("true")){
                courseLog.setSigned(true);
            }else {
                courseLog.setSigned(false);
            }
            Student student = studentRepository.findByUserName(stus[i]);
            courseLog.setStudentId(student.getMail());
            courseLogRepository.save(courseLog);
        }

        return 0;
    }

}
