/**
 * @author Tyler Brown
 */
package controller;


import DAO.CountryDAO;
import DAO.CustDAO;
import DAO.DivDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static DAO.CustDAO.custLastID;
import static controller.LogInController.loginName;

/**
 * This class is used to control all user interactions that occur with the Add Customer screen.
 */
public class AddCustomerController {
    Stage stage;
    Parent scene;

    Country selectedCountry = null;

    @FXML
    private TextField CustomerNameTxt;

    @FXML
    private TextField CustomerAddressTxt;

    @FXML
    private TextField CustomerPostalTxt;

    @FXML
    private TextField CustomerPhoneTxt;

    @FXML
    private ComboBox<Country> CountryCombo;

    @FXML
    private ComboBox<Division> FLDivisionCombo;

    @FXML
    private Button SaveBtn;

    @FXML
    private Button CancelBtn;

    /**
     * This method sorts the Divsions by displaying only Divisions connected to selected Country or displays all Divisions.
     *  Filtration occurs via a lambda expression. The lambda expression was used because it removed about 20 lines of code.
     *  Compare this Sort Divisions compared to the Sort Divisions for the Update Customer Controller.
     * @param event Refers to the action of selecting a Country to sort the Divisions ComboBox by.
     * @throws IOException Occurs if the screen can not obtain proper input from getAllDivisions method.
     */
    @FXML
    void onActionSortDivisions(ActionEvent event) throws IOException {
        try {
            selectedCountry = CountryCombo.getSelectionModel().getSelectedItem();
            FLDivisionCombo.setItems(DivDAO.getAllDivisions().filtered(d -> d.getCountryID() == selectedCountry.getCountryID()));
        }
        catch (IOException i) {
        }
    }

    /**
     * * This method returns the user to the Main Menu without saving any information.
     * @param event Refers to the action of clicking on  the 'Cancel' button.
     * @throws IOException Occurs if the main screen can not obtain proper input from ObservableArray Lists.
     */
    @FXML
    void onActionCancelCustomer(ActionEvent event) throws IOException {
        try {
            selectedCountry = null;

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (IOException i) {
        }
    }

    /**
     *      * This method returns the user to the Main Menu and adds new Customer to table/observable list and database.
     * @param event Refers to the action of clicking on  the 'Save' button.
     * @throws SQLException Occurs if the createCustomer method throws an error when communicating with the DB.
     * @throws IOException Occurs if the main screen can not obtain proper input from ObservableArray Lists.
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException, IOException {
        try {
            int custID = custLastID() + 1;
            String custName = CustomerNameTxt.getText();
            String custAddress = CustomerAddressTxt.getText();
            String custPostal = CustomerPostalTxt.getText();
            String custPhone = CustomerPhoneTxt.getText();
            LocalDateTime custCreateDate = LocalDateTime.now();
            String custCreatedBy = loginName;
            LocalDateTime custLastUpdate = LocalDateTime.now();
            String custLastUpdatedBy = loginName;
            int divID = FLDivisionCombo.getSelectionModel().getSelectedItem().getDivisionID();
            String divName = FLDivisionCombo.getSelectionModel().getSelectedItem().getDivisionName();

            Customer customer = new Customer(custID, custName, custAddress, custPostal, custPhone, custCreateDate, custCreatedBy,
                    custLastUpdate, custLastUpdatedBy, divID, divName);

            CustDAO.createCustomer(custName, custAddress, custPostal, custPhone, custCreateDate, custCreatedBy,
                    custLastUpdatedBy, divID, customer);

            selectedCountry = null;

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (SQLException s) {
        }
        catch (IOException i) {
        }
    }

    /**
     * This method initializes the Add Customer screen with any relevant information.
     * @throws IOException Occurs if the screen can not obtain proper input from getAllDivisions and getAllCountries methods.
     */
    @FXML
    void initialize() throws IOException {
        try {
            CountryCombo.setItems(CountryDAO.getAllCountries());
            FLDivisionCombo.setItems(DivDAO.getAllDivisions());
        }
        catch (IOException i) {
        }
    }
}
