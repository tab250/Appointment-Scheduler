/**
 * @author Tyler Brown
 */
package controller;


import DAO.AppDAO;
import DAO.ContactDAO;
import DAO.CustDAO;
import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import static DAO.AppDAO.appLastID;
import static controller.LogInController.loginName;
import static utilities.DateTime.*;

/**
 * This class is used to control all user interactions that occur with the Add Appointment screen.
 */
public class AddAppointmentController {
    Stage stage;
    Parent scene;


    @FXML
    private TextField AppointmentTitleTxt;

    @FXML
    private TextField AppointmentDescriptionTxt;

    @FXML
    private TextField AppointmentLocationTxt;

    @FXML
    private ComboBox<Contact> ContactCombo;

    @FXML
    private TextField AppointmentTypeTxt;


    @FXML
    private TextField AppointmentStartDTTxt;

    @FXML
    private TextField AppointmentEndDTTxt;

    @FXML
    private ComboBox<Customer> CustomerCombo;

    @FXML
    private ComboBox<User> UserCombo;

    @FXML
    private Button SaveBtn;

    @FXML
    private Button CancelBtn;

    /**
     * This method returns the user to the Main Menu without saving any information.
     * @param event Refers to the event of user clicking on 'Cancel' button under on the screen.
     * @throws IOException Occurs if the main screen can not obtain proper input from ObservableArray Lists.
     */
    @FXML
    void onActionCancelAppointment(ActionEvent event) throws IOException {
        try {
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        catch (IOException i) {
        }
    }

    /**
     * This method returns the user to the Main Menu and adds new Appointment to table/observable list and database.
     * @param event Refers to the event of the user clicking on the 'Save' button on the screen.
     * @throws SQLException Occurs if the createApp method throws an error when communicating with the DB.
     * @throws IOException Occurs if the main screen can not obtain proper input from ObservableArray Lists.
     */
    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException, IOException {
        try {
            int scheduleApp = 0;
            boolean noOverlap = true;

            int appID = appLastID() + 1;
            String appTitle = AppointmentTitleTxt.getText();
            String appDescription = AppointmentDescriptionTxt.getText();
            String appLocation = AppointmentLocationTxt.getText();
            String appType = AppointmentTypeTxt.getText();
            LocalDateTime appStart = convertToUTC(LocalDateTime.parse(formatDT(AppointmentStartDTTxt.getText())));
            LocalDateTime appEnd = convertToUTC(LocalDateTime.parse(formatDT(AppointmentEndDTTxt.getText())));
            LocalDateTime appCreateDate = LocalDateTime.now();
            String appCreatedBy = loginName;
            LocalDateTime appLastUpdate = LocalDateTime.now();
            String appLastUpdatedBy = loginName;
            int custID = CustomerCombo.getSelectionModel().getSelectedItem().getCustID();
            int userID = UserCombo.getSelectionModel().getSelectedItem().getUserID();
            int contactID = ContactCombo.getSelectionModel().getSelectedItem().getContactID();
            String contactName = ContactCombo.getSelectionModel().getSelectedItem().getContactName();

            //Make sure the start date/time is not after the end date/time
            if (appStart.isAfter(appEnd)) {
                Alert mixUp = new Alert(Alert.AlertType.ERROR);
                mixUp.setTitle("Incorrect Date/Time");
                mixUp.setContentText("The Start date/time is AFTER the End date/time. Please adjust.");
                mixUp.showAndWait();

                AppointmentEndDTTxt.clear();
            }
            else {
                switch (checkOfficeHours(appStart, appEnd)) {
                    case 0:
                        scheduleApp += 1;

                        if (conflictingTime(appStart, appEnd) == true) {
                            noOverlap = true;
                        }
                        else {
                            noOverlap = false;
                        }
                        break;
                    case 1:
                        Alert weekend = new Alert(Alert.AlertType.ERROR);
                        weekend.setTitle("Trying to Schedule Appointment on Weekend");
                        weekend.setContentText("The date you selected for either the START or END of the Appointment is on the weekend. " +
                                "Please select a day between Monday and Friday.");
                        weekend.showAndWait();

                        AppointmentStartDTTxt.clear();
                        AppointmentEndDTTxt.clear();
                        break;
                    case 2:
                        Alert officeClosed = new Alert(Alert.AlertType.ERROR);
                        officeClosed.setTitle("Trying to Schedule Appointment Outside of Office Hours");
                        officeClosed.setContentText("The time you selected for either the START or END of the Appointment is not during office hours. " +
                                "Please select a time between 0800 and 2200 hours Eastern Standard Time (EST)");
                        officeClosed.showAndWait();

                        AppointmentStartDTTxt.clear();
                        AppointmentEndDTTxt.clear();
                        break;
                    case 3:
                        Alert dateAndTime = new Alert(Alert.AlertType.ERROR);
                        dateAndTime.setTitle("Trying to Schedule Appointment when Office is Closed");
                        dateAndTime.setContentText("The time and date you selected for the END of the Appointment is not during " +
                                "office hours or days. Please select and appropriate date and time. Office hours " +
                                "are 0800 to 2200 Monday through Friday Eastern Standard Time (EST)");
                        dateAndTime.showAndWait();

                        AppointmentStartDTTxt.clear();
                        AppointmentEndDTTxt.clear();
                        break;
                }
            }

            if (scheduleApp == 1 && noOverlap == true) {
                Appointment app = new Appointment(appID, appTitle, appDescription, appLocation, appType, appStart, appEnd, appCreateDate,
                        appCreatedBy, appLastUpdate, appLastUpdatedBy, custID, userID, contactID, contactName);

                AppDAO.createApp(appTitle, appDescription, appLocation, appType, appStart, appEnd, appCreateDate,
                        appCreatedBy, appLastUpdatedBy, custID, userID, contactID, app);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch (SQLException s) {
        }
        catch (IOException i) {
        }
    }

    /**
     * This method initializes the Add Appointment screen with any relevant information.
     */
    @FXML
    void initialize() {
        Alert dateAndTime = new Alert(Alert.AlertType.INFORMATION);
        dateAndTime.setTitle("Date Time Format");
        dateAndTime.setContentText("Please make sure to put an underscore '_' between the date and time for Start Date/Time and End Date/Time. ");
        dateAndTime.showAndWait();

        ContactCombo.setItems(ContactDAO.getAllContacts());
        CustomerCombo.setItems(CustDAO.getAllCustomers());
        UserCombo.setItems(UserDAO.getAllUsers());
    }
}
