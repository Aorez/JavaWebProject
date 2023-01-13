package com.aorez.controller;

import com.aorez.model.Cls;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClsController {
    public List<Cls> analyse(String grade, float sec) {
        List<Cls> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select grade, sec, course, cls, count(*) as 'num',\n" +
                    "count(case when score=100 then 1 end) as '100',\n" +
                    "count(case when score between 90 and 99 then 1 end) as '90-99',\n" +
                    "count(case when score between 80 and 89 then 1 end) as '80-89',\n" +
                    "count(case when score between 70 and 79 then 1 end) as '70-79',\n" +
                    "count(case when score between 60 and 69 then 1 end) as '60-69',\n" +
                    "count(case when score between 50 and 59 then 1 end) as '50-59',\n" +
                    "count(case when score between 40 and 49 then 1 end) as '40-49',\n" +
                    "count(case when score<40 then 1 end) as '39',\n" +
                    "round(avg(score),3) as 'avg',\n" +
                    "round(count(case when score>59 then 1 end) / count(*) * 100, 3) as 'pass',\n" +
                    "round(count(case when score>89 then 1 end) / count(*) * 100, 3) as 'exc',\n" +
                    "max(score) as 'max',\n" +
                    "min(score) as 'min'\n" +
                    "from stu\n" +
                    "where grade=? and sec=?\n" +
                    "group by grade, sec, course, cls\n" +
                    "order by course, avg desc, pass desc, exc desc, cls";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, grade);
            preparedStatement.setFloat(2, sec);
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                Cls cls = new Cls();
                cls.setGrade(resultSet.getString("grade"));
                cls.setSec(resultSet.getFloat("sec"));

                cls.setCourse(resultSet.getString("course"));
                cls.setCls(resultSet.getInt("cls"));
                cls.setNum(resultSet.getInt("num"));
                int[] count = new int[8];
                for (int i = 0; i < 8; i++) {
                    count[i] = resultSet.getInt(5 + i);
                }
                cls.setCount(count);
                cls.setAvg(resultSet.getFloat("avg"));
                cls.setPass(resultSet.getFloat("pass"));
                cls.setExc(resultSet.getFloat("exc"));
                cls.setMax(resultSet.getFloat("max"));
                cls.setMin(resultSet.getFloat("min"));

                list.add(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionInfo.free();
        return list;
    }
}
