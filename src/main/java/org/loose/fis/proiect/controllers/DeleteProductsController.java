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
import org.loose.fis.proiect.model.Product;

import java.util.Objects;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;


public class DeleteProductsController
{
    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;
    @FXML
    private Button Delete;
    @FXML
    private Text DeleteMessage;

    private static ObjectRepository<Product> productRepository;

    private static Nitrite database;


    public static void initDatabase()
    {

        database = Nitrite.builder()
                .filePath(getPathToFile("products.db").toFile())
                .openOrCreate("test", "test");

        productRepository = database.getRepository(Product.class);

    }


    public void handleDeleteAction() throws Exception
    {
        Delete.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        if(!Delete.isDisable())
        {
            initDatabase();
            String s=  list.getSelectionModel().getSelectedItem() ;
            String p = "";
            int i,k=0,l=0,j = 0,t=0;
            for(i=0;i<s.length()-9;i++)
            {
                if (s.charAt(i) == ':' && s.charAt(i + 1) == ' ' && t==0)
                {
                    k = i + 2;
                    t++;
                }
                if (s.charAt(i) == ' ' && s.charAt(i+9)=='P')
                    j = i - 1;

            }
            int x;
            for(x=k;x<=j;x++)
                p=p+ s.charAt(x);
            for (Product pro : productRepository.find())
                if (Objects.equals(pro.getName(),p))
                {
                    productRepository.remove(pro);
                }

            database.close();


            cancelDeleteProductsPage();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("DeleteProducts.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            DeleteProductsController controller = loader.getController();
            controller.set();
            Stage stage = (Stage) (BackButton.getScene().getWindow());
            stage.setTitle("Delete Products");
            stage.setScene(scene);
            stage.show();



        }
        else
        {
            DeleteMessage.setText("Select an item!");
        }

    }



    public void handleBackAction() throws Exception
    {

        database.close();
        Stage Back= new Stage();
        Parent back = FXMLLoader.load(getClass().getClassLoader().getResource("ManagerPage.fxml"));
        Back.setTitle("Manager Page");
        Back.setScene(new Scene(back, 350, 400));
        Back.show();
        cancelDeleteProductsPage();

    }


    public void set()
    {
        initDatabase();
        for(Product p : productRepository.find())
        {
            list.getItems().add("Name: "+ p.getName() + "         Price: "  + p.getPrice() +"         Stock: " + p.getStock() + "         Category: " + p.getCategory() +"         Company: "+ p.getCompany());

        }
        database.close();
    }




    public void cancelDeleteProductsPage()
    {
        BackButton.getScene().getWindow().hide();
    }

}
