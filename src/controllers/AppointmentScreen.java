package controllers;
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
import modles.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.temporal.ChronoUnit;
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
    private TableColumn<AppointMents, Integer> userIdTable;
    @FXML
    private RadioButton allappointments;
    @FXML
    private RadioButton weekappointments;
    @FXML
    private RadioButton monthAppontments;
    public TextField Appointment_ID;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private TableColumn<AppointMents, String> typeTable;
    @FXML
    private TableView<AppointMents> AppointmentTable;
    @FXML
    private TableColumn<AppointMents, Integer> AppointmentIdTable;
    @FXML
    private TableColumn<AppointMents, String> titleTable;
    @FXML
    private TableColumn<AppointMents, String> descriptionTable;
    @FXML
    private TableColumn<AppointMents, String> locationTable;
    @FXML
    private TableColumn<AppointMents, Integer> contactTable;
    @FXML
    private TableColumn<AppointMents, String> startTable;
    @FXML
    private TableColumn<AppointMents,String> endTable;
    @FXML
    private TableColumn<AppointMents, Integer> customerIdTable;
    @FXML
    private Button BackButton;
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
        int cusId = DataManagement.customersid;
        user_Id.setText(String.valueOf(DataManagement.userIdNumber));
        allappointments.setSelected(true);

        updateAppointmentButton.setDisable(true);
        DeleteAppointmentButton.setDisable(true);
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
        /**
         * appointMentList
         * is an ObservableList of appointment.
         * it receives all appointment data that have the matching customer ID
         */
        ObservableList<AppointMents> appointMentList;
        try {
            appointMentList = AppointMentsCRUD.getAllRecords();
            preparedTable(appointMentList);
        }catch (ClassNotFoundException | SQLException e) {e.printStackTrace();}
        allappointments.selectedProperty().addListener((observableValue, aBoolean, isNowSelected)->{
            if(isNowSelected){ObservableList<AppointMents> appointmentList;
                try{appointmentList = AppointMentsCRUD.getAllRecords();
                    preparedTable(appointmentList);
                }catch (ClassNotFoundException | SQLException ex) {Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);}
                weekappointments.setSelected(false);
                monthAppontments.setSelected(false);
            }
        });
        weekappointments.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if (isNowSelected) {
                ObservableList<AppointMents> appointmentList;
                try {appointmentList = AppointMentsCRUD.getAllRecordsInNext7Days(); preparedTable(appointmentList);
                } catch (ClassNotFoundException | SQLException ex) {Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);}allappointments.setSelected(false);
                allappointments.setSelected(false);
                monthAppontments.setSelected(false);
            }
        });
        monthAppontments.selectedProperty().addListener((observableValue, aBoolean, isNowSelected) -> {
            if (isNowSelected) {ObservableList<AppointMents> appointmentList;
                try {appointmentList = AppointMentsCRUD.getAllRecordsInNextMonth(); preparedTable(appointmentList);
                } catch (ClassNotFoundException | SQLException ex) {Logger.getLogger(AppointmentScreen.class.getName()).log(Level.SEVERE, null, ex);}
                allappointments.setSelected(false);
                weekappointments.setSelected(false);
            }
        });
        // lists created to provide items for comboBoxes like onboard, contacts, or hours and minutes.
        // adds the names of the users contacts and adds it to the arrayList called contactNames.
        ObservableList<String> typeData = FXCollections.observableArrayList("onboard","update");
        ObservableList<String> hours = FXCollections.observableArrayList();
        ObservableList<String> minutes = FXCollections.observableArrayList();
        ObservableList<String> contactNames=FXCollections.observableArrayList();
        ObservableList<Contacts> contactList = null;
        try{contactList = ContactsCRUD.getEveryRecords(); }catch (ClassNotFoundException | SQLException e){System.out.println("Error contacts"+e);}
        contactList.forEach(contacts -> {String addContact = contacts.getContactsName().getValue(); contactNames.add(addContact);});
        hours.addAll("00","01", "02", "03", "04", "05", "06", "07", "08", "09", "10","11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22","23");
        minutes.addAll("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10","11","12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28","29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39","40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59");
        ContactComboBox.setItems(contactNames);
        typeComboBox.setItems(typeData);
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
        AppointmentTable.getSelectionModel().selectedItemProperty().addListener((obbs, oldSelection, newSelection) ->{
            if(newSelection != null){
                updateAppointmentButton.setDisable(false);
                DeleteAppointmentButton.setDisable(false);
                AppointMents appoint = AppointmentTable.getSelectionModel().getSelectedItem();
                int appointmentId = appoint.getAppointId().getValue();
                DataManagement.appointId = appointmentId;
                String title = appoint.getTitle().getValue();
                String description =appoint.getDescription().getValue();
                String location = appoint.getLocation().getValue();
                String type = appoint.getType().getValue();
                String startTime = appoint.getStartTime().getValue();
                String endTime = appoint.getEndTime().getValue();
                int contactId = appoint.getContactId().getValue();
                int customerId  = appoint.getCustomerId().getValue();
                try {
                    String contactName = ContactsCRUD.getContactName(contactId); ContactComboBox.setValue(contactName);
                } catch (ClassNotFoundException | SQLException e) {e.printStackTrace();}
                //autofills date picker, time and comboBoxs with start and end time select from the appointment table value.
                int begin = 0; int end = 10;
                int Hrbegin = 11; int Hrend = 13;
                int Minbegin = 14; int Minend = 16;
                CharSequence sdater = startTime.subSequence(begin,end);
                CharSequence edater = endTime.subSequence(begin,end);
                CharSequence sHr = startTime.subSequence(Hrbegin,Hrend);
                CharSequence sMin = startTime.subSequence(Minbegin,Minend);
                CharSequence eHr = endTime.subSequence(Hrbegin,Hrend);
                CharSequence eMin = endTime.subSequence(Minbegin,Minend);
                try {Appointment_ID.setText(String.valueOf(appointmentId));
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
                    user_Id.setText(String.valueOf(DataManagement.userIdNumber));
                }catch (Exception e){System.out.println("Error"+e);}
            }
        });
    }
    /**
     * AddAppiontmentActionButtion
     * adds new appointment data to mysql and the appointment table
     * first it creates a list called appointMentsList and stores all appointment data in this list.
     * then it gives each value a name then add it to a list.
     * creates a new appointment id number by adds one to the id with the highest number.
     * During this assignment I had difficultly getting addAppointmentActionButton to create a new appointment id number
     * first I tried giving appointId the value of 1 and was planing to put it in a for loop, but I quickly realised that wasn't going to work.
     * Then it hit me I just needed to count the number of appointments currently in the table and add 1 to that number to create a new and unique ID number.
     * but I had to create a list to hold all the appointment data.
     * counting the number of items in the list was very simple just had to add ".size()" at the end of list(appointMentList). appointMentList.size();
     * then all I had to do was add +1 and equal it to appointId. "int appointId= appointMentsList.size() +1;
     * But then I learned that appointId automatically picks a new id number. I removed it from Insert INTO sql statement worked better without it "
     * @param event
     */
    @FXML
    public void AddAppiontmentActionButtion(ActionEvent event) {
        try {ObservableList<AppointMents> appointMentsList = AppointMentsCRUD.getAllRecords();
            String title = TitleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String contact = ContactComboBox.getValue();
            String type = typeComboBox.getValue();
            int customerId = DataManagement.customersid;
            int userId = DataManagement.userIdNumber;
            int contactId = ContactsCRUD.getContactsIdFromName(contact);
            String sHr = startHourComoBox.getValue();
            String sMin = startMinComoBox.getValue();
            String endHr = endHourComoBox.getValue();
            String endMin = endMinuteComoBox.getValue();
            LocalDate sDate = StartDatePicker.getValue();
            LocalDate eDate = EndDatePicker.getValue();
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
            LocalTime theStartTime = LocalTime.of(8, 0);
            LocalTime theEndTime = LocalTime.of(22, 0);
            LocalDateTime lsdt = LocalDateTime.of(myLsd,myLst);
            LocalDateTime ledt = LocalDateTime.of(myLed, myLet);
            ZonedDateTime locSZdt = ZonedDateTime.of(lsdt,ZoneId.systemDefault());
            ZonedDateTime locEZdt = ZonedDateTime.of(ledt, ZoneId.systemDefault());
            ZoneId estZId = ZoneId.of("America/New_York");
            ZonedDateTime estSZDT = ZonedDateTime.ofInstant(locSZdt.toInstant(), estZId);
            ZonedDateTime estEZDT = ZonedDateTime.ofInstant(locEZdt.toInstant(), estZId);
            AppointmentTable.setItems(appointMentsList);
            preparedTable(appointMentsList);
            int overCount = AppointMentsCRUD.isOverLappingAppointments(lsdt,ledt);
            boolean isTextEmpty = title.isEmpty() || description.isEmpty() || location.isEmpty() || type == null || contact == null  || sDate == null || eDate == null || sHr == null || sMin == null || endHr == null || endMin == null;
            boolean isOverLaping = overCount >= 1;
            boolean isOutsideWorkingHours = estSZDT.toLocalTime().isAfter(theEndTime) || estEZDT.toLocalTime().isBefore(theStartTime) || estSZDT.toLocalTime().isBefore(theStartTime) || estEZDT.toLocalTime().isAfter(theEndTime);
            boolean isEndDateBeforeStartDate = myLsd.isAfter(myLed) || myLet.isBefore(myLst);
            boolean isDateBeforeCurrentDate = lsdt.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            boolean appointmentIsACopy = false;
            LocalDateTime pendingEventStartDateTime = LocalDateTime.of(myLsd,myLst);
            LocalDateTime pendingEventEndDateTime = LocalDateTime.of(myLed,myLet);
            for (DataManagement dataManagement: AppointMentsCRUD.getAppointmentTimes(lsdt,ledt)){
                LocalDateTime bookedStart=dataManagement.getApSTime();
                LocalDateTime bookedEnd=dataManagement.getApETime();
                if(bookedStart.equals(pendingEventStartDateTime) && bookedEnd.equals(pendingEventEndDateTime)) {
                    appointmentIsACopy = true;
                    break;
                }
            }


            if (!isOverLaping && !appointmentIsACopy &&!isTextEmpty && !isOutsideWorkingHours && !isEndDateBeforeStartDate && !isDateBeforeCurrentDate) {
                    AppointMentsCRUD.insertIntoAppointMents(title, description, location, type, lsdt, ledt, customerId, userId, contactId);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AppontmentScreen.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
            }else if(isOverLaping) {Alert alert = new Alert(Alert.AlertType.WARNING, " OverLapping Appointment!\n The time selected would cause an overlap with a previously booked appointment.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }else if(isOutsideWorkingHours){Alert alert = new Alert(Alert.AlertType.WARNING, "The time selected is outside company hours", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
            }else if(isEndDateBeforeStartDate){Alert alert = new Alert(Alert.AlertType.WARNING, " Start date & time can't come after end date & time.", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
            }else if(isDateBeforeCurrentDate) {Alert alert = new Alert(Alert.AlertType.WARNING, " Date and time cant be less then today date and time.", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
            }else if(appointmentIsACopy){Alert alert = new Alert(Alert.AlertType.WARNING, "Selected event time matches Booked event time!", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);
            }else {Alert alert = new Alert(Alert.AlertType.WARNING, "All questions must be answered to make an appointment!", ButtonType.OK);
                    alert.showAndWait().filter(response -> response == ButtonType.OK);}
            } catch (ClassNotFoundException | IOException | SQLException e) {e.printStackTrace();}
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
        try {FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/HomePage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {System.out.println("back button not working " + e );}
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
    void updateActionButton(ActionEvent event)throws ClassNotFoundException, SQLException, IOException {
        int apptId = DataManagement.appointId;
        try {
            ObservableList<AppointMents> appointMentsList = AppointMentsCRUD.getAllRecords();
            preparedTable(appointMentsList);
            String title = TitleTextField.getText();
            String description = descriptionTextField.getText();
            String location = locationTextField.getText();
            String contact = ContactComboBox.getValue();
            String type = typeComboBox.getValue();
            String sHr = startHourComoBox.getValue();
            String sMin = startMinComoBox.getValue();
            String endHr = endHourComoBox.getValue();
            String endMin = endMinuteComoBox.getValue();
            int customerId = DataManagement.customersid;
            LocalDate sDate = StartDatePicker.getValue();
            LocalDate eDate = EndDatePicker.getValue();
            int userId = DataManagement.userIdNumber;
            int contactId = ContactsCRUD.getContactsIdFromName(contact);
            LocalDate myLsd = LocalDate.now();
            LocalTime myLst = LocalTime.now();
            LocalDate myLed = LocalDate.now();
            LocalTime myLet = LocalTime.now();
            if (sDate != null && eDate != null && sHr != null && sMin != null && endHr != null && endMin != null) {
                myLsd = LocalDate.of(sDate.getYear(), sDate.getMonthValue(), sDate.getDayOfMonth());
                myLst = LocalTime.of(Integer.parseInt(sHr), Integer.parseInt(sMin));
                myLed = LocalDate.of(eDate.getYear(), eDate.getMonthValue(), eDate.getDayOfMonth());
                myLet = LocalTime.of(Integer.parseInt(endHr), Integer.parseInt(endMin));
            }
            LocalTime theStartTime = LocalTime.of(8, 0);
            LocalTime theEndTime = LocalTime.of(22, 0);
            LocalDateTime lsdt = LocalDateTime.of(myLsd, myLst);
            LocalDateTime ledt = LocalDateTime.of(myLed, myLet);
            ZonedDateTime locSZdt = ZonedDateTime.of(lsdt, ZoneId.systemDefault());
            ZonedDateTime locEZdt = ZonedDateTime.of(ledt, ZoneId.systemDefault());
            ZoneId estZId = ZoneId.of("America/New_York");
            ZonedDateTime estSZDT = ZonedDateTime.ofInstant(locSZdt.toInstant(), estZId);
            ZonedDateTime estEZDT = ZonedDateTime.ofInstant(locEZdt.toInstant(), estZId);
            int overCount = AppointMentsCRUD.isOverLappingAppointments(lsdt, ledt);
            boolean isTextEmpty = title.isEmpty() || description.isEmpty() || location.isEmpty() || type == null || contact == null || sDate == null || eDate == null || sHr == null || sMin == null || endHr == null || endMin == null;
            boolean isOverLaping = overCount >= 1;
            boolean isOutsideWorkingHours = estSZDT.toLocalTime().isAfter(theEndTime) || estEZDT.toLocalTime().isBefore(theStartTime) || estSZDT.toLocalTime().isBefore(theStartTime) || estEZDT.toLocalTime().isAfter(theEndTime);
            boolean isEndDateBeforeStartDate = myLsd.isAfter(myLed) || myLet.isBefore(myLst);
            boolean isDateBeforeCurrentDate = lsdt.isBefore(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
            LocalDateTime pendingEventStartDateTime = LocalDateTime.of(myLsd,myLst);
            LocalDateTime pendingEventEndDateTime = LocalDateTime.of(myLed,myLet);
            boolean appointmentIsACopy = false;
            for (DataManagement dataManagement: AppointMentsCRUD.getAppointmentTimes(lsdt,ledt)){
                LocalDateTime bookedStart=dataManagement.getApSTime();
                LocalDateTime bookedEnd=dataManagement.getApETime();
                if(bookedStart.equals(pendingEventStartDateTime) && bookedEnd.equals(pendingEventEndDateTime)) {
                    appointmentIsACopy = true;
                    break;
                }
            }
            if (!isOverLaping && !appointmentIsACopy && !isTextEmpty && !isOutsideWorkingHours && !isEndDateBeforeStartDate && !isDateBeforeCurrentDate) {
                AppointMentsCRUD.updateAppointment(apptId, title, description, location, type, lsdt, ledt, customerId, userId, contactId);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AppontmentScreen.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else if (isOverLaping) {
                Alert alert = new Alert(Alert.AlertType.WARNING, " OverLapping Appointment!\n The time selected would cause an overlap with a previously booked appointment.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            } else if (isOutsideWorkingHours) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "The time selected is outside company hours", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            } else if (isEndDateBeforeStartDate) {
                Alert alert = new Alert(Alert.AlertType.WARNING, " Start date & time can't come after end date & time.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            } else if (isDateBeforeCurrentDate) {
                Alert alert = new Alert(Alert.AlertType.WARNING, " Date and time cant be less then today date and time.", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }else if(appointmentIsACopy) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "No changes were made!\n Edited a text filed or combo box inorder updating this appointment", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "All questions must be answered to make an appointment!", ButtonType.OK);
                alert.showAndWait().filter(response -> response == ButtonType.OK);
            }
        } catch (SQLException e) {
            System.out.println("Update Error" + e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "All Questions must be answered!", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }
    }
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
        AppointMents appoint = AppointmentTable.getSelectionModel().getSelectedItem();
        int appointId = appoint.getAppointId().getValue();
        String appointType = appoint.getType().getValue();
        try{AppointMentsCRUD.deleteAppointment(appointId);
            ObservableList<AppointMents> appointList= AppointMentsCRUD.getAllRecordsForCustomer(DataManagement.customersid);
            preparedTable(appointList);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment was successfully deleted! " + "Appointment Id: " +appointId+ ", type: "+appointType , ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AppontmentScreen.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (SQLException | IOException | ClassNotFoundException throwables) {throwables.printStackTrace();}
    }
}
