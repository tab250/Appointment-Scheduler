/**
 * @author Tyler Brown
 */
package main;

import DAO.*;
import com.mysql.jdbc.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DBConnection;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.ZoneId;
import static utilities.Log.createLogFile;

/**
 * This is the Main class for the program. All other classes, methods, and files are called via this class. The lambda
 * expressions can be found in controller/AddCustomerController and controller/LogInController.
 */
public class Main extends Application {

    public static Connection conn = null;
    public static ZoneId userZone;

    /**
     * This method presents the Main Menu screen to the user.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception e) {
        }
    }

    /**
     * This is the Main method for the program.
     */
    public static void main(String[] args) throws SQLException {
        try {
            /**
             * This field is used in other classes to determine the user's location for time adjustments
             */
            userZone = ZoneId.systemDefault();

            /**
             * This method establishes the connect to the database
             */
            conn = DBConnection.initConnection(); //Establish database connection

            /**
             * The following six methods pull all information from the connected database
             */
            CountryDAO.readCountries();
            DivDAO.readDivisions();
            AppDAO.readApps();
            CustDAO.readCustomers();
            ContactDAO.readContact();
            UserDAO.readUsers();

            createLogFile();

            launch(args); //Run all other code

            /**
             * This method terminates the connect to the database
             */
            DBConnection.termConnection(); //Disconnect from the database last
        }
        catch (SQLException | FileNotFoundException s) {
        }
    }
}