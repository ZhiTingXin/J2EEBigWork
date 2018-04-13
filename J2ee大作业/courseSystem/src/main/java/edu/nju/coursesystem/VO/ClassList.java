package edu.nju.coursesystem.VO;

import edu.nju.coursesystem.model.ClassModel;

import java.util.List;

public class ClassList {
    public List<ClassModel> getClassModels() {
        return classModels;
    }

    public void setClassModels(List<ClassModel> classModels) {
        this.classModels = classModels;
    }

    private List<ClassModel> classModels;
}
