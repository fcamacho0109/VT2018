package module.App;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import module.toolbar.ControllerToolBar;

public class App {
    ControllerToolBar toolBar;
    String title = "";

    public ControllerToolBar getToolBar() {
        return toolBar;
    }

    public void setToolBar(ControllerToolBar toolBar) {
        this.toolBar = toolBar;
    }

    public Scene getView(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/res_toolbar/GeneralToolbar.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/res_main/MainScreen.fxml"));
            Scene scene = new Scene(root);
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

    public App(){

    }
}
