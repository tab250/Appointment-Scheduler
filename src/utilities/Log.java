/**
 * @author Tyler Brown
 */
package utilities;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import static controller.LogInController.*;
import static main.Main.userZone;

/**
 * This class is used for creating and updating a login attempts file.
 */
public class Log {

    public static String logFile = "login_activity.txt";

    /**
     * This method creates a text file to keep track of login attempts.
     */
    public static void createLogFile() throws FileNotFoundException {
        try {
            Path path = Paths.get("login_activity.txt");
            if (!Files.exists(path)) {
                PrintWriter outputFile = new PrintWriter(logFile);

                System.out.println("File created");
                outputFile.close();
            }
        }
        catch (FileNotFoundException f) {
        }
    }

    /**
     * This method updates the text file with every attempted login and includes the attempt number, time of attempt and if it was successful.
     */
    public static void loginAttempt(int attemptNum, LocalDateTime attemptTime, boolean attemptOutcome) throws IOException {
        try {
            FileWriter addToFile = new FileWriter(logFile, true);
            PrintWriter updateFile = new PrintWriter(addToFile);

            String loginOutcome;
            if (attemptOutcome == true) {
                loginOutcome = "yes";
            }
            else {
                loginOutcome = "no";
            }

            updateFile.println("Login Attempt: " + logAttemptNum);
            updateFile.println("Attempt Date:" + logAttemptDT + "   Timezone: " + userZone);
            updateFile.println("Was attempt successful? " + loginOutcome + "\n\n");

            System.out.println("File updated");
            updateFile.close();
        }
        catch (IOException i) {
        }
    }
}
