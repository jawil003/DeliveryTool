package model;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnect {
    private static final String hostname = "db4free.net";
    private static final String port = "3306";
    private static final String user = "delivery";
    private static final String password = "34zy23npNZ";
    private static String dbname = "deliverytool";
    private static Connection conn = null;

    public static Connection establishConnection()
            throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        System.out.println("* Treiber laden");
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        System.out.println(Class.forName("com.mysql.cj.jdbc.Driver"));
        System.out.println(Class.forName("com.mysql.cj.jdbc.Driver").newInstance());
        System.out.println("* Verbindung aufbauen");
        String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
        System.out.println(url);
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (CommunicationsException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Server antwortet nicht");
            alert.setContentText(
                    "Entweder ist keine Internetverbindung vorhanden\noder der Datenbankserver ist aktuell nicht erreichbar");
            alert.showAndWait();
            return null;
        }
        return conn;
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
