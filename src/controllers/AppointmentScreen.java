package controllers;
import com.sun.jdi.Value;
import dbmanager.dbGide;
import javafx.fxml.Initializable;
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
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;
import modles.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sainvilus
 */

/**AppointmentScreen is a class that holds algorithms for appointment view it also implements initializable with gets things started for the appointmentScreen.
 * lambda is used for the all,weekly and monthly radio buttons inorder filter the appointment table
 * lambda is used in Alert messages
 */

public class AppointmentScreen implements Initializable {

    public TextField user_Id;
    @FXML
    private ComboBox<String> customerComboBox;
    @FXML
    private TableColumn<AppointMents, Integer> userIdTable;
    @FXML
    private RadioButton allappointments;

    @FXML
    private RadioButton weekappointments;

    @FXML
    private RadioButton monthAppontments;

    /**
     * this text field is disabled but will still display the appointment id when a column is selected from the appointment table.
     */
    public TextField Appointment_ID;
    /**
     *  string type comboBox allows you to scroll down and pick an answer from a number of chooses.
     */
    @FXML
    private ComboBox<String> typeComboBox;
    /**
     *  String column type table
     *  used in customer table to hold type data.
     */
    @FXML
    private TableColumn<AppointMents, String> typeTable;
    /**
     *   appointmentTable
     *   is a tableview that displays the users upcoming appointments and other useful data.
     */
    @FXML
    private TableView<AppointMents> AppointmentTable;
    /**
     * AppointMentIdTable
     * is the first column in the appointMents table.
     * the id is used to keep track of each and every appointment this help prevents users from overbooking and missing appointments.
     */
    @FXML
    private TableColumn<AppointMents, Integer> AppointmentIdTable;
    /**
     * titleTable
     * is a string  held in the title column and represents the tittle of the meeting.
     */
    @FXML
    private TableColumn<AppointMents, String> titleTable;
    /**
     * descriptionTable
     * is a  small summery  about the upcoming meeting.
     * It is held in the description column
     */
    @FXML
    private TableColumn<AppointMents, String> descriptionTable;
    /**
     * locationTable
     * is the meeting spot address. held in location column in the appointment table.
     */
    @FXML
    private TableColumn<AppointMents, String> locationTable;
    /**
     * contactTable
     * column contact
     */
    @FXML
    private TableColumn<AppointMents, Integer> contactTable;
    /**
     * startTable
     *  column starttime
     */
    @FXML
    private TableColumn<AppointMents, String> startTable;
    /**
     * endTable
     * column endtime
     */
    @FXML
    private TableColumn<AppointMents,String> endTable;
    /**
     * customerIdTable
     *  column customerid
     */
    @FXML
    private TableColumn<AppointMents, Integer> customerIdTable;
    /**
     * BackButton
     * from appointment Screen if pressed it will bring you back to the home page.
     */
    @FXML
    private Button BackButton;
    /**
     * AddAppointmentButton
     *  is a button that  when click on it adds new data to the sql db as well as the appointment table
     */
    @FXML
    private Button AddAppointmentButton;
    /**
     * updateAppointmentButton
     *  is a button that when click on it updates old data to the sql db as well as the appointment table
     *  select an appointment on the table then edit any of the textFields or comboBoxes.
     *  Once done click update your changes will appear on the table
     */
    @FXML
    private Button updateAppointmentButton;
    /**
     * DeleteAppointmentButton
     *  select an appointment on the table click delete,
     *  and it is erased from both mysql and the appointmentTable delete button
     */
    @FXML
    private Button DeleteAppointmentButton;

