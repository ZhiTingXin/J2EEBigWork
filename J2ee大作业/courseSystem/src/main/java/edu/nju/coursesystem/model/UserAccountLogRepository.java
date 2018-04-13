package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAccountLogRepository extends JpaRepository<UserAccountLog,Integer> {
    List<UserAccountLog> findByUserId(String userId);
}
