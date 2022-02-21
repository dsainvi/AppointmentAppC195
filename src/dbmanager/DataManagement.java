package dbmanager;
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

    public  static int appointId;

    public static String appointTime;


}
