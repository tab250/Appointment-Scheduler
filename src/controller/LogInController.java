/**
 * @author Tyler Brown
 */
package controller;


import DAO.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utilities.DBConnection;
import utilities.Report;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

import static main.Main.userZone;
import static utilities.DateTime.within15;
import static utilities.Log.loginAttempt;

/**
 * This class is used to control all user interactions that occur with the Log In screen.
 */
public class LogInController {

    Stage stage;
    Parent scene;

    public static String loginName;
    private static String loginPass;

    public static int logAttemptNum = 0;
    public static boolean logAttemptSuccess = false;
    public static LocalDateTime logAttemptDT;

    ResourceBundle bundle;

    @FXML
    private Label SchedulerLbl;

    @FXML
    private Label LoginLbl;

    @FXML
    private Label ErrorLbl;

    @FXML
    private Label UsernameLbl;

    @FXML
    private Label PasswordLbl;

    @FXML
    private TextField UsernameTxtField;

    @FXML
    private TextField PasswordTxtField;

    @FXML
    private Label UserLocationLbl;

    @FXML
    private Button LogInBtn;

    @FXML
    private Button ExitBtn;

    /**
     * This method closes the program when the exit button is selected.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        DBConnection.termConnection();
        System.exit(0);
    }

    /**
     * This method checks the login information to determine what type of user is trying to login and accepts if information is correct.
     * A lambda expression along with a DateTime method(utilities/DateTime) to display a message to the user if there is an appointment
     * coming soon. The same interface is used to generate another popup that displays several reports for the user detailing. The lambda
     * expression is used here to better utilize the within15 method in the utilities/DateTime class. This interface was also used to try
     * a multi-lined lambda expression.
     */
    @FXML
    void onActionLogIn(ActionEvent event) throws IOException {
        try {
            for (int i = 0; i < UserDAO.getAllUsers().size(); i++) {
                if (UsernameTxtField.getText().equals("test")
                        && PasswordTxtField.getText().equals("test")) {
                    loginName = UsernameTxtField.getText();
                    loginPass = PasswordTxtField.getText();
                }
                else if (UsernameTxtField.getText().equals("test")
                        && !PasswordTxtField.getText().equals("test")){
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        ErrorLbl.setText(bundle.getString("Invalid_password"));
                        PasswordTxtField.clear();
                    }
                    else {
                        ErrorLbl.setText("Invalid password");
                        PasswordTxtField.clear();
                    }
                }
                else if (!UsernameTxtField.getText().equals("test")
                        && PasswordTxtField.getText().equals("test")){
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        ErrorLbl.setText(bundle.getString("Invalid_username"));
                        UsernameTxtField.clear();
                    }
                    else {
                        ErrorLbl.setText("Invalid username");
                        UsernameTxtField.clear();
                    }
                }
                else {
                    if (Locale.getDefault().getLanguage().equals("fr")) {
                        ErrorLbl.setText(bundle.getString("Invalid_username_password"));
                        UsernameTxtField.clear();
                        PasswordTxtField.clear();
                    }
                    else {
                        ErrorLbl.setText("Invalid Username and Password");
                        UsernameTxtField.clear();
                        PasswordTxtField.clear();
                    }
                }
            }
            if (loginName != null && loginPass != null) {
                if (loginName.equals("test") && loginPass.equals("test")) {
                    Report incomingApp = () -> {
                        if (within15() != null) {
                            Alert appSoon = new Alert(Alert.AlertType.ERROR);
                            appSoon.setTitle("Appointment Soon!");
                            appSoon.setContentText("There is an appointment within 15 minutes! \n\nAppointment ID: " + within15().getAppID() +
                                    "\nAppointment Date: " + within15().getAppUserStart() + "\nAppointment Type: " +
                                    within15().getAppType() + ".");
                            appSoon.showAndWait();
                        }
                        else if (within15() == null){
                            Alert noAppSoon = new Alert(Alert.AlertType.ERROR);
                            noAppSoon.setTitle("No Appointment Soon!");
                            noAppSoon.setContentText("There are no appointments within 15 minutes.");
                            noAppSoon.showAndWait();
                        }
                    };
                    incomingApp.reportInfo();

                    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();

                    logAttemptNum += 1;
                    logAttemptDT = LocalDateTime.now();
                    logAttemptSuccess = true;

                    loginAttempt(logAttemptNum, logAttemptDT, logAttemptSuccess);
                }
            }
            else {
                logAttemptNum += 1;
                logAttemptDT = LocalDateTime.now();
                logAttemptSuccess = false;

                loginAttempt(logAttemptNum, logAttemptDT, logAttemptSuccess);
            }
        }
        catch (IOException i) {
        }
    }

    /**
     * This method initializes the login screen to appear in either Engligh or French depending on user location and displays the users via ZoneId.
     */
    @FXML
    void initialize() {
        bundle = ResourceBundle.getBundle("TransTo_", Locale.getDefault());
        if (Locale.getDefault().getLanguage().equals("fr")) {
            SchedulerLbl.setText(bundle.getString(SchedulerLbl.getText()));
            LoginLbl.setText(bundle.getString(LoginLbl.getText()));
            UsernameLbl.setText(bundle.getString(UsernameLbl.getText()));
            PasswordLbl.setText(bundle.getString(PasswordLbl.getText()));
            LogInBtn.setText(bundle.getString(LogInBtn.getText()));
            ExitBtn.setText(bundle.getString(ExitBtn.getText()));
        }
        UserLocationLbl.setText(String.valueOf(ZoneId.systemDefault()));
    }
}
