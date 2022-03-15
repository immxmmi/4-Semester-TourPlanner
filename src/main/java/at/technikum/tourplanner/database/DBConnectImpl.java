package at.technikum.tourplanner.database;

import at.technikum.tourplanner.utils.TextColor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectImpl implements Cloneable, DBConnect {


    private String databaseName;
    private String username;
    private String password;
    private String port;
    private String jdbcURL;
    protected Connection connection;
    private static DBConnectImpl instance = null;


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
    public DBConnectImpl(String databaseName, String username, String password, String port) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL JDBC driver not found");
            e.printStackTrace();
        }

        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
        this.port = port;
        this.jdbcURL = "jdbc:postgresql://localhost:" + this.port + "/" + this.databaseName + "";
        this.startConnect();
    }

    /**
     * DEFAULT CONSTRUCTOR
     **/
    public DBConnectImpl() {
        this("swe2db", "swe2user", "swe2pw", "5432");
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
        if (connection == null) {
            this.startConnect();
        }
        return this.connection;
    }

    /**
     * Get CURRENT CONNECTION
     **/
    public static DBConnectImpl getInstance() {
        if (instance == null)
            instance = new DBConnectImpl();
        return instance;
    }

    static {
        DBConnectImpl.instance = new DBConnectImpl();
    }


}
