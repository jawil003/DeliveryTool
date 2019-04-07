package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLConnect {
    private final static String hostname = "db4free.net";
    private final static String port = "3306";
    private final static String user = "delivery";
    private final static String password = "34zy23npNZ";
    private static String dbname;
    private static Connection conn = null;

    public static Connection establishConnection(String databaseName) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        dbname = databaseName;
        System.out.println("* Treiber laden");
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        System.out.println(Class.forName("com.mysql.cj.jdbc.Driver"));
        System.out.println(Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
        System.out.println("* Verbindung aufbauen");
        String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
        conn = DriverManager.getConnection(url, user, password);
        return conn;

    }

    public static ResultSet sqlRequest(String sqlCommand) throws SQLException {
        return conn.createStatement().executeQuery(sqlCommand);
    }

    public static boolean endingConnection() throws SQLException {
        System.out.println("* Datenbank-Verbindung beenden");
        if (conn.isValid(1000)) {
            conn.close();
            return true;
        } else {
            return false;
        }
    }

    public static String getHostname() {
        return hostname;
    }

    public static String getPort() {
        return port;
    }

    public static String getDbname() {
        return dbname;
    }

    public static void setDbname(String dbname) {
        SQLConnect.dbname = dbname;
    }

    public static String getUser() {
        return user;
    }

    public static String getPassword() {
        return password;
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        SQLConnect.conn = conn;
    }
}
