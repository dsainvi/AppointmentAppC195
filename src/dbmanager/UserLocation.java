package dbmanager;
import java.util.Locale;
import java.util.TimeZone;

//gets timezone base on the location of  the user
/**
 * UserLocation
 *  find the users current location inorder to present the proper timezone
 */
public class UserLocation {
    /**
     * grabMyLocation
     * discovers your current location. Then it figures out the clients' timezone base on the location of  the user
     * @return Location
     */
    public static String grabMyLocation(){
        String myLocation = TimeZone.getDefault().getDisplayName() + "," + Locale.getDefault().getCountry();
        return myLocation;
    }
}
