/**
 * @author Tyler Brown
 */
package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utilities.DBQuery;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import static main.Main.conn;

/**
 * This class is used to interact with the connected database and all Country objects made.
 * This class includes only the Read action associated with the database since no other manipulations can occur.
 */
public class CountryDAO {

    private static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    private static ResultSet dbResults;

    /**
     * @return all Countries
     */
    public static ObservableList<Country> getAllCountries() {
        return allCountries;
    }

    /**
     * The Read action that parses through every Country in the database and adds each to the observable list.
     */
    public static void readCountries() throws SQLException {

        try {
            String sqlReadCountries = "SELECT * FROM countries";

            DBQuery.setPrepStatement(conn, sqlReadCountries);
            PreparedStatement ps = DBQuery.getPrepStatement();
            ps.execute();

            dbResults = ps.getResultSet();

            while (dbResults.next()) {
                int countryID = dbResults.getInt("Country_ID");
                String countryName = dbResults.getString("Country");
                LocalDateTime createDate = dbResults.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = dbResults.getString("Created_By");
                LocalDateTime lastUpdate = dbResults.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = dbResults.getString("Last_Updated_By");

                Country country = new Country(countryID, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allCountries.add(country);
            }
        }
        catch (SQLException s) {
        }
    }
}
