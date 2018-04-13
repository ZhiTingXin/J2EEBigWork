package edu.nju.coursesystem.VO;

public class InsList {
    private Integer insNum;
    private Integer courseNum;
    private Integer firstClass;//小于5
    private Integer secondClass;//5-10
    private Integer thirdClass;//第三梯队，课程数大于10

    public Integer getInsNum() {
        return insNum;
    }

    public void setInsNum(Integer insNum) {
        this.insNum = insNum;
    }

    public Integer getCourseNum() {
        return courseNum;
    }

    public void setCourseNum(Integer courseNum) {
        this.courseNum = courseNum;
    }

    public Integer getFirstClass() {
        return firstClass;
    }

    public void setFirstClass(Integer firstClass) {
        this.firstClass = firstClass;
    }

    public Integer getSecondClass() {
        return secondClass;
    }

    public void setSecondClass(Integer secondClass) {
        this.secondClass = secondClass;
    }

    public Integer getThirdClass() {
        return thirdClass;
    }

    public void setThirdClass(Integer thirdXClass) {
        this.thirdClass = thirdXClass;
    }
}
