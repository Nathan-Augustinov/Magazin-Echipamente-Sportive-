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
import org.loose.fis.proiect.model.User;


import static org.loose.fis.proiect.services.FileSystemService.getPathToFile;

public class SeeClientDetailsController
{
    @FXML
    private ListView<String> list = new ListView <String> ();
    @FXML
    private Button BackButton;
    @FXML
    private Button ShowClientDetails;
    @FXML
    private Text SeeClientDetailsMessage;

    private static ObjectRepository<User> userRepository;
    private static Nitrite database;

    public static void initDatabase()
    {
        database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public void handleBackAction() throws Exception
    {
        Stage Back= new Stage();
        Parent back = FXMLLoader.load(getClass().getClassLoader().getResource("ManagerPage.fxml"));
        Back.setTitle("Manager Page");
        Back.setScene(new Scene(back, 350, 400));
        Back.show();
        cancelSeeClientsDetails();
    }

    public void handleShowClientDetailsAction() throws Exception
    {
        ShowClientDetails.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        if(!ShowClientDetails.isDisable())
        {
            cancelSeeClientsDetails();
            FXMLLoader loader=new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("ShowClientDetails.fxml"));
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            ShowClientDetailsController controller = loader.getController();
            controller.set(list.getSelectionModel().getSelectedItem());
            Stage stage = (Stage) (ShowClientDetails.getScene().getWindow());
            stage.setTitle("Show Client Details");
            stage.setScene(scene);
            stage.show();

        }

        else

        {
            SeeClientDetailsMessage.setText("Select a client!");

        }
    }

    public void set()
    {
        initDatabase();

        for (User user : userRepository.find())
        {
            if(user.getRole().equals("Client"))
            {
                list.getItems().add(user.getUsername());
            }

        }
        database.close();
    }

    public void cancelSeeClientsDetails()
    {
        BackButton.getScene().getWindow().hide();
    }

}
