/**
 * @author Tyler Brown
 */
package model;


import java.time.LocalDateTime;

/**
 * This class is used to create User objects.
 */
public class User {

    private int userID;
    private String username;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public User(int userID, String username, String password, LocalDateTime createDate, String createdBy,
                LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the User ID
     */
    public int getUserID() { return userID; }

    /**
     * @param userID the User ID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the User Name
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the Username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the User Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the Password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return User create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the User Created Date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return User Creator
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the User Creator to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return Last Update (date)
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the User's Last Update (date) to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return Last Updated By (user)
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the User's Last Updater (user) to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return (Integer.toString(userID) + ":  " + username);
    }
}
