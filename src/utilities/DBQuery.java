/**
 * @author Tyler Brown
 */

package utilities;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is used to interact with database queries.
 */
public class DBQuery {

    //Statement reference
    private static PreparedStatement statement;

    /**
     * This method sends a Prepared Statement to query the database
     */
    //Create Statement object
    public static void setPrepStatement(Connection conn, String sqlStatement) throws SQLException {
        try {
            statement = conn.prepareStatement(sqlStatement);
        }
        catch (SQLException s) {
        }
    }

    /**
     * This method returns a Prepared Statement that queried the database
     */
    //Return Statement object
    public static PreparedStatement getPrepStatement() throws SQLException {
        return statement;
    }
}
