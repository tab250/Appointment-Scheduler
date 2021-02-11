/**
 * @author Tyler Brown
 */
package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import utilities.DBQuery;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import static main.Main.conn;

/**
 * This class is used to interact with the connected database and all Appointment objects made.
 * This class includes the Create, Read, Update, and Delete actions associated with the database.
 */
public class AppDAO {

    private static ObservableList<Appointment> allApps = FXCollections.observableArrayList();
    private static ResultSet dbResults;


    /**
     * @return all Appointments
     */
    public static ObservableList<Appointment> getAllApps() {
        return allApps;
    }

    /**
     * @return ID connected to last Appointment in list
     */
    public static int appLastID() {
        Appointment lastApp = allApps.get(allApps.size() - 1);
        int lastID = lastApp.getAppID();

        return lastID;
    }

    /**
     * Adds a new Appointment to the Appointment observable list.
     */
    private static void  addApp(Appointment newApp) {
        allApps.add(newApp);
    }

    /**
     * Updates an Appointment, in the observable list, if a selected Appointment's information was altered.
     */
    private static void upApp(int upAppID, String upAppTitle, String upAppDesc, String upAppLoc, String upAppType,
                                 LocalDateTime upAppStart, LocalDateTime upAppEnd, String upAppLUB,
                                 int upAppCustID, int upAppUserID, int upAppContactID) {
        for (int i = 0; i < allApps.size(); i++) {
            if (upAppID == allApps.get(i).getAppID()) {
                if (allApps.get(i).getAppTitle() != upAppTitle) {
                    allApps.get(i).setAppTitle(upAppTitle);
                }
                if (allApps.get(i).getAppDescription() != upAppDesc) {
                    allApps.get(i).setAppDescription(upAppDesc);
                }
                if (allApps.get(i).getAppLocation() != upAppLoc) {
                    allApps.get(i).setAppLocation(upAppLoc);
                }
                if (allApps.get(i).getAppType() != upAppType) {
                    allApps.get(i).setAppType(upAppType);
                }
                if (allApps.get(i).getAppUTCStart() != upAppStart) {
                    allApps.get(i).setAppUTCStart(upAppStart);
                }
                if (allApps.get(i).getAppUTCEnd() != upAppEnd) {
                    allApps.get(i).setAppUTCEnd(upAppEnd);
                }
                if (allApps.get(i).getAppLastUpdatedBy() != upAppLUB) {
                    allApps.get(i).setAppLastUpdatedBy(upAppLUB);
                }
                if (allApps.get(i).getCustID() != upAppCustID) {
                    allApps.get(i).setCustID(upAppCustID);
                }
                if (allApps.get(i).getUserID() != upAppUserID) {
                    allApps.get(i).setUserID(upAppUserID);
                }
                if (allApps.get(i).getContactID() != upAppContactID) {
                    allApps.get(i).setContactID(upAppContactID);
                    for (int j = 0; j < ContactDAO.getAllContacts().size(); j++) {
                        if (ContactDAO.getAllContacts().get(j).getContactID() == upAppContactID) {
                            allApps.get(i).setContactName(ContactDAO.getAllContacts().get(j).getContactName());
                        }
                    }
                }
            }
        }
    }

    /**
     * Deletes Appointment in Appointment Observable list.
     */
    private static void removeApp(Appointment removeApp) {
        for (int i = 0; i < allApps.size(); i++) {
            if (removeApp.getAppID() == allApps.get(i).getAppID()) {
                allApps.remove(i);
            }
        }
    }

    /**
     *The Create action that tells database to add a new Appointment to the appointments table.
     */
    //CRUD Create
    public static void createApp(String newTitle, String newDesc, String newLoc, String newType, LocalDateTime newStart,
                                 LocalDateTime newEnd, LocalDateTime newCreateDate, String newCreatedBy, String newLastUpdatedBy,
                                 int newCustomerID, int newUserID, int newContactID, Appointment newApp) throws SQLException{
        try {
            String sqlCreateApp = "INSERT INTO appointments(Title, Description, Location, Type, Start, End, Create_Date, " +
                    "Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

            DBQuery.setPrepStatement(conn, sqlCreateApp);

            PreparedStatement stmt = DBQuery.getPrepStatement();

            String appTitle = newTitle;
            String appDescription = newDesc;
            String appLocation = newLoc;
            String appType = newType;
            Timestamp appStart = Timestamp.valueOf(newStart);
            Timestamp appEnd = Timestamp.valueOf(newEnd);
            Timestamp appCreateDate = Timestamp.valueOf(newCreateDate);
            String appCreatedBy = newCreatedBy;
            String appLastUpdatedBy = newLastUpdatedBy;
            int customerID = newCustomerID;
            int userID = newUserID;
            int contactID = newContactID;

            stmt.setString(1, appTitle);
            stmt.setString(2, appDescription);
            stmt.setString(3, appLocation);
            stmt.setString(4, appType);
            stmt.setTimestamp(5, appStart);
            stmt.setTimestamp(6, appEnd);
            stmt.setTimestamp(7, appCreateDate);
            stmt.setString(8, appCreatedBy);
            stmt.setString(9, appLastUpdatedBy);
            stmt.setInt(10, customerID);
            stmt.setInt(11, userID);
            stmt.setInt(12, contactID);

            stmt.execute();

            if(stmt.getUpdateCount() == 1) {
                addApp(newApp);
                System.out.println("Appointment successfully added");
            }
        }
        catch (SQLException s) {

        }
    }

