package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseLogRepository extends JpaRepository<CourseLog,Integer> {
    List<CourseLog> findByCourseId(Integer courseId);
    List<CourseLog> findByStudentId(String studentId);
}
