/**
 * @author Tyler Brown
 */

package model;


import java.time.LocalDateTime;

/**
 * This class is used to create Country objects.
 */
public class Country {

    private int countryID;
    private String countryName;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    public Country(int countryID, String countryName, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.countryName = countryName;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the Country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID the Country Id to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @return the Country Name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * @param countryName the Country Name to set
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return Country create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the Country Created Date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Country Creator
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the Country Creator to set
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
     * @param lastUpdate the Country's Last Update (date) to set
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
     * @param lastUpdatedBy the Country's Last Updater (user) to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Override
    public String toString() {
        return countryName;
    }
}