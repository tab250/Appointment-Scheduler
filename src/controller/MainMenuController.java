/**
 * @author Tyler Brown
 */
package controller;


import DAO.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import static utilities.DateTime.*;

/**
 * This class is used to control all user interactions that occur with the Main Menu screen.
 */
public class MainMenuController {

    Stage stage;
    Parent scene;

    private ObservableList<Country> currentCountries = FXCollections.observableArrayList();
    private ObservableList<Division> currentDivs = FXCollections.observableArrayList();
    private ObservableList<Customer> currentCustomers = FXCollections.observableArrayList();
    private ObservableList<Appointment> currentApps = FXCollections.observableArrayList();

    private ObservableList<Division> sortDivisions = FXCollections.observableArrayList();
    private ObservableList<Customer> sortCustByCountry = FXCollections.observableArrayList();
    private ObservableList<Customer> sortCustByDiv = FXCollections.observableArrayList();
    private ObservableList<Appointment> sortAppsByCountry = FXCollections.observableArrayList();
    private ObservableList<Appointment> sortAppsByDiv = FXCollections.observableArrayList();

    private ObservableList<Appointment> sortAppByWeek = FXCollections.observableArrayList();
    private ObservableList<Appointment> sortAppByMonth = FXCollections.observableArrayList();

    private Country selectedCountry = null;
    private Division selectedDiv = null;

    private boolean countryClearBool = false;
    private boolean divClearBool = false;


    private ObservableList<String> report1Info = FXCollections.observableArrayList();
    private ObservableList<String> appTypesList = FXCollections.observableArrayList();
    private ObservableList<String> report2Info = FXCollections.observableArrayList();
    private ObservableList<String> report3Info = FXCollections.observableArrayList();

    @FXML
    private TableView<Appointment> AppointmentTblView;

    @FXML
    private TableColumn<Appointment, Integer> AppIDCol;

    @FXML
    private TableColumn<Appointment, String> AppTitleCol;

    @FXML
    private TableColumn<Appointment, String> AppDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> AppLocationCol;

    @FXML
    private TableColumn<Appointment, String> AppContactCol;

    @FXML
    private TableColumn<Appointment, String> AppTypeCol;

    @FXML
    private TableColumn<Appointment, String> AppStartCol;

    @FXML
    private TableColumn<Appointment, String> AppEndCol;

    @FXML
    private TableColumn<Appointment, Integer> AppCustomerIDCol;

    @FXML
    private TableColumn<Appointment, Integer> AppUserIDCol;

    @FXML
    private RadioButton WeeklyBtn;

    @FXML
    private ToggleGroup SortBy;

    @FXML
    private RadioButton MonthlyBtn;

    @FXML
    private Button clearAppSortBtn;

    @FXML
    private Button AppAddBtn;

    @FXML
    private Button AppUpdateBtn;

    @FXML
    private Button AppDeleteBtn;

    @FXML
    private TableView<Customer> CustomerTblView;

    @FXML
    private TableColumn<Customer, Integer> CustomerIDCol;

    @FXML
    private TableColumn<Customer, String> CustomerNameCol;

    @FXML
    private TableColumn<Customer, String> CustomerAddressCol;

    @FXML
    private TableColumn<Customer, String> CustomerPostalCol;

    @FXML
    private TableColumn<Customer, String> CustomerPhoneCol;

    @FXML
    private TableColumn<Customer, String> CustomerFLDCol;

    @FXML
    private ComboBox<Country> SortByCountryCombo;

    @FXML
    private ComboBox<Division> SortByFLDCombo;

    @FXML
    private Button CountryClearBtn;

    @FXML
    private Button DivisionClearBtn;

    @FXML
    private Button CustomerAddBtn;

    @FXML
    private Button CustomerUpdateBtn;

    @FXML
    private Button CustomerDeleteBtn;

    @FXML
    private Button Report1Btn;

    @FXML
    private Button Report2Btn;

    @FXML
    private Button Report3Btn;

    @FXML
    private ListView<String> Report1List;

    @FXML
    private ListView<String> Report2List;

    @FXML
    private ListView<String> Report3List;

