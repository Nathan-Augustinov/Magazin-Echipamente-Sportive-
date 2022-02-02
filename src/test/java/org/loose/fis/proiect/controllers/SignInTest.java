package org.loose.fis.proiect.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.proiect.model.User;
import org.loose.fis.proiect.services.FileSystemService;
import org.loose.fis.proiect.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class SignInTest
{
    public static final String USER_1 = "user1";

    @BeforeEach
    void setUp() throws Exception
    {
        FileSystemService.APPLICATION_FOLDER=".test-registration-example";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        //UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        //UserService.CloseDatabase();
    }

    @Start
    void start(Stage Registration) throws Exception
    {
        /*Parent signin = FXMLLoader.load(getClass().getClassLoader().getResource("SignIn.fxml"));
        SignIn.setTitle("Sign In");
        SignIn.setScene(new Scene(signin, 350, 400));
        SignIn.show();*/
        Parent registration = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Registration.setTitle("Registration");
        Registration.setScene(new Scene(registration, 350, 450));
        Registration.show();
    }

    @Test
    void testsignin(FxRobot robot)
    {
        UserService.initDatabase();
        robot.clickOn("#firstname");
        robot.write(USER_1);
        robot.clickOn("#lastname");
        robot.write(USER_1);
        robot.clickOn("#username");
        robot.write(USER_1);
        robot.clickOn("#password");
        robot.write(USER_1);
        robot.clickOn("#role");
        //robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#email");
        robot.write(USER_1);

        robot.clickOn("#registerbutton");
        UserService.CloseDatabase();
        //robot.clickOn("#backbutton");
        //robot.clickOn("#signinbutton");
        robot.clickOn("#username");
        robot.write(USER_1);
        robot.clickOn("#password");
        robot.write(USER_1);

        robot.clickOn("#signinbutton");
        assertThat(robot.window("Client Page")).isShowing();
    }
}