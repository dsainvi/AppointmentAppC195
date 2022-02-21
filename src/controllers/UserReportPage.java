package controllers;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import modles.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Report Controller
 * shows the reports
 * @author Sainvilus
 */
public class UserReportPage implements  Initializable{
    @FXML
    private RadioButton apptsbymonth;
    @FXML
    private RadioButton customersadded;

    @FXML
    private RadioButton contactschedules;
    @FXML
    private TextArea textarea;
    @FXML
    private RadioButton apptsbytype;
    /**
     * initialize
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final ToggleGroup group = new ToggleGroup();
        apptsbytype.setToggleGroup(group);
        apptsbytype.setSelected(true);
        apptsbymonth.setToggleGroup(group);
        contactschedules.setToggleGroup(group);
        customersadded.setToggleGroup(group);
        controleAppointsByType();

        //  appointment by type radio button selected
        apptsbytype.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    textarea.setText("");
                    controleAppointsByType();
                }
            }
        });

        // appointment by month radio button selected
        apptsbymonth.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    textarea.setText("");
                    try {
                        ObservableList<AppointMents> apptList = AppointMentsCRUD.getAllRecords();
                        String month;

                        HashMap<String, Integer> monthMap = new HashMap<String, Integer>();
                        for (int i = 0; i < apptList.size(); i++) {
                            String date = apptList.get(i).getStartTime().getValue();
                            month = date.split(" ")[0].split("-")[1];
                            if (monthMap.containsKey(month)) {
                                monthMap.put(month, monthMap.get(month) + 1);
                            } else {
                                monthMap.put(month, 1);
                            }
                        }
                        String reslt = "";
                        for (Map.Entry entry : monthMap.entrySet()) {
                            String monthName = changeNum2Month(entry.getKey().toString());
                            reslt += monthName + ": " + entry.getValue() + "\n";
                        }
                        textarea.setText(reslt);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // Handle  radio button selected
        contactschedules.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) { textarea.setText("");
                    try {
                        ObservableList<AppointMents> apptList =AppointMentsCRUD.getAllRecords();
                        ObservableList<Contacts> contactList =ContactsCRUD.getEveryRecords();
                        String contactName; int id; int contactId; int apptId; int customerId; String title; String type; String description; String start; String end;

                        StringBuilder reslt =new StringBuilder();
                        for (int i = 0; i < contactList.size(); i++) {
                            contactName = contactList.get(i).getContactsName().getValue();
                            id = contactList.get(i).getContactId().getValue();

                            for(int j = 0; j < apptList.size(); j++) {
                                contactId = apptList.get(j).getContactId().getValue();
                                if (contactId == id) {
                                    apptId = apptList.get(j).getAppointId().getValue();
                                    customerId = apptList.get(j).getContactId().getValue();
                                    title = apptList.get(j).getTitle().getValue();
                                    type = apptList.get(j).getType().getValue();
                                    description = apptList.get(j).getDescription().getValue();
                                    start = apptList.get(j).getStartTime().getValue();
                                    end = apptList.get(j).getEndTime().getValue();
                                    reslt.append("Contact: ").append(contactName).append("\n");
                                    reslt.append("ApptID: ").append(apptId);
                                    reslt.append(" CustomerID: ").append(customerId);
                                    reslt.append(" Title: ").append(title);
                                    reslt.append(" Type: ").append(type);
                                    reslt.append(" Desc: ").append(description);
                                    reslt.append(" Start: ").append(start);
                                    reslt.append(" End: ").append(end).append("\n");
                                }
                            }
                        }

                        textarea.setText(reslt.toString());

                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

        // customers added  this  week radio button selected
        customersadded.selectedProperty().addListener(new ChangeListener<Boolean>() {
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    textarea.setText("");

                    try {
                        ObservableList<Customers> customerList = CustomersCRUD.getAllRecordsThisWeek();
                        int count =customerList.size();
                        textarea.setText(Integer.toString(count));
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });

    }


    /**
     * gets the report displays appointment type.
     */
    private void controleAppointsByType() {
        try {
            ObservableList<AppointMents> apptList = AppointMentsCRUD.getAllRecords();
            int updateCount = 0;
            int onboardCount = 0;

            for (int i = 0; i < apptList.size(); i++) {
                if (apptList.get(i).getType().getValue().equals("onboard")) {
                    onboardCount++;
                } else if (apptList.get(i).getType().getValue().equals("update")) {
                    updateCount++;
                }
            }

            String reslt = " ";
            reslt +="Update appointments: " +updateCount + "\n";
            reslt +="Onboarding appointments: "+onboardCount +"\n";
            textarea.setText(reslt);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UserReportPage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     *  back button Action. sends the user back to home page.
     *
     * @param event
     */
    @FXML
    void backPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/HomePage.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Divisions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * converts number into the months.
     *
     * @param month the number changes to the month name
     * @return month name
     */
    private String changeNum2Month(String month) {switch (month) {
        case "01": return "January"; case "02": return "February"; case "03": return "March";
        case "04": return "April"; case "05": return "May"; case "06": return "June";
        case "07": return "July"; case "08": return "August"; case "09": return "September";
        case "10": return "October"; case "11": return "November"; case "12": return "December";
        default: return "";
    }
    }

}
