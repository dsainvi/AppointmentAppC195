package dbmanager;
import java.sql.Connection;
import java.sql.DriverManager;

//jdbc file controller gets info from sql

/**
 * dbGide class
 */
public class dbGide {
    /**
     * protocol equals jdbc.
     * jdbc is able to communicate to both sql and java.
     * jdbc is very useful when managing databases and apps.
     */
//SQL credentals and a connector
private static final String protocol = "jdbc";
    /**
     * vendor
     * vendor is a string that informs the receiver that the interface used to manage the Data is mysql.
     */
private static final String vendor = ":mysql:";
    /**
     * location
     * location is the localhost it refers to the users current device and access net servers linked closely to the host.
     */
private static final String location = "//localhost/";
    /**
     * database name is client_schedule.
     * this tell mysql with db we are looking for.
     */
private static final String databaseName = "client_schedule";
    /**
     * jdbcUrl is a combination of important that helps locate a specific database
     * such as the users location and timezone
     *
     */
private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    /**
     * driver
     * store the jdbc driver code and is used as a translator between sql and java.
     */
private static final String driver = "com.mysql.cj.jdbc.Driver";
    /**
     * sql username
     * The username  in combo with the password are used to get entry into mysql db.
     */
private static final String userName = "sqlUser";
    /**
     * password to sql
     *  Store the password needed to access the DB
     */
private static final String password = "Passw0rd!";
    /**
     * connection
     * uses DriverManager to open a connection with the db
     */
public static Connection conect;

//connects to sqldb via DriverManager
    /**
     * winConnection
     * connections and controls the link from mysql db to the application.
     */
    public static void winConnection() {
        try{
            Class.forName(driver);
            conect = DriverManager.getConnection(jdbcUrl, userName, password);
            System.out.println("Connected");
        }
        catch(Exception e)
        {
            System.out.println("error " + e.getMessage());
        }
}

}

/**
 * @sources C195 Code Repository: C195_QSG\Connecting_to_the_Database_IntelliJ
 *
 */
