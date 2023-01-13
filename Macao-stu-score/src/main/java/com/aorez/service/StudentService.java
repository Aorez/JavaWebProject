package com.aorez.service;

import com.aorez.pojo.PageBean;
import com.aorez.pojo.Student;
import com.aorez.sql.StudentSql;

import java.util.List;

public class StudentService {
    private StudentSql studentSql = new StudentSql();

    public PageBean<Student> selectByConditionAndPage(int currentPage, int pageSize, Student student) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        List<Student> rows = studentSql.selectByConditionAndPage(student, begin, size);
        int totalCount = studentSql.selectTotalCountByCondition(student);


        PageBean<Student> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(rows);

        return pageBean;
    }
}