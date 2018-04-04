package module;

import Core.Database;
import Core.Session;
import Core.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import module.calendario.MyCalendar;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    TextField userLoginText;
    @FXML
    PasswordField userLoginPass;
    @FXML
    Button loginBtn;



    @FXML
    private void login_Btn(){

        String user = "";
        String pass = "";
        user = userLoginText.getText().toString();
        pass = userLoginPass.getText().toString();





        if (!(user.equals("") && pass.equals("")) ) {
            userLoginText.setText("");
            userLoginPass.setText("");
            //userLoginText.setPromptText("Minimo 8 caracteres");
            //userLoginPass.setPromptText("Minimo 8 caracteres");

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Aun no se definen acciones para el boton!" +
                    "\nEditar metodo login_Btn() para agregar login \n" +
                    "con user role y password de la BD");


            alert.showAndWait();
        }
        else {
            User user1 = new User(user,"LastName harcoded","33 33 33 33",User.Rol.Admin);
            Session.getInstance().setUser(user1);
            Session.getInstance().getApp().stage.centerOnScreen();
            Stage myStage = (Stage)(loginBtn.getScene().getWindow());
            Session.getInstance().getApp().stage = myStage;
            MyCalendar myCalendar = new MyCalendar();
            Session.getInstance().getApp().setPane(Session.getInstance().getMyCalendar().getCalendar());
            Session.getInstance().getApp().stage.centerOnScreen();
            //Session.getInstance().getApp().getController().setPane();


        }



    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Database.getInstance();
        Database.getInstance().getCalendarios();
        Database.getInstance().getEventos();

        /*comboType.setItems(FXCollections.observableArrayList(
                User.Rol.Admin,
                User.Rol.Usuario,
                User.Rol.Master
        ));
        */
    }
    private void MyCustomEntryDialog(){

    }
}
