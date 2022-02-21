package modles;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
//state id name and number

/**
 * Division model class
 */
public class Divisions {
    /**
     *  division id
     */
    private IntegerProperty divisionId;

    /**
     *  division country id
     */
    private IntegerProperty countryId;

    /**
     *  division name
     */
    private StringProperty divisionName;

    /**
     * Divisions
     * turned division id, name and countryId into new SimpleProperties.
     */
    public Divisions() {
        this.divisionId = new SimpleIntegerProperty();
        this.countryId = new SimpleIntegerProperty();
        this.divisionName = new SimpleStringProperty();
    }

    /**
     *
     */
    public  IntegerProperty getDivisionId(){return divisionId;}
    /**
     *
     * get countryId
     * @return countryId
     */
    public IntegerProperty getCountryId() {
        return countryId;
    }
    /**
     * gets division Name
     * @return divName
     */
    public StringProperty getDivisionsName() {
        return divisionName;
    }

    /**
     * sets country id turns int id into intPorperty countryId
     * @param id
     */
    public void setCountryId(int id) {
        this.countryId.set(id);
    }

    /**
     * sets division name turns string name  into stringPorperty divisionName
     * @param name
     */
    public void setDivisionsName(String name) {
        this.divisionName.set(name);
    }
    /**
     * sets division id turns int id into intPorperty divisionId
     * @param id
     */
    public void setDivisionsId(int id) {
        this.divisionId.set(id);
    }


}
