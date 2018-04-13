package edu.nju.coursesystem.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstitutionRepository extends JpaRepository<Institution,Integer>{
    Institution findByOrgid(String orgid);
    List<Institution> findByState(String state);
    List<Institution> findAll();
    Institution findByInstitutionName(String name);
}
