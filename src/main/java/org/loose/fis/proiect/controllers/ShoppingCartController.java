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
import org.loose.fis.proiect.model.Product;
import org.loose.fis.proiect.model.User;
import org.loose.fis.proiect.controllers.SignInController.*;

import java.util.ArrayList;
import java.util.Objects;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class ShoppingCartController
{
    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;
    @FXML
    private Button RemoveProduct;
    @FXML
    private Button FinishCommand;
    @FXML
    private Text ShoppingCartMessage;

    private String username;

    private static ObjectRepository<Product> productRepository;

    private static Nitrite database;

    private static ObjectRepository<Product> shoppingRepository;
    private static Nitrite shopping;

    private static ObjectRepository<Order> ordersRepository;
    private static Nitrite orders;

    private static ObjectRepository<User> userRepository;
    private static Nitrite user;

    public static void initUser()
    {
        user = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = user.getRepository(User.class);
    }

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

    public static void initOrders()
    {

        orders = Nitrite.builder()
                .filePath(getPathToFile("orders.db").toFile())
                .openOrCreate("test", "test");

        ordersRepository = orders.getRepository(Order.class);

    }

    public void handleBackAction() throws Exception
    {
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

    public void handleRemoveProductAction() throws Exception
    {
        initDatabase();
        initShopping();
        RemoveProduct.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        if(!RemoveProduct.isDisable())
        {
            String namefield="";
            String pricefield="";
            String stockfield="";
            String categoryfield="";
            String companyfield="";
            String s=list.getSelectionModel().getSelectedItem();

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
            namefield=p;

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
            pricefield=p;

            p = "";
            k=0;l=0;j = 0;t=0;
            for(i=0;i<s.length()-9;i++)
            {
                if (s.charAt(i) == ':' && s.charAt(i + 1) == ' ')
                {
                    t++;
                }
                if(t==2)
                {
                    k=i+2;
                }
                if (s.charAt(i) == ' ' && s.charAt(i+9)=='C' && s.charAt(i+10)=='a')
                    j = i - 1;

            }
            for(x=k+1;x<=j;x++)
                p=p+ s.charAt(x);
            stockfield=p;

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
            categoryfield=p;

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
            companyfield=p;

            int o=0;
            for(Product product : productRepository.find())
            {
                if(Objects.equals(namefield,product.getName()))
                {
                    product.setStock(String.valueOf(Integer.parseInt(product.getStock())+Integer.parseInt(stockfield)));
                    productRepository.update(product);
                    shoppingRepository.remove(product);
                    o=1;
                }

            }
            if(o==0)
            {
                productRepository.insert(new Product(namefield,pricefield,stockfield,categoryfield,companyfield));
                for(Product product : shoppingRepository.find())
                {
                    if(Objects.equals(namefield,product.getName()))
                    {
                        shoppingRepository.remove(product);
                    }
                }

            }

        }
        else
        {
            ShoppingCartMessage.setText("Select an item!");
        }
        database.close();
        shopping.close();

        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("ShoppingCart.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        ShoppingCartController controller = loader.getController();
        controller.set(username);
        Stage stage = (Stage) (RemoveProduct.getScene().getWindow());
        stage.setTitle("Shopping cart");
        stage.setScene(scene);
        stage.show();

    }


    public void handleFinishCommandAction() throws Exception
    {
        initOrders();
        initShopping();
        if(shoppingRepository.size()!=0) {
            ArrayList<Product> order = new ArrayList<Product>();
            for (Product p : shoppingRepository.find()) {
                order.add(p);
            }
            Order ord = new Order(username, order);
            ordersRepository.insert(ord);

            for (Product p : shoppingRepository.find())
                shoppingRepository.remove(p);
            orders.close();
            shopping.close();
            FXMLLoader loader = new FXMLLoader();
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
        else
        {
            ShoppingCartMessage.setText("The list is empty!");
            orders.close();
            shopping.close();
        }
    }

    public void cancelShoppingCartPage()
    {
        BackButton.getScene().getWindow().hide();
    }
    public void set(String username)
    {
        initShopping();
        for(Product p : shoppingRepository.find())
        {
            list.getItems().add("Name: "+ p.getName() + "         Price: "  + p.getPrice() +"         Stock: " + p.getStock() + "         Category: " + p.getCategory() +"         Company: "+ p.getCompany());
        }
        shopping.close();
        this.username=username;
    }
}
