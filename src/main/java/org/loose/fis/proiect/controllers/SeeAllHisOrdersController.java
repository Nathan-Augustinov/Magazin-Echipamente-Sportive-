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


public class SeeAllHisOrdersController {


    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;
    @FXML
    private Button ShowOrder;
    @FXML
    private Text ShowOrderMessage;

    private static String username;

    private static ObjectRepository<Order> ordersRepository;
    private static Nitrite orders;

    public static void initOrders()
    {

        orders = Nitrite.builder()
                .filePath(getPathToFile("orders.db").toFile())
                .openOrCreate("test", "test");

        ordersRepository = orders.getRepository(Order.class);

    }



    public void handleBackAction() throws Exception
    {
        Stage Client= new Stage();
        Parent client = FXMLLoader.load(getClass().getClassLoader().getResource("ClientPage.fxml"));
        Client.setTitle("ClientPage");
        Client.setScene(new Scene(client, 350, 400));
        Client.show();
        cancelSeeAllHisOrders();

    }


    public void handleShowOrderAction() throws Exception
    {


        ShowOrder.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        if(!ShowOrder.isDisable())
        {
            cancelSeeAllHisOrders();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("ShowOrder.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ShowOrderController controller = loader.getController();
            controller.set(list.getSelectionModel().getSelectedItem(),username);
            Stage stage = (Stage) (ShowOrder.getScene().getWindow());
            stage.setTitle("Show Order");
            stage.setScene(scene);
            stage.show();

        }

        else

        {
            ShowOrderMessage.setText("Select an order!");

        }


    }

    public void set(String username)
    {
        initOrders();
       this.username=username;
        int i=1;
        for (Order o : ordersRepository.find())
        {
           if (Objects.equals(o.getUsername() ,this.username))
            {
                list.getItems().add("                 Order " + i);
                i++;

            }


        }
        orders.close();



    }

    public void cancelSeeAllHisOrders()
    {
        BackButton.getScene().getWindow().hide();
    }

}
