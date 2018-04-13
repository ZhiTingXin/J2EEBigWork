package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
    Student findByMail(String mail);
    Student findByConfirm(String confirm);
    Student findByUserName(String name);
}
