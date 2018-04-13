package edu.nju.coursesystem.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "courseLog")
public class CourseLog {
    private Date time;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer courseLogId;
    private Integer courseId;
    private String studentId;
    //是否签到
    private boolean signed;
    //成绩
    private Integer grade;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public boolean isSigned() {
        return signed;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getCourseLogId() {
        return courseLogId;
    }

    public void setCourseLogId(Integer courseLogId) {
        this.courseLogId = courseLogId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

}
