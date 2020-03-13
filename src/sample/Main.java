package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainDialog.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();


    }

    @Override
    public void init() throws Exception {
       ContactData.getInstance().loadContacts();
    }

    @Override
    public void stop() throws Exception {
        ContactData.getInstance().saveContacts();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
