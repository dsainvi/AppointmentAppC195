package modles;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Customers class
 */
public class Customers{
    /**
     * customerid
     */
    private IntegerProperty cusId;
    /**
     * customername
     */
    private  StringProperty cusname;
    /**
     * customeraddress
     */
    private StringProperty cusAddress;
    /**
     * customerzip
     */
    private StringProperty zipCode;
    /**
     * customerphone
     */
    private StringProperty telephoneNumber;
    /**
     * customerDivision
     */
    private IntegerProperty firstLvlDivision;


    /**
     * customers simple properties
     * recreating private variable and adding them to a public Customers
     */
// customer simple property
    public Customers(){
        this.cusId=new SimpleIntegerProperty();
        this.cusname=new SimpleStringProperty();
        this.cusAddress=new SimpleStringProperty();
        this.firstLvlDivision=new SimpleIntegerProperty();
        this.telephoneNumber=new SimpleStringProperty();
        this.zipCode=new SimpleStringProperty();


    }

    /**
     * getcusId
     * gets customer data  inorder to populate customer tables
     * @return cusId
     */
    // gets customer data  inorder to populate customer tables
    public IntegerProperty getcusId(){return cusId;}
    /**
     * getcusName
     * @return cusname
     */
    public  StringProperty getcusName(){return cusname;}
    /**
     * getcusAddress
     * @return cusAddress
     */
    public  StringProperty getcusAddress(){return cusAddress;}
    /**
     * getfirstLvLDivision
     * int
     * @return firstLvlDivision
     */
     public IntegerProperty getfirstLvLDivision(){return firstLvlDivision;}


    /**
     * getTelephoneNumber
     * string
     * @return telephoneNumber
     */
    public  StringProperty getTelephoneNumber(){return telephoneNumber;}
    /**
     * getZipCode
     * @return zipCode
     */
    public  StringProperty getZipCode(){return zipCode;}

    /**
     * setCusId
     * sets int id into cusId
     * @param id
     */
    //sets customer data to matching variables sets int / strings into property values
    public  void setCusId(int id){this.cusId.set(id);}


    /**
     * setCusname
     * @param cusName
     */
    public  void setCusname(String cusName){this.cusname.set(cusName);}

    /**
     * setCusAddress
     * @param address
     */
    public  void setCusAddress(String address){this.cusAddress.set(address);}

    /**
     * setfirstLvlDivision
     * @param divId
     */
    public void setfirstLvLDivision(int divId){this.firstLvlDivision.set(divId);}



    /**
     * setTelephoneNumber
     * @param telephone
     */
     public void setTelephoneNumber(String telephone){this.telephoneNumber.set(telephone);}

    /**
     * setZipCode
     * @param zipCode
     */
     public void setZipCode(String zipCode){this.zipCode.set(zipCode);}


}
