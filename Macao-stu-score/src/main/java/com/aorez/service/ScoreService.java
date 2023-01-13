package com.aorez.service;

import com.aorez.pojo.Course;
import com.aorez.pojo.PageBean;
import com.aorez.pojo.Score;
import com.aorez.pojo.Student;
import com.aorez.sql.CourseSQL;
import com.aorez.sql.ScoreSQL;

import java.util.List;

public class ScoreService {
    private CourseSQL courseSQL = new CourseSQL();
    private ScoreSQL scoreSQL = new ScoreSQL();

    public boolean insert(Score score) {
        if (!score.valueCheck()) {
            return false;
        }
        Course course = courseSQL.selectByCourseName(score.getCourseName());
        if (course == null) {
            return false;
        }
        score.setCourseId(course.getCourseId());
        return scoreSQL.insert(score);
    }

    public PageBean<Score> selectByConditionAndPage(int currentPage, String scoreOrdered, int pageSize, Score score) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        List<Score> rows = scoreSQL.selectByConditionAndPage(score, scoreOrdered, begin, size);
        int totalCount = scoreSQL.selectTotalCountByCondition(score);


        PageBean<Score> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(rows);

        return pageBean;
    }
}