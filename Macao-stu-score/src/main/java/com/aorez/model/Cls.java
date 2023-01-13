package com.aorez.model;

public class Cls {
    private String grade;
    private float sec;

    private int cls;
    private String course;
    private int num;
    private int[] count = new int[8];
    private float avg;
    private float pass;
    private float exc;
    private float max;
    private float min;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public float getSec() {
        return sec;
    }

    public void setSec(float sec) {
        this.sec = sec;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getCls() {
        return cls;
    }

    public void setCls(int cls) {
        this.cls = cls;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int[] getCount() {
        return count;
    }

    public void setCount(int[] count) {
        this.count = count;
    }

    public float getAvg() {
        return avg;
    }

    public void setAvg(float avg) {
        this.avg = avg;
    }

    public float getPass() {
        return pass;
    }

    public void setPass(float pass) {
        this.pass = pass;
    }

    public float getExc() {
        return exc;
    }

    public void setExc(float exc) {
        this.exc = exc;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

}
