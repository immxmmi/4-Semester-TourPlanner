package at.technikum.tourplanner.database.common;

import at.technikum.tourplanner.config.ConfigurationManagerImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect implements Cloneable {

    //LOGGER
    private final static Logger log = LogManager.getLogger(DBConnect.class.getName());
    private static ConfigurationManagerImpl config = new ConfigurationManagerImpl();

    private String databaseName;
    private String username;
    private String password;
    private String port;
    private String url;
    private String jdbcURL;
    protected Connection connection;
    private static DBConnect instance = null;
    private Boolean DBOnline = true;


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
        this(   config.getDbUrl(),
                config.getDbDatabase(),
                config.getDbUsername(),
                config.getDbPassword(),
                config.getDbPort());
    }

    /**
     * START CONNECTION
     **/
    private void startConnect() {
        log.debug("START CONNECTION");
        try {
            this.connection = DriverManager.getConnection(this.jdbcURL, this.username, this.password);
            log.debug("CONNECT DB -- success");
           // System.out.println(TextColor.ANSI_GREEN + "CONNECT DB -- success" + TextColor.ANSI_RESET);
        } catch (SQLException e) {
            DBOnline = false;
            log.error("CONNECT DB -- failed" );
            log.error(e);
            //System.out.println(TextColor.ANSI_RED + "CONNECT DB -- failed" + TextColor.ANSI_RESET);
            //e.printStackTrace();
        }
    }

    /**
     * STOP CONNECTION
     **/

    public void stopConnect() {
        log.debug("STOP CONNECTION");
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException throwables) {
                log.error(throwables);
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

    public Boolean getDBOnline() {
        return DBOnline;
    }
}
