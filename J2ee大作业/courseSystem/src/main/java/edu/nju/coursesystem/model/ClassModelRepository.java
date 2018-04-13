package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassModelRepository extends JpaRepository<ClassModel,Integer> {
    List<ClassModel> findByCourseId(Integer courseId);
    ClassModel findByClassNameAndAndCourseId(String className,Integer courseId);
    ClassModel findByClassId(Integer classId);
}
