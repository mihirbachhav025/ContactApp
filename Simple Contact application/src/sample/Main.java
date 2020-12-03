package sample;

import Datasource.contactData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("ContactsApp");
        Image icon = new Image("/static/Google_Contacts_logo.png");
        primaryStage.getIcons().add(icon);
      primaryStage.setScene(new Scene(root, 370, 600));
      primaryStage.resizableProperty().setValue(Boolean.FALSE);
        primaryStage.show();
    }


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void init() throws Exception {
        try{
            contactData.getInstance().loadContacts();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        try{
            contactData.getInstance().storeContacts();
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
