package modles;
import dbmanager.QueryManager;
import dbmanager.dbGide;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
// getting and setting appointment screen

/**
 * AppointmentCRUD class
 *
 */
public class AppointMentsCRUD {
    /**
     * Appointment
     * runs SQL query throw QueryManager
     * @param q
     * @param errMessage
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private static void Appointment(String q, String errMessage) throws SQLException, ClassNotFoundException {
        try {
            QueryManager.manifestQuery(q);
        } catch (Exception e) {
            System.out.println(errMessage + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * getAppointMentsObjects
     * makes a list of appointment data
     *sets each variable to appt then appoint is added to appointmentList
     * @param reslt  Resultset
     * @return appointmentList
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static ObservableList<AppointMents> getAppointMentsObjects(ResultSet reslt) throws SQLException, ClassNotFoundException {
        try {
            ObservableList<AppointMents> appointmentList = FXCollections.observableArrayList();

            while (reslt.next()) {
                AppointMents appt = new AppointMents();
                appt.setAppointmentId(reslt.getInt("Appointment_ID"));
                appt.setAppointmentTitle(reslt.getString("Title"));
                appt.setAppointmentDescription(reslt.getString("Description"));
                appt.setAppointmentLocation(reslt.getString("Location"));
                appt.setContactId(reslt.getInt("Contact_ID"));
                appt.setAppointmentType(reslt.getString("Type"));
                appt.setAppointmentStartTime(reslt.getTimestamp("Start").toString());
                appt.setAppointmentEndTime(reslt.getTimestamp("End").toString());
                appt.setCustomerId(reslt.getInt("Customer_ID"));
                appt.setUserId(reslt.getInt("User_ID"));
                appointmentList.add(appt);
            }

            return appointmentList;

        } catch (SQLException e) {
            System.out.println("Error while getting appointment: " + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * hasAppointMents
     * looks to see if there is at least 1 customer appointments
     * uses customerId as the key value to find appointments
     * @param customerId
     * @return  boolean
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static boolean hasAppointMents(int customerId) throws SQLException, ClassNotFoundException {
        String q = "select * from appointments where Customer_ID = "+customerId+"";

        try {
            QueryManager.manifestQuery(q);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<AppointMents> apptList = getAppointMentsObjects(rs);
            if (apptList.isEmpty()) {
                return false;
            }

            return true;

        } catch (SQLException e) {
            System.out.println("Error  " + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Checks for appointments booked at the same time
     * q equals the sql query statement
     * @param customerId   customerId
     * @param startTime
     * @param endTime
     * @return  boolean
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static boolean isDubbleAppointMentTime(int customerId, LocalDateTime startTime, LocalDateTime endTime) throws SQLException, ClassNotFoundException {
        String q = "SELECT COUNT(*) as Duplicate_Count FROM appointments WHERE Customer_ID = "+customerId+"  AND (('"+startTime+"' BETWEEN Start AND End) OR ('"+endTime+"' BETWEEN Start AND End) OR (Start BETWEEN '"+startTime+"' AND '"+endTime+"') OR (End BETWEEN '"+startTime+"' AND '"+endTime+"'))";
        try {
            QueryManager.manifestQuery(q);
            ResultSet rs = QueryManager.getReslt();
            if(rs.next()) {
                if (Integer.parseInt(rs.getString(1)) >= 1) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error " + e);
            e.printStackTrace();
            throw e;
        }
    }



    /**
     * Inserts appointMents
     * adds data into the sql appointment table
     * @param title          appointment title added
     * @param description  appointment description added
     * @param location    appointment location added
     * @param type       appointment Type added
     * @param start         appointment Start datetime added
     * @param end           appointment End datetime  added
     * @param customerId   appointment customerid  added
     * @param userId     appointment userid  added
     * @param contactId    appointment contactid  added
     * @throws SQLException
     * @throws ClassNotFoundException
     * @return
     */
    public static int insertIntoAppointMents(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException, ClassNotFoundException {
        String q = "Insert into appointments(Appointment_ID,Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = dbGide.conect.prepareStatement(q);
        ps.setInt(1,id);
        ps.setString(2,title);
        ps.setString(3,description);
        ps.setString(4,location);
        ps.setString(5,type);
        ps.setTimestamp(6, Timestamp.valueOf(start));
        ps.setTimestamp(7, Timestamp.valueOf(end));
        ps.setInt(8,customerId);
        ps.setInt(9,userId);
        ps.setInt(10,contactId);
        int rowAffected = ps.executeUpdate();
        return rowAffected;
    }

    /**
     * Updates the appointment data in the database
     * query equals the sql statement
     * @param id  id used as key
     * @param title title updated
     * @param description  description updated
     * @param location location updated
     * @param type type updated
     * @param start start datetime  updated
     * @param end    end datetime  updated
     * @param customerId   customer id  updated
     * @param userId    user id  updated
     * @param contactId  contact id  updated
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static int updateAppointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws ClassNotFoundException, SQLException {
        String query = "UPDATE appointments set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = '"+id+"'";
        PreparedStatement ps = dbGide.conect.prepareStatement(query);
        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7,customerId);
        ps.setInt(8,userId);
        ps.setInt(9,contactId);
        int rowAffected = ps.executeUpdate();
        return rowAffected;
    }

    /**
     * Deletes appointments
     * uses id as a key.
     * @param id  AppointMents used to delete appointment
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void deleteAppointment(int id) throws ClassNotFoundException, SQLException {
        String query = "delete from client_schedule.appointments where Appointment_ID = '"+id+"' ";
        Appointment(query, "Error deleting appointment from database");
    }

    /**
     * gets all appointments for a specific customer
     * uses customerId as a key
     * @param customerId used so appointments can be found
     * @return appointmentList a list appointMents
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<AppointMents> getAllRecordsForCustomer(int customerId) throws ClassNotFoundException,SQLException{
        String query = "select * from appointments where Customer_ID = '"+customerId+"'";
        try {
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<AppointMents> appointmentList = getAppointMentsObjects(rs);

            return appointmentList;

        } catch (SQLException e) {
            System.out.println("Error  appointments" + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * gets all appointMent Record and adds it to appointmentList
     * @return  list of all appointMents
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<AppointMents> getAllRecords() throws ClassNotFoundException, SQLException{
        String query = "SELECT * FROM appointments";

        try {
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<AppointMents> appointmentList = getAppointMentsObjects(rs);

            return appointmentList;

        } catch (SQLException e) {
            System.out.println("Error appointMents " + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Gets all appointments 7 days
     * @return an observable list of appointment objects
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<AppointMents> getAllRecordsInNext7Days() throws ClassNotFoundException, SQLException {
        String query = "select * from client_schedule.appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 7 DAY)";

        try {
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<AppointMents> appointmentList = getAppointMentsObjects(rs);

            return appointmentList;

        } catch (SQLException e) {
            System.out.println("Error  appointments seven days  " + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Gets all appointments' month an observable list of appointment objects
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<AppointMents> getAllRecordsInNextMonth() throws ClassNotFoundException, SQLException {
        String query = "select * from client_schedule.appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 1 MONTH)";

        try {
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<AppointMents> appointmentList = getAppointMentsObjects(rs);

            return appointmentList;

        } catch (SQLException e) {
            System.out.println("Error  appointments month"  + e);
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Gets all appointments next 15 minutes
     * @return list of appointments
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static ObservableList<AppointMents> getAllRecordsInNext15Minutes() throws ClassNotFoundException, SQLException {
        String query = "select * from appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 15 MINUTE)";

        try {
            QueryManager.manifestQuery(query);
            ResultSet rs = QueryManager.getReslt();
            ObservableList<AppointMents> appointmentList = getAppointMentsObjects(rs);

            return appointmentList;

        } catch (SQLException e) {
            System.out.println("Error 15 minutes " + e);
            e.printStackTrace();
            throw e;
        }
    }
}
