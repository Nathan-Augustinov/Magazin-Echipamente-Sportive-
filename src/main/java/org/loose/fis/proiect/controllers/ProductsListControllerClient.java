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
import org.loose.fis.proiect.model.Product;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class ProductsListControllerClient
{
    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;

    private static String username;


    private static ObjectRepository<Product> productRepository;
    private static Nitrite database;



    public static void initDatabase()
    {
        database = Nitrite.builder()
                .filePath(getPathToFile("products.db").toFile())
                .openOrCreate("test", "test");

        productRepository = database.getRepository(Product.class);

    }




    public void set(String username)
    {
        initDatabase();
        for(Product p : productRepository.find())
        {
            list.getItems().add("Name: "+ p.getName() + "         Price: "  + p.getPrice() +"         Stock: " + p.getStock() + "         Category: " + p.getCategory() +"         Company:"+ p.getCompany());

        }
        database.close();
        this.username=username;
    }





    public void handleBackAction() throws Exception
    {
        database.close();
        Stage Back= new Stage();
        Parent back = FXMLLoader.load(getClass().getClassLoader().getResource("ClientPage.fxml"));
        Back.setTitle("Client Page");
        Back.setScene(new Scene(back, 350, 400));
        Back.show();
        cancelProductListPage();

    }


    public void handleBuyAction() throws Exception
    {
        cancelProductListPage();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("BuyProducts.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        BuyProductsController controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (BackButton.getScene().getWindow());
        stage.setTitle("Buy Products");
        stage.setScene(scene);
        stage.show();

    }


    public void cancelProductListPage()
    {
        BackButton.getScene().getWindow().hide();
    }


}
