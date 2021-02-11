/**
 * @author Tyler Brown
 */
package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;
import utilities.DBQuery;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import static main.Main.conn;

/**
 * This class is used to interact with the connected database and all First-Level Division objects made.
 * This class includes only the Read action associated with the database since no other manipulations can occur.
 */
public class DivDAO {

    private static ObservableList<Division> allDivisions = FXCollections.observableArrayList();
    private static ResultSet dbResults;

    /**
     * @return all Divisions
     */
    public static ObservableList<Division> getAllDivisions() throws IOException {
        return allDivisions;
    }

    /**
     * The Read action that parses through every Division in the database and adds each to the observable list.
     */
    public static void readDivisions() throws SQLException {

        try {
            String sqlReadDivisions = "SELECT * FROM first_level_divisions";

            DBQuery.setPrepStatement(conn, sqlReadDivisions);
            PreparedStatement ps = DBQuery.getPrepStatement();
            ps.execute();

            dbResults = ps.getResultSet();

            while (dbResults.next()) {
                int divisionID = dbResults.getInt("Division_ID");
                String divisionName = dbResults.getString("Division");
                LocalDateTime createDate = dbResults.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = dbResults.getString("Created_By");
                LocalDateTime lastUpdate = dbResults.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = dbResults.getString("Last_Updated_By");
                int countryID = dbResults.getInt("COUNTRY_ID");

                Division division = new Division(divisionID, divisionName, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
                allDivisions.add(division);
            }
        }
        catch (SQLException s) {
        }
    }
}
