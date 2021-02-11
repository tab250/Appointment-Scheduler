/**
 * @author Tyler Brown
 */
package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;
import utilities.DBQuery;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static main.Main.conn;

/**
 * This class is used to interact with the connected database and all Contact objects made.
 * This class includes only the Read action associated with the database since no other manipulations can occur.
 */
public class ContactDAO {

    private static ObservableList<Contact> allContacts = FXCollections.observableArrayList();
    private static ResultSet dbResults;

    /**
     * @return all Contacts
     */
    public static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }

    /**
     * The Read action that parses through every Contact in the database and adds each to the observable list.
     */
    public static void readContact() throws SQLException {
        try {
            String sqlReadContacts = "SELECT * FROM contacts";

            DBQuery.setPrepStatement(conn, sqlReadContacts);
            PreparedStatement ps = DBQuery.getPrepStatement();
            ps.execute();

            dbResults = ps.getResultSet();

            while (dbResults.next()) {
                int contactID = dbResults.getInt("Contact_ID");
                String contactName = dbResults.getString("Contact_Name");
                String contactEmail = dbResults.getString("Email");

                Contact contact = new Contact(contactID, contactName, contactEmail);
                allContacts.add(contact);
            }
        }
        catch (SQLException s) {
        }
    }
}
