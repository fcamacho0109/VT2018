package module.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import module.toolbar.ControllerToolBar;

public class App {
    ControllerToolBar controller;
    String title = "";
    public Stage stage;

    public ControllerToolBar getController() {
        return controller;
    }

    public void setController(ControllerToolBar controller) {
        this.controller = controller;
    }

    public void setPane(Node node){

        controller.setPane(node);
    }

    public Scene getView(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/res_toolbar/GeneralToolbar.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/res_main/MainScreen.fxml"));
            Scene scene = new Scene(root,1300, 900);

            return scene;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public App(Stage stage){
        this.stage = stage;
    }
}
