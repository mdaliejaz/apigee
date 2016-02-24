import java.sql.*;
import java.util.ArrayList;

public class SQLHelper {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/apigee?useSSL=false";

    //  Database credentials
    static final String USER = "user";
    static final String PASS = "password";

    public ArrayList<String> query(int iPCount) {
        Statement stmt = null;
        ResultSet resultSet = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = String.format("SELECT ip FROM IPCount Order by count DESC LIMIT %d", iPCount);

            resultSet = stmt.executeQuery(sql);

            ArrayList<String> ipAddresses = new ArrayList<String>();
            while (resultSet.next()) {
                ipAddresses.add(resultSet.getString("ip"));
            }
            return ipAddresses;

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (resultSet != null)
                    resultSet.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<String>();
    }

    public void insert(String key) {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = String.format("INSERT INTO IPCount (ip, count) VALUES ('%s', %d)" +
                    "ON DUPLICATE KEY UPDATE count=count+1", key, 1);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTable() {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "delete from IPCount";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//    public void dropTable() {
//        Statement stmt = null;
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            stmt = conn.createStatement();
//            String sql = "drop table if exists IPCount";
//            stmt.executeUpdate(sql);
//            stmt.close();
//        } catch (SQLException se) {
//            se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (conn != null)
//                    conn.close();
//                if (stmt != null)
//                    stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }

//    public void createTable() {
//        Statement stmt = null;
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            stmt = conn.createStatement();
//            String sql = "create table IPCount" +
//                    "(ip varchar(255) NOT NULL" +
//                    "count int NOT NULL" +
//                    "PRIMARY KEY(ip))";
//            stmt.executeUpdate(sql);
//            stmt.close();
//        } catch (SQLException se) {
//            se.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (conn != null)
//                    conn.close();
//                if (stmt != null)
//                    stmt.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
