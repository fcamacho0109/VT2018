package module.toolbar;

import Core.Session;
import com.calendarfx.model.Calendar;
import com.calendarfx.view.DateControl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerToolBar implements Initializable{


    @FXML
    VBox vBox;
    @FXML
    TabPane tabPane;

    @FXML
    Tab tab_citas,tab_pacientes,tab_compra_venta,tab_inventario,tab_recetas,tab_usuarios,tab_ayuda;

    @FXML
    Pane pane;

    @FXML
    Button boton_nueva_cita;

    @FXML
    private void nuevaCita(){
        DateControl dateControl = new DateControl() {
            @Override
            public Optional<Calendar> getCalendarAt(double x, double y) {
                return super.getCalendarAt(x, y);
            }
        };
       // LocalDate localdate = new LocalDate();

        vBox.getChildren().add(dateControl );

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Session.getInstance().getApp().setController(this);
        setToolbarByRol();


    }
    private void setToolbarByRol(){
        switch(Session.getInstance().getUser().rol){
            case Admin:
                tabPane.getTabs().remove(tab_recetas);
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
    public void setCalendar(Calendar calendar){
        //pane.resize(1000,1300);
    }
    public void setPane(Node node){

        vBox.getChildren().remove(1);
        vBox.getChildren().add(node);



    }
}
