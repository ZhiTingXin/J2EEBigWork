package edu.nju.coursesystem.controller;

import edu.nju.coursesystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping(value = "/PlanRelease")
public class PlanReleaseController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ClassModelRepository classModelRepository;
    @Autowired
    private InstitutionRepository institutionRepository;
    @RequestMapping(method = RequestMethod.GET)
    public String getPage(){
        return "PlanRelease";
    }
    @RequestMapping(value = "/ReleaseCourse",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public int releaseCourse(@RequestParam("learnTime")String learnTime,@RequestParam("courseName")String name,@RequestParam("courseDiscription")String des,
    @RequestParam("beginDate")String date,@RequestParam("endDate")String endDate,@RequestParam("needNum")String needNum,
                             @RequestParam("classVOList")String classvo,@RequestParam("courseClass")String courseClass,@RequestParam("institution")String institution){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Institution institution1 = institutionRepository.findByOrgid(institution);
        Course course = new Course();
        course.setCourseName(name);
        course.setCourseClass(courseClass);
        course.setNeedNum(Integer.parseInt(needNum));
        course.setInstitute(institution1.getInstitutionName());
        try {

            course.setBeginDate(new Date(simpleDateFormat.parse(date).getTime()));
            course.setEndDate(new Date(simpleDateFormat.parse(endDate).getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        course.setCourseDescription(des);
        course.setNeedNum(Integer.parseInt(needNum));
        course.setLastNum(Integer.parseInt(needNum));
        java.util.Date  date1 = new java.util.Date();
        try {
            if (date1.before(simpleDateFormat.parse(date))){
                //尚未开始
                course.setCourseState("尚未开始");
            }else if (date1.after(simpleDateFormat.parse(endDate))){
                course.setCourseState("已结束");
            }else {
                course.setCourseState("正在进行");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("日期解析错误");
        }
        course.setLearntime(learnTime);
        //将得到的Course保存
        courseRepository.save(course);
        Course getCourse= courseRepository.findByCourseName(name);
        String[] strings = classvo.split("-");
        for (int i= 0;i<strings.length;i++){
            ClassModel classModel = new ClassModel();
            String[] strings1 = strings[i].split(",");
            classModel.setCourseId(getCourse.getCourseId());
            classModel.setClassName(strings1[0]);
            classModel.setNum(Integer.parseInt(strings1[1]));
            classModel.setLastNum(Integer.parseInt(strings1[1]));
            classModel.setPrice(Integer.parseInt(strings1[3]));
            classModel.setTeacher(strings1[2]);
            //将生成的class进行保存
            classModelRepository.save(classModel);
        }
        return 0;
    }

}
