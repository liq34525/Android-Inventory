package comp3350.ei.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHSQLDB {

    public static Connection connect(String dbPath) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath
                + ";shutdown=true", "SA", "");
        if (con != null) {
            System.out.println("Connection created successfully");
        }
        else {
            System.out.println("Problem with creating connection");
        }
        return con;
    }
}
