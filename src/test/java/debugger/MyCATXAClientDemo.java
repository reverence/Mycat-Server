package debugger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

public class MyCATXAClientDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 1. 获得数据库连接
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:8066/dbtest", "root", "root");
        conn.setAutoCommit(false);

        // 2. 开启 MyCAT XA 事务
        conn.prepareStatement("set xa=on").execute();

        // 3. 插入 SQL
        // 3.1 SQL1 A库
        String sql1 = String.format("insert into travelrecord(id,name) VALUES (%d, '%s')",
                1,"test1");
        conn.prepareStatement(sql1).execute();
        // 3.2 SQL2 B库
        String sql2 = String.format("insert into travelrecord(id,name) VALUES(%d, %s)", 2,"test2");
        conn.prepareStatement(sql2).execute();

        // 4. 提交 XA 事务
        conn.commit();
    }

}
