import com.aorez.pojo.User;
import com.aorez.util.ConnectionInfo;
import com.aorez.util.ConnectionPool;
import com.aorez.util.SqlFileReader;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 测试读取sql文件为字符串
 */
public class TestSqlFileReader {
    @Test
    public void testSqlFileReader() throws SQLException {
        String sql = SqlFileReader.readSqlFileAsString("src/main/resources/sql/UserSql-select.sql");
        sql = sql.replaceFirst("'username'", "'张'");
        sql = sql.replaceFirst("'password'", "'zhang'");
//        System.out.println(sql);

        ConnectionInfo connectionInfo = ConnectionPool.getConnectionInfo();
        Connection conn = connectionInfo.getConn();
        Statement statement = conn.createStatement();
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
    }
}