    /**
     * This method displays the first report (see README) for the user to read.
     */
    @FXML
    void onActionDisplayReport1(ActionEvent event) {
        if (!report1Info.isEmpty()) {
            report1Info.removeAll();
        }
        int appTypeCount = 0;

        int appMonthCount = 0;
        Month currentMonth = LocalDateTime.now().getMonth();

        for (int i = 0; i < AppDAO.getAllApps().size(); i++) {
            if (!appTypesList.contains(AppDAO.getAllApps().get(i).getAppType())) {
                appTypesList.add(AppDAO.getAllApps().get(i).getAppType());
                System.out.println("Type added");
            }
        }
        for (int k = 0; k < appTypesList.size(); k++) {
            for (int l = 0; l < AppDAO.getAllApps().size(); l++) {
                if (AppDAO.getAllApps().get(l).getAppType() == appTypesList.get(k)) {
                    appTypeCount +=1;
                }
            }
            report1Info.add("Appointment Type: " + appTypesList.get(k) + "\n   Total appointments: " +appTypeCount);
            appTypeCount = 0;
        }

        for (int i = 0; i < Month.values().length; i++) {
            for (int j = 0; j < AppDAO.getAllApps().size(); j++) {
                if (AppDAO.getAllApps().get(j).getAppUserStart().getMonth() == currentMonth) {
                    appMonthCount += 1;
                }
            }
            report1Info.add("Month: " + currentMonth + "\n   Total appointments: " + appMonthCount);
            currentMonth = currentMonth.plus(1);
            appMonthCount = 0;
        }
        Report1List.getItems().addAll(report1Info);
    }


    /**
     * This method displays the second report (see README) for the user to read.
     */
    @FXML
    void onActionDisplayReport2(ActionEvent event) {
        if (!report2Info.isEmpty()) {
            report2Info.removeAll();
        }

        for (int i = 0; i < ContactDAO.getAllContacts().size(); i++) {
            for (int j = 0; j < AppDAO.getAllApps().size(); j++) {
                if (AppDAO.getAllApps().get(j).getContactID() == ContactDAO.getAllContacts().get(i).getContactID()) {
                    report2Info.add("Contact: " + ContactDAO.getAllContacts().get(i).getContactName() + "\nAppointment ID: "
                            + AppDAO.getAllApps().get(j).getAppID() + "\n   Title: " + AppDAO.getAllApps().get(j).getAppTitle()
                            + "\n   Type: " + AppDAO.getAllApps().get(j).getAppType() + "\n   Description:" + AppDAO.getAllApps().get(j).getAppDescription()
                            + "\n   Start Date/Time: " + AppDAO.getAllApps().get(j).getAppUserStart() + "\n   End Date/Time: "
                            + AppDAO.getAllApps().get(j).getAppUserEnd());
                }
            }
        }
        Report2List.getItems().addAll(report2Info);
    }

    /**
     * This method displays the third report (see README) for the user to read.
     */
    @FXML
    void onActionDisplayReport3(ActionEvent event) throws IOException {
        try {
            if (!report3Info.isEmpty()) {
                report3Info.removeAll();
            }
            int customerPerCountry = 0;

            for (int i = 0; i < CountryDAO.getAllCountries().size(); i++) {
                for (int j = 0; j < DivDAO.getAllDivisions().size(); j++) {
                    for (int k = 0; k < CustDAO.getAllCustomers().size(); k++) {
                        if (CustDAO.getAllCustomers().get(k).getDivisionID() == DivDAO.getAllDivisions().get(j).getDivisionID() &&
                                DivDAO.getAllDivisions().get(j).getCountryID() == CountryDAO.getAllCountries().get(i).getCountryID()) {
                            customerPerCountry += 1;
                        }
                    }
                }
                report3Info.add("Customers for " + CountryDAO.getAllCountries().get(i).getCountryName() + ": " + customerPerCountry);

                customerPerCountry = 0;
            }
            Report3List.getItems().addAll(report3Info);
        }
        catch(IOException i) {
        }
    }

