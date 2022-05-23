package at.technikum.tourplanner.database.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AbstractDBTable {

    //LOGGER
    private final static Logger log = LogManager.getLogger(AbstractDBTable.class.getName());

    protected Connection connection = DBConnect.getInstance().getConnection(); // BESTEHENDE VERBINDUNG WIRD VERWENDET
    //protected Connection connection = (Connection) new DBConnectImpl(); // ERSTELLT IMMER EINE NEUE VERBINDUNG
    protected PreparedStatement statement; // STATEMENT --> SQL ABFRAGE
    protected ResultSet result;    // RESULT DER SQL ABFRAGE
    protected String tableName;    // TABELLEN NAME DES SQL BEFEHLS
    protected String[] parameter;  // PARAMETER FÜR DIE SQL ABFRAGE



    /*******************************************************************/
    /**                          Constructor                          **/
    /*******************************************************************/
    public AbstractDBTable(PreparedStatement statement, ResultSet result) {
        this.statement = statement;
        this.result = result;
    }

    public AbstractDBTable() {
        this(null, null);
    }
    /*******************************************************************/


    /*******************************************************************/
    /**                            DATABASE                           **/
    /*******************************************************************/
    protected boolean setStatement(String sql, String[] parameter) {
        try {
            this.statement = connection.prepareStatement(sql);
            for (int i = 0; i < parameter.length; i++) {
                statement.setString(i + 1, parameter[i]);
            }
            log.debug("SQL: " + this.statement);
            if (statement.execute()) {
               this.result = this.statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Statement ERROR " + e);
        }
        return true;
    }
    protected boolean setDataByte(String sql, byte[] data, String id) {
        try {
            this.statement = connection.prepareStatement(sql);

                statement.setBytes(1, data);
                statement.setString(2,id);

            if (statement.execute()) {
                this.result = this.statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("Statement ERROR " + e);

        }
        return true;
    }
    protected void closeStatement() {
        try {
            if (this.statement != null) {
                this.statement.close();
                log.debug("CLOSE STATEMENT");
            }
            if (this.result != null) {
                this.result.close();
                log.debug("CLOSE RESULT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*******************************************************************/

    public boolean delete(String itemID) {
        log.debug("SQL DELETE:");
        this.parameter = new String[]{itemID};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE \""+this.tableName.replace("\"","")+"ID\" = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
