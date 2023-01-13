package com.aorez.util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 封装连接和连接状态
 */
public class ConnectionInfo {
    /**
     * 连接变量
     */
    private Connection conn;

    /**
     * 状态
     * 0为空闲，1为占用
     */
    private int status = 0;

    private int index = -1;

    /**
     * 构造函数
     * 传递Connection连接
     * @param conn
     * @param index
     */
    public ConnectionInfo(Connection conn, int index) {
        this.conn = conn;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 释放连接
     * 外部调用
     * 将状态设为空闲
     */
    public void free() {
        ConnectionPool.free(this.index);
    }

    /**
     * 连接池调用
     * 将状态设为空闲
     */
    public void selfFree() {
        this.setStatus(0);
    }

    /**
     * 占用连接
     * 将状态设为占用
     */
    public void occupy() {
        this.setStatus(1);
    }

    /**
     * 关闭连接
     */
    public void close() {
        try {
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
