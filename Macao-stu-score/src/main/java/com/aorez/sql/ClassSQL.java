package com.aorez.sql;

import com.aorez.pojo.Cls;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassSQL {
    public List<Cls> selectAll() {
        List<Cls> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
//            String sql = SqlFileReader.readSqlFile("ClassSQL", "selectAll");
            String sql = "select tc1.grade, tc1.class_id, tc1.classroom_id, tc1.headmaster_id, tt.name,\n" +
                    "       (select count(sno)\n" +
                    "        from tb_class tc2 join tb_student ts on tc2.class_id = ts.class_id\n" +
                    "        where tc1.class_id = tc2.class_id\n" +
                    "        group by tc2.class_id\n" +
                    "       ) student_num\n" +
                    "from tb_class tc1 join tb_teacher tt on tt.teacher_id = tc1.headmaster_id\n" +
                    "where true";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            list = new ArrayList<>();
            while (resultSet.next()) {
                Cls cls = new Cls();
                cls.setGrade(resultSet.getString("grade"));
                cls.setClassId(resultSet.getString("class_id"));
                cls.setClassroomId(resultSet.getString("classroom_id"));
                cls.setHeadmasterId(resultSet.getString("headmaster_id"));
                cls.setHeadmasterName(resultSet.getString("name"));
                cls.setStudentNum(resultSet.getInt("student_num"));
                list.add(cls);
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

    public boolean insert(Cls cls) {
        if (!cls.valueCheck()) {
            return false;
        }
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "insert into tb_class (grade, class_id, classroom_id, headmaster_id)  values (?,?,?,?);";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, cls.getGrade());
            preparedStatement.setString(index++, cls.getClassId());
            preparedStatement.setString(index++, cls.getClassroomId());
            preparedStatement.setString(index++, cls.getHeadmasterId());
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

    public boolean updateByClassId(Cls cls) {
        if (!cls.valueCheck()) {
            return false;
        }
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "update tb_class set grade=?, classroom_id=?, headmaster_id=? where class_id=?;";
            preparedStatement = connection.prepareStatement(sql);
            int index = 1;
            preparedStatement.setString(index++, cls.getGrade());
            preparedStatement.setString(index++, cls.getClassroomId());
            preparedStatement.setString(index++, cls.getHeadmasterId());
            preparedStatement.setString(index++, cls.getClassId());
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

    public boolean deleteByClassId(String classId) {
        boolean b;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            String sql = "delete from tb_class where class_id="+classId;
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

    public List<Cls> selectByCondition(Cls cls) {
        List<Cls> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
            String sql = "select tc1.grade, tc1.class_id, tc1.classroom_id, tc1.headmaster_id, tt.name,\n" +
                    "       (select count(sno)\n" +
                    "        from tb_class tc2 join tb_student ts on tc2.class_id = ts.class_id\n" +
                    "        where tc1.class_id = tc2.class_id\n" +
                    "        group by tc2.class_id\n" +
                    "       ) student_num\n" +
                    "from tb_class tc1 join tb_teacher tt on tt.teacher_id = tc1.headmaster_id\n" +
                    "where true";
            String classId = cls.getClassId();
            String grade = cls.getGrade();
            String classroomId = cls.getClassroomId();
            String headmasterId = cls.getHeadmasterId();
            String headmasterName = cls.getHeadmasterName();

            if (classId!=null && classId.length()>0) {
                sql += " and tc1.class_id='"+classId+"'";
            }
            if (grade!=null && grade.length()>0) {
                sql += " and tc1.grade='"+grade+"'";
            }
            if (classroomId!=null && classroomId.length()>0) {
                sql += " and tc1.classroom_id='"+classroomId+"'";
            }
            if (headmasterId!=null && headmasterId.length()>0) {
                sql += " and tc1.headmaster_id='"+headmasterId+"'";
            }
            if (headmasterName!=null && headmasterName.length()>0) {
                sql += " and tt.name='"+headmasterName+"'";
            }
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cls cs = new Cls();
                cs.setGrade(resultSet.getString("grade"));
                cs.setClassId(resultSet.getString("class_id"));
                cs.setClassroomId(resultSet.getString("classroom_id"));
                cs.setHeadmasterId(resultSet.getString("headmaster_id"));
                cs.setHeadmasterName(resultSet.getString("name"));
                cs.setStudentNum(resultSet.getInt("student_num"));
                list.add(cs);
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
    public List<Cls> selectByConditionAndPage(Cls cls, int begin, int size) {
        List<Cls> list = null;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            list = new ArrayList<>();
            connection = connectionInfo.getConn();
            String sql = "select tc1.grade, tc1.class_id, tc1.classroom_id, tc1.headmaster_id, tt.name,\n" +
                    "       (select count(sno)\n" +
                    "        from tb_class tc2 join tb_student ts on tc2.class_id = ts.class_id\n" +
                    "        where tc1.class_id = tc2.class_id\n" +
                    "        group by tc2.class_id\n" +
                    "       ) student_num\n" +
                    "from tb_class tc1 join tb_teacher tt on tt.teacher_id = tc1.headmaster_id\n" +
                    "where true";
            String classId = cls.getClassId();
            String grade = cls.getGrade();
            String classroomId = cls.getClassroomId();
            String headmasterId = cls.getHeadmasterId();
            String headmasterName = cls.getHeadmasterName();

            if (classId!=null && classId.length()>0) {
                sql += " and tc1.class_id='"+classId+"'";
            }
            if (grade!=null && grade.length()>0) {
                sql += " and tc1.grade='"+grade+"'";
            }
            if (classroomId!=null && classroomId.length()>0) {
                sql += " and tc1.classroom_id='"+classroomId+"'";
            }
            if (headmasterId!=null && headmasterId.length()>0) {
                sql += " and tc1.headmaster_id='"+headmasterId+"'";
            }
            if (headmasterName!=null && headmasterName.length()>0) {
                sql += " and tt.name='"+headmasterName+"'";
            }
            sql += " limit ?,?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, begin);
            preparedStatement.setInt(2, size);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Cls cs = new Cls();
                cs.setGrade(resultSet.getString("grade"));
                cs.setClassId(resultSet.getString("class_id"));
                cs.setClassroomId(resultSet.getString("classroom_id"));
                cs.setHeadmasterId(resultSet.getString("headmaster_id"));
                cs.setHeadmasterName(resultSet.getString("name"));
                cs.setStudentNum(resultSet.getInt("student_num"));
                list.add(cs);
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
    public int selectTotalCountByCondition(Cls cls) {
        int totalCount = 0;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionInfo.getConn();
            String sql = "select count(*) " +
                    "from tb_class tc1 join tb_teacher tt on tt.teacher_id = tc1.headmaster_id " +
                    "where true";
            String classId = cls.getClassId();
            String grade = cls.getGrade();
            String classroomId = cls.getClassroomId();
            String headmasterId = cls.getHeadmasterId();
            String headmasterName = cls.getHeadmasterName();

            if (classId!=null && classId.length()>0) {
                sql += " and tc1.class_id='"+classId+"'";
            }
            if (grade!=null && grade.length()>0) {
                sql += " and tc1.grade='"+grade+"'";
            }
            if (classroomId!=null && classroomId.length()>0) {
                sql += " and tc1.classroom_id='"+classroomId+"'";
            }
            if (headmasterId!=null && headmasterId.length()>0) {
                sql += " and tc1.headmaster_id='"+headmasterId+"'";
            }
            if (headmasterName!=null && headmasterName.length()>0) {
                sql += " and tt.name='"+headmasterName+"'";
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
