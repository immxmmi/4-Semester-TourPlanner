package at.technikum.tourplanner.database.common;

import at.technikum.tourplanner.utils.TextColor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractDBTable {

    protected Connection connection = DBConnect.getInstance().getConnection(); // BESTEHENDE VERBINDUNG WIRD VERWENDET
    //protected Connection connection = (Connection) new DBConnectImpl(); // ERSTELLT IMMER EINE NEUE VERBINDUNG
    protected PreparedStatement statement; // STATEMENT --> SQL ABFRAGE
    protected ResultSet result;    // RESULT DER SQL ABFRAGE
    protected String tableName;    // TABELLEN NAME DES SQL BEFEHLS
    protected String[] parameter;  // PARAMETER FÜR DIE SQL ABFRAGE
    protected TextColor textColor = new TextColor();


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
            if (statement.execute()) {
                this.result = this.statement.executeQuery();
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            System.out.println("Statement ERROR " + e);

        }
        return true;
    }

    protected void closeStatement() {
        try {
            if (this.statement != null) {
                this.statement.close();
            }
            if (this.result != null) {
                this.result.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*******************************************************************/

    public boolean delete(String itemID) {
        this.parameter = new String[]{itemID};
        this.setStatement("DELETE FROM "+this.tableName+" WHERE \"tourId\" = ? ;", this.parameter);
        this.closeStatement();
        return true;
    }
}
