package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseStudentsRepository extends JpaRepository<CourseStudents,Integer> {
    List<CourseStudents> findByCourseId(Integer courseId);
    List<CourseStudents> findByMail(String mail);
    CourseStudents findByMailAndCourseId(String mail, Integer courseId);
}
