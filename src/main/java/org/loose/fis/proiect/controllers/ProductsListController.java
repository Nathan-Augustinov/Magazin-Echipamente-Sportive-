package org.loose.fis.proiect.controllers;

import com.sun.glass.ui.Menu;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.model.Product;
import org.loose.fis.proiect.model.User;
import org.loose.fis.proiect.services.ProductService;
import org.loose.fis.proiect.services.UserService;
import java.io.IOException;
import java.util.Objects;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class ProductsListController
{

    @FXML
    private Button BackButton;


    @FXML
    private ListView<String> list = new ListView <String> ();




    private static ObjectRepository <Product> productRepository;
    private static Nitrite database;



    public static void initDatabase()
    {

        database = Nitrite.builder()
                .filePath(getPathToFile("products.db").toFile())
                .openOrCreate("test", "test");

        productRepository = database.getRepository(Product.class);

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


    public void handleBackAction() throws Exception
    {
        database.close();
        Stage Back= new Stage();
        Parent back = FXMLLoader.load(getClass().getClassLoader().getResource("ManagerPage.fxml"));
        Back.setTitle("Manager Page");
        Back.setScene(new Scene(back, 350, 400));
        Back.show();
        cancelProductListPage();
    }



    public void cancelProductListPage()
    {
        BackButton.getScene().getWindow().hide();
    }


    public void handleAddAction() throws Exception
    {
        database.close();
        Stage Add= new Stage();
        Parent add = FXMLLoader.load(getClass().getClassLoader().getResource("AddProducts.fxml"));
        Add.setTitle("Add Product");
        Add.setScene(new Scene(add, 350, 400));
        Add.show();
        cancelProductListPage();
    }

    public void handleEditAction() throws Exception
    {
        database.close();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("EditProducts.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        EditProductsController controller = loader.getController();
        controller.set();
        Stage stage = (Stage) (BackButton.getScene().getWindow());
        stage.setTitle("Edit Products");
        stage.setScene(scene);
        stage.show();

    }

    public void handleDeleteAction() throws Exception
    {

        database.close();
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

}