    /**
     * This method clears the Country combo box selection and returns all Customers to Table prior to filtering.
     */
    @FXML
    void clearCountry(ActionEvent event) {
        sortDivisions.clear();

        sortCustByCountry.clear();
        sortCustByDiv.clear();

        sortAppsByCountry.clear();
        sortAppsByDiv.clear();

        SortByCountryCombo.getSelectionModel().clearSelection();

        CustomerTblView.setItems(currentCustomers);
        SortByFLDCombo.setItems(currentDivs);
        AppointmentTblView.setItems(currentApps);
    }

    /**
     * This method clears the Division combo box selection and returns all Customers to Table prior to filtering.
     */
    @FXML
    void clearDivision(ActionEvent event) {
        if (SortByCountryCombo.getSelectionModel().getSelectedItem() != null) {

            sortCustByDiv.clear();
            sortAppsByDiv.clear();

            SortByFLDCombo.getSelectionModel().clearSelection();

            CustomerTblView.setItems(sortCustByCountry);
            SortByFLDCombo.setItems(currentDivs);
            AppointmentTblView.setItems(sortAppsByCountry);
        }
        else {
            sortCustByDiv.clear();
            sortCustByDiv.clear();

            SortByFLDCombo.getSelectionModel().clearSelection();

            CustomerTblView.setItems(currentCustomers);
            SortByFLDCombo.setItems(currentDivs);
            AppointmentTblView.setItems(currentApps);
        }
    }

