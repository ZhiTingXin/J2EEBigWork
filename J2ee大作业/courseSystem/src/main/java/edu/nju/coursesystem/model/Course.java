package edu.nju.coursesystem.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "courseId")
    private Integer courseId;
    private String courseName;
    private Date beginDate;
    private Date endDate;
    private String institute;

    public void setNeedNum(Integer needNum) {
        this.needNum = needNum;
    }

    public void setLastNum(Integer lastNum) {
        this.lastNum = lastNum;
    }

    public void setLearntime(String learntime) {
        this.learntime = learntime;
    }

    private Integer needNum;
    private Integer lastNum;
    private String learntime;
    private String courseState;

    public String getCourseClass() {
        return courseClass;
    }

    public void setCourseClass(String courseClass) {
        this.courseClass = courseClass;
    }

    private String courseClass;

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    private String courseDescription;

    public String getCourseState(){
        return courseState;
    }

    public void setCourseState(String courseState) {
        this.courseState = courseState;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Integer getNeedNum() {
        return needNum;
    }

    public Integer getLastNum() {
        return lastNum;
    }

    public String getLearntime() {
        return learntime;
    }
}
