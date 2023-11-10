package Util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public static Connection init() {
        Connection conn = null;

        try {
            // JDBC Driver 등록
            Class.forName("org.mariadb.jdbc.Driver");

            // 연결하기
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/team_prj",
                    "root", "12345");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                return conn;
            }
        }
        return null;
    }
}