    /**
     * This method takes the user to the Add Appointment screen.
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddAppointment.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (IOException i) {
        }
    }

    /**
     * This method takes the user to the Add Customer screen.
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (IOException i) {
        }
    }

    /**
     * This method removes a Customer from the Customers table and database.
     */
    @FXML
    void onActionCustomerDelete(ActionEvent event) throws SQLException {
        try {
            Customer delCustomer = CustomerTblView.getSelectionModel().getSelectedItem();

            if (currentApps != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "ALERT! \n This customer: " + delCustomer.getCustName() +
                        ", has at least one appointment in the schedule. By confirming to delete the customer, all appointments " +
                        "for this customer will also be deleted. Do you want to delete this customer and their appointments?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    for (int j = 0; j < currentApps.size(); j++) {
                        AppDAO.deleteApp(currentApps.get(j));
                    }
                    Alert deletedCustomer = new Alert(Alert.AlertType.ERROR);
                    deletedCustomer.setTitle("Error Pop Up");
                    deletedCustomer.setContentText("Customer: " + delCustomer.getCustName() + " has been deleted.");
                    deletedCustomer.showAndWait();

                    CustDAO.deleteCustomer(delCustomer);
                    currentCustomers.remove(delCustomer);
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " +
                        "Customer: " + delCustomer.getCustName() + "?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    CustDAO.deleteCustomer(delCustomer);
                    currentCustomers.remove(delCustomer);

                    Alert deletedCustomer = new Alert(Alert.AlertType.ERROR);
                    deletedCustomer.setTitle("Error Pop Up");
                    deletedCustomer.setContentText("Customer: " + delCustomer.getCustName() + " has been deleted.");
                    deletedCustomer.showAndWait();
                }
            }
        }
        catch (NullPointerException n) {
            Alert noCustSelected = new Alert(Alert.AlertType.ERROR);
            noCustSelected.setTitle("No Customer Selected");
            noCustSelected.setContentText("Please select a Customer to delete");
            noCustSelected.showAndWait();
        }
        catch (SQLException s) {
        }
    }

    /**
     * This method removes an Appointment from the Appointments table and database.
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        try {
            Appointment delAppointment = AppointmentTblView.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " +
                    "Appointment ID: " + delAppointment.getAppID() + ", Type: " + delAppointment.getAppType() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Alert deletedApp = new Alert(Alert.AlertType.ERROR);
                deletedApp.setTitle("Error Pop Up");
                deletedApp.setContentText(" Appointment, ID: " + delAppointment.getAppID() + ", Type: " + delAppointment.getAppType() + " has been deleted.");
                deletedApp.showAndWait();

                AppDAO.deleteApp(delAppointment);
                currentApps.remove(delAppointment);
            }
        }
        catch (NullPointerException n) {
            Alert noCustSelected = new Alert(Alert.AlertType.ERROR);
            noCustSelected.setTitle("No Appointment Selected");
            noCustSelected.setContentText("Please select an Appointment to delete");
            noCustSelected.showAndWait();
        }
        catch (SQLException s) {
        }
    }

    /**
     * This method sorts the Customer and associated Appointment tables to only show information that corresponds to selected Country.
     */
    @FXML
    void onActionSortByCountry(ActionEvent event) {
        if (countryClearBool == true) {
            sortCustByCountry.clear();
            sortCustByDiv.clear();
            sortAppsByCountry.clear();
            sortAppsByDiv.clear();
            sortDivisions.clear();
        }

        selectedCountry = SortByCountryCombo.getSelectionModel().getSelectedItem();

        if (selectedCountry != null) {
            for (int i = 0; i < currentCountries.size(); i++) {
                if (currentDivs.get(i).getCountryID() == selectedCountry.getCountryID()) {
                    sortDivisions.add(currentDivs.get(i));
                }
            }
        }
        SortByFLDCombo.setItems(sortDivisions);

        for (int j = 0; j < sortDivisions.size(); j++) {
            for (int k = 0; k < currentCustomers.size(); k++) {
                if (sortDivisions.get(j).getDivisionID() == currentCustomers.get(k).getDivisionID() && !sortAppsByCountry.contains(currentCustomers.get(k))) {
                    sortCustByCountry.add(currentCustomers.get(k));
                }
            }
        }
        CustomerTblView.setItems(sortCustByCountry);

        for (int l = 0; l < sortCustByCountry.size(); l++) {
            for (int m = 0; m < currentApps.size(); m++) {
                if (sortCustByCountry.get(l).getCustID() == currentApps.get(m).getCustID()) {
                    sortAppsByCountry.add(currentApps.get(m));
                }
            }
        }
        AppointmentTblView.setItems(sortAppsByCountry);

        countryClearBool = true;
    }

    /**
     * This method sorts the Customer and Appointment tables to only show information that corresponds to selected Division.
     */
    @FXML
    void onActionSortByFLD(ActionEvent event) {
        if (divClearBool == true) {
            sortCustByDiv.clear();
            sortAppsByDiv.clear();
        }

        selectedDiv = SortByFLDCombo.getSelectionModel().getSelectedItem();

        if (!SortByCountryCombo.getSelectionModel().isEmpty()) {
            selectedCountry = SortByCountryCombo.getSelectionModel().getSelectedItem();
        }

        if (selectedDiv != null && selectedCountry != null) {
            for (int i = 0; i < sortCustByCountry.size(); i++) {
                if (sortCustByCountry.get(i).getDivisionID() == selectedDiv.getDivisionID()) {
                    sortCustByDiv.add(sortCustByCountry.get(i));
                }
            }
            for (int j = 0; j < sortCustByDiv.size(); j++) {
                for (int k = 0; k < sortAppsByCountry.size(); k++) {
                    if (sortCustByDiv.get(j).getCustID() == sortAppsByCountry.get(k).getCustID()) {
                        sortAppsByDiv.add(sortAppsByCountry.get(k));
                    }
                }
            }
        }

        if (selectedDiv != null && selectedCountry == null) {
            for (int l = 0; l < currentCustomers.size(); l++) {
                if (currentCustomers.get(l).getDivisionID() == selectedDiv.getDivisionID()) {
                    sortCustByDiv.add(currentCustomers.get(l));
                }
            }
            for (int m = 0; m < sortCustByDiv.size(); m++) {
                for (int n = 0; n < currentApps.size(); n++) {
                    if (sortCustByDiv.get(m).getCustID() == currentApps.get(n).getCustID()) {
                        sortAppsByDiv.add(currentApps.get(n));
                    }
                }
            }
        }

        CustomerTblView.setItems(sortCustByDiv);
        AppointmentTblView.setItems(sortAppsByDiv);

        divClearBool = true;
    }

    /**
     * This method filters Appointments to display only those that occur within the next month.
     */
    @FXML
    void onActionSortByMonth(ActionEvent event) {
        sortAppByWeek.removeAll(AppDAO.getAllApps());
        sortAppByMonth.removeAll(AppDAO.getAllApps());

        if (MonthlyBtn.isSelected()) {
            if (!sortAppsByDiv.isEmpty()) {
                for (int i = 0; i < sortAppsByDiv.size(); i++) {
                    if (withinMonth(sortAppsByDiv.get(i)) == true) {
                        sortAppByMonth.add(sortAppsByDiv.get(i));
                    }
                }
            }
            else if (!sortAppsByCountry.isEmpty() && sortAppsByDiv.isEmpty()) {
                for (int i = 0; i < sortAppsByCountry.size(); i++) {
                    if (withinMonth(sortAppsByCountry.get(i)) == true) {
                        sortAppByMonth.add(sortAppsByCountry.get(i));
                    }
                }
            }
            else if (sortAppsByDiv.isEmpty() && sortAppsByCountry.isEmpty()) {
                for (int i = 0; i < currentApps.size(); i++) {
                    if (withinMonth(currentApps.get(i)) == true) {
                        sortAppByMonth.add(currentApps.get(i));
                    }
                }
            }

            if (sortAppByMonth.isEmpty()) {
                Alert noApps = new Alert(Alert.AlertType.ERROR);
                noApps.setTitle("No Appointments within Month");
                noApps.setContentText("There are not any appointments scheduled within a month.");
                noApps.showAndWait();

                MonthlyBtn.setSelected(false);
            }
            else {
                AppointmentTblView.setItems(sortAppByMonth);
            }
        }
    }

    /**
     * This method filters Appointments to display only those that occur within the next week.
     */
    @FXML
    void onActionSortByWeek(ActionEvent event) {
        sortAppByWeek.removeAll(AppDAO.getAllApps());
        sortAppByMonth.removeAll(AppDAO.getAllApps());

        if (WeeklyBtn.isSelected()) {
            if (!sortAppsByDiv.isEmpty()) {
                for (int i = 0; i < sortAppsByDiv.size(); i++) {
                    if (withinWeek(sortAppsByDiv.get(i)) == true) {
                        sortAppByWeek.add(sortAppsByDiv.get(i));
                    }
                }
            }
            else if (!sortAppsByCountry.isEmpty() && sortAppsByDiv.isEmpty()) {
                for (int i = 0; i < sortAppsByCountry.size(); i++) {
                    if (withinWeek(sortAppsByCountry.get(i)) == true) {
                        sortAppByWeek.add(sortAppsByCountry.get(i));
                    }
                }
            }
            else if (sortAppsByDiv.isEmpty() && sortAppsByCountry.isEmpty()) {
                for (int i = 0; i < currentApps.size(); i++) {
                    if (withinWeek(currentApps.get(i)) == true) {
                        sortAppByWeek.add(currentApps.get(i));
                    }
                }
            }
            if (sortAppByWeek.isEmpty()) {
                Alert noApps = new Alert(Alert.AlertType.ERROR);
                noApps.setTitle("No Appointments within Week");
                noApps.setContentText("There are not any appointments scheduled within a week.");
                noApps.showAndWait();

                WeeklyBtn.setSelected(false);
            }
            else {
                AppointmentTblView.setItems(sortAppByWeek);
            }
        }
    }

    /**
     * This method any Appointment that appeared prior to filtering via Month or Week radio button.
     */
    @FXML
    void onActionClearAppSort(ActionEvent event) {
        if(WeeklyBtn.isSelected() || MonthlyBtn.isSelected()) {
            MonthlyBtn.setSelected(false);
            WeeklyBtn.setSelected(false);

            sortAppByWeek.removeAll(AppDAO.getAllApps());
            sortAppByMonth.removeAll(AppDAO.getAllApps());

            if (!sortAppsByDiv.isEmpty()) {
                AppointmentTblView.setItems(sortAppsByDiv);
            }
            else if (!sortAppsByCountry.isEmpty() && sortAppsByDiv.isEmpty()) {
                AppointmentTblView.setItems(sortAppsByCountry);
            }
            else if (sortAppsByDiv.isEmpty() && sortAppsByCountry.isEmpty()) {
                AppointmentTblView.setItems(currentApps);
            }
        }
    }

    /**
     * This method takes the user to the Update Appointment screen.
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            loader.load();

            UpdateAppointmentController updateAppointmentController = loader.getController();
            updateAppointmentController.initialize(AppointmentTblView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert noCustSelected = new Alert(Alert.AlertType.ERROR);
            noCustSelected.setTitle("No Customer Selected");
            noCustSelected.setContentText("Please select an Appointment to update");
            noCustSelected.showAndWait();
        }
    }

    /**
     * This method takes the user to the Update Customer screen.
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            loader.load();

            UpdateCustomerController updateCustomerController = loader.getController();
            updateCustomerController.initialize(CustomerTblView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (NullPointerException e) {
            Alert noCustSelected = new Alert(Alert.AlertType.ERROR);
            noCustSelected.setTitle("No Customer Selected");
            noCustSelected.setContentText("Please select a Customer to Update");
            noCustSelected.showAndWait();
        }
    }

    /**
     * This method initializes the Main Menu.
     */
    @FXML
    void initialize() throws SQLException, ParseException, IOException {
        try {
            currentCustomers.removeAll(CustDAO.getAllCustomers());
            currentDivs.removeAll(DivDAO.getAllDivisions());
            currentCountries.removeAll(CountryDAO.getAllCountries());
            currentApps.removeAll(AppDAO.getAllApps());

            sortCustByCountry.clear();
            sortCustByDiv.clear();
            sortAppsByCountry.clear();
            sortAppsByDiv.clear();

            sortDivisions.clear();

            selectedCountry = null;
            selectedDiv = null;

            countryClearBool = false;
            divClearBool = false;

            //Add all Customers to a new currentCustomers list that can be manipulated by Main Menu actions without messing up actual list
            for (int i = 0; i < CustDAO.getAllCustomers().size(); i++) {
                currentCustomers.add(CustDAO.getAllCustomers().get(i));
            }

            //Add all Appointments to a new currentApps list that can be manipulated by Main Menu actions without messing up actual list
            for (int a = 0; a < AppDAO.getAllApps().size(); a++) {
                currentApps.add((AppDAO.getAllApps().get(a)));
            }
            for (int b = 0; b < currentApps.size(); b++) {
                currentApps.get(b).getAppUserStart();
                currentApps.get(b).getAppUserEnd();
            }
            //Add all Divisions that are connected to a Customer, in currentCustomers, to list that will display viewing options for Divisions
            for (int j = 0; j < DivDAO.getAllDivisions().size(); j++) {
                for (int s = 0; s < currentCustomers.size(); s++) {
                    if (DivDAO.getAllDivisions().get(j).getDivisionID() == currentCustomers.get(s).getDivisionID()) {
                        currentDivs.add(DivDAO.getAllDivisions().get(j));
                    }
                }
            }
            //Set the Divisions combo box to contain the currentDivs list
            SortByFLDCombo.setItems(currentDivs);

            //Add all Countries that are connected to a Customer, via currentDivs, to list that will display viewing options for Countries
            for (int k = 0; k < currentDivs.size(); k++) {
                for (int t = 0; t < CountryDAO.getAllCountries().size(); t++) {
                    if (currentDivs.get(k).getCountryID() == CountryDAO.getAllCountries().get(t).getCountryID()) {
                        currentCountries.add(CountryDAO.getAllCountries().get(t));
                    }
                }
            }
            //Set the Countries combo box to contain the currentCountries list
            SortByCountryCombo.setItems(currentCountries);

            //Populate the Customers table
            CustomerTblView.setItems(currentCustomers);
            CustomerIDCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("custID"));
            CustomerNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("custName"));
            CustomerAddressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("custAddress"));
            CustomerPostalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("custPostal"));
            CustomerPhoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("custPhone"));
            CustomerFLDCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionName"));

            //Populate the Appointments table
            AppointmentTblView.setItems(currentApps);
            AppIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appID"));
            AppTitleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appTitle"));
            AppDescriptionCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appDescription"));
            AppLocationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appLocation"));
            AppContactCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactName"));
            AppTypeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appType"));
            AppStartCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appUserStart"));
            AppEndCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("appUserEnd"));
            AppCustomerIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("custID"));
            AppUserIDCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userID"));
        }
        catch(IOException i) {
        }
    }
}

