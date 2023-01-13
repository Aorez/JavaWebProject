import com.aorez.pojo.User;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TestConnectionPool {
    /**
     * 测试路径显示
     */
    @Test
    public void testDir() {
        System.out.println(System.getProperty("user.dir"));
    }

    /**
     * 通过返回值判断两个连接是否同一个
     * @return
     * @throws SQLException
     */
    public ConnectionInfo testConnectionPool() throws SQLException {
        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection conn = connectionInfo.getConn();
        Statement statement = conn.createStatement();
        String sql = "select * from tb_user";
        ResultSet resultSet = statement.executeQuery(sql);
        List<User> list = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPassword(password);
            list.add(user);
        }
        System.out.println(list);
        resultSet.close();
        statement.close();
        connectionInfo.free();

        return connectionInfo;
    }

    /**
     * 判断两个ConnectionInfo是否同个对象，从而判断两次查询是否利用同一个连接
     * @throws SQLException
     */
    @Test
    public void testConnectionPoolTwice() throws SQLException {
        ConnectionInfo connectionInfo1 = testConnectionPool();
        ConnectionInfo connectionInfo2 = testConnectionPool();
        System.out.println(ConnectionPool.getPoolSize());
        System.out.println(ConnectionPool.getOccupiedSize());
        System.out.println(connectionInfo1);
        System.out.println(connectionInfo2);
    }

    /**
     * 测试properties文件的读取
     */
    @Test
    public void testProperties() {
        Properties properties = new Properties();
        //可以直接写文件名
        //或者在resources某个目录下，直接加目录名，最前面不用斜杠
        InputStream inputStream = TestConnectionPool.class.getClassLoader().getResourceAsStream("jdbc.properties");
        if (inputStream == null) {
            System.out.println("null");
        }
        try {
            properties.load(inputStream);
            String driver = properties.getProperty("driver");
            String url = properties.getProperty("url");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            System.out.println(driver);
            System.out.println(url);
            System.out.println(username);
            System.out.println(password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