    /**
     * The Read action that parses through every Appointment in the database and adds each to the observable list.
     */
    //CRUD Read
    public static void readApps() throws SQLException {
        try {
            String sqlReadApps = "SELECT appointments.*, contacts.Contact_Name FROM appointments, contacts " +
                    "WHERE appointments.Contact_ID = contacts.Contact_ID ORDER BY Appointment_ID";

            DBQuery.setPrepStatement(conn, sqlReadApps);
            PreparedStatement ps = DBQuery.getPrepStatement();
            ps.execute();

            dbResults = ps.getResultSet();

            while (dbResults.next()) {
                int appID = dbResults.getInt("Appointment_ID");
                String appTitle = dbResults.getString("Title");
                String appDescription = dbResults.getString("Description");
                String appLocation = dbResults.getString("Location");
                String appType = dbResults.getString("Type");
                LocalDateTime appStart = dbResults.getTimestamp("Start").toLocalDateTime();
                LocalDateTime appEnd = dbResults.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = dbResults.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = dbResults.getString("Created_By");
                LocalDateTime lastUpdate = dbResults.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = dbResults.getString("Last_Updated_By");
                int customerID = dbResults.getInt("Customer_ID");
                int userID = dbResults.getInt("User_ID");
                int contactID = dbResults.getInt("Contact_ID");
                String contactName = dbResults.getString("Contact_Name");

                Appointment app = new Appointment(appID, appTitle, appDescription, appLocation, appType, appStart,
                        appEnd, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID, contactName);

                allApps.add(app);
            }
        }
        catch (SQLException s) {
        }
    }

    /**
     *The Update action that sends information to the database to update any altered information for an Appointment.
     */
    //CRUD Update
    public static void updateApp(String upTitle, String upDesc, String upLoc, String upType, LocalDateTime upStart,
                                 LocalDateTime upEnd, String upLUB, int upCustomerID, int upUserID, int upContactID,
                                 int upAppID) throws SQLException, IOException {
        try {
            String sqlUpdateApp = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, " +
                    "End = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                    "WHERE Appointment_ID = ?";

            DBQuery.setPrepStatement(conn, sqlUpdateApp);

            PreparedStatement stmt = DBQuery.getPrepStatement();

            String appTitle = upTitle;
            String appDescription = upDesc;
            String appLocation = upLoc;
            String appType = upType;
            Timestamp appStart = Timestamp.valueOf(upStart);
            Timestamp appEnd = Timestamp.valueOf(upEnd);
            String appLUB = upLUB;
            int customerID = upCustomerID;
            int userID = upUserID;
            int contactID = upContactID;
            int appID = upAppID;

            stmt.setString(1, appTitle);
            stmt.setString(2, appDescription);
            stmt.setString(3, appLocation);
            stmt.setString(4, appType);
            stmt.setTimestamp(5, appStart);
            stmt.setTimestamp(6, appEnd);
            stmt.setString(7, appLUB);
            stmt.setInt(8, customerID);
            stmt.setInt(9, userID);
            stmt.setInt(10, contactID);
            stmt.setInt(11, appID);

            stmt.execute();

            if(stmt.getUpdateCount() == 1) {
                for (int i = 0; i < allApps.size(); i++) {
                    if (allApps.get(i).getAppID() == upAppID) {
                        allApps.get(i).setAppLastUpdate(LocalDateTime.now());
                    }
                }
                upApp(upAppID, upTitle, upDesc, upLoc, upType, upStart, upEnd, upLUB, upCustomerID, upUserID, upContactID);
                System.out.println("Appointment successfully updated");
            }
        }
        catch (SQLException s) {
        }
    }

    /**
     * The Delete action associated with the database that deletes a specified Appointment.
     */
    //CRUD Delete
    public static void deleteApp(Appointment appToDelete) throws SQLException{
        try {
            String sqlDeleteApp = "DELETE FROM appointments WHERE Appointment_ID = ? AND Title = ?";

            DBQuery.setPrepStatement(conn, sqlDeleteApp);

            PreparedStatement stmt = DBQuery.getPrepStatement();

            stmt.setInt(1, appToDelete.getAppID());
            stmt.setString(2, appToDelete.getAppTitle());

            stmt.execute();

            if(stmt.getUpdateCount() == 1) {
                removeApp(appToDelete);
                System.out.println("Appointment successfully deleted");
            }
        }
        catch (SQLException s) {
        }
    }
}
