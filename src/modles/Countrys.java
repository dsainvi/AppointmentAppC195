package modles;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//getter setter countries
/**
 * country class
 */
public class Countrys {
    /**
     * countryId
     */
    private IntegerProperty countryId;
    /**
     * countryName
     */
    private StringProperty countryName;
    /**
     * Countrys
     * turned country id and name into new SimpleProperties.
     */
    public Countrys() {
        this.countryId = new SimpleIntegerProperty();
        this.countryName = new SimpleStringProperty();
    }
    /**
     * getCountryName
     * gets a string country name and returns countryName.
     * @return countryName
     */
    public StringProperty getCountryName() {
        return countryName;
    }
    /**
     *set country id
     * changes int id into Integer countryId.
     * @param id
     */
    public void setCountryId(int id) {
        this.countryId.set(id);
    }
    /**
     * setCountryName
     * changes the String name into a StringProperty countryName.
     * @param name
     */
    public void setCountryName(String name) {
        this.countryName.set(name);
    }

}
