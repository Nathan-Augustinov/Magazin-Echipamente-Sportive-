package org.loose.fis.proiect.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.proiect.model.Product;

import java.lang.String;
import java.util.Objects;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;


public class EditButtonFromEditProductsController {
    @FXML
    private Button backbutton;
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

    private static ObjectRepository<Product> productRepository;
    private static Nitrite database;

    public static void initDatabase()
    {

         database = Nitrite.builder()
                .filePath(getPathToFile("products.db").toFile())
                .openOrCreate("test", "test");

        productRepository = database.getRepository(Product.class);

    }

    public void handleEditAction() throws Exception
    {
        initDatabase();
        for(Product p : productRepository.find())
        {
            if(Objects.equals(namefield.getText(),p.getName()))
            {
                p.setPrice(pricefield.getText());
                p.setStock(stockfield.getText());
                p.setCategory(categoryfield.getText());
                p.setCompany(companyfield.getText());
                productRepository.update(p);
            }
        }
        cancelEditButtonPage();
        database.close();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SeeProductsList.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        ProductsListController controller = loader.getController();
        controller.set();
        Stage stage = (Stage) (backbutton.getScene().getWindow());
        stage.setTitle("Products List");
        stage.setScene(scene);
        stage.show();
    }
    public void handleBackAction() throws Exception
    {
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("EditProducts.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        EditProductsController controller = loader.getController();
        controller.set();
        Stage stage = (Stage) (backbutton.getScene().getWindow());
        stage.setTitle("Edit Products");
        stage.setScene(scene);
        stage.show();

    }

    public void cancelEditButtonPage()
    {
        backbutton.getScene().getWindow().hide();
    }

    public TextField getNamefield() {
        return namefield;
    }

    public void setNamefield(TextField namefield) {
        this.namefield = namefield;
    }

    public TextField getPricefield() {
        return pricefield;
    }

    public void setPricefield(TextField pricefield) {
        this.pricefield = pricefield;
    }

    public TextField getStockfield() {
        return stockfield;
    }

    public void setStockfield(TextField stockfield) {
        this.stockfield = stockfield;
    }

    public TextField getCategoryfield() {
        return categoryfield;
    }

    public void setCategoryfield(TextField categoryfield) {
        this.categoryfield = categoryfield;
    }

    public TextField getCompanyfield() {
        return companyfield;
    }

    public void setCompanyfield(TextField companyfield) {
        this.companyfield = companyfield;
    }

    public void set(String s)
    {

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
        stockfield.setText(p);

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
    }
}
