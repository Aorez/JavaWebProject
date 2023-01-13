import com.aorez.pojo.User;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TestJDBC {
    /**
     * jdbc连接基本写法
     * @throws Exception
     */
    @Test
    public void testJdbc() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://127.0.0.1:3306/student_information_management_system?useSSL=false";
        String dbName = "root";
        String dbPassword = "root221899";
        Connection conn = DriverManager.getConnection(url, dbName, dbPassword);
        String sql = "select * from tb_user;";
        Statement stmt = conn.createStatement();
        ResultSet resultSet =stmt.executeQuery(sql);
        List<User> list = new ArrayList<User>();
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
        stmt.close();
        conn.close();
    }
}
