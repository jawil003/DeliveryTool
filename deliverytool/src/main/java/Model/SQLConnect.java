package Model;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;

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

    private static void establishConnection()
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
            e.printStackTrace();
        }
    }

    public LinkedList<String> selectItems(String tableName, String... tableRows) throws SQLException {
        LinkedList<String> result = new LinkedList<>();
        final ResultSet resultSet = conn.createStatement().executeQuery("SELECT " +
                " * "
                + "FROM " +
                tableName);
        for (String e : tableRows) {
            result.add(resultSet.getString(e));
        }
        closeConnection();
        return result;
    }

    public void insertItems(String sqlStatement) throws SQLException {
        conn.createStatement().executeUpdate(sqlStatement);
        closeConnection();

    }

    private void closeConnection() throws SQLException {
        conn.close();
    }
}
