package edu.nju.coursesystem.VO;

import edu.nju.coursesystem.model.CourseLog;
import edu.nju.coursesystem.model.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Grade {
    private String courseName;
    private String time;
    private Integer grade;

    public Grade (String courseName, Date date,Integer grade){
        this.courseName = courseName;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.time = simpleDateFormat.format(date);
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
