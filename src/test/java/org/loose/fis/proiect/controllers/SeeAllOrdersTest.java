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
import org.loose.fis.proiect.services.ProductService;
import org.loose.fis.proiect.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class SeeAllOrdersTest {

    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";
    public static final String PRODUCT_1 = "product1";
    public static final String Priceandstock = "10";

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
    static void testseeallorders(FxRobot robot) {
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

        ProductService.addProduct(PRODUCT_1,Priceandstock,Priceandstock,PRODUCT_1,PRODUCT_1);
        robot.clickOn("#buyproductsbutton");

        robot.type(KeyCode.UP);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#AddToCart");
        robot.clickOn("#stockfield");
        robot.write("1");
        robot.clickOn("#addtoshoppingcartbutton");
        robot.clickOn("#backbutton");
        robot.clickOn("#ShoppingCart");
        robot.clickOn("#FinishCommand");
        robot.clickOn("#BackButton");
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


        robot.clickOn("#seeallordersbutton");
        robot.clickOn("#ShowOrder");
        assertThat(robot.lookup("#ShowAllOrdersMessage").queryText()).hasText("Select an order!");

        robot.type(KeyCode.UP);
        robot.type(KeyCode.ENTER);
        robot.clickOn("#ShowOrder");
        assertThat(robot.window("Show All Orders")).isShowing();
    }
}