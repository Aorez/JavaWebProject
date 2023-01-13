package com.aorez.sql;

import com.aorez.pojo.Teacher;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherSQL {
    public List<Teacher> selectAll() {
        List<Teacher> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select grade, teacher_id, name, sex, age, course_name from tb_teacher join tb_course tc on tc.course_id = tb_teacher.course_id;";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setTeacherId(resultSet.getString("teacher_id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setSex(resultSet.getString("sex"));
                teacher.setAge(Integer.parseInt(resultSet.getString("age")));
                teacher.setCourseName(resultSet.getString("course_name"));
                teacher.setGrade(resultSet.getString("grade"));
                list.add(teacher);
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
        return list;
    }

    public boolean insert(Teacher teacher) {
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "insert into tb_teacher (teacher_id, name, sex, age, course_id)  values (?,?,?,?,?);";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, teacher.getTeacherId());
            preparedStatement.setString(index++, teacher.getName());
            preparedStatement.setString(index++, teacher.getSex());
            preparedStatement.setInt(index++, teacher.getAge());
            preparedStatement.setInt(index++, teacher.getCourseId());
            preparedStatement.executeUpdate();
            connection.commit();
            b = true;
        } catch (SQLException e) {
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

    public boolean updateByTeacherId(Teacher teacher) {
        if (!teacher.valuesCheck()) {
            return false;
        }
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update tb_teacher set name=?, sex=?, age=?, course_id=? where teacher_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, teacher.getName());
            preparedStatement.setString(index++, teacher.getSex());
            preparedStatement.setInt(index++, teacher.getAge());
            preparedStatement.setInt(index++, teacher.getCourseId());
            preparedStatement.setString(index++, teacher.getTeacherId());
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

    public boolean deleteByTeacherId(String teacherId) {
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "delete from tb_teacher where teacher_id="+teacherId;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            connection.commit();
            b = true;
        } catch (Exception e) {
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

    public List<Teacher> selectByCondition(Teacher teacher) {
        List<Teacher> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
            String sql = "select grade, teacher_id, name, sex, age, course_name from tb_teacher join tb_course tc on tc.course_id = tb_teacher.course_id " +
                    "where true";
            String grade = teacher.getGrade();
            String teacherId = teacher.getTeacherId();
            String name = teacher.getName();
            String sex = teacher.getSex();
            Integer age = teacher.getAge();
            String courseName = teacher.getCourseName();
            if (grade!=null && grade.length()>0) {
                sql += " and grade='"+grade+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and teacher_id='"+teacherId+"'";
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
            if (courseName!=null && courseName.length()>0) {
                sql += " and course_name='"+courseName+"'";
            }
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher tch = new Teacher();
                tch.setTeacherId(resultSet.getString("teacher_id"));
                tch.setName(resultSet.getString("name"));
                tch.setSex(resultSet.getString("sex"));
                tch.setAge(Integer.parseInt(resultSet.getString("age")));
                tch.setCourseName(resultSet.getString("course_name"));
                tch.setGrade(resultSet.getString("grade"));
                list.add(tch);
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

    public List<String> selectTeacherIdByCourseId(int courseId) {
        List<String> list = new ArrayList<>();
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select teacher_id from tb_teacher where course_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, courseId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("teacher_id"));
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
        return list;
    }

    public List<Teacher> selectByConditionAndPage(Teacher teacher, int begin, int size) {
        List<Teacher> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
            String sql = "select grade, teacher_id, name, sex, age, course_name from tb_teacher join tb_course tc on tc.course_id = tb_teacher.course_id " +
                    "where true";
            String grade = teacher.getGrade();
            String teacherId = teacher.getTeacherId();
            String name = teacher.getName();
            String sex = teacher.getSex();
            Integer age = teacher.getAge();
            String courseName = teacher.getCourseName();
            if (grade!=null && grade.length()>0) {
                sql += " and grade='"+grade+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and teacher_id='"+teacherId+"'";
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
            if (courseName!=null && courseName.length()>0) {
                sql += " and course_name='"+courseName+"'";
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher tch = new Teacher();
                tch.setTeacherId(resultSet.getString("teacher_id"));
                tch.setName(resultSet.getString("name"));
                tch.setSex(resultSet.getString("sex"));
                tch.setAge(Integer.parseInt(resultSet.getString("age")));
                tch.setCourseName(resultSet.getString("course_name"));
                tch.setGrade(resultSet.getString("grade"));
                list.add(tch);
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

    public int selectTotalCountByCondition(Teacher teacher) {
        int totalCount = 0;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionInfo.getConn();
            String sql = "select count(*) from tb_teacher join tb_course tc on tc.course_id = tb_teacher.course_id " +
                    "where true";
            String grade = teacher.getGrade();
            String teacherId = teacher.getTeacherId();
            String name = teacher.getName();
            String sex = teacher.getSex();
            Integer age = teacher.getAge();
            String courseName = teacher.getCourseName();
            if (grade!=null && grade.length()>0) {
                sql += " and grade='"+grade+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and teacher_id='"+teacherId+"'";
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
            if (courseName!=null && courseName.length()>0) {
                sql += " and course_name='"+courseName+"'";
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
