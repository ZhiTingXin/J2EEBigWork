package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course ,Integer>{
    //查找全部课程
    List<Course> findAll();
    //查找机构课程
    List<Course> findByInstitute(String institute);
    List<Course> findByInstituteAndCourseState(String institution,String courseState);
    Course findByCourseName(String courseName);
    List<Course> findByCourseState(String courseState);
    Course findByCourseId(Integer courseId);
}
