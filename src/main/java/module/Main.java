package module;

import Core.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/res_main/MainScreen.fxml"));
        primaryStage.setTitle("Croquetos");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        Session.getInstance().initializaSession(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
