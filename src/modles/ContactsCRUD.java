package modles;

import dbmanager.QueryManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * ContactsCRUD class
 */
public class ContactsCRUD {

    /**
     * Gets all contact records and saves it in a list
     * while loop goes throw each item in the till all variables have a value
     * recalibrates variables by setting them to Contact. Then  it adds contact to contactsList
     * @return   contactsList
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<Contacts> getEveryRecords() throws ClassNotFoundException, SQLException {
        String q = "Select * from client_schedule.contacts";
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        QueryManager.manifestQuery(q);
        ResultSet reslt = QueryManager.getReslt();

        while (reslt.next()) {
            int contactId =reslt.getInt("Contact_ID");
            String contactName =reslt.getString("Contact_Name");
            String contactEmail =reslt.getString("Email");

            Contacts contact = new Contacts();
            contact.setContactsId(contactId);
            contact.setContactsName(contactName);
            contact.setContactsEmail(contactEmail);
            contactsList.add(contact);

        }
        return contactsList;
    }

    /**
     * Gets contactId from contact name
     * uses name as a key
     * @param name  contact Name
     * @return  contactId
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int getContactsIdFromName(String name) throws ClassNotFoundException, SQLException {
        String q = "select Contact_ID from contacts where Contact_Name = '"+name+"'";

        try {
            QueryManager.manifestQuery(q);
            ResultSet reslt = QueryManager.getReslt();
            if(reslt.next()) {
                int contactId = Integer.parseInt(reslt.getString(1));
                return contactId;
            }
            else {
                return 0;
            }

        } catch (SQLException e) {
            System.out.println("Error contact id" + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * finds the name of your contacts based on the contactId
     * it uses contactId as a key and contactName is the value
     * @param contactId
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String getContactName(int contactId) throws ClassNotFoundException, SQLException {
        String q = "select Contact_Name from contacts where Contact_ID =" +contactId+"";

        try {
            QueryManager.manifestQuery(q);
            ResultSet rs = QueryManager.getReslt();
            if(rs.next()) {
                String contactName = rs.getString(1);         ;
                return  contactName;
            }
            return "";

        } catch (SQLException e) {
            System.out.println("Error while getting the name of a contact " + e);
            e.printStackTrace();
            throw e;
        }
    }



}
