package com.aorez.sql;

import com.aorez.pojo.User;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserSQL {

    /**
     * 根据用户名和密码对用户表进行选择
     * @param username
     * @param password
     * @return
     */
    public User select(String username, String password) {
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        try {
            Statement statement = connection.createStatement();
//            String sql = SqlFileReader.readSqlFile(this.getClass().getSimpleName(), Thread.currentThread().getStackTrace()[1].getMethodName());
            String sql = "select * from tb_user where username='usernameUsername' and password='passwordPassword';";
            sql = sql.replaceFirst("usernameUsername", username);
            sql = sql.replaceFirst("passwordPassword", password);
//            String sql = "select * from tb_user where username='" + username + "' and password='" + password + "';";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);

                resultSet.close();
                statement.close();
                connectionInfo.free();
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询所有用户
     * @return
     */
    public List<User> selectAll() {
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from tb_user;";
            ResultSet resultSet = statement.executeQuery(sql);
            List<User> list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setId(resultSet.getInt("id"));
                list.add(user);
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

    /**
     * 根据id更新用户
     * @param user
     * @return
     */
    public boolean updateById(User user) {
        //判断是否有空的值
        if (user.hasNull()) {
            return false;
        }

        boolean b = false;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        //创建事务
        //用户名重复时回滚
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "update tb_user set username='"+user.getUsername()+"',password='"+user.getPassword()+"' where id="+user.getId()+";";
            connection.setAutoCommit(false);
            statement.executeUpdate(sql);
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

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionInfo.free();
        return b;
    }

    public boolean deleteById(int id) {
        boolean b = false;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        //创建事务
        //用户名重复时回滚
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "delete from tb_user where id=" + id;
            connection.setAutoCommit(false);
            statement.executeUpdate(sql);
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

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionInfo.free();
        return b;
    }

    public boolean insert(User user) {
        if (user.hasNull()) {
            return false;
        }

        boolean b = false;
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection connection = connectionInfo.getConn();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String sql = "insert into tb_user values (null,'"+user.getUsername()+"','"+user.getPassword()+"');";
            connection.setAutoCommit(false);
            statement.executeUpdate(sql);
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

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        connectionInfo.free();
        return b;
    }
}
