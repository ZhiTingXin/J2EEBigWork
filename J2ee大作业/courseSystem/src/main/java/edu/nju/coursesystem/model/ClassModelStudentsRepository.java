package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClassModelStudentsRepository extends JpaRepository<ClassModelStudents,Integer> {
    List<ClassModelStudents> findByClassmodelId(Integer classModelId);
    List<ClassModelStudents> findByMail(String mail);
    ClassModelStudents findByMailAndClassmodelId(String mail,Integer classId);
}
