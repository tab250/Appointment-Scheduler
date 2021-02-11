/**
 * @author Tyler Brown
 */
package controller;


import DAO.CountryDAO;
import DAO.CustDAO;
import DAO.DivDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.SQLException;
import static controller.LogInController.loginName;

/**
 * This class is used to control all user interactions that occur with the Update Customer screen.
 */
public class UpdateCustomerController {
    Stage stage;
    Parent scene;

    private ObservableList<Division> sortedDivs = FXCollections.observableArrayList();
    Country selectedCountry = null;

    @FXML
    private TextField CustomerUpdateID;

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
     * This method sorts the Divsions by displaying only Divisions connected to selected Country and the Division already associated with this Customer.
     * @param event Refers to the action of selecting a Country to sort the Divisions ComboBox by.
     * @throws IOException Occurs if the screen can not obtain proper input from getAllDivisions method.
     */
    @FXML
    void onActionSortDivisions(ActionEvent event) throws IOException {
        try {
            sortedDivs.removeAll(DivDAO.getAllDivisions());
            selectedCountry = null;

            selectedCountry = CountryCombo.getSelectionModel().getSelectedItem();

            for (int d = 0; d < DivDAO.getAllDivisions().size(); d++) {
                if (DivDAO.getAllDivisions().get(d).getCountryID() == selectedCountry.getCountryID()){
                    sortedDivs.add(DivDAO.getAllDivisions().get(d));
                }
            }
            FLDivisionCombo.setItems(sortedDivs);
        }
        catch (IOException i) {
        }
    }

    /**
     * This method returns the user to the Main Menu without saving any information.
     * @param event Refers to the event of user clicking on 'Cancel' button under on the screen.
     * @throws IOException Refers to the event of user clicking on 'Cancel' button under on the screen.
     */
    @FXML
    void onActionCancelUpdate(ActionEvent event) throws IOException {
        try {
            sortedDivs.removeAll(DivDAO.getAllDivisions());
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
     * This method returns the user to the Main Menu and updates an existing Customer in the table/observable list and database.
     * @param event Refers to the action of clicking on  the 'Save' button.
     * @throws SQLException Occurs if the updateCustomer method throws an error when communicating with the DB.
     * @throws IOException Occurs if the main screen can not obtain proper input from ObservableArray Lists.
     */
    @FXML
    void onActionSaveUpdate(ActionEvent event) throws SQLException, IOException {
        try {
            int custID = Integer.parseInt(CustomerUpdateID.getText());

            String custUpdateName = CustomerNameTxt.getText();
            String custUpdateAddress = CustomerAddressTxt.getText();
            String custUpdatePostal = CustomerPostalTxt.getText();
            String custUpdatePhone = CustomerPhoneTxt.getText();
            String custUpdateLUB = loginName;
            int divUpdateID = FLDivisionCombo.getSelectionModel().getSelectedItem().getDivisionID();

            CustDAO.updateCustomer(custUpdateName, custUpdateAddress, custUpdatePostal, custUpdatePhone, custUpdateLUB, divUpdateID, custID);

            sortedDivs.removeAll(DivDAO.getAllDivisions());
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
     * @param selectedCustomer Populates TextFields with information from the selected Customer to modify/update.
     * @throws IOException Occurs if the screen can not obtain proper input from ObservableArray Lists.
     */
    @FXML
    void initialize(Customer selectedCustomer) throws IOException {
        try {
            //Initialize all of the text fields to contain information about the Customer to edit/update
            CustomerUpdateID.setText(String.valueOf(selectedCustomer.getCustID()));
            CustomerNameTxt.setText(String.valueOf(selectedCustomer.getCustName()));
            CustomerAddressTxt.setText(String.valueOf(selectedCustomer.getCustAddress()));
            CustomerPostalTxt.setText(String.valueOf(selectedCustomer.getCustPostal()));
            CustomerPhoneTxt.setText(String.valueOf(selectedCustomer.getCustPhone()));

            //Initialize the Division combo box to display the division of the selected Customer to update
            for (int i =0; i < DivDAO.getAllDivisions().size(); i++) {
                if (DivDAO.getAllDivisions().get(i).getDivisionID() == selectedCustomer.getDivisionID()) {
                    FLDivisionCombo.getSelectionModel().select(DivDAO.getAllDivisions().get(i));
                }
            }
            //Set a Division object to equal selected Division to use for setting the Country combo box
            Division selectedDiv = FLDivisionCombo.getSelectionModel().getSelectedItem();

            //Initialize the Country combo box to display the country that contains the selected Division
            if (selectedDiv != null) {
                for (int j = 0; j < CountryDAO.getAllCountries().size(); j++) {
                    if (CountryDAO.getAllCountries().get(j).getCountryID() == selectedDiv.getCountryID()) {
                        CountryCombo.getSelectionModel().select(CountryDAO.getAllCountries().get(j));
                    }
                }
            }

            //Set a Country object to equal selected Country to use for setting the Division combo box initial options
            selectedCountry = CountryCombo.getSelectionModel().getSelectedItem();

            //Sort through all of the Divisions to add the ones that are within the selected Country
            if (selectedCountry != null) {
                for (int d = 0; d < DivDAO.getAllDivisions().size(); d ++) {
                    if (DivDAO.getAllDivisions().get(d).getCountryID() == selectedCountry.getCountryID()) {
                        sortedDivs.add(DivDAO.getAllDivisions().get(d));
                    }
                }
            }

            //Set the Division combo box to display, upon selecting it, the Divisions within the selected Country
            FLDivisionCombo.setItems(sortedDivs);
            CountryCombo.setItems(CountryDAO.getAllCountries());
        }
        catch (IOException i) {
        }
    }
}
