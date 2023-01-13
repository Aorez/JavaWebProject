import com.aorez.pojo.User;
import com.aorez.sql.UserSQL;
import org.junit.Test;

public class TestUserSql {
    @Test
    public void testSelect() {
        UserSQL userSQL = new UserSQL();
        User user = userSQL.select("张", "zhang");
        if (user == null) {
            System.out.println("登录失败");
        }
        else {
            System.out.println(user.getUsername() + "：登录成功");
        }
    }
}
