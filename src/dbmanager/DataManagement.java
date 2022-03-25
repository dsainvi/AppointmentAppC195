package dbmanager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import modles.AppointMents;
import java.time.LocalDateTime;
/**
 * DataManagement
 * stores useful information about the current user and uses that information to present an accurate and organized seduce
 *  saves username userid and customer id to be reused later in controllers.
 */
//saves username userid and customer id to be reused later in controllers
public class DataManagement {
    /**
     * currentUserName
     * saves the username used to log-in to the app.
     */
    public static String currentUserName;
    /**
     * userIdNumber
     * saves the userId number that considers with the username and password used to log-in.
     * It is called twice in appointmentScreen.
     * Once in add-appointment button and another time in update-appointment button.
     *
     */
    public static int userIdNumber;
    /**
     * customerid
     *  customer id to be reused later in controllers
     *  it is used by two controllers, the homepage and the appointment screen inorder to accurately add, delete, and update appointments.
     */
    public static int customersid;
    public static int appointId;
    public static String appointTime;
    public static LocalDateTime appointSTime;
    public static LocalDateTime appointETime;
    public static LocalDateTime aSDateTime;
    public static LocalDateTime aEDateTime;
    public static int clones;
    public static int overcount;
    public  static int rowAdded;
    private LocalDateTime apSTime;
    private LocalDateTime apETime;
    public static ObservableList<DataManagement> appointTimeList;
    public DataManagement(LocalDateTime apSTime, LocalDateTime apETime) {
            this.apSTime = apSTime;
            this.apETime = apETime;
    }
    public LocalDateTime getApSTime(){return apSTime;}
    public LocalDateTime getApETime() {return apETime;}
    public void setApSTime(LocalDateTime apSTime) {
        this.apSTime = apSTime;
    }
    public void setApETime(LocalDateTime apETime) {
        this.apETime = apETime;
    }

}
