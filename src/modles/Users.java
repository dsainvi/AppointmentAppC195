package modles;
import javafx.beans.property.IntegerProperty;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.StringProperty;
//user model declares users variables to get and set from sqlDB
/**
 *Users class
 *holds all the getters and setter needed  for User data
 * create empty variables inorder to populate them with User data found in the sql db
 */
public class Users {
    private StringProperty usersName;
    private StringProperty passWord;
    private IntegerProperty usersId;
//allows userid name and password to be used by other docs
    /**
     * Users turns private user variable into new simple Properties.
     */
    public Users(){
        this.usersId=new SimpleIntegerProperty();
        this.usersName=new SimpleStringProperty();
        this.passWord=new SimpleStringProperty();
    }
    /**
     * get userid number from the db
     * @return usersId
     */
    public IntegerProperty getUsersId() {return usersId;}
    /**
     * get username from  the db
     * @return  usersName
     */
    public StringProperty getUsersName() {return usersName;}
    /**
     * gets password from db
     * @return  passWord
     */
    public StringProperty getPassWord() {return passWord;}
    /**
     * sets userId turn it from an int to a IntegerProperty
     * @param usersId
     */
    public void setUsersId(int usersId) {this.usersId.set(usersId);}
    /**
     * sets userName turn it from a String into a StringProperty
     * @param usersName
     */
    public void setUsersName(String usersName){this.usersName.set(usersName);}
    /**
     * sets password turn it from a String into a StringProperty
     * @param passWord
     */
    public void setPassWord(String passWord){this.passWord.set(passWord);}
/**
 * @source C195 Code RepositorySelectDays\DAODemo\src\daodemo\model
 */
}