    /**
     *  locationTextField
     *  type in the location where you want to have the meeting.
     */
    @FXML
    private TextField locationTextField;
    /**
     * descriptionTextField
     * is a textField that allows users to write down talking point
     */
    @FXML
    private TextField descriptionTextField;
    /**
     * ContactComboBox
     * is a comboBox that lets you select a friend from a list of your contacts
     */
    @FXML
    private ComboBox<String> ContactComboBox;
    /**
     * StartDatePicker
     * datePicker used to select the date the meeting will start
     */
    @FXML
    private DatePicker StartDatePicker;
    /**
     * TitleTextField
     * is a textField that allows users to write down the meeting tittle
     */
    @FXML
    private TextField TitleTextField;
    /**
     * endHourComoBox
     *  is a comboBox that allows you to pick the hour you would like the meeting to end
     */
    @FXML
    private ComboBox<String> endHourComoBox;
    /**
     *  endMinuteComoBox
     *  is a comboBox that allows you to pick the minute you would like the meeting to end.
     */
    @FXML
    private ComboBox<String> endMinuteComoBox;
    /**
     * EndDatePicker
     * datePicker used to select the date the meeting will end.
     */
    @FXML
    private DatePicker EndDatePicker;
    /**
     * startHourComoBox
     *  is a comboBox that allows you to pick the hour you would like the meeting to start.
     */
    @FXML
    private ComboBox<String> startHourComoBox;
    /**
     * startMinComoBox
     * is a comboBox that allows you to pick the minute you would like the meeting to start.
     */
    @FXML
    private ComboBox<String> startMinComoBox;

    /**
     * preparedTable
     * prepares table takes the data acquired from mysql db and fills in each column with the correct data.
     * @param appointMentList
     */
    private void preparedTable(ObservableList<AppointMents> appointMentList){AppointmentTable.setItems(appointMentList);}
    /**
     * initializes
     * gets everything ready before appointmentScreen is called to the stage.
     * it turns buttons clickable and un-clickable, inputs the data to the table
     * and gets customer id from DataManager.
     * lambda is used for the all,weekly and monthly radio buttons inorder filter the appointment table
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Turn buttons clickable and or un-clickable
        AddAppointmentButton.setDisable(false);
        updateAppointmentButton.setDisable(false);
        DeleteAppointmentButton.setDisable(false);
        int userid = DataManagement.userIdNumber;
        user_Id.setText(String.valueOf(userid));
        //inputs the data to the table using lambda expressions
        AppointmentIdTable.setCellValueFactory(cellData-> cellData.getValue().getAppointId().asObject());
        titleTable.setCellValueFactory(cellData -> cellData.getValue().getTitle());
        descriptionTable.setCellValueFactory(cellData -> cellData.getValue().getDescription());
        locationTable.setCellValueFactory(cellData -> cellData.getValue().getLocation());
        contactTable.setCellValueFactory(cellData -> cellData.getValue().getContactId().asObject());
        typeTable.setCellValueFactory(cellData -> cellData.getValue().getType());
        startTable.setCellValueFactory(cellData -> cellData.getValue().getStartTime());
        endTable.setCellValueFactory(cellData -> cellData.getValue().getEndTime());
        customerIdTable.setCellValueFactory(cellData -> cellData.getValue().getCustomerId().asObject());
        userIdTable.setCellValueFactory(cellData->cellData.getValue().getUserId().asObject());
        //gets customer id
        int cusId = DataManagement.customersid;
        /**
         * appointMentList
         * is an ObservableList of appointment.
         * it receives all appointment data that have the matching customer ID
         */
        ObservableList<AppointMents> appointMentList;
        try {
            appointMentList = AppointMentsCRUD.getAllRecordsForCustomer(cusId);
            if(cusId == 0 ){
                appointMentList = AppointMentsCRUD.getAllRecords();
                allappointments.setSelected(true);
            }
            if(appointMentList.isEmpty() && cusId > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Selected Customer has no upcoming appointments!", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            preparedTable(appointMentList);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        allappointments.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if (isNowSelected) {
                ObservableList<AppointMents> appointmentList;
                try {
                    appointmentList = AppointMentsCRUD.getAllRecords();
                    preparedTable(appointmentList);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                weekappointments.setSelected(false);
                monthAppontments.setSelected(false);
            }

        });

