package Model.PizzenDB;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SQLConnect {

    //SQL Variables:
    private static final String hostname = "db4free.net"; //the webadress of the database
    private static final String port = "3306"; //the connection port of the mySQL Server
    private static final String user = "delivery"; //username for login in database
    private static final String password = "34zy23npNZ"; //password for login in database
    private final static String dbname = "deliverytool"; //name of the database
    private static Connection conn = null; //connection to the database

    public SQLConnect() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        establishConnection();
    }

    private void establishConnection()
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
        }

    }

    private ResultSet selectItems(String sqlStatement) throws SQLException {
        final ResultSet resultSet = conn.createStatement().executeQuery(sqlStatement);
        //closeConnection();
        return resultSet;

    }

    private void insertItems(String sqlStatement) throws SQLException {
        conn.createStatement().executeUpdate(sqlStatement);
    }

    public LinkedList<Pizza> getPizzen() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        LinkedList<Pizza> pizzen = new LinkedList<>();

        String sql;
        ResultSet set = selectItems("SELECT * FROM Pizza");

        while (set.next()) {
            Pizza pizza = new Pizza(set.getString(1),
                    null, set.getDouble(2),
                    set.getDouble(3), set.getDouble(4),
                    set.getDouble(5));
            pizzen.add(pizza);
        }

        closeConnection();
        return pizzen;
    }

    public void setPizza(Pizza pizza) throws SQLException {
        insertItems("INSERT INTO Pizza " +
                "(Name, PreisKlein, PreisMittel, PreisGroß, PreisFamilie) " +
                "VALUES " +
                "(" +
                "'" + pizza.getName() + "', " +
                "'" + pizza.getPreisKlein().orElse(0.00) + "', " +
                "'" + pizza.getPreisMittel().orElse(0.00) + "', " +
                "'" + pizza.getPreisGroß().orElse(0.00) + "', " +
                "'" + pizza.getPreisFamilie().orElse(0.00) + "'"
                + ")");
        closeConnection();
    }

    private void closeConnection() throws SQLException {
        conn.close();
        System.out.println("* Verbindung abgebaut");
        String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
        System.out.println(url);
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
