package modles;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
//gets and sets appointments
/**
 * AppointMents class
 */
public class AppointMents {
    /**
     *  appointment id = id
     */
    private IntegerProperty id;
    /**
     *  customer id
     */
    private IntegerProperty customerId;

    /**
     *  user id
     */
    private IntegerProperty userId;

    /**
     * contact id
     */
    private IntegerProperty contactId;

    /**
     *  title
     */
    private StringProperty title;

    /**
     * description
     */
    private StringProperty description;

    /**
     *  location
     */
    private StringProperty location;

    /**
     *  type
     */
    private StringProperty type;

    /**
     *  startTime
     */
    private StringProperty startTime;

    /**
     * endTime
     */
    private StringProperty endTime;

    /**
     * AppointMents
     * Sets up AppointMents set every variable equal to a new SimpleProperty
     */
    public AppointMents() {
        this.id = new SimpleIntegerProperty();
        this.customerId = new SimpleIntegerProperty();
        this.userId = new SimpleIntegerProperty();
        this.contactId = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.startTime = new SimpleStringProperty();
        this.endTime = new SimpleStringProperty();
    }

    /**
     * getId
     * is an Integer variable used to get the appointment id
     * used by appointmentScreen in initialize, updateActionButton, and DeleteAppointmentAction stage.
     * @return  id
     */
    public IntegerProperty getAppointId() {
        return id;
    }



    /**
     * getCustomerId
     * used by appointmentScreen in initialize. it helps populate customerId column in the Appointment Table.
     * @return  customerId
     */
    public IntegerProperty getCustomerId() {

        return customerId;
    }
    /**
     * getUserId
     *  it is used by initialize.
     *  int userid equals the userId number.
     * @return  userId
     */
    public IntegerProperty getUserId() {

        return userId;
    }
    /**
     *getContactId
     * it is used by appointmentScreen in initialize. it helps populate contact column in the Appointment Table.
     * @return  contactId
     */
    public IntegerProperty getContactId() {

        return contactId;
    }
    /**
     *  getTitle
     *  is used by appointmentScreen in initialize.  it is used by an array list called appoint and given the variable name tittle.
     *  it also helps populate the Appointment Table.
     * @return title
     */
    public StringProperty getTitle() {
        return title;
    }
    /**
     * getDescription
     * it is used by appointmentScreen in initialize and attached to an array list called appoint and given the variable name description..
     *  getDescription also populated the description column in the Appointment Table.
     * @return description
     */
    public StringProperty getDescription() {
        return description;
    }
    /**
     * getLocation
     * gets location
     *  is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @return  location
     */
    public StringProperty getLocation() {
        return location;
    }

    /**
     * getType
     * gets type
     *  is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @return type
     */
    public StringProperty getType() {
        return type;
    }

    /**
     *gets startTime
     *  is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @return starttime
     */
    public StringProperty getStartTime() {
        return startTime;
    }
    /**
     *  getEndTime
     * gets endTime
     *  is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @return endtime
     */
    public StringProperty getEndTime() {
        return endTime;
    }




    /**
     * setAppointmentId
     * Sets appointmentId
     * is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param id   appointmentId
     */
    public void setAppointmentId(int id) {

        this.id.set(id);
    }

    /**
     * setId
     * Sets  appointmentId
     *  is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param userId
     */
    public void setUserId(int userId) {

        this.userId.set(userId);
    }
    /**
     * sets contactId
     *  is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param id   contactid
     */
    public void setContactId(int id) {
        this.contactId.set(id);
    }
    /**
     * setCustomerId
     * Sets customerid
     * is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param id     customerid
     */
    public void setCustomerId(int id) {

        this.customerId.set(id);
    }

    /**
     * setAppointmentEndTime
     * sets  appointment endtime
     * is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param time  time used to get appointment endtime
     */
    public void setAppointmentEndTime(String time) {
        this.endTime.set(time);
    }
    /**
     * setAppointmentType
     *sets  appointment type
     *  is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param type
     */
    public void setAppointmentType(String type) {
        this.type.set(type);
    }

    /**
     * setAppointmentStartTime
     * is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param time
     */
    public void setAppointmentStartTime(String time) {
        this.startTime.set(time);
    }
    /**
     * setAppointmentLocation
     * Sets the appointment location
     * is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param location  the appointment location
     */
    public void setAppointmentLocation(String location) {
        this.location.set(location);
    }

    /**
     * setAppointmentDescription
     * sets  appointment description
     * is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param desc  appointment description
     */
    public void setAppointmentDescription(String desc) {
        this.description.set(desc);
    }

    /**
     * setAppointmentTitle
     * sets appointment title
     * is used by appointmentScreen in initialize. it helps populate the Appointment Table.
     * @param title
     */
    public void setAppointmentTitle(String title) {
        this.title.set(title);
    }


}
