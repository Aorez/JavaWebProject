package com.aorez.pojo;

/**
 * 教师表的类
 */
public class Teacher {
    private String grade;
    private String teacherId;
    private String name;
    private String sex;
    private Integer age;
    //数据库中是course_id，通过表的连接，导出courseName
    private String courseName;
    private Integer courseId;

    public boolean valuesCheck() {
        if (teacherId==null || teacherId.length()<=0 || name==null || name.length()<=0 || age<=0 || age>=100 || courseName==null || courseName.length()<=0) {
            return false;
        }
        else {
            return true;
        }
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
