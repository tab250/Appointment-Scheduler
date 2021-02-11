/**
 * @author Tyler Brown
 */

package utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static java.lang.Class.forName;

/**
 * This class is used to interact with the database, i.e. establishing connections and terminating them.
 */
public class DBConnection {

    //JDBC ip address parts
    private static final String protocol = "jdbc";
    private static final String vendName = ":mysql:";
    private static final String ip = "//wgudb.ucertify.com/WJ07JHH?autoReconnect=true&useSSL=false";

    //JDBC url
    private static final String url = protocol + vendName + ip;
    private static Connection connect = null;

    //Driver reference
    private static final String driver = "com.mysql.jdbc.Driver";

    //Username and password
    private static final String userName = "U07JHH";
    private static final String password = "53689042456";

    /**
     * This method uses above fields to generate a connection with the database.
     */
    //Method to initialize the connection to the database
    public static Connection initConnection() {
        try{
            forName(driver);
            connect = (Connection) DriverManager.getConnection(url, userName, password);
            System.out.println("Connection initiated");
        }
        catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return connect;
    }

    /**
     * This method is called to terminate the connection with the database.
     */
    //Method to terminate the connection to the database
    public static void termConnection() {
        try {
            connect.close();
            System.out.println("Connection terminated");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
