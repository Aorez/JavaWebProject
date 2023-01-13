package com.aorez.sql;

import com.aorez.pojo.Course;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseSQL {
    public Course selectByCourseName(String courseName) {
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Course course = null;
        String sql = "select * from tb_course where course_name=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, courseName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                course = new Course();
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setGrade(resultSet.getString("grade"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
        return course;
    }

    public List<Course> selectAll() {
        List<Course> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            String sql = SqlFileReader.readSqlFile("CourseSQL", "selectAll");
            String sql = "select tb_course.course_id, tb_course.course_name, tb_course.grade, tt.teacher_id, tt.name\n" +
                    "from tb_course left join tb_teacher tt on tb_course.course_id = tt.course_id\n" +
                    "where true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                Course course = new Course();
                course.setGrade(resultSet.getString("grade"));
                course.setCourseId(resultSet.getInt("course_id"));
                course.setCourseName(resultSet.getString("course_name"));
                course.setTeacherId(resultSet.getString("teacher_id"));
                course.setTeacherName(resultSet.getString("name"));
                list.add(course);
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

    public boolean insert(Course course) {
        if (!course.valueCheck()) {
            return false;
        }
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "insert into tb_course (course_id, course_name, grade)  values (null,?,?);";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, course.getCourseName());
            preparedStatement.setString(index++, course.getGrade());
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

    public boolean updateByCourseId(Course course) {
        if (!course.valueCheck()) {
            return false;
        }
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update tb_course set grade=?, course_name=? where course_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, course.getGrade());
            preparedStatement.setString(index++, course.getCourseName());
            preparedStatement.setInt(index++, course.getCourseId());
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

    public boolean deleteByCourseId(String courseId) {
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "delete from tb_course where course_id="+courseId;
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

    public List<Course> selectByCondition(Course course) {
        List<Course> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
//            String sql = SqlFileReader.readSqlFile("CourseSQL", "selectAll");
            String sql = "select tb_course.course_id, tb_course.course_name, tb_course.grade, tt.teacher_id, tt.name\n" +
                    "from tb_course left join tb_teacher tt on tb_course.course_id = tt.course_id\n" +
                    "where true";
            String courseName = course.getCourseName();
            String grade = course.getGrade();
            String teacherId = course.getTeacherId();
            String teacherName = course.getTeacherName();
            if (courseName!=null && courseName.length()>0) {
                sql += " and tb_course.course_name='"+courseName+"'";
            }
            if (grade!=null && grade.length()>0) {
                sql += " and tb_course.grade='"+grade+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and tt.teacher_id='"+teacherId+"'";
            }
            if (teacherName!=null && teacherName.length()>0) {
                sql += " and tt.name='"+teacherName+"'";
            }
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course crs = new Course();
                crs.setGrade(resultSet.getString("grade"));
                crs.setCourseId(resultSet.getInt("course_id"));
                crs.setCourseName(resultSet.getString("course_name"));
                crs.setTeacherId(resultSet.getString("teacher_id"));
                crs.setTeacherName(resultSet.getString("name"));
                list.add(crs);
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
    public List<Course> selectByConditionAndPage(Course course, int begin, int size) {
        List<Course> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
//            String sql = SqlFileReader.readSqlFile("CourseSQL", "selectAll");
            String sql = "select tb_course.course_id, tb_course.course_name, tb_course.grade, tt.teacher_id, tt.name\n" +
                    "from tb_course left join tb_teacher tt on tb_course.course_id = tt.course_id\n" +
                    "where true";
            String courseName = course.getCourseName();
            String grade = course.getGrade();
            String teacherId = course.getTeacherId();
            String teacherName = course.getTeacherName();
            if (courseName!=null && courseName.length()>0) {
                sql += " and tb_course.course_name='"+courseName+"'";
            }
            if (grade!=null && grade.length()>0) {
                sql += " and tb_course.grade='"+grade+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and tt.teacher_id='"+teacherId+"'";
            }
            if (teacherName!=null && teacherName.length()>0) {
                sql += " and tt.name='"+teacherName+"'";
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course crs = new Course();
                crs.setGrade(resultSet.getString("grade"));
                crs.setCourseId(resultSet.getInt("course_id"));
                crs.setCourseName(resultSet.getString("course_name"));
                crs.setTeacherId(resultSet.getString("teacher_id"));
                crs.setTeacherName(resultSet.getString("name"));
                list.add(crs);
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
    public int selectTotalCountByCondition(Course course) {
        int totalCount = 0;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionInfo.getConn();
            String sql = "select count(*) " +
                    "from tb_course left join tb_teacher tt on tb_course.course_id = tt.course_id " +
                    "where true";
            String courseName = course.getCourseName();
            String grade = course.getGrade();
            String teacherId = course.getTeacherId();
            String teacherName = course.getTeacherName();
            if (courseName!=null && courseName.length()>0) {
                sql += " and tb_course.course_name='"+courseName+"'";
            }
            if (grade!=null && grade.length()>0) {
                sql += " and tb_course.grade='"+grade+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and tt.teacher_id='"+teacherId+"'";
            }
            if (teacherName!=null && teacherName.length()>0) {
                sql += " and tt.name='"+teacherName+"'";
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
