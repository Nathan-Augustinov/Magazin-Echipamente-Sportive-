package org.loose.fis.proiect.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
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
class SeeClientDetailsTest {
    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-registration-example";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
    }

    @Start
    void start(Stage Registration) throws Exception {
        Parent registration = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Registration.setTitle("Registration");
        Registration.setScene(new Scene(registration, 350, 450));
        Registration.show();
    }

    @Test
    void testseeclientdetails(FxRobot robot) {
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
        //UserService.CloseDatabase();

        robot.clickOn("#username");
        robot.write(USER_1);
        robot.clickOn("#password");
        robot.write(USER_1);

        robot.clickOn("#signinbutton");
        robot.clickOn("#logoutbutton");

        robot.clickOn("#registrationbutton");


        robot.clickOn("#firstname");
        robot.write(USER_2);
        robot.clickOn("#lastname");
        robot.write(USER_2);
        robot.clickOn("#username");
        robot.write(USER_2);
        robot.clickOn("#password");
        robot.write(USER_2);
        robot.clickOn("#role");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#email");
        robot.write(USER_2);

        robot.clickOn("#registerbutton");
        UserService.CloseDatabase();

        robot.clickOn("#username");
        robot.write(USER_2);
        robot.clickOn("#password");
        robot.write(USER_2);

        robot.clickOn("#signinbutton");

        robot.clickOn("#clientdetailsbutton");
        robot.clickOn("#ShowClientDetails");
        assertThat(robot.lookup("#SeeClientDetailsMessage").queryText()).hasText("Select a client!");

        robot.type(KeyCode.UP);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#ShowClientDetails");
        assertThat(robot.window("Show Client Details")).isShowing();
    }
}