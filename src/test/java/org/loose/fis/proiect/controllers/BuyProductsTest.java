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
import org.loose.fis.proiect.model.Product;
import org.loose.fis.proiect.services.FileSystemService;
import org.loose.fis.proiect.services.ProductService;
import org.loose.fis.proiect.services.UserService;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class BuyProductsTest
{
    @AfterEach
    void tearDown()
    {
        UserService.CloseDatabase();
    }

    public static final String USER_1 = "user1";
    public static final String PRODUCT_1 = "product1";
    public static final String Priceandstock = "10";
    public static final String PRODUCT_2 = "product2";
    public static final String Priceandstock1 = "15";

    @BeforeEach
    void setUp() throws Exception
    {
        FileSystemService.APPLICATION_FOLDER=".test-registration-example";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
    }

    @Start
    void start(Stage Registration) throws Exception
    {
        Parent registration = FXMLLoader.load(getClass().getClassLoader().getResource("register.fxml"));
        Registration.setTitle("Registration");
        Registration.setScene(new Scene(registration, 350, 450));
        Registration.show();
    }

    @Test
    void testbuyproducts(FxRobot robot)
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

        robot.clickOn("#username");
        robot.write(USER_1);
        robot.clickOn("#password");
        robot.write(USER_1);

        robot.clickOn("#signinbutton");

        ProductService.addProduct(PRODUCT_1,Priceandstock,Priceandstock,PRODUCT_1,PRODUCT_1);
        ProductService.addProduct(PRODUCT_2,Priceandstock1,Priceandstock1,PRODUCT_2,PRODUCT_2);
        robot.clickOn("#buyproductsbutton");

        robot.clickOn("#AddToCart");
        assertThat(robot.lookup("#AddToCartMessage").queryText()).hasText("Select an item!");


        robot.type(KeyCode.UP);
        robot.type(KeyCode.ENTER);

        robot.clickOn("#AddToCart");
        assertThat(robot.window("Add to cart")).isShowing();


        robot.clickOn("#backbutton");

        robot.clickOn("#ShoppingCart");
        assertThat(robot.window("Shopping cart")).isShowing();


    }

}