package com.aorez.service;

import com.aorez.pojo.Cls;
import com.aorez.pojo.PageBean;
import com.aorez.sql.ClassSQL;

import java.util.List;

public class ClassService {
    private ClassSQL classSQL = new ClassSQL();

    public PageBean<Cls> selectByConditionAndPage(int currentPage, int pageSize, Cls cls) {
        int begin = (currentPage - 1) * pageSize;
        int size = pageSize;
        List<Cls> rows = classSQL.selectByConditionAndPage(cls, begin, size);
        int totalCount = classSQL.selectTotalCountByCondition(cls);


        PageBean<Cls> pageBean = new PageBean<>();
        pageBean.setTotalCount(totalCount);
        pageBean.setRows(rows);

        return pageBean;
    }
}