package comp3350.ei.application;

import org.hsqldb.jdbc.JDBCDriver;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    private static String dbName="EI";

    public static void main(String[] args)
    {
        //CLI.run();
        System.out.println("All done");
    }

    public static void setDBPathName(final String name) {
        try {
            DriverManager.registerDriver(new JDBCDriver());
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dbName = name;
    }

    public static String getDBPathName() {
        return dbName;
    }

}
