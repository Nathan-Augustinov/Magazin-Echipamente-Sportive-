package org.loose.fis.proiect.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.model.Order;

import java.util.Objects;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;


public class ShowOrderController
{

    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;

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



    public void handleBackAction () throws Exception
    {
        cancelSeeAllHisOrders();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SeeAllHisOrders.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        SeeAllHisOrdersController controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (BackButton.getScene().getWindow());
        stage.setTitle("See All Your Orders");
        stage.setScene(scene);
        stage.show();

    }

    public void set (String s,String username)
    {
         this.username = username;
         int i;
         int m=0;


         String p = "";
        for (i=0; i < s.length() ; i++)
        {
            if (s.charAt(i)== ' ' && s.charAt(i+1)!= ' ')
            {
                m = i ;
                break;
            }
        }
        int k=0;
         for (i=m+7;i<s.length();i++)
             p = p + s.charAt(i);
         int t;
         initOrders();
         for (Order o : ordersRepository.find())
         {
             if (Objects.equals(o.getUsername(),this.username))
             {
                 k++;
                 if (k==Integer.parseInt(p))
                 {
                     for (t = 0 ; t<o.getOrder().size();t++)
                     {
                         list.getItems().add(o.getOrder().get(t).toString());
                     }
                 }
             }
         }
         orders.close();
    }

    public void cancelSeeAllHisOrders()
    {
        BackButton.getScene().getWindow().hide();
    }

}
