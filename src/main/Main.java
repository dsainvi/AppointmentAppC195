package main;


import dbmanager.dbGide;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Sainvilus
 * Main page loads  login screen and sets the launge to france depending on the users location
 *
 * This application is great but if I could add a feature I would add a feature able to notify customers of new appointment and send email updates.
 * Other than that, I would seek out features that enhances the user interface experience
 * like adding a chat box allowing all memmbers atending the meeting to better communicate.
 */

/** Main class creates an app that displays a message.*/
public class Main extends Application {
    static Stage stage;
    /**
     * start
     * begins the application via main displays the login screen.
     * @param stage
     * @throws Exception
     */
//starts login page
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        this.stage=stage;
        stage.setScene(scene);
        stage.show();
    }
    /**
     * getStage
     * help organize screen changes while maintaining the same stage
     * @return stage
     */
//gets stage
    public static Stage getStage() {return stage;}
    /**
     * main is the heart of the application. for this application main check if the user speaks French based on his/her location
     * if so it prints hello world in French and creates a resources bundle that calls the Language properties
     * else it prints hello world in english.
     * @param arg
     */
    public static void main(String[] arg){
        if(Locale.getDefault().getLanguage().equals("fr")) {
            ResourceBundle rb = ResourceBundle.getBundle("resources.Lag", Locale.getDefault());
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        }
        else {System.out.println("hello world");

        }
        dbGide.winConnection();
        launch(arg);

    }
}

    /**
     * @sources C195 Code Repository:
     *
     */
