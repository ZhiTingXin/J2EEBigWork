package edu.nju.coursesystem.model;

import javax.persistence.*;

@Entity
@Table(name = "classModelStudents")
public class ClassModelStudents {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String mail;
    private Integer classmodelId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getClassmodelId() {
        return classmodelId;
    }

    public void setClassmodelId(Integer classmodelId) {
        this.classmodelId = classmodelId;
    }
}
