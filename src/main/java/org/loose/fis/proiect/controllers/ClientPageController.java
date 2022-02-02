package org.loose.fis.proiect.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.*;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.controllers.ProductsListController;
import org.loose.fis.proiect.model.Order;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;


public class ClientPageController
{
    @FXML
    private Button buyproductsbutton;
    @FXML
    private Button seeallyourordersbutton;
    @FXML
    private Button seeproductslistbutton;
    @FXML
    private Button logoutbutton;

    private static String username;


    public void handleBuyProductsAction() throws Exception
    {
        cancelClientPage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("BuyProducts.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        BuyProductsController controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (buyproductsbutton.getScene().getWindow());
        stage.setTitle("Buy Products");
        stage.setScene(scene);
        stage.show();
    }


    public void handleSeeAllYourOrdersAction() throws Exception
    {
        cancelClientPage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SeeAllHisOrders.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        SeeAllHisOrdersController controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (seeallyourordersbutton.getScene().getWindow());
        stage.setTitle("See All Your Orders");
        stage.setScene(scene);
        stage.show();
    }


    public void handleSeeProductsListAction() throws Exception
    {
        cancelClientPage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SeeProductsListClient.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        ProductsListControllerClient controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (seeproductslistbutton.getScene().getWindow());
        stage.setTitle("Products List");
        stage.setScene(scene);
        stage.show();


    }
    public void set(String username)
    {
        this.username=username;
    }

    public void handleLogoutClientAction() throws Exception
    {
        Stage LogoutClient= new Stage();
        Parent logoutclient = FXMLLoader.load(getClass().getClassLoader().getResource("StartPage.fxml"));
        LogoutClient.setTitle("StartPage");
        LogoutClient.setScene(new Scene(logoutclient, 350, 400));
        LogoutClient.show();
        cancelClientPage();
    }
    public void cancelClientPage()
    {
        logoutbutton.getScene().getWindow().hide();
    }
}
