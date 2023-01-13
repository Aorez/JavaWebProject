package com.aorez.service;

import com.aorez.pojo.Course;
import com.aorez.pojo.PageBean;
import com.aorez.pojo.Teacher;
import com.aorez.sql.CourseSQL;
import com.aorez.sql.TeacherSQL;

import java.util.List;

public class TeacherService {
    private TeacherSQL teacherSQL = new TeacherSQL();
    private CourseSQL courseSQL = new CourseSQL();

    public boolean insert(Teacher teacher) {
        if (!teacher.valuesCheck()) {
            return false;
        }
        //根据课程名称查询课程对象
        Course course = courseSQL.selectByCourseName(teacher.getCourseName());
        //如果查不到就是课程名称不合法
        if (course == null) {
            return false;
        }
        //两个年级不一样也不能添加成功
        if (!teacher.getGrade().equals(course.getGrade())) {
            return false;
        }
        teacher.setCourseId(course.getCourseId());
        return teacherSQL.insert(teacher);
    }

    public boolean updateByTeacherId(Teacher teacher) {
        if (!teacher.valuesCheck()) {
            return false;
        }
        Course course = courseSQL.selectByCourseName(teacher.getCourseName());
        if (course == null) {
            return false;
        }
        if (!teacher.getGrade().equals(course.getGrade())) {
            return false;
        }
        teacher.setCourseId(course.getCourseId());
        return teacherSQL.updateByTeacherId(teacher);
    }

    public PageBean<Teacher> selectByConditionAndPage(int currentPage, int pageSize, Teacher teacher) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        List<Teacher> rows = teacherSQL.selectByConditionAndPage(teacher, begin, size);
        int totalCount = teacherSQL.selectTotalCountByCondition(teacher);


        PageBean<Teacher> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(rows);

        return pageBean;
    }
}