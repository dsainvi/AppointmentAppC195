package controllers;
//javafx
import dbmanager.DataManagement;
import dbmanager.UserLocation;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
//main users modules
import main.Main;
import modles.AppointMents;
import modles.AppointMentsCRUD;
import modles.Users;
import modles.UsersCRUD;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


//•  accepts a user ID and password and provides an appropriate error message
//
//•  determines the user’s location (i.e., ZoneId) and displays it in a label on the log-in form
//
//•  displays the log-in form in English or French based on the user’s computer language setting to translate all the text, labels, buttons, and errors on the form
//
//•  automatically translates error control messages into English or French based on the user’s computer language setting

/**
 * @author Sainvilus
 */
/** login class called to stage by main. the login page ask for the user credentale and if coretct it will bring them to the homepage.
 * It also documents all login attements in a log.
 * lambda is used in alert messages
 */
public class LoginScreen implements Initializable {
    @FXML
    private Label AppointmentLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label userLocation;
    @FXML
    private TextField usersNameTextField;
    @FXML
    private Button logIn;
    @FXML
    private PasswordField passWordTextField;



    /**
     * initialize the login controller class.
     * finds out if the user is in a French-speaking country.
     * Then it translates all the english words on the login page into French words.
     * else it will print english login
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        if(Locale.getDefault().getLanguage().equals("fr")) {
            rb = ResourceBundle.getBundle("resources.Lag", Locale.getDefault());
            AppointmentLabel.setText(rb.getString("appointment"));
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            logIn.setText(rb.getString("login"));
        }
        else {
            System.out.println("english login");
        }
        userLocation.setText(UserLocation.grabMyLocation());

    }

    /**
     * gateKeeper
     * if gateKeeper equals true that means your username and password where correct and have been login and the homePage will appear
     * but if gateKeeper equals false  an alert message will display "access denied" and you will remain on the login page.
     */
    boolean gateKeeper = false;


    /**
     * login Action displays the homePage aka the customer screen.
     * compares the users input to the username and password stored in the database.
     * if they both match it sends you to the home page else you get Alert message "wrong username and password."
     * lambda is used to alert user if username&password are incorrect or when they have an appointment in 15 minutes
     * @throws Exception
     */
    @FXML
    public void LogInButtonAction() throws Exception{
        String usersName = usersNameTextField.getText();
        String passWord = passWordTextField.getText();


        // loops throw copy of user list puts usernames in sqlusername and passwords in sqlpasswords

//
//          for loop
//         loops thought every username and password in the db and check if it matches the users input.
//          This application is great but if I could add some feature I would add a feature able to notify customers of new appointment and send email updates.
//         Other then that I would seek out features that enhances the user interface experience

        for (Users users: UsersCRUD.getEveryUser()){
            if (users.getUsersName().getValue().equals (usersName) && users.getPassWord().getValue().equals (passWord)) {
                gateKeeper = true;
                int sqlUsersId = users.getUsersId().getValue();
                String sqlUsersname = users.getUsersName ().getValue();
//                String sqlPassword = users.getPassWord().getValue();
                DataManagement.currentUserName=sqlUsersname;
                DataManagement.userIdNumber=sqlUsersId;
                break;
            }
        }
        //set screen to home page aka customer view if username and password are correct records' event in logIn file
        if (gateKeeper) {
            System.out.println("Access granted") ;
            //file name and item;
            String logfile= "src/loginfiles/LogIn_history.txt", item;
            // Create open filewriter
            FileWriter fileW = new FileWriter(logfile, true);
            // creates and opens file
            PrintWriter outputDoc = new PrintWriter (fileW) ;
            // records date of last login
            String logInDate = LocalDateTime.now().toString();
            //print access granted
            item = "Username: " + usersName + ", DateTime: " + logInDate + ", Access Granted." ;
            outputDoc.println(item);
            System.out.println(item);
            //close file
            outputDoc.close();
            //show home page
            SceneChange();

        }
        else if (!gateKeeper && Locale.getDefault().getLanguage().equals("fr")){
            Alert incorrect_username_and_password = new Alert(Alert.AlertType.INFORMATION, "Nom d’utilisateur et mot de passe incorrects", ButtonType.OK);
            incorrect_username_and_password.showAndWait();
            //file name and item;
            String logfile= "src/loginfiles/LogIn_history.txt", item;
            // Create open filewriter
            FileWriter fileW = new FileWriter (logfile, true);
            // creates and opens file
            PrintWriter outputDoc = new PrintWriter (fileW);
            // records date of last Login
            String logInDate = LocalDateTime.now().toString();
            // saves if login was Denied
            item = "Username: " + usersName + ", DateTime: " + logInDate + "Access Denied." ;
            outputDoc.println(item);
            System.out.println(item);
            //close file
            outputDoc.close();

        }
        // else access denied
        else {
            Alert incorrect_username_and_password = new Alert(Alert.AlertType.INFORMATION, "Incorrect username and password", ButtonType.OK);
            incorrect_username_and_password.showAndWait();
            //file name and item;
            String logfile= "src/loginfiles/LogIn_history.txt", item;
            // Create open filewriter
            FileWriter fileW = new FileWriter (logfile, true);
            // creates and opens file
            PrintWriter outputDoc = new PrintWriter (fileW);
            // records date of last Login
            String logInDate = LocalDateTime.now().toString();
            // saves if login was Denied
            item = "Username: " + usersName + ", DateTime: " + logInDate + "Access Denied." ;
            outputDoc.println(item);
            System.out.println(item);
            //close file
            outputDoc.close();
        }
// 15 minute appointment alert
        ObservableList<AppointMents> appointMents15MinList = AppointMentsCRUD.getAllRecordsInNext15Minutes();
        appointMents15MinList.forEach(appointMents -> {
            int appointId = appointMents.getAppointId().getValue();
            String appointDate = appointMents.getStartTime().getValue();
            DataManagement.appointId = appointId;
            DataManagement.appointTime = appointDate;
        });

        if(!appointMents15MinList.isEmpty()){
            int appointid = DataManagement.appointId;
            String appointTime= DataManagement.appointTime;
            Alert appointmentIn15 = new Alert(Alert.AlertType.INFORMATION, "Upcoming appointment in 15 minutes.\n Appointment Id number is "+appointid+ " it meeting starts at " +appointTime+".", ButtonType.OK);
            appointmentIn15.showAndWait();
        }
        else {
            Alert appointmentIn15 = new Alert(Alert.AlertType.INFORMATION, " No upcoming appointments.", ButtonType.OK);
            appointmentIn15.showAndWait();
        }

    }
    /**
     * SceneChanger
     * from the login screen it loads and displays the home page aka customer page
     * it is uses in an if statement and only works if gateKeeper is true
     */
    private void SceneChange() {
        try {
            Parent page = FXMLLoader.load(getClass().getResource("../views/HomePage.fxml"));
            Scene scene = new Scene(page);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    


}