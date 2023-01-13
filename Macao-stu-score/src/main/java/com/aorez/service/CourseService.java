package com.aorez.service;

import com.aorez.pojo.Course;
import com.aorez.pojo.PageBean;
import com.aorez.sql.CourseSQL;

import java.util.List;

public class CourseService {
    private CourseSQL courseSQL = new CourseSQL();

    public PageBean<Course> selectByConditionAndPage(int currentPage, int pageSize, Course course) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        List<Course> rows = courseSQL.selectByConditionAndPage(course, begin, size);
        int totalCount = courseSQL.selectTotalCountByCondition(course);


        PageBean<Course> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(rows);

        return pageBean;
    }
}