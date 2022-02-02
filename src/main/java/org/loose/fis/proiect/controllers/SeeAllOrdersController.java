package org.loose.fis.proiect.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.model.Order;

import java.util.Objects;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;


public class SeeAllOrdersController
{

    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;
    @FXML
    private Button ShowOrder;
    @FXML
    private Text ShowAllOrdersMessage;

    private static ObjectRepository<Order> ordersRepository;
    private static Nitrite orders;

    public static void initOrders()
    {

        orders = Nitrite.builder()
                .filePath(getPathToFile("orders.db").toFile())
                .openOrCreate("test", "test");

        ordersRepository = orders.getRepository(Order.class);

    }


    public void handleBackAction () throws Exception
    {
        Stage Back= new Stage();
        Parent back = FXMLLoader.load(getClass().getClassLoader().getResource("ManagerPage.fxml"));
        Back.setTitle("Manager Page");
        Back.setScene(new Scene(back, 350, 400));
        Back.show();
        cancelSeeAllOrders();
    }

    public void handleShowOrderAction() throws Exception

    {
        ShowOrder.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        if(!ShowOrder.isDisable())
        {
            cancelSeeAllOrders();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("ShowAllOrders.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ShowAllOrdersController controller = loader.getController();
            controller.set(list.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) (ShowOrder.getScene().getWindow());
            stage.setTitle("Show All Orders");
            stage.setScene(scene);
            stage.show();

        }

        else

        {
            ShowAllOrdersMessage.setText("Select an order!");

        }
    }

    public void set ()
    {
        initOrders();
        int i=1;

        for (Order o : ordersRepository.find())
        {

                list.getItems().add("                 Order " + i);
                i++;

        }
        orders.close();
    }


    public void cancelSeeAllOrders()
    {
        BackButton.getScene().getWindow().hide();
    }
}
