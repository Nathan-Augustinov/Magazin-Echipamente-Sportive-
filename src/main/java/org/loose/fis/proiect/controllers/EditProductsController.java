package org.loose.fis.proiect.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.model.Product;

import java.awt.*;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class EditProductsController
{
    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;
    @FXML
    private Button Edit;
    @FXML
    private Text EditMessage;

    private static ObjectRepository<Product> productRepository;

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
        cancelEditProductsPage();
    }
    public void handleEditAction() throws Exception
    {
            Edit.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
            if(!Edit.isDisable())
            {
                FXMLLoader loader=new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("EditButtonFromEditProducts.fxml"));
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                EditButtonFromEditProductsController controller = loader.getController();
                controller.set(list.getSelectionModel().getSelectedItem());
                Stage stage = (Stage) (BackButton.getScene().getWindow());
                stage.setTitle("Edit the product");
                stage.setScene(scene);
                stage.show();
                database.close();
            }
            else
            {
                EditMessage.setText("Select an item!");
            }



    }
    public void cancelEditProductsPage()
    {
        BackButton.getScene().getWindow().hide();
    }
}
