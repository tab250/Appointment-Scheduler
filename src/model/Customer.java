/**
 * @author Tyler Brown
 */
package model;


import java.time.LocalDateTime;

/**
 * This class is used to create Customer objects.
 */
public class Customer {
    private int custID;
    private String custName;
    private String custAddress;
    private String custPostal;
    private String custPhone;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int divisionID;
    private String divisionName;

    public Customer(int custID, String custName, String custAddress, String custPostal, String custPhone, LocalDateTime createDate,
                    String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID, String divisionName) {
        this.custID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custPostal = custPostal;
        this.custPhone = custPhone;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionID = divisionID;
        this.divisionName = divisionName;
    }

    /**
     * @return the Customer ID
     */
    public int getCustID() {
        return custID;
    }

    /**
     * @param custID the Customer ID to set
     */
    public void setCustID(int custID) {
        this.custID = custID;
    }

    /**
     * @return the Customer Name
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the Customer Name to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the Customer Address
     */
    public String getCustAddress() {
        return custAddress;
    }

    /**
     * @param custAddress the Customer Address to set
     */
    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    /**
     * @return the Customer Postal Code
     */
    public String getCustPostal() { return custPostal; }

    /**
     * @param custPostal the Customer Postal to set
     */
    public void setCustPostal(String custPostal) {
        this.custPostal = custPostal;
    }

    /**
     * @return the Customer Phone Number
     */
    public String getCustPhone() {
        return custPhone;
    }

    /**
     * @param custPhone the Customer Phone to set
     */
    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    /**
     * @return Customer create date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the Customer Created Date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return Customer Creator
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the Customer Creator to set
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
     * @param lastUpdate the Customer's Last Update (date) to set
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
     * @param lastUpdatedBy the Customer's Last Updater (user) to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the Division ID (linked to the Customer)
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID the Division ID (linked to the Customer) to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @return the Division Name
     */
    public String getDivisionName() {
        return divisionName;
    }

    /**
     * @param divisionName the Division Name to set
     */
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    @Override
    public String toString() {
        return (Integer.toString(custID) + ":  " + custName);
    }
}
