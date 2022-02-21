package modles;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Contacts class
 */
public class Contacts {
    /**
     * contact id
     */
    private IntegerProperty id;

    /**
     * contact name
     */
    private StringProperty name;

    /**
     * contact email
     */
    private StringProperty email;

    /**
     *  Contacts turns the variables into new simple string and int properties.
     *  id, name, and email.
     */
    public Contacts() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
    }

    /**
     * Gets the contact id property
     * @return
     */
    public IntegerProperty getContactId() { return id;
    }


    /**
     *getContactsName
     * Gets the contact name property.
     * @return name
     */
    public StringProperty getContactsName() {
        return name;
    }

    /**
     * turns int into an intProperty
     * Sets contact id
     * @param id
     */
    public void setContactsId(int id) {
        this.id.set(id);
    }

    /**
     * turns a string into stringProperty.
     * Sets contact name.
     * @param name
     */

    public void setContactsName(String name) {
        this.name.set(name);
    }

    /**
     * sets emails
     *  turns a string email into stringProperty email.
     * @param email
     */
    public void setContactsEmail(String email) {
        this.email.set(email);
    }

}
