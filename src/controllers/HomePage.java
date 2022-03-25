package controllers;
//customer name, address, postal code, and phone number.
// gets customers page after login page
// Customer IDs are auto-generated, and first-level division (i.e., states, provinces) and country data are collected using separate combo boxes.
//
import java.io.IOException;
import dbmanager.DataManagement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.Main;
import modles.*;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
//customer page
/**
 * home page class displays a customer table and allows user to edit customer data.
 */
public class HomePage {
    public Label userId;
    public Label UserId;
    public Label CusId;
    public Button AppointmentButton;
    public Button DeleteButton;
    @FXML
    private TableColumn<Customers, Integer>customerCountryTable;
    @FXML
    private TableColumn<Customers, String>customerZipcodeTable;
    @FXML
    private TableColumn<Customers, String> customerPhoneTable;
    @FXML
    private TableColumn<Customers, String>customerAddressTable;
    @FXML
    private TableColumn<Customers, String>CustomerNameTable;
    @FXML
    private TableColumn<Customers, Integer> customerIdTable;
    @FXML
    private TableView<Customers>CustomerTable;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField CustomerNameTextField;
    @FXML
    private TextField zipTextField;
    @FXML
    private TextField AddressTextField;
    public TextField CustomerIdTextField;
    @FXML
    private ComboBox<String> CountryComboBox;
    @FXML
    private ComboBox<String> lvlDivisionComboBox;
    /**
     *
     * prepareTable
     *  fills the customer table with customer data stored in cusList
     * @param cusList
     */
    private void prepareTable(ObservableList<Customers> cusList) {CustomerTable.setItems(cusList);}
    /**
     * initialize
     * gets everything ready before Home page is called.
     * sets up table columns with its matching value item
     * it turns buttons clickable and un-clickable, inputs the data to the table.
     * lambda is used for the customer table inorder to know when an item on the table is selected
     * lambda is used for an alert message
     * @throws Exception
     */
    @FXML
    public void initialize()throws Exception{
        AppointmentButton.setDisable(true);
        DeleteButton.setDisable(true);
        customerCountryTable.setCellValueFactory(cellData->cellData.getValue().getfirstLvLDivision().asObject());
        customerZipcodeTable.setCellValueFactory(cellData->cellData.getValue().getZipCode());
        customerPhoneTable.setCellValueFactory(cellData->cellData.getValue().getTelephoneNumber());
        customerAddressTable.setCellValueFactory(cellData->cellData.getValue().getcusAddress());
        CustomerNameTable.setCellValueFactory(cellData->cellData.getValue().getcusName());
        customerIdTable.setCellValueFactory(cellData->cellData.getValue().getcusId().asObject());
        UserId.setText(String.valueOf(DataManagement.userIdNumber));
        ObservableList<Customers>cusList= CustomersCRUD.getEveryCustomer();
        prepareTable(cusList);
        ObservableList<Countrys> countrysList= CountrysCRUD.getEveryCountrys();
        ObservableList<String> countryNames= FXCollections.observableArrayList();
        countrysList.forEach((countrys) ->{ String countryAdd = countrys.getCountryName().getValue(); countryNames.add(countryAdd);});
        CountryComboBox.setItems(countryNames);
        CustomerTable.getSelectionModel().selectedItemProperty().addListener((Obbs,olderData,newData)-> {
            if (newData !=null){ Customers cus2mers=CustomerTable.getSelectionModel().getSelectedItem();
                    AppointmentButton.setDisable(false);
                    DeleteButton.setDisable(false);
                    String cusAddress= cus2mers.getcusAddress().getValue();
                    AddressTextField.setText(cusAddress);
                    String zipCode= cus2mers.getZipCode().getValue();
                    zipTextField.setText(zipCode);
                    String cusName= cus2mers.getcusName().getValue();
                    CustomerNameTextField.setText(cusName);
                    String telephone= cus2mers.getTelephoneNumber().getValue();
                    phoneNumberTextField.setText(telephone);
                    // autofill customer id text fields
                    String customerID = cus2mers.getcusId().getValue().toString();
                    CustomerIdTextField.setText(customerID);
                    // autofills countryComboBox
                    int divisionId = cus2mers.getfirstLvLDivision().getValue();
                    // autofills countryComboBox
                    if(divisionId<=55){CountryComboBox.setValue(countryNames.get(0));
//                            CountryComboBox.setValue("US");
                    } else if(divisionId>=60 && divisionId <=73){CountryComboBox.setValue(countryNames.get(2));
//                            CountryComboBox.setValue("Canada");
                    } else  if(divisionId >= 100){CountryComboBox.setValue(countryNames.get(1));
//                            CountryComboBox.setValue("Uk");
                    }
                    try{
                        String divisionName = DivisionsCRUD.getDivisionName(divisionId);
                        lvlDivisionComboBox.setValue(divisionName);
                    }catch (ClassNotFoundException | SQLException e) {e.printStackTrace();
                    }
            }
        });
    }
    /**
     * countryIsSelected
     * gets country's
     * get all first level divisions where country_id = id (from above)
     * lambda is used to create a list of states for division ComboBox
     * @param event
     * @throws Exception
     */
    @FXML
    void countryIsSelected(ActionEvent event) throws Exception {
        String selectedCountry = CountryComboBox.getValue();
        int selectedId = CountrysCRUD.getCountrysId(selectedCountry);
        ObservableList<String> States= FXCollections.observableArrayList();
        States.addAll("Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "IllinoisIndiana", "Iowa", "Kansas", "Kentucky", "Louisiana",
                "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "MontanaNebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota",
                "Ohio", "Oklahoma", "Oregon", "PennsylvaniaRhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming");
        ObservableList<String> UKCountries = FXCollections.observableArrayList();
        UKCountries.addAll("England", "Scotland", "Wales", "Northern Ireland");
        ObservableList<String> candidaProven = FXCollections.observableArrayList();
        candidaProven.addAll("Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador", "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario", "Prince Edward Island", "Quebec", "Saskatchewan", "Yukon");
        // get all first level divisions where country_id = id (from above)
        ObservableList<Divisions> divisionList = DivisionsCRUD.getEveryDivisions();
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        divisionList.forEach((division) -> {String divisionToAdd = division.getDivisionsName().getValue(); divisionNames.add(divisionToAdd);});
        //give scroll down list of state names
        if(selectedId == 1){lvlDivisionComboBox.setItems(States);
        }else if(selectedId == 2){lvlDivisionComboBox.setItems(UKCountries);
        }else if(selectedId == 3){lvlDivisionComboBox.setItems(candidaProven);
        }
    }
    /**
     * AppointmentButtonAction
     * click on an item in the customerTable before clicking AppointmentButton will load up only the appointments that customer has.
     * loads up the appointmentScreen
     *  gives customer id from DataManager an int value found in mysql db in the customers table
     * @param event
     * @throws IOException
     */
    @FXML
    void AppointmentButtonAction(ActionEvent event) throws IOException{
        try {
            Customers customers = CustomerTable.getSelectionModel().getSelectedItem();
            DataManagement.customersid = customers.getcusId().getValue();
                FXMLLoader loading = new FXMLLoader(getClass().getResource("../views/AppontmentScreen.fxml"));
                Parent root = loading.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
        } catch (IOException e) {Logger.getLogger(LoginScreen.class.getName()).log(Level.SEVERE, "HomePage appoint button", e);}
    }
    /**
     * logoutAction
     * this button action logs you out and sends you back to the login page
     * @param event
     */
    public void logoutAction(ActionEvent event) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource("../views/LoginScreen.fxml"));
            Scene scene = new Scene(page);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){

        }
    }
    /**
     * AddAction
     *  adds a new customers via the user inputs.
     *  Set each form value a variable that match the values in mysql query statement.
     *  inserts this data into the DB via mysql statement.
     *  then refreshes the home page with an update table that contains the newly added value.
     *  creates a new customer id number by adds one to the customer list size.
     * @param event
     * @throws IOException
     */
    @FXML
    public void AddAction(ActionEvent event) throws IOException {
        try {String cusDiv = lvlDivisionComboBox.getValue();
            int cusDivison = DivisionsCRUD.getDivisionsId(cusDiv);
            String cusAddress = AddressTextField.getText();
            String zipCode= zipTextField.getText();
            String cusName= CustomerNameTextField.getText();
            ObservableList<Customers> cusList= CustomersCRUD.getEveryCustomer();
            int cusID =cusList.size()+1;
            String telephone= phoneNumberTextField.getText();
            String cusCountry = CountryComboBox.getValue();
            boolean validCus = CusValid(cusName,cusAddress,zipCode,cusCountry,telephone,cusDiv);
            if (validCus) {
                CustomersCRUD.insertCus(cusID, cusName, cusAddress, zipCode, telephone, cusDivison);
            }else {Alert alert = new Alert(Alert.AlertType.INFORMATION, "All Questions must be answered!", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }catch (Exception e){System.out.println("Add button not working"+e);}
        Parent page = FXMLLoader.load(getClass().getResource("../views/HomePage.fxml"));
        Scene scene = new Scene(page);
        Stage stage = Main.getStage();
        stage.setScene(scene);
        stage.show();
    }
    /**
     *  DeleteAction
     *  click on an item from the customer table, that click the delete button,
     * it will erase the selected data from both mysqldb, and the customerTable will refresh with an update table.
     * Then an alert will pop-up; confirming the customer data was successfully deleted
     * lambda is used for an alert message
     * else
     * @param event
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @FXML
    public void DeleteAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        Customers cus2omers = CustomerTable.getSelectionModel().getSelectedItem();
        int cusid= cus2omers.getcusId().getValue();
        if(!AppointMentsCRUD.hasAppointMents(cusid)) {
            try {CustomersCRUD.deleteCus(cusid);
                ObservableList<Customers> customersList = CustomersCRUD.getEveryCustomer();
                prepareTable(customersList);
            } catch (SQLException throwables) {throwables.printStackTrace();}
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deleted", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
            Parent page = FXMLLoader.load(getClass().getResource("../views/HomePage.fxml"));
            Scene scene = new Scene(page);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        } else {Alert alert = new Alert(Alert.AlertType.WARNING, "This customer still has appointments coming up and cannot be deleted", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);;
        }
    }
    /**
     *  updateAction
     *  is a button that when click on it updates old data to the sql db as well as the customer table
     *  select a customer from the table. Then edit any of the textFields  and or comboBoxes.
     *  Once done click update.
     *  It will then take your inputs and sends it to mysql where the selected customer items will be edited.
     *  Your changes will appear on the customers table and in mysql.
     *  lambda is used for an alert message
     * @param event
     */
    @FXML
    public void UpdateAction(ActionEvent event) {
        try {String cusDiv = lvlDivisionComboBox.getValue();
            int cusDivison = DivisionsCRUD.getDivisionsId(cusDiv);
            String cusAddress = AddressTextField.getText();
            String zipCode= zipTextField.getText();
            String cusCountry = CountryComboBox.getValue();
            String cusName= CustomerNameTextField.getText();
            Customers customers = CustomerTable.getSelectionModel().getSelectedItem();
            int cusID =customers.getcusId().getValue();
            String telephone= phoneNumberTextField.getText();
            // check if fields where left blank
            boolean validCus = CusValid(cusName,cusAddress,zipCode,cusCountry,telephone,cusDiv);
            if (validCus){
                CustomersCRUD.updateCus(cusID,cusName,cusAddress,zipCode,telephone,cusDivison);
                ObservableList<Customers> cusList= CustomersCRUD.getEveryCustomer();
                prepareTable(cusList);
                Parent page = FXMLLoader.load(getClass().getResource("../views/HomePage.fxml"));
                Scene scene = new Scene(page);
                Stage stage = Main.getStage();
                stage.setScene(scene);
                stage.show();
            } else {Alert alert = new Alert(Alert.AlertType.INFORMATION, "All Questions must be answered!", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        }catch (Exception e){System.out.println("Update button not working"+e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All Questions must be answered!", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }
    }
    /**
     *  CusValid
     *  is a boolean used to check and see if any of the text fields and combo Boxes where left empty
     *  CusValid is used in updateAction, if you click the update button
     *   and left a textField blank CusValid would equal false and cause an alert message to pop up "All Questions must be answered!"
     * validates customers
     * @param cusName
     * @param cusaddress
     * @param cuscountry
     * @param cuszip
     * @param telephone
     * @param cusDiv
     * @return
     */
    private boolean CusValid(String cusName, String cusaddress, String cuscountry, String cuszip, String telephone, String cusDiv) {
        if(cusName.isBlank() || cusaddress.isBlank() || cuscountry.isBlank() || cuszip.isBlank() || telephone.isBlank() || cusDiv.isBlank()) {
            return false;
        }return true;
    }
    public void reportsPressed(ActionEvent event) throws IOException {
        Parent page = FXMLLoader.load(getClass().getResource("../views/UserReport.fxml"));
        Scene scene = new Scene(page);
        Stage stage = Main.getStage();
        stage.setScene(scene);
        stage.show();
    }
    }