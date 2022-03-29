package at.technikum.tourplanner.database.common;

import at.technikum.tourplanner.utils.TextColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect implements Cloneable {


    private static String databaseName;
    private static String username;
    private static String password;
    private static String port;
    private static String url;
    private static String jdbcURL;
    protected Connection connection;
    private static DBConnect instance = null;


    /**
     *
     * @param databaseName Name     der Datenbank
     * @param username     Username der Datenbank
     * @param password     Password der Datenbank
     * @param port         Port     der Datenbank (STANDARD PORT: 5432)
     */

    /**
     * CONSTRUCTOR
     **/
    public DBConnect(String url, String databaseName, String username, String password, String port) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found");
            e.printStackTrace();
        }

        this.url = url;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.port = port;
        this.jdbcURL = this.url + this.port + "/" + this.databaseName + "";
        this.startConnect();
    }

    /**
     * DEFAULT CONSTRUCTOR
     **/
    public DBConnect() {
       // this("swe2db", "swe2user", "swe2pw", "5432");
        this(ConfigurationManager.GetConfigPropertyValue("db_url"),ConfigurationManager.GetConfigPropertyValue("db_database"), ConfigurationManager.GetConfigPropertyValue("db_username"), ConfigurationManager.GetConfigPropertyValue("db_password"), ConfigurationManager.GetConfigPropertyValue("db_port"));
    }


    /**
     * START CONNECTION
     **/
    private void startConnect() {
        try {
            this.connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
            System.out.println(TextColor.ANSI_GREEN + "CONNECT DB -- success" + TextColor.ANSI_RESET);
        } catch (SQLException e) {
            System.out.println(TextColor.ANSI_RED + "CONNECT DB -- failed" + TextColor.ANSI_RESET);
            e.printStackTrace();
        }
    }

    /**
     * STOP CONNECTION
     **/

    public void stopConnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            this.connection = null;
        }
    }

    /**
     * Get CONNECTION
     **/

    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Get CURRENT CONNECTION
     **/
    public static DBConnect getInstance() {
        if (instance == null)
            instance = new DBConnect();
        return instance;
    }

    static {
        DBConnect.instance = new DBConnect();
    }


}
