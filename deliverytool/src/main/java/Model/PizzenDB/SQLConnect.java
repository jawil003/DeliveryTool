package Model.PizzenDB;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class SQLConnect {

    /**
     * @author Jannik Will
     * @version 1.0
     */

    //SQL Variables:
    private static final String hostname = "db4free.net"; //the webadress of the database
    private static final String port = "3306"; //the connection port of the mySQL Server
    private static final String user = "delivery"; //username for login in database
    private static final String password = "34zy23npNZ"; //password for login in database
    private final static String dbname = "deliverytool"; //name of the database
    private static Connection conn = null; //connection to the database

    private boolean isRunning;

    /**
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public SQLConnect()
            throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        isRunning = true;
        establishConnection();
    }

    /**
     * @return hostname of used database
     */
    public static String getHostname() {
        return hostname;
    }

    /**
     * @return port of the mySQL Server
     */
    public static String getPort() {
        return port;
    }

    /**
     * @return name of mySQL database
     */
    public static String getDbname() {
        return dbname;
    }

    /**
     * @return username for auth on the database
     */
    public static String getUser() {
        return user;
    }

    /**
     * @return passwort for mySQL database
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @return SQLConnection with mySQL database
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * @param conn
     */
    public static void setConn(Connection conn) {
        SQLConnect.conn = conn;
    }

    /**
     * @throws SQLException
     */
    private void closeConnection() throws SQLException {
        conn.close();
        System.out.println("* Verbindung abgebaut");
        String url = "jdbc:mysql://" + hostname + ":" + port + "/" + dbname;
        System.out.println(url);
        isRunning = false;
    }

    /**
     * Set up the connection to the mySQL Database.
     *
     * @throws SQLException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
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

    /**
     * Make a SQL SELECT Request to the mySQL Database.
     *
     * @param sqlStatement
     * @return ResultSet
     * @throws SQLException
     */
    private ResultSet selectItems(String sqlStatement) throws SQLException {
        final ResultSet resultSet = conn.createStatement().executeQuery(sqlStatement);
        //closeConnection();
        return resultSet;

    }

    /**
     * Make a SQL INSERT Request to the mySQL Database.
     *
     * @param sqlStatement
     * @throws SQLException
     */
    private void insertItems(String sqlStatement) throws SQLException {
        conn.createStatement().executeUpdate(sqlStatement);
    }

    /**
     * Make a SQL SELECT Request to the mySQL DB and returns all Pizza Entries as a LinkedList.
     *
     * @return LinkedList of Pizza Entries
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public LinkedList<Pizza> getPizzen()
            throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
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

    /**
     * Add a new Pizza Entry to the database by inherit INSERT Statement in background
     * @param pizza
     * @throws SQLException
     */
    public void addPizza(Pizza pizza) throws SQLException {
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

    /**
     * @return true (if SQLConnection still runs), else false */
    public boolean isRunning() {
        return isRunning;
    }
}