        weekappointments.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if (isNowSelected) {
                ObservableList<AppointMents> appointmentList;
                try {
                    appointmentList = AppointMentsCRUD.getAllRecordsInNext7Days();
                    preparedTable(appointmentList);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                allappointments.setSelected(false);
                monthAppontments.setSelected(false);
            }

        });

        monthAppontments.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if (isNowSelected) {
                ObservableList<AppointMents> appointmentList;
                try {
                    appointmentList = AppointMentsCRUD.getAllRecordsInNextMonth();
                    preparedTable(appointmentList);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                allappointments.setSelected(false);
                weekappointments.setSelected(false);
            }
        });

        // lists created to provide items for comboBoxes like onboard, contacts, or hours and minutes.
        ObservableList<String> typeData = FXCollections.observableArrayList("onboard", "update");
        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        ObservableList<Contacts> contactList = null;
        try{
            contactList = ContactsCRUD.getEveryRecords();
        } catch (ClassNotFoundException | SQLException e){
            System.out.println("Error with contacts" +e);
        }
        // adds the names of the users contacts and adds it to the arrayList called contactNames.
        ObservableList<String> contactNames=FXCollections.observableArrayList();
        contactList.forEach(contacts -> {
            String addContact = contacts.getContactsName().getValue();
            contactNames.add(addContact) ;
        });
        typeComboBox.setItems(typeData);
        ContactComboBox.setItems(contactNames);
        ObservableList<Customers> customerList = null;
        try {
            customerList = CustomersCRUD.getEveryCustomer();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ObservableList<String> customerNames=FXCollections.observableArrayList();
        customerList.forEach(customers -> {
            String addCus = customers.getcusName().getValue();
            customerNames.add(addCus);
        });
        customerComboBox.setItems(customerNames);
        //hours and minute chooses
        hours.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
                "13", "14", "15", "16", "17", "18", "19", "20", "21", "22","23", "24");
        minutes.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
                "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
                "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41",
                "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53",
                "54", "55", "56", "57", "58", "59");
        /**
         * startHourComoBox
         * lists created to provide all 24 hours as a start time for the comboBox
         * sets holds each and every  hour as an item
         */
        startHourComoBox.setItems(hours);
        /**
         * startMinComoBox
         * lists created to provide all 60 minutes as a start time for the comboBox
         * sets holds each and every  minute as an item
         */
        startMinComoBox.setItems(minutes);
        /**
         *  endHourComoBox
         * lists created to provide all 24 hours as an  end time for the comboBox
         * sets holds each and every  hour as an item
         */
        endHourComoBox.setItems(hours);
        /**
         * endMinComoBox
         * lists created to provide all 60 minutes as an end time for the comboBox
         * sets holds each and every  minute as an item
         */
        endMinuteComoBox.setItems(minutes);

        /**
         * appoint is a lambda expression used as a listener waiting for an item to be clicked on.
         *  when an item in the AppointmentTable is click on the textFields, comboBoxes, and datePickers get auto-filled with the data you selected.
         *  sets table with proper info
         */
        //sets table with proper info
        AppointmentTable.getSelectionModel().selectedItemProperty().addListener((obbs, oldSelection, newSelection) ->{
            if(newSelection != null){
                AppointMents appoint = AppointmentTable.getSelectionModel().getSelectedItem();
                String appointmentId = appoint.getAppointId().getValue().toString();
                String title = appoint.getTitle().getValue();
                String description =appoint.getDescription().getValue();
                String location = appoint.getLocation().getValue();
                String type = appoint.getType().getValue();
                String startTime = appoint.getStartTime().getValue();
                String endTime = appoint.getEndTime().getValue();
                int contactId = appoint.getContactId().getValue();
                int customerId  = appoint.getCustomerId().getValue();
                //userId saved in DataManagement
                String userId = appoint.getUserId().getValue().toString();
                int userIdenity = DataManagement.userIdNumber;
                try {
                    String customerName =  CustomersCRUD.getCustomerName(customerId);
                    customerComboBox.setValue(customerName);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    String contactName = ContactsCRUD.getContactName(contactId);
                    ContactComboBox.setValue(contactName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                //auto fill datepiker and time comboboxes with start and end time select from the appointment table value.
                int begin = 0;
                int end = 10;
                int sHrbegin = 11;
                int sHrend = 13;
                int sMinbegin = 14;
                int sMinend = 16;
                int eHrbegin = 11;
                int eHrend = 13;
                int eMinbegin = 14;
                int eMinend = 16;
                CharSequence sdater = startTime.subSequence(begin,end);
                CharSequence edater = endTime.subSequence(begin,end);
                CharSequence sHr = startTime.subSequence(sHrbegin,sHrend);
                CharSequence sMin = startTime.subSequence(sMinbegin,sMinend);
                CharSequence eHr = endTime.subSequence(eHrbegin,eHrend);
                CharSequence eMin = endTime.subSequence(eMinbegin,eMinend);

                try {
                    TitleTextField.setText(title);
                    descriptionTextField.setText(description);
                    locationTextField.setText(location);
                    typeComboBox.setValue(type);
                    StartDatePicker.setValue(LocalDate.parse(sdater));
                    EndDatePicker.setValue(LocalDate.parse(edater));
                    startHourComoBox.setValue(sHr.toString());
                    startMinComoBox.setValue(sMin.toString());
                    endHourComoBox.setValue(eHr.toString());
                    endMinuteComoBox.setValue(eMin.toString());
                    Appointment_ID.setText(appointmentId);
                    user_Id.setText(String.valueOf(userIdenity));

                } catch (Exception e){
                    System.out.println("Error"+e);
                }
            }
        });
    }

    /**
     * AddAppiontmentActionButtion
     * adds new appointment data to mysql and the appointment table
     * first it creates a list called appointMentsList and stores all appointment data in this list.
     * then it gives each value a name then add it to a list.
     * creates a new appointment id number by adds one to the id with the highest number.
     *
     * During this assignment I had difficultly getting addAppointmentActionButton to create a new appointment id number
     * first I tried giving appointId the value of 1 and was planing to put it in a for loop, but I quickly realised that wasn't going to work.
     * Then it hit me I just needed to count the number of appointments currently in the table and add 1 to that number to create a new and unique ID number.
     * but I had to create a list to hold all the appointment data.
     * counting the number of items in the list was very simple just had to add ".size()" at the end of list(appointMentList). appointMentList.size();
     * then all I had to do was add +1 and equal it to appointId. "int appointId= appointMentsList.size() +1;"
     * @param event
     */

//adds appointments to sql and list
    @FXML
    public void AddAppiontmentActionButtion(ActionEvent event) {
        try {
            // give each value a name then add it to a list
            ObservableList<AppointMents> appointMentsList = AppointMentsCRUD.getAllRecords();
            boolean textIsEmpty = false;
            boolean isOutsideWorkingHours = false;
            int appointId = appointMentsList.size() + 1;
//          int customerId= DataManagement.customersid;
            String customer = customerComboBox.getValue();
//          if(DataManagement.customersid == 0){customerId = CustomersCRUD.getCustomersIdFromName(customer);}
            int customerId = CustomersCRUD.getCustomersIdFromName(customer);
            String title = TitleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String contact = ContactComboBox.getValue();
            String type = typeComboBox.getValue();
            String sHr = startHourComoBox.getValue();
            String sMin = startMinComoBox.getValue();
            String endHr = endHourComoBox.getValue();
            String endMin = endMinuteComoBox.getValue();
            LocalDate sDate = StartDatePicker.getValue();
            LocalDate eDate = EndDatePicker.getValue();
            int userId = Integer.parseInt(user_Id.getText());
            int contactId = ContactsCRUD.getContactsIdFromName(contact);
            if (title.isEmpty() || description.isEmpty() || location.isEmpty() || type == null || contact == null || customer == null || sDate == null || eDate == null || sHr == null || sMin == null || endHr == null || endMin == null){
                textIsEmpty = true;
            }
            ZoneId myZoneId = ZoneId.systemDefault();
            ZoneId utcZoneId = ZoneId.of("UTC");
            LocalDate myLsd = LocalDate.now();
            LocalTime myLst = LocalTime.now();
            LocalDate myLed =  LocalDate.now();
            LocalTime myLet =  LocalTime.now();
            if(sDate != null && eDate != null && sHr != null && sMin != null && endHr != null && endMin != null) {
                myLsd = LocalDate.of(sDate.getYear(), sDate.getMonthValue(), sDate.getDayOfMonth());
                myLst = LocalTime.of(Integer.parseInt(sHr), Integer.parseInt(sMin));
                myLed = LocalDate.of(eDate.getYear(), eDate.getMonthValue(), eDate.getDayOfMonth());
                myLet = LocalTime.of(Integer.parseInt(endHr), Integer.parseInt(endMin));
            }
            LocalDateTime lsdt = LocalDateTime.of(myLsd,myLst);
            LocalDateTime ledt = LocalDateTime.of(myLed, myLet);
            ZonedDateTime  locSZdt = ZonedDateTime.of(lsdt, myZoneId);
            ZonedDateTime locEZdt = ZonedDateTime.of(ledt, myZoneId);
            ZonedDateTime startUtcZDT = ZonedDateTime.ofInstant(locSZdt.toInstant(), utcZoneId);
            ZonedDateTime endUtcZDT = ZonedDateTime.ofInstant(locEZdt.toInstant(), utcZoneId);
            locSZdt = ZonedDateTime.ofInstant(startUtcZDT.toInstant(), myZoneId);
            locEZdt = ZonedDateTime.ofInstant(endUtcZDT.toInstant(), myZoneId);
            AppointMents appointMents = new AppointMents();
            appointMents.setAppointmentStartTime(locSZdt.toLocalDateTime().toString());
            String stt = appointMents.getStartTime().getValue();
            System.out.println(stt);
            AppointmentTable.setItems(appointMentsList);
            preparedTable(appointMentsList);
//            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            customFormatter.format(locsZdt);
//            customFormatter.format(loceZdt);

            ZoneId pacificZId = ZoneId.of("America/Los_Angeles");
            ZonedDateTime pacificSZDT = ZonedDateTime.ofInstant(locSZdt.toInstant(), pacificZId);
            ZonedDateTime pacificEZDT = ZonedDateTime.ofInstant(locEZdt.toInstant(), pacificZId);

            System.out.println("pacific: " + pacificSZDT);
            //8:00am - 10:00pm
            LocalTime theStartTime = LocalTime.of(8,0);
            LocalTime theEndTime = LocalTime.of(22,0);
            if(locSZdt.toLocalDateTime().toLocalTime().isBefore(theStartTime)) {
                isOutsideWorkingHours = true;
                System.out.println("the start time is outside");
            }
            if(locEZdt.toLocalDateTime().toLocalTime().isAfter(theEndTime)) {
                isOutsideWorkingHours = true;
                System.out.println("the end time is outside");
            }
//            if(pacificZDT.toLocalDateTime().toLocalTime().isBefore(theStartTime) || pacificZDT.toLocalDateTime().toLocalTime().isAfter(theEndTime)) {
//                isOutsideWorkingHours = true;
//                System.out.println(theStartTime);
//                System.out.println(theEndTime);
//            }
            // checks for overlapping appointments
            boolean isDuplicateAppointmentTime = AppointMentsCRUD.isDubbleAppointMentTime(customerId, lsdt, ledt);
            // if there are no duplicates deliver mysql statement
            // witch ask that the data submitted be added to the database as well as the appointments table
            if (!isDuplicateAppointmentTime && !textIsEmpty && !isOutsideWorkingHours) {
                AppointMentsCRUD.insertIntoAppointMents(appointId, title, description, location, type, lsdt, ledt, customerId, userId, contactId);
                startTable.setUserData(locSZdt);
                endTable.setUserData(locEZdt);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AppontmentScreen.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();

            } else if(isDuplicateAppointmentTime){
                Alert alert = new Alert(Alert.AlertType.WARNING, " This appointment time no longer available  .", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            else if(isOutsideWorkingHours){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Outside company hours", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "All Questions must be answered!", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
            // all fields must be answered this Alert lets the user know
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All Questions must be answered!", ButtonType.OK);
//            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }
    }

    /**
     * BackActionButton
     * makes navigating throw the app pages easier.
     * This button goes back to home page
     * @param event
     * @throws IOException
     */
    @FXML
    public void BackActionButton(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/HomePage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("back button not working " + e );
        }

    }

    /**
     * updateActionButton
     *  is a button that when click on it updates old data to the sql db as well as the appointment table
     *  select an appointment on the table then edit any of the textFields or comboBoxes.
     *  Once done click update your changes will appear on the table.
     *  take user input and sends it to mysql so that the selected appointment will be edited.
     *  then it refreshes the appointment page and updates the data on the appointment table.
     * @param event
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void updateActionButton(ActionEvent event)throws ClassNotFoundException, SQLException, IOException{
        AppointMents appoint = AppointmentTable.getSelectionModel().getSelectedItem();
        int apptId= appoint.getAppointId().getValue();
        try{

            ObservableList<AppointMents> appointMentsList= AppointMentsCRUD.getAllRecords();
            preparedTable(appointMentsList);

            String title= TitleTextField.getText();
            String description= descriptionTextField.getText();
            String location= locationTextField.getText();
            String customer = customerComboBox.getValue();
            String contact= ContactComboBox.getValue();
            String type=  typeComboBox.getValue();
            String sHr= startHourComoBox.getValue();
            String sMin=  startMinComoBox.getValue();
            String endHr =  endHourComoBox.getValue();
            String endMin=  endMinuteComoBox.getValue();
            int customerId= CustomersCRUD.getCustomersIdFromName(customer);
            LocalDate sDate= StartDatePicker.getValue();
            LocalDate eDate= EndDatePicker.getValue();
            int userId= DataManagement.userIdNumber;
            int contactId = ContactsCRUD.getContactsIdFromName(contact);
            //
            LocalDateTime lsdt = LocalDateTime.of(sDate.getYear(), sDate.getMonthValue(), sDate.getDayOfMonth(), Integer.parseInt(sHr), Integer.parseInt(sMin));
            LocalDateTime ledt = LocalDateTime.of(eDate.getYear(), eDate.getMonthValue(), eDate.getDayOfMonth(), Integer.parseInt(endHr), Integer.parseInt(endMin));


            AppointMentsCRUD.updateAppointment(apptId,title,description,location,type,lsdt,ledt,customerId,userId,contactId);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AppontmentScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (SQLException e){
            System.out.println("Update Error"+e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All Questions must be answered!", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }

    }

    //deletes appointment selected

    /**
     *
     * DeleteAppointmentButton
     * A user clicks on an item from the appointments table. If that user clicks the delete button,
     * it is erased from both mysql db, but it is also deleted  from the appointmentTable.
     * An alert will pop up confirming the appointment was successfully deleted
     * lambda is used for an alert message
     * @param event
     */
    @FXML
    public void DeleteAppointmentAction(ActionEvent event) {
        AppointMents appoint = (AppointMents) AppointmentTable.getSelectionModel().getSelectedItem();
        int appointId = appoint.getAppointId().getValue();
        String appointType = appoint.getType().getValue();
        try{
            AppointMentsCRUD.deleteAppointment(appointId);
            String customer = customerComboBox.getValue();
            int cusId= CustomersCRUD.getCustomersIdFromName(customer);
            ObservableList<AppointMents> appointList= AppointMentsCRUD.getAllRecordsForCustomer(cusId);
            preparedTable(appointList);


            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment was deleted! " + "Appointment Id: " +appointId+ ", type: "+appointType , ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AppontmentScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
