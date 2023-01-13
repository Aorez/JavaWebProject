package com.aorez.pojo;

/**
 * 学生表的类
 */
public class Student {
    private String sno;
    private String name;
    private String sex;
    private Integer age;
    private String grade;
    private String classId;

    public boolean valuesCheck() {
        if (sno==null || sno.length()<=0 || name==null || name.length()<=0 || age<=0 || age>=100 || classId==null || classId.length()<=0) {
            return false;
        }
        else {
            return true;
        }
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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

    @Override
    public String toString() {
        return "student{" +
                "sno='" + sno + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", grade='" + grade + '\'' +
                ", classId='" + classId + '\'' +
                '}';
    }
}
