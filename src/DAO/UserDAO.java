/**
 * @author Tyler Brown
 */
package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utilities.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import static main.Main.conn;

/**
 * This class is used to interact with the connected database and all User objects made.
 * This class includes only the Read action associated with the database since no other manipulations can occur.
 */
public class UserDAO {

    private static ObservableList<User> allUsers = FXCollections.observableArrayList();
    private static ResultSet dbResults;

    /**
     * @return all Users
     */
    public static ObservableList<User> getAllUsers() {
        return allUsers;
    }

    /**
     * The Read action that parses through every User in the database and adds each to the observable list.
     */
    public static void readUsers() throws SQLException {

        try {
            String sqlReadUsers = "SELECT * FROM users";

            DBQuery.setPrepStatement(conn, sqlReadUsers);
            PreparedStatement ps = DBQuery.getPrepStatement();
            ps.execute();

            dbResults = ps.getResultSet();
            while (dbResults.next()) {
                int userID = dbResults.getInt("User_ID");
                String userName = dbResults.getString("User_Name");
                String password = dbResults.getString("Password");
                LocalDateTime createDate = dbResults.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = dbResults.getString("Created_By");
                LocalDateTime lastUpdate = dbResults.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = dbResults.getString("Last_Updated_By");

                User user = new User(userID, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allUsers.add(user);
            }
        }
        catch (SQLException s) {
        }
    }
}
