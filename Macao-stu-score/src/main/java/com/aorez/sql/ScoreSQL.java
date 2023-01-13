package com.aorez.sql;

import com.aorez.pojo.Score;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
private String sno;
private String studentName;
private Integer courseId;
private String courseName;
private String teacherId;
private String teacherName;
private float score;
 */
public class ScoreSQL {
    public List<Score> selectAll() {
        List<Score> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            String sql = SqlFileReader.readSqlFile("ScoreSQL", "selectAll");
            String sql = "select tb_score.sno, ts.name student_name, tb_score.course_id, course_name, tb_score.teacher_id, tt.name teacher_name, score\n" +
                    "from tb_score join tb_student ts on ts.sno = tb_score.sno\n" +
                    "    join tb_course tc on tc.course_id = tb_score.course_id\n" +
                    "    join tb_teacher tt on tb_score.teacher_id = tt.teacher_id\n" +
                    "where true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                Score score = new Score();
                score.setSno(resultSet.getString("sno"));
                score.setStudentName(resultSet.getString("student_name"));
                score.setCourseId(resultSet.getInt("course_id"));
                score.setCourseName(resultSet.getString("course_name"));
                score.setTeacherId(resultSet.getString("teacher_id"));
                score.setTeacherName(resultSet.getString("teacher_name"));
                score.setScore(resultSet.getFloat("score"));
                list.add(score);
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

    public boolean insert(Score score) {
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "insert into tb_score (sno, course_id, teacher_id, score) values (?,?,?,?);";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, score.getSno());
            preparedStatement.setInt(index++, score.getCourseId());
            preparedStatement.setString(index++, score.getTeacherId());
            preparedStatement.setFloat(index++, score.getScore());
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

    public boolean updateBySnoAndCourseId(Score score) {
        if (!score.valueCheck()) {
            return false;
        }
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update tb_score set score=? where sno=? and course_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setFloat(index++, score.getScore());
            preparedStatement.setString(index++, score.getSno());
            preparedStatement.setInt(index++, score.getCourseId());
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

    public boolean deleteBySnoAndCourseId(String sno, String courseId) {
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "delete from tb_score where sno=? and course_id=?";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, sno);
            preparedStatement.setInt(index++, Integer.parseInt(courseId));
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

    public List<Score> selectByCondition(Score score, String scoreOrdered) {
        List<Score> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
//            String sql = SqlFileReader.readSqlFile("ScoreSQL", "selectAll");
            String sql = "select tb_score.sno, ts.name student_name, tb_score.course_id, course_name, tb_score.teacher_id, tt.name teacher_name, score\n" +
                    "from tb_score join tb_student ts on ts.sno = tb_score.sno\n" +
                    "    join tb_course tc on tc.course_id = tb_score.course_id\n" +
                    "    join tb_teacher tt on tb_score.teacher_id = tt.teacher_id\n" +
                    "where true";
            String sno = score.getSno();
            String studentName = score.getStudentName();
            String courseName = score.getCourseName();
            String teacherId = score.getTeacherId();
            String teacherName = score.getTeacherName();
            if (sno!=null && sno.length()>0) {
                sql += " and tb_score.sno='"+sno+"'";
            }
            if (studentName!=null && studentName.length()>0) {
                sql += " and ts.name='"+studentName+"'";
            }
            if (courseName!=null && courseName.length()>0) {
                sql += " and tc.course_name='"+courseName+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and tb_score.teacher_id='"+teacherId+"'";
            }
            if (teacherName!=null && teacherName.length()>0) {
                sql += " and tt.name='"+teacherName+"'";
            }
            //增加排序
            if ("up".equals(scoreOrdered)) {
                sql += " order by score asc";
            }
            else if ("down".equals(scoreOrdered)) {
                sql += " order by score desc";
            }
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Score scr = new Score();
                scr.setSno(resultSet.getString("sno"));
                scr.setStudentName(resultSet.getString("student_name"));
                scr.setCourseId(resultSet.getInt("course_id"));
                scr.setCourseName(resultSet.getString("course_name"));
                scr.setTeacherId(resultSet.getString("teacher_id"));
                scr.setTeacherName(resultSet.getString("teacher_name"));
                scr.setScore(resultSet.getFloat("score"));
                list.add(scr);
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
    public List<Score> selectByConditionAndPage(Score score, String scoreOrdered, int begin, int size) {
        List<Score> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
//            String sql = SqlFileReader.readSqlFile("ScoreSQL", "selectAll");
            String sql = "select tb_score.sno, ts.name student_name, tb_score.course_id, course_name, tb_score.teacher_id, tt.name teacher_name, score\n" +
                    "from tb_score join tb_student ts on ts.sno = tb_score.sno\n" +
                    "    join tb_course tc on tc.course_id = tb_score.course_id\n" +
                    "    join tb_teacher tt on tb_score.teacher_id = tt.teacher_id\n" +
                    "where true";
            String sno = score.getSno();
            String studentName = score.getStudentName();
            String courseName = score.getCourseName();
            String teacherId = score.getTeacherId();
            String teacherName = score.getTeacherName();
            if (sno!=null && sno.length()>0) {
                sql += " and tb_score.sno='"+sno+"'";
            }
            if (studentName!=null && studentName.length()>0) {
                sql += " and ts.name='"+studentName+"'";
            }
            if (courseName!=null && courseName.length()>0) {
                sql += " and tc.course_name='"+courseName+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and tb_score.teacher_id='"+teacherId+"'";
            }
            if (teacherName!=null && teacherName.length()>0) {
                sql += " and tt.name='"+teacherName+"'";
            }
            //增加排序
            if ("up".equals(scoreOrdered)) {
                sql += " order by score asc";
            }
            else if ("down".equals(scoreOrdered)) {
                sql += " order by score desc";
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Score scr = new Score();
                scr.setSno(resultSet.getString("sno"));
                scr.setStudentName(resultSet.getString("student_name"));
                scr.setCourseId(resultSet.getInt("course_id"));
                scr.setCourseName(resultSet.getString("course_name"));
                scr.setTeacherId(resultSet.getString("teacher_id"));
                scr.setTeacherName(resultSet.getString("teacher_name"));
                scr.setScore(resultSet.getFloat("score"));
                list.add(scr);
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
    public int selectTotalCountByCondition(Score score) {
        int totalCount = 0;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionInfo.getConn();
            String sql = "select count(*) " +
                    "from tb_score join tb_student ts on ts.sno = tb_score.sno " +
                    "    join tb_course tc on tc.course_id = tb_score.course_id " +
                    "    join tb_teacher tt on tb_score.teacher_id = tt.teacher_id " +
                    "where true";
            String sno = score.getSno();
            String studentName = score.getStudentName();
            String courseName = score.getCourseName();
            String teacherId = score.getTeacherId();
            String teacherName = score.getTeacherName();
            if (sno!=null && sno.length()>0) {
                sql += " and tb_score.sno='"+sno+"'";
            }
            if (studentName!=null && studentName.length()>0) {
                sql += " and ts.name='"+studentName+"'";
            }
            if (courseName!=null && courseName.length()>0) {
                sql += " and tc.course_name='"+courseName+"'";
            }
            if (teacherId!=null && teacherId.length()>0) {
                sql += " and tb_score.teacher_id='"+teacherId+"'";
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
