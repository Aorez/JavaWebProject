package com.aorez.pojo;

/**
 * 班级表的类
 */
public class Cls {
    private String grade;
    private String classId;
    private String classroomId;
    private String headmasterId;
    private String headmasterName;
    private Integer studentNum;

    public boolean valueCheck() {
        if (grade==null || grade.length()<=0 || classId==null || classId.length()<=0 || classroomId==null || classroomId.length()<=0) {
            return false;
        }
        else {
            return true;
        }
    }

    public String getHeadmasterName() {
        return headmasterName;
    }

    public void setHeadmasterName(String headmasterName) {
        this.headmasterName = headmasterName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getHeadmasterId() {
        return headmasterId;
    }

    public void setHeadmasterId(String headmasterId) {
        this.headmasterId = headmasterId;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    @Override
    public String toString() {
        return "Cls{" +
                "grade='" + grade + '\'' +
                ", classId='" + classId + '\'' +
                ", classroomId='" + classroomId + '\'' +
                ", headmasterId='" + headmasterId + '\'' +
                ", studentNum=" + studentNum +
                '}';
    }
}
