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

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;
import static org.loose.fis.proiect.controllers.AddToCartController.*;

public class BuyProductsController
{
    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;
    @FXML
    private Button ShoppingCart;
    @FXML
    private Button AddToCart;
    @FXML
    private Text AddToCartMessage;

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

    public void handleBackAction() throws Exception
    {
        Stage Back= new Stage();
        Parent back = FXMLLoader.load(getClass().getClassLoader().getResource("ClientPage.fxml"));
        Back.setTitle("Client Page");
        Back.setScene(new Scene(back, 350, 400));
        Back.show();
        cancelBuyProducts();
    }

    public void handleAddToCartAction() throws Exception
    {
        AddToCart.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        if(!AddToCart.isDisable())
        {
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("AddToCart.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            AddToCartController controller = loader.getController();
            controller.set(list.getSelectionModel().getSelectedItem(),username);
            Stage stage = (Stage) (AddToCart.getScene().getWindow());
            stage.setTitle("Add to cart");
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            AddToCartMessage.setText("Select an item!");
        }

    }

    public void handleShoppingCartAction() throws Exception
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ShoppingCart.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        ShoppingCartController controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (AddToCart.getScene().getWindow());
        stage.setTitle("Shopping cart");
        stage.setScene(scene);
        stage.show();
    }

    public void set(String username)
    {
        initDatabase();
        for(Product p : productRepository.find())
        {
            list.getItems().add("Name: "+ p.getName() + "         Price: "  + p.getPrice() +"         Stock: " + p.getStock() + "         Category: " + p.getCategory() +"         Company: "+ p.getCompany());

        }
        this.username=username;
        database.close();
    }

    public void cancelBuyProducts()
    {
        BackButton.getScene().getWindow().hide();
    }
}
