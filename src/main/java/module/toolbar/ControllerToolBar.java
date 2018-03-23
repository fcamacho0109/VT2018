package module.toolbar;

import Core.Session;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerToolBar implements Initializable{

    @FXML
    TabPane tabPane;

    @FXML
    Tab tab_citas,tab_pacientes,tab_compra_venta,tab_inventario,tab_recetas,tab_usuarios,tab_ayuda;

    @FXML
    AnchorPane anchorpane_workspace;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Session.getInstance().getApp().setToolBar(this);
        setToolbarByRol();


    }
    private void setToolbarByRol(){
        switch(Session.getInstance().getUser().rol){
            case Admin:
                break;
            case Master:
                break;
            case Usuario:
                tabPane.getTabs().remove(tab_inventario);
                tabPane.getTabs().remove(tab_recetas);
                tabPane.getTabs().remove(tab_usuarios);
                break;

        }
    }
}
