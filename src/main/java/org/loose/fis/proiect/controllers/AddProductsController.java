package org.loose.fis.proiect.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class AddProductsController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField companyField;
    @FXML
    private Button addproductbutton;
    @FXML
    private Button backbutton;
    @FXML
    private Text AddProductMessage;


    public void handleAddProductAction() throws Exception
    {

        if(nameField.getText().trim().isEmpty())
        {
            AddProductMessage.setText("Name missing!");
        }
        else
        if(priceField.getText().trim().isEmpty())
        {
            AddProductMessage.setText("Price missing!");
        }
        else
        if(stockField.getText().trim().isEmpty())
        {
            AddProductMessage.setText("Stock missing!");
        }
        else
        if(categoryField.getText().trim().isEmpty())
        {
            AddProductMessage.setText("Category missing!");
        }
        else
        if(companyField.getText().trim().isEmpty())
        {
            AddProductMessage.setText("Company missing!");
        }
        else
        {
            if(ProductService.checkProductAlreadyExist(nameField.getText(),stockField.getText())==1)
            {
                AddProductMessage.setText("Stock changed!");
            }
            else
            {
                ProductService.addProduct(nameField.getText(), priceField.getText(), stockField.getText(), categoryField.getText(), companyField.getText());
                AddProductMessage.setText("Product added!");
            }
        }
    }
    public void handleBackAction() throws Exception
    {

        Stage Back= new Stage();
        Parent back = FXMLLoader.load(getClass().getClassLoader().getResource("ManagerPage.fxml"));
        Back.setTitle("ManagerPage");
        Back.setScene(new Scene(back, 350, 400));
        Back.show();
        cancelAddProducts();
    }
    public void cancelAddProducts()
    {
        addproductbutton.getScene().getWindow().hide();
    }
}
