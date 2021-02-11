/**
 * @author Tyler Brown
 */

package utilities;


import java.io.IOException;

/**
 * This interface is used to send a welcome pop up to the user when they sign to tell them if there is an upcoming Appointment.
 */
public interface Report {
    void reportInfo() throws IOException;
}
