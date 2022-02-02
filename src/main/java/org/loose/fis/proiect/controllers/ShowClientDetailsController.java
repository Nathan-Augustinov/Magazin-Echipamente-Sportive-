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
import org.loose.fis.proiect.model.User;

import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class ShowClientDetailsController
{
    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;

    private static ObjectRepository<User> userRepository;
    private static Nitrite database;

    public static void initDatabase()
    {
        database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public void handleBackAction() throws  Exception
    {
        cancelShowClientDetails();
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("SeeClientDetails.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        SeeClientDetailsController controller = loader.getController();
        controller.set();
        Stage stage = (Stage) (BackButton.getScene().getWindow());
        stage.setTitle("See Clients Details");
        stage.setScene(scene);
        stage.show();
    }

    public void set(String s)
    {
        initDatabase();
        for(User user : userRepository.find())
        {
            if(user.getUsername().equals(s))
            {
                list.getItems().add("Firstname: " + user.getFirstname());
                list.getItems().add("Lastname: " + user.getLastname());
                list.getItems().add("E-mail: " + user.getEmail());
            }
        }
        database.close();
    }

    public void cancelShowClientDetails()
    {
        BackButton.getScene().getWindow().hide();
    }
}
