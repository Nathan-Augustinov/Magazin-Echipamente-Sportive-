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
import org.loose.fis.proiect.services.FileSystemService;
import org.loose.fis.proiect.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ZRegistrationTest
{
    @AfterEach
    void tearDown() {
        UserService.CloseDatabase();
    }

    public static final String USER_1 = "user1";

    @BeforeEach
    void setUp() throws Exception
    {
        FileSystemService.APPLICATION_FOLDER=".test-registration-example";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @Start
     void start(Stage Registration) throws Exception
    {
        Parent registration = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Registration.setTitle("Registration");
        Registration.setScene(new Scene(registration, 350, 400));
        Registration.show();
    }


    @Test
    void testregister(FxRobot robot)
    {
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
        robot.clickOn("#backbutton");
        robot.clickOn("#registrationbutton");

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
        assertThat(robot.lookup("#registrationmessage").queryText()).hasText(String.format("An account with the username %s already exists!",USER_1));



    }

}