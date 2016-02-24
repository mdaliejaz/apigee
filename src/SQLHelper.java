import java.sql.*;
import java.util.ArrayList;

/**
 * Created by ali on 2/24/16.
 */
public class SQLHelper {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/Ali";

    //  Database credentials
    static final String USER = "username";
    static final String PASS = "password";

    private Connection conn = null;
    private Statement stmt = null;

    public void setConnection() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> query(int iPCount) {
        try {
            stmt = conn.createStatement();
            String sql;

            sql = String.format("SELECT ip FROM MyTable Order by count DESC LIMIT %d", iPCount);
            ResultSet rs = stmt.executeQuery(sql);

            ArrayList<String> ipAddresses = new ArrayList<String>();
            while (rs.next()) {
                ipAddresses.add(rs.getString("ip"));
            }
            rs.close();
            stmt.close();
            return ipAddresses;

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<String>();
    }

    public void insert(String key) {
        try {
            stmt = conn.createStatement();
            String sql = String.format("INSERT INTO MyTable (ip, count) VALUES (%s, %d)" +
                    "ON DUPLICATE KEY UPDATE count=count+1", key, 1);
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
        }

    }

    public void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
