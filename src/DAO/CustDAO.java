/**
 * @author Tyler Brown
 */
package DAO;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import utilities.DBQuery;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import static main.Main.conn;

/**
 * This class is used to interact with the connected database and all Customer objects made.
 * This class includes the Create, Read, Update, and Delete actions associated with the database.
 */
public class CustDAO {

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    /**
     * @return all Customers
     */
    public static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }

    /**
     * @return ID connected to last Customer in list
     */
    public static int custLastID() {
        Customer lastCust = allCustomers.get(allCustomers.size() - 1);
        int lastID = lastCust.getCustID();

        return lastID;
    }

    /**
     * Adds a new Customer to the Customer observable list.
     */
    private static void  addCust(Customer newCust) {
        allCustomers.add(newCust);
    }

    /**
     * Updates an Customer, in the observable list, if a selected Customer's information was altered.
     */
    private static void upCust(int upCustID, String upCustName, String upCustAddress, String upCustPostal, String upCustPhone,
                                 String upCustLUB, int upCustDivID) throws IOException {
        try {
            for (int i = 0; i < allCustomers.size(); i++) {
                if (upCustID == allCustomers.get(i).getCustID()) {
                    if (allCustomers.get(i).getCustName() != upCustName) {
                        allCustomers.get(i).setCustName(upCustName);
                    }
                    if (allCustomers.get(i).getCustAddress() != upCustAddress) {
                        allCustomers.get(i).setCustAddress(upCustAddress);
                    }
                    if (allCustomers.get(i).getCustPostal() != upCustPostal) {
                        allCustomers.get(i).setCustPostal(upCustPostal);
                    }
                    if (allCustomers.get(i).getCustPhone() != upCustPhone) {
                        allCustomers.get(i).setCustPhone(upCustPhone);
                    }
                    if (allCustomers.get(i).getCustPhone() != upCustPhone) {
                        allCustomers.get(i).setCustPhone(upCustPhone);
                    }
                    if (allCustomers.get(i).getLastUpdatedBy() != upCustLUB) {
                        allCustomers.get(i).setLastUpdatedBy(upCustLUB);
                    }
                    if (allCustomers.get(i).getDivisionID() != upCustDivID) {
                        allCustomers.get(i).setDivisionID(upCustDivID);
                        for (int j = 0; j < DivDAO.getAllDivisions().size(); j++) {
                            if (DivDAO.getAllDivisions().get(j).getDivisionID() == upCustDivID) {
                                allCustomers.get(i).setDivisionName(DivDAO.getAllDivisions().get(j).getDivisionName());
                            }
                        }
                    }
                }
            }
        }
        catch (IOException i) {
        }
    }

    /**
     * Deletes Appointment in Appointment Observable list
     */
    private static void removeCust(Customer removeCust) {
        for (int i = 0; i < allCustomers.size(); i++) {
            if (removeCust.getCustID() == allCustomers.get(i).getCustID()) {
                allCustomers.remove(i);
            }
        }
    }

    /**
     *The Create action that tells database to add a new Customer to the customers table.
     */
    //CRUD Create
    public static void createCustomer(String newCustName, String newCustAddress, String newCustPostal, String newCustPhone,
                                      LocalDateTime newCustCreateDate, String newCustCreatedBy, String newCustLastUpdateBy,
                                      int newDivID, Customer newCust) throws SQLException {

        try {
            String sqlCreateApp = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, " +
                    "Last_Updated_By, Division_ID) VALUES(?,?,?,?,?,?,?,?)";

            DBQuery.setPrepStatement(conn, sqlCreateApp);

            PreparedStatement stmt = DBQuery.getPrepStatement();

            String custName = newCustName;
            String custAddress = newCustAddress;
            String custPostal = newCustPostal;
            String custPhone = newCustPhone;
            Timestamp custCreateDate = Timestamp.valueOf(newCustCreateDate);
            String custCreatedBy = newCustCreatedBy;
            String custLastUpdatedBy = newCustLastUpdateBy;
            int divID = newDivID;

            stmt.setString(1, custName);
            stmt.setString(2, custAddress);
            stmt.setString(3, custPostal);
            stmt.setString(4, custPhone);
            stmt.setTimestamp(5, custCreateDate);
            stmt.setString(6, custCreatedBy);
            stmt.setString(7, custLastUpdatedBy);
            stmt.setInt(8, divID);

            stmt.execute();

            if(stmt.getUpdateCount() == 1) {
                addCust(newCust);
                System.out.println("Customer successfully added");
            }
        }
        catch (SQLException s) {
        }
    }

    /**
     * The Read action that parses through every Customer in the database and adds each to the observable list.
     */
    //CRUD Read
    public static void readCustomers() throws SQLException {

        try {
            String sqlReadCustomers = "SELECT customers.*, first_level_divisions.Division FROM customers, first_level_divisions " +
                    "WHERE customers.Division_ID = first_level_divisions.Division_ID;";

            DBQuery.setPrepStatement(conn, sqlReadCustomers);

            PreparedStatement stmt = DBQuery.getPrepStatement();
            stmt.execute();

            ResultSet  dbResults = stmt.getResultSet();
            while (dbResults.next()) {
                int customerID = dbResults.getInt("Customer_ID");
                String customerName = dbResults.getString("Customer_Name");
                String customerAddress = dbResults.getString("Address");
                String postalCode = dbResults.getString("Postal_Code");
                String phoneNum = dbResults.getString("Phone");
                LocalDateTime createDate = dbResults.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = dbResults.getString("Created_By");
                LocalDateTime lastUpdate = dbResults.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = dbResults.getString("Last_Updated_By");
                int divisionID = dbResults.getInt("Division_ID");
                String divisionName = dbResults.getString("Division");

                Customer customer = new Customer(customerID, customerName, customerAddress, postalCode, phoneNum, createDate,
                        createdBy, lastUpdate, lastUpdatedBy, divisionID, divisionName);
                allCustomers.add(customer);
            }
        }
        catch (SQLException s) {
        }
    }

    /**
     *The Update action that sends information to the database to update any altered information for a Customer.
     */
    //CRUD Update
    public static void updateCustomer(String upCustName, String upCustAddress, String upCustPostal, String upCustPhone,
                                      String upCustLUB, int upDivID, int upCustID) throws SQLException, IOException {

        try {
            String sqlUpdateCustomer = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                    "Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

            DBQuery.setPrepStatement(conn, sqlUpdateCustomer);

            PreparedStatement stmt = DBQuery.getPrepStatement();


            String custUpdateName = upCustName;
            String custUpdateAddress = upCustAddress;
            String custUpdatePostal = upCustPostal;
            String custUpdatePhone = upCustPhone;
            String custUpdateLUB = upCustLUB;
            int divUpdateID = upDivID;
            int custID = upCustID;

            stmt.setString(1, custUpdateName);
            stmt.setString(2, custUpdateAddress);
            stmt.setString(3, custUpdatePostal);
            stmt.setString(4, custUpdatePhone);
            stmt.setString(5, custUpdateLUB);
            stmt.setInt(6, divUpdateID);
            stmt.setInt(7, custID);

            stmt.execute();

            for (int i = 0; i < allCustomers.size(); i++) {
                if (allCustomers.get(i).getCustID() == custID) {
                    upCust(custID, custUpdateName, custUpdateAddress, custUpdatePostal, custUpdatePhone, custUpdateLUB, divUpdateID);
                }
            }
            if(stmt.getUpdateCount() == 1) {
                System.out.println("Customer successfully updated");
            }
        }
        catch (SQLException s) {
        }
        catch (IOException i) {
        }
    }

    /**
     * The Delete action associated with the database that deletes a specified Customer.
     */
    //CRUD Delete
    public static void deleteCustomer(Customer customerToDelete) throws SQLException {
        try {
            String sqlDeleteCustomer = "DELETE FROM customers WHERE Customer_ID = ? AND Customer_Name = ?";

            DBQuery.setPrepStatement(conn, sqlDeleteCustomer);

            PreparedStatement stmt = DBQuery.getPrepStatement();

            stmt.setInt(1, customerToDelete.getCustID());
            stmt.setString(2, customerToDelete.getCustName());

            stmt.execute();

            if(stmt.getUpdateCount() == 1) {
                removeCust(customerToDelete);
                System.out.println("Customer successfully deleted");
            }
        }
        catch (SQLException s) {
        }
    }
}
