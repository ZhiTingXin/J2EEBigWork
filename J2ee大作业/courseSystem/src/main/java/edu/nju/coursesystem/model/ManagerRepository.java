package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager,String> {
    Manager findByUsername(String username);
}
