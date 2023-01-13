package com.aorez.sql;

import com.aorez.pojo.Student;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentSql {
    public List<Student> selectAll() {
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from tb_student;";
            ResultSet resultSet = statement.executeQuery(sql);
            List<Student> list = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setSno(resultSet.getString("sno"));
                student.setName(resultSet.getString("name"));
                student.setSex(resultSet.getString("sex"));
                student.setAge(Integer.parseInt(resultSet.getString("age")));
                student.setGrade(resultSet.getString("grade"));
                student.setClassId(resultSet.getString("class_id"));
                list.add(student);
            }

            resultSet.close();
            statement.close();
            connectionInfo.free();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insert(Student student) {
        if (!student.valuesCheck()) {
            return false;
        }

        boolean b = false;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "insert into tb_student (sno, name, sex, age, grade, class_id) values (?,?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, student.getSno());
            preparedStatement.setString(index++, student.getName());
            preparedStatement.setString(index++, student.getSex());
            preparedStatement.setInt(index++, student.getAge());
            preparedStatement.setString(index++, student.getGrade());
            preparedStatement.setString(index++, student.getClassId());
            preparedStatement.executeUpdate();
            //PreparedStatement也要提交
            connection.commit();
            b = true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            b = false;
        }

        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionInfo.free();
        return b;
    }

    public boolean updateBySno(Student student) {
        if (!student.valuesCheck()) {
            return false;
        }
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update tb_student set name=?,sex=?,age=?,grade=?,class_id=? where sno=?";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, student.getName());
            preparedStatement.setString(index++, student.getSex());
            preparedStatement.setInt(index++, student.getAge());
            preparedStatement.setString(index++, student.getGrade());
            preparedStatement.setString(index++, student.getClassId());
            preparedStatement.setString(index++, student.getSno());
            preparedStatement.executeUpdate();
            connection.commit();
            b = true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            b = false;
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionInfo.free();
        return b;
    }

    public boolean deleteBySno(String sno) {
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "delete from tb_student where sno="+sno;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.commit();
            b = true;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            b = false;
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionInfo.free();
        return b;
    }

    public List<Student> selectByCondition(Student student) {
        List<Student> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
            String sql = "select * from tb_student " +
                    "where true";
            String grade = student.getGrade();
            String classId = student.getClassId();
            String sno = student.getSno();
            String name = student.getName();
            String sex = student.getSex();
            Integer age = student.getAge();
            if (grade!=null && grade.length()>0) {
                sql += " and grade='"+grade+"'";
            }
            if (classId!=null && classId.length()>0) {
                sql += " and class_id='"+classId+"'";
            }
            if (sno!=null && sno.length()>0) {
                sql += " and sno='"+sno+"'";
            }
            if (name!=null && name.length()>0) {
                sql += " and name='"+name+"'";
            }
            if (sex!=null && sex.length()>0) {
                sql += " and sex='"+sex+"'";
            }
            if (age!=null) {
                sql += " and age='"+age+"'";
            }
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student stu = new Student();
                stu.setSno(resultSet.getString("sno"));
                stu.setName(resultSet.getString("name"));
                stu.setSex(resultSet.getString("sex"));
                stu.setAge(Integer.parseInt(resultSet.getString("age")));
                stu.setGrade(resultSet.getString("grade"));
                stu.setClassId(resultSet.getString("class_id"));
                list.add(stu);
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

    public List<Student> selectByConditionAndPage(Student student, int begin, int size) {
        List<Student> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
            String sql = "select * from tb_student " +
                    "where true";
            String grade = student.getGrade();
            String classId = student.getClassId();
            String sno = student.getSno();
            String name = student.getName();
            String sex = student.getSex();
            Integer age = student.getAge();
            if (grade!=null && grade.length()>0) {
                sql += " and grade='"+grade+"'";
            }
            if (classId!=null && classId.length()>0) {
                sql += " and class_id='"+classId+"'";
            }
            if (sno!=null && sno.length()>0) {
                sql += " and sno='"+sno+"'";
            }
            if (name!=null && name.length()>0) {
                sql += " and name='"+name+"'";
            }
            if (sex!=null && sex.length()>0) {
                sql += " and sex='"+sex+"'";
            }
            if (age!=null) {
                sql += " and age='"+age+"'";
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student stu = new Student();
                stu.setSno(resultSet.getString("sno"));
                stu.setName(resultSet.getString("name"));
                stu.setSex(resultSet.getString("sex"));
                stu.setAge(Integer.parseInt(resultSet.getString("age")));
                stu.setGrade(resultSet.getString("grade"));
                stu.setClassId(resultSet.getString("class_id"));
                list.add(stu);
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

    public int selectTotalCount() {
        int totalCount = 0;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select count(*) from tb_student";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
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
        return totalCount;
    }

    public int selectTotalCountByCondition(Student student) {
        int totalCount = 0;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionInfo.getConn();
            String sql = "select count(*) from tb_student " +
                    "where true";
            String grade = student.getGrade();
            String classId = student.getClassId();
            String sno = student.getSno();
            String name = student.getName();
            String sex = student.getSex();
            Integer age = student.getAge();
            if (grade!=null && grade.length()>0) {
                sql += " and grade='"+grade+"'";
            }
            if (classId!=null && classId.length()>0) {
                sql += " and class_id='"+classId+"'";
            }
            if (sno!=null && sno.length()>0) {
                sql += " and sno='"+sno+"'";
            }
            if (name!=null && name.length()>0) {
                sql += " and name='"+name+"'";
            }
            if (sex!=null && sex.length()>0) {
                sql += " and sex='"+sex+"'";
            }
            if (age!=null) {
                sql += " and age='"+age+"'";
            }
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                totalCount = resultSet.getInt(1);
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
        return totalCount;
    }
}
