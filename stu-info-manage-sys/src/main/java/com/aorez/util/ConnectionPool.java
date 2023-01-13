package com.aorez.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 粗糙的连接池类
 */
public class ConnectionPool {
    /**
     * 连接池当前连接数量
     */
    private static int poolSize = 0;

    /**
     * 连接池被占用数量
     */
    private static int occupiedSize = 0;

    /**
     * 数据库创建连接所需参数
     */
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    /**
     * 释放资源，关闭所有连接
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        try{
            closeAll();
        }catch(Throwable t){
            throw t;
        }finally{
            super.finalize();
        }
    }

    /**
     * 连接池数组
     */
    private static CopyOnWriteArrayList<ConnectionInfo> pool = new CopyOnWriteArrayList<>();

    private static void loadProperties() {
        Properties properties = new Properties();
        InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(inputStream);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得连接信息对象
     * @return
     */
    public static synchronized ConnectionInfo getConnectionInfo() {
        ConnectionInfo connectionInfo = null;

        //连接不够，需要创建连接
        if (occupiedSize >= poolSize) {
            connectionInfo = createConnectionInfo();
            connectionInfo.occupy();
            occupiedSize++;
        }
        else {
            for (ConnectionInfo info : pool) {
                if (info.getStatus() == 0) {
                    connectionInfo = info;
                    connectionInfo.occupy();
                    occupiedSize++;
                    break;
                }
            }
        }

        return connectionInfo;
    }

    /**
     * 创建连接信息对象
     * @return
     */
    private static synchronized ConnectionInfo createConnectionInfo() {
        loadProperties();

        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        ConnectionInfo connectionInfo = new ConnectionInfo(connection, poolSize);
        pool.add(connectionInfo);
        poolSize++;
        return connectionInfo;
    }

    /**
     * 释放对应下标的连接
     * @param index
     */
    public static synchronized void free(int index) {
        //下标为负或溢出
        if (index < 0 || index >= poolSize) {
            return;
        }
        else {
            if (pool.get(index).getStatus() == 1) {
                pool.get(index).selfFree();
                occupiedSize--;
            }
        }
    }

    /**
     * 关闭对应下标的连接
     * @param index
     */
    private static synchronized void close(int index) {
        //下标为负或溢出
        if (index < 0 || index >= poolSize) {
            return;
        }
        else {
            pool.get(index).close();
            poolSize--;
            pool.remove(index);
        }
    }

    /**
     * 关闭所有连接
     */
    public static synchronized void closeAll() {
        for (int i = poolSize - 1; i >= 0; i--) {
            close(i);
        }
    }

    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        ConnectionPool.driver = driver;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        ConnectionPool.url = url;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ConnectionPool.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        ConnectionPool.password = password;
    }

    public static int getPoolSize() {
        return poolSize;
    }

    public static int getOccupiedSize() {
        return occupiedSize;
    }
}
