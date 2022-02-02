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

import java.util.Objects;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class AddToCartController
{
    @FXML
    private Button backbutton;
    @FXML
    private Button addtoshoppingcartbutton;
    @FXML
    private TextField namefield=new TextField();
    @FXML
    private TextField pricefield=new TextField();
    @FXML
    private TextField stockfield=new TextField();
    @FXML
    private TextField categoryfield=new TextField();
    @FXML
    private TextField companyfield=new TextField();
    @FXML
    private Text AddToShoppingCartMessage;

    private static ObjectRepository<Product> productRepository;
    private static Nitrite database;

    private static ObjectRepository<Product> shoppingRepository;
    private static Nitrite shopping;

    private String username;

    public static void initDatabase()
    {

        database = Nitrite.builder()
                .filePath(getPathToFile("products.db").toFile())
                .openOrCreate("test", "test");

        productRepository = database.getRepository(Product.class);

    }

    public static void initShopping()
    {

        shopping = Nitrite.builder()
                .filePath(getPathToFile("shoppingcart.db").toFile())
                .openOrCreate("test", "test");

        shoppingRepository = shopping.getRepository(Product.class);

    }

    public void handleBackAction() throws Exception
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("BuyProducts.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        BuyProductsController controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (backbutton.getScene().getWindow());
        stage.setTitle("Buy Products");
        stage.setScene(scene);
        stage.show();
    }

    public void handleAddToShoppingCartAction() throws Exception
    {
        initDatabase();
        initShopping();
        if(stockfield.getText().trim().isEmpty())
        {
            AddToShoppingCartMessage.setText("Complete the stockfield!");
        }
        else
        {
            for(Product p :productRepository.find())
            {
                if(Objects.equals(namefield.getText(), p.getName()))
                {
                    if(Integer.parseInt(stockfield.getText())>Integer.parseInt(p.getStock()))
                    {
                        AddToShoppingCartMessage.setText("Please introduce a lower stock!");
                    }
                    else
                    {
                        p.setStock(String.valueOf(Integer.parseInt(p.getStock())-Integer.parseInt(stockfield.getText())));
                        Product p1=new Product();
                        p1.setName(p.getName());
                        p1.setPrice(p.getPrice());
                        p1.setStock(stockfield.getText());
                        p1.setCategory(p.getCategory());
                        p1.setCompany(p.getCompany());
                        int k=0;
                        for(Product product:shoppingRepository.find())
                        {
                            if(Objects.equals(product.getName(),p1.getName()))
                            {
                                product.setStock(String.valueOf(Integer.parseInt(p1.getStock())+Integer.parseInt(product.getStock())));
                                shoppingRepository.update(product);
                                k=1;
                            }
                        }
                        if(k==0)
                        {
                            shoppingRepository.insert(p1);
                        }

                        productRepository.update(p);
                        if(Integer.parseInt(p.getStock())==0)
                        {
                            productRepository.remove(p);
                        }
                        AddToShoppingCartMessage.setText("Added to shopping cart!");
                    }
                }
            }


        }
        shopping.close();
        database.close();
    }
    public void set(String s,String username)
    {
        initDatabase();
        this.username=username;
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
        namefield.setText(p);
        namefield.setDisable(true);
        p = "";
        k=0;l=0;j = 0;t=0;
        for(i=0;i<s.length()-9;i++)
        {
            if (s.charAt(i) == ':' && s.charAt(i + 1) == ' ')
            {
                t++;
            }
            if(t==1)
            {
                k=i+2;
            }
            if (s.charAt(i) == ' ' && s.charAt(i+9)=='S')
                j = i - 1;

        }
        for(x=k+1;x<=j;x++)
            p=p+ s.charAt(x);
        pricefield.setText(p);
        pricefield.setDisable(true);

        p = "";
        k=0;l=0;j = 0;t=0;
        for(i=0;i<s.length()-9;i++)
        {
            if (s.charAt(i) == ':' && s.charAt(i + 1) == ' ')
            {
                t++;
            }
            if(t==3)
            {
                k=i+2;
            }
            if (s.charAt(i) == ' ' && s.charAt(i+9)=='C' && s.charAt(i+10)=='o')
                j = i - 1;

        }
        for(x=k+1;x<=j;x++)
            p=p+ s.charAt(x);
        categoryfield.setText(p);
        categoryfield.setDisable(true);

        p = "";
        k=0;l=0;j = 0;t=0;
        for(i=0;i<s.length()-1;i++)
        {
            if (s.charAt(i) == ':' && s.charAt(i + 1) == ' ')
            {
                t++;
            }
            if(t==4)
            {
                k=i+2;
            }

        }
        for(x=k+1;x<s.length();x++)
            p=p+ s.charAt(x);
        companyfield.setText(p);
        companyfield.setDisable(true);
        database.close();
    }
}
