package org.loose.fis.proiect.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.loose.fis.proiect.services.FileSystemService;
import org.loose.fis.proiect.services.UserService;
import javafx.scene.control.Button;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StartPageController
{




    @FXML
    private Button registrationbutton;
    @FXML
    private Button signinbutton;

    @FXML
    public void handleRegistrationAction() throws Exception, IOException
    {
        Stage Registration = new Stage();
        UserService.initDatabase();
        Parent registration = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Registration.setTitle("Registration");
        Registration.setScene(new Scene(registration, 350, 400));
        Registration.show();
        CancelStartPage();

    }



    @FXML
    public void handleSignInAction() throws Exception
    {
       Stage SignIn= new Stage();
       Parent signin = FXMLLoader.load(getClass().getClassLoader().getResource("SignIn.fxml"));
       SignIn.setTitle("Sign In");
       SignIn.setScene(new Scene(signin, 350, 400));
       SignIn.show();
       CancelStartPage();
    }



    public void handleEXITAction()
    {
        CancelStartPage();
    }

    public void CancelStartPage()
    {
        registrationbutton.getScene().getWindow().hide();
    }


}
