/**
 * @author Tyler Brown
 */

package model;

import java.time.LocalDateTime;
import static utilities.DateTime.convertToZone;


/**
 * This class is used to create Appointment objects.
 */
public class Appointment {

    private int appID;
    private String appTitle;
    private String appDescription;
    private String appLocation;
    private String appType;
    private LocalDateTime appUTCStart;
    private LocalDateTime appUTCEnd;
    private LocalDateTime appCreateDate;
    private String appCreatedBy;
    private LocalDateTime appLastUpdate;
    private String appLastUpdatedBy;
    private int custID;
    private int userID;
    private int contactID;

    private String contactName;

    private LocalDateTime appUserStart;
    private LocalDateTime appUserEnd;

    public Appointment(int appID, String appTitle, String appDescription, String appLocation, String appType, LocalDateTime appUTCStart,
                       LocalDateTime appUTCEnd, LocalDateTime appCreateDate, String appCreatedBy, LocalDateTime appLastUpdate,
                       String appLastUpdatedBy, int custID, int userID, int contactID, String contactName) {
        this.appID = appID;
        this.appTitle = appTitle;
        this.appDescription = appDescription;
        this.appLocation = appLocation;
        this.appType = appType;
        this.appUTCStart = appUTCStart;
        this.appUTCEnd = appUTCEnd;
        this.appCreateDate = appCreateDate;
        this.appCreatedBy = appCreatedBy;
        this.appLastUpdate = appLastUpdate;
        this.appLastUpdatedBy = appLastUpdatedBy;
        this.custID = custID;
        this.userID = userID;
        this.contactID = contactID;

        this.contactName = contactName;

        appUserStart = convertToZone(appUTCStart);
        appUserEnd = convertToZone(appUTCEnd);
    }

    /**
     * @return the Appointment ID
     */
    public int getAppID() { return appID; }

    /**
     * @param appID the Appointment Id to set
     */
    public void setAppID(int appID) { this.appID = appID; }

    /**
     * @return the Appointment Title
     */
    public String getAppTitle() { return appTitle; }

    /**
     * @param appTitle the Appointment Title to set
     */
    public void setAppTitle(String appTitle) { this.appTitle = appTitle; }

    /**
     * @return the Appointment Description
     */
    public String getAppDescription() { return appDescription; }

    /**
     * @param appDescription the Appointment Description to set
     */
    public  void setAppDescription(String appDescription) { this.appDescription = appDescription; }

    /**
     * @return the Appointment Location
     */
    public String getAppLocation() { return appLocation; }

    /**
     * @param appLocation the Appointment Location to set
     */
    public  void setAppLocation(String appLocation) { this.appLocation = appLocation; }

    /**
     * @return the Appointment Type
     */
    public String getAppType() { return appType; }

    /**
     * @param appType the Appointment Type to set
     */
    public void setAppType(String appType) { this.appType = appType; }

    /**
     * @return the Start time in UTC (for the appointment)
     */
    public LocalDateTime getAppUTCStart() { return appUTCStart; }

    /**
     * @param appUTCStart the Appointment Start time (UTC) to set
     */
    public void setAppUTCStart(LocalDateTime appUTCStart) {  this.appUTCStart = appUTCStart; }

    /**
     * @return the End time in UTC (for the appointment)
     */
    public LocalDateTime getAppUTCEnd() { return appUTCEnd; }

    /**
     * @param appUTCEnd the Appointment End time (UTC) to set
     */
    public void setAppUTCEnd(LocalDateTime appUTCEnd) { this.appUTCEnd = appUTCEnd; }

    /**
     * @return Appointment create date
     */
    public LocalDateTime getAppCreateDate() { return appCreateDate; }

    /**
     * @param appCreateDate the Appointment Created Date to set
     */
    public void setAppCreateDate(LocalDateTime appCreateDate) { this.appCreateDate = appCreateDate; }

    /**
     * @return Appointment Creator
     */
    public String getAppCreatedBy() { return appCreatedBy; }

    /**
     * @param appCreatedBy the Appointment Creator to set
     */
    public void setAppCreatedBy(String appCreatedBy) { this.appCreatedBy = appCreatedBy; }

    /**
     * @return Last Update (date)
     */
    public LocalDateTime getAppLastUpdate() { return appLastUpdate; }

    /**
     * @param appLastUpdate the Appointment's Last Update (date) to set
     */
    public void setAppLastUpdate(LocalDateTime appLastUpdate) { this.appLastUpdate = appLastUpdate; }

    /**
     * @return Last Updated By (user)
     */
    public String getAppLastUpdatedBy() { return appLastUpdatedBy; }

    /**
     * @param appLastUpdatedBy the Appointment's Last Updater (user) to set
     */
    public void setAppLastUpdatedBy(String appLastUpdatedBy) { this.appLastUpdatedBy = appLastUpdatedBy; }

    /**
     * @return the Customer Id
     */
    public int getCustID() { return custID; }

    /**
     * @param custID the Appointment's Customer ID to set
     */
    public void setCustID(int custID) { this.custID = custID; }

    /**
     * @return the User Id
     */
    public int getUserID() { return userID; }

    /**
     * @param userID the Appointment's User ID to set
     */
    public void setUserID(int userID) { this.userID = userID; }

    /**
     * @return the Contact Id
     */
    public int getContactID() { return contactID; }

    /**
     * @param contactID the Appointment's Contact ID to set
     */
    public void setContactID(int contactID) { this.contactID = contactID; }

    /**
     * @return the Contact Name
     */
    public String getContactName() { return contactName; }

    /**
     * @param contactName the Appointment's Contact Name to set
     */
    public void setContactName(String contactName) { this.contactName = contactName; }

    /**
     * @return the Users Start time (for the appointment)
     */
    public LocalDateTime getAppUserStart() { return appUserStart; }

    public void setAppUserStart(LocalDateTime appUTCStart) { appUserStart = convertToZone(appUserStart); }

    /**
     * @return the Users End time (for the appointment)
     */
    public LocalDateTime getAppUserEnd() { return appUserEnd; }

    public void setAppUserEnd(LocalDateTime appUTCEnd) { appUserEnd = convertToZone(appUserEnd); }
}
