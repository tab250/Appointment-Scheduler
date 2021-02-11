/**
 * @author Tyler Brown
 */

package model;

/**
 * This class is used to create Contact objects.
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String contactEmail;

    public Contact (int contactID, String contactName, String contactEmail) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return the Contact Id
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID the Contact ID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * @return the Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the Contact Name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the Contact Email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * @param contactEmail the Contact Email to set
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return (Integer.toString(contactID) + ":  " + contactName);
    }
}